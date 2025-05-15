package net.engineeringdigest.journalApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// Entity annotation => class to be mapped to a table
@Entity
// If the table name in DB is same as class name, then no need for @Table annotaion
@Table(name = "journalentry")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
