package net.engineeringdigest.journalApp.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name="username", unique = true, nullable = false)
    private String username;

    @NotNull
    @Column(name="password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user_of_journal")
    private List<JournalEntry> journalEntries = new ArrayList<>();

    // Used for authorization
    @Column(name="roles")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
}
