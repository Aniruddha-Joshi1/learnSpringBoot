package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.response.ApiResponse;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<JournalEntry>>> getAllJournalEntries() {
        ApiResponse<List<JournalEntry>> resp = new ApiResponse<>();
        resp.setData(journalEntryService.getAllJournalEntries());
        resp.setMessage("Fetched all the journal entries");
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<JournalEntry>> addEntry(@RequestBody JournalEntry myEntry){
        ApiResponse<JournalEntry> resp = new ApiResponse<>();
        journalEntryService.saveJournalEntry(myEntry);
        resp.setData(myEntry);
        resp.setMessage("Entry added successfully");
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<ApiResponse<JournalEntry>> getEntryById(@PathVariable Long myId){
        ApiResponse<JournalEntry> resp = new ApiResponse<>();
        JournalEntry neededEntry = journalEntryService.getJournalEntryById(myId);
        resp.setData(neededEntry);
        resp.setMessage(String.format("Entry with id {} retrieved successfully", myId));
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<ApiResponse<String>> deleteEntryById(@PathVariable Long myId){
        ApiResponse<String> resp = new ApiResponse<>();
        journalEntryService.deleteJournalEntry(myId);
        resp.setMessage(String.format("Entry with id {}, deleted successfully", myId));
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
            resp.setMessage(String.format("Updated the journal entry with title - {}, successfully", updatedJournalEntry.getTitle()));
            resp.setData(updatedJournalEntry);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        }
    }
}
