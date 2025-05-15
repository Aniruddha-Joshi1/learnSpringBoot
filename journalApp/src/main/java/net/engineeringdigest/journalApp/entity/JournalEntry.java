package net.engineeringdigest.journalApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

// Entity annotation => class to be mapped to a table
@Entity
// If the table name in DB is same as class name, then no need for @Table annotaion
@Table(name = "journalentry")
public class JournalEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
     private long id;

     private String title;

     @Column(name = "journalcontent")
     private String content;

     @ManyToOne
     @JoinColumn(name = "user_id", nullable = false)
     @JsonIgnore
     private User user_of_journal;

    public JournalEntry() {
    }

    public JournalEntry(long id, String title, String content, User user_of_journal) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user_of_journal = user_of_journal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser_of_journal() {
        return user_of_journal;
    }

    public void setUser_of_journal(User user_of_journal) {
        this.user_of_journal = user_of_journal;
    }
}
