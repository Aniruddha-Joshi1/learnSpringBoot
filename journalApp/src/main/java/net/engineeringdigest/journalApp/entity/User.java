package net.engineeringdigest.journalApp.entity;

import com.sun.istack.NotNull;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @Column(name = "user_id")
    private long id;

    @Column(name="username", unique = true, nullable = false)
    private String username;

    @NotNull
    @Column(name="password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user_of_journal")
    private List<JournalEntry> journalEntries = new ArrayList<>();

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
