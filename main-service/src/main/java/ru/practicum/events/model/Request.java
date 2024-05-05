package ru.practicum.events.model;

import lombok.*;
import ru.practicum.users.model.User;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Request {

    @Column(name = "created")
    private LocalDateTime created = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event")
    @ToString.Exclude
    private Event event;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester")
    @ToString.Exclude
    private User requester;

    @Enumerated(EnumType.STRING)
    private State status = State.PENDING;

    public Request(Event event, User requester) {
        this.event = event;
        this.requester = requester;
    }

    public Request(Event event, User requester, State status) {
        this.event = event;
        this.requester = requester;
        this.status = status;
    }
}
