package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.response.ApiResponse;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse<List<JournalEntry>>> getAllJournalEntriesOfUser(@PathVariable String username) {
        User user = userService.getByUserName(username);
        List<JournalEntry> journalEntriesOfUser = user.getJournalEntries();
        ApiResponse<List<JournalEntry>> resp = new ApiResponse<>();
        if(journalEntriesOfUser!=null && !journalEntriesOfUser.isEmpty()){
            resp.setData(journalEntriesOfUser);
            resp.setMessage("Fetched all the journal entries");
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        } else{
            resp.setData(null);
            resp.setMessage("No journal entries present for the user");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }
    }

    @PostMapping("/{username}")
    public ResponseEntity<ApiResponse<JournalEntry>> addEntry(@RequestBody JournalEntry myEntry, @PathVariable String username){
        ApiResponse<JournalEntry> resp = new ApiResponse<>();
        journalEntryService.saveJournalEntry(myEntry, username);
        resp.setData(myEntry);
        resp.setMessage("Entry added successfully");
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<ApiResponse<Optional<JournalEntry>>> getEntryById(@PathVariable Long myId){
        ApiResponse<Optional<JournalEntry> > resp = new ApiResponse<>();
        Optional<JournalEntry> neededEntry = journalEntryService.getJournalEntryById(myId);
        resp.setData(neededEntry);
        resp.setMessage(String.format("Entry with id %d retrieved successfully", myId));
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<ApiResponse<String>> deleteEntryById(@PathVariable Long myId){
        ApiResponse<String> resp = new ApiResponse<>();
        journalEntryService.deleteJournalEntry(myId);
        resp.setMessage(String.format("Entry with id %d, deleted successfully", myId));
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<ApiResponse<JournalEntry>> updateEntryById(@PathVariable Long myId, @RequestBody JournalEntry myEntry){
        ApiResponse<JournalEntry> resp = new ApiResponse<>();
        JournalEntry updatedJournalEntry = journalEntryService.updateJournalEntry(myId, myEntry);
        if(updatedJournalEntry == null){
            resp.setMessage("This updation is not possible since the entry does not exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        } else{
            resp.setMessage(String.format("Updated the journal entry with title - %d, successfully", updatedJournalEntry.getTitle()));
            resp.setData(updatedJournalEntry);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        }
    }
}
