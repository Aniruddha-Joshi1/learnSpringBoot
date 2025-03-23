package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JournalEntryService {
    @Autowired
    private JournalEntryRepositoryInterface journalEntryRepositoryInterface;

    public void saveJournalEntry(JournalEntry journalEntry) {
        journalEntryRepositoryInterface.save(journalEntry);
    }

    public void deleteJournalEntry(JournalEntry journalEntry){
        journalEntryRepositoryInterface.delete(journalEntry);
    }
}
