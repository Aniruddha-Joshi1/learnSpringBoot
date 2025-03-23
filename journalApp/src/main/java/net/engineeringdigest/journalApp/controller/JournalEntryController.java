package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

//    @GetMapping
//    public List<JournalEntry> getAll() {
//        return new ArrayList<>(journalEntries.values());
//    }

    @PostMapping
    public boolean addEntry(@RequestBody JournalEntry myEntry){
        journalEntryService.saveJournalEntry(myEntry);
        return true;
    }

//    @GetMapping("/id/{myId}")
//    public JournalEntry getEntryById(@PathVariable Long myId){
//        return journalEntries.get(myId);
//    }
//
//    @DeleteMapping("/id/{myId}")
//    public JournalEntry deleteEntryById(@PathVariable Long myId){
//        return journalEntries.remove(myId);
//    }
//
//    @PutMapping("/id/{myId}")
//    public JournalEntry updateEntryById(@PathVariable Long myId, @RequestBody JournalEntry myEntry){
//        return journalEntries.put(myId, myEntry);
//    }
}
