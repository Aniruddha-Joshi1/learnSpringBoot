package net.engineeringdigest.journalApp.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @Column(name = "user_id")
    private long id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @OneToMany(mappedBy = "user_of_journal")
    private List<JournalEntry> journalEntries;

    public User(){

    }

    public User(long id, String password, String username, List<JournalEntry> journalEntries) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.journalEntries = journalEntries;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<JournalEntry> getJournalEntries() {
        return journalEntries;
    }

    public void setJournalEntries(List<JournalEntry> journalEntries) {
        this.journalEntries = journalEntries;
    }
}
