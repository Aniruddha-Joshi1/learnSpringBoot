package net.engineeringdigest.journalApp.entity;

import javax.persistence.*;

// Entity annotation => class to be mapped to a table
@Entity
// If the table name in DB is same as class name, then no need for @Table annotaion
@Table(name = "journalentry")
public class JournalEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private long id;

     private String title;

     @Column(name = "journalcontent")
     private String content;

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
}
