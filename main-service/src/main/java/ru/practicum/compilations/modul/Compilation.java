package ru.practicum.compilations.modul;

import lombok.*;
import ru.practicum.events.model.Event;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "compilations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "pinned")
    private boolean pinned;

    @ManyToMany
    @JoinTable(
            name = "compilation_event",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> events;

    public Compilation(String title, boolean pinned, List<Event> events) {
        this.title = title;
        this.pinned = pinned;
        this.events = events;
    }
}
