package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JournalEntryService {
    @Autowired
    private JournalEntryRepositoryInterface journalEntryRepositoryInterface;

    public void saveJournalEntry(JournalEntry journalEntry) {
        journalEntryRepositoryInterface.save(journalEntry);
    }

    public void deleteJournalEntry(long id){
        journalEntryRepositoryInterface.deleteById(id);
    }

    public JournalEntry getJournalEntryById(long id){
        return journalEntryRepositoryInterface.findById(id).orElse(null);
    }

    public List<JournalEntry> getAllJournalEntries(){
        List<JournalEntry> journalEntries = journalEntryRepositoryInterface.findAll();
        return journalEntries;
    }

    public JournalEntry updateJournalEntry(long id, JournalEntry newJournalEntry){
        JournalEntry oldEntry = journalEntryRepositoryInterface.findById(id).orElse(null);
        if(oldEntry != null){
            oldEntry.setContent(newJournalEntry.getContent());
            oldEntry.setTitle(newJournalEntry.getTitle());
            journalEntryRepositoryInterface.save(newJournalEntry);
            return newJournalEntry;
        }
        return null;
    }
}
