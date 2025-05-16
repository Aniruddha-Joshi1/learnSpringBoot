package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.response.ApiResponse;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Authenticated endpoint
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

    @GetMapping()
    public ResponseEntity<ApiResponse<List<JournalEntry>>> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
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

    @PostMapping()
    public ResponseEntity<ApiResponse<JournalEntry>> addEntry(@RequestBody JournalEntry myEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        ApiResponse<JournalEntry> resp = new ApiResponse<>();
        journalEntryService.saveJournalEntry(myEntry, username);
        resp.setData(myEntry);
        resp.setMessage("Entry added successfully");
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<ApiResponse<Optional<JournalEntry>>> getEntryById(@PathVariable Long myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getByUserName(username);
        ApiResponse<Optional<JournalEntry> > resp = new ApiResponse<>();
        List<JournalEntry> journalEntriesWithMatchingId = user.getJournalEntries().stream()
                .filter(journalEntry -> journalEntry.getId().equals(myId))
                .collect(Collectors.toList());
        // Only if the ID is accessible to the user, you can get the response
        if(!journalEntriesWithMatchingId.isEmpty()){
            Optional<JournalEntry> neededEntry = journalEntryService.getJournalEntryById(myId);
            resp.setData(neededEntry);
            resp.setMessage(String.format("Entry with id %d retrieved successfully", myId));
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        }
        resp.setMessage("The entered id is wrong");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<ApiResponse<String>> deleteEntryById(@PathVariable Long myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        ApiResponse<String> resp = new ApiResponse<>();
        User user = userService.getByUserName(username);
        List<JournalEntry> journalEntryWithMatchingId = user.getJournalEntries().stream()
                .filter(journalEntry -> journalEntry.getId().equals(myId))
                .collect(Collectors.toList());
        if(!journalEntryWithMatchingId.isEmpty()){
            journalEntryService.deleteJournalEntry(myId);
            resp.setMessage(String.format("Entry with id %d, deleted successfully", myId));
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        }
        resp.setMessage("You don't have permission to delete");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<ApiResponse<JournalEntry>> updateEntryById(@PathVariable Long myId, @RequestBody JournalEntry myEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getByUserName(username);
        ApiResponse<JournalEntry> resp = new ApiResponse<>();
        List<JournalEntry> journalEntryWithMatchingId = user.getJournalEntries().stream()
                .filter(journalEntry -> journalEntry.getId().equals(myId))
                .collect(Collectors.toList());
        if(!journalEntryWithMatchingId.isEmpty()){
            JournalEntry updatedJournalEntry = journalEntryService.updateJournalEntry(myId, myEntry);
            resp.setMessage(String.format("Updated the journal entry with title - %s, successfully", updatedJournalEntry.getTitle()));
            resp.setData(updatedJournalEntry);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        }
        resp.setMessage("This updation is not possible since the entry does not exist");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }
}
