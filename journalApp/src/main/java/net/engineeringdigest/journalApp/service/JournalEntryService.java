package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class JournalEntryService {
    @Autowired
    private JournalEntryRepositoryInterface journalEntryRepositoryInterface;

    @Autowired
    private UserService userService;

    public void saveJournalEntry(JournalEntry journalEntry, String username) {
        try {
            User user = userService.getByUserName(username);
            journalEntry.setUser_of_journal(user);
            // To save the journal entry with the user
            JournalEntry savedEntry = journalEntryRepositoryInterface.save(journalEntry);
            user.getJournalEntries().add(savedEntry);
            // To save the updated journal entries in User DB
            userService.saveUser(user);
        } catch (Exception e){
            System.out.println("Exception occured - " + e.getMessage());
        }
    }

    public void deleteJournalEntry(long id){
        journalEntryRepositoryInterface.deleteById(id);
    }

    public Optional<JournalEntry> getJournalEntryById(long id){
        return journalEntryRepositoryInterface.findById(id);
    }

    public List<JournalEntry> getAllJournalEntries(){
        List<JournalEntry> journalEntries = journalEntryRepositoryInterface.findAll();
        return journalEntries;
    }

    public JournalEntry updateJournalEntry(Long id, JournalEntry newJournalEntry){
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
