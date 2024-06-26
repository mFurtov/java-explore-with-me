package ru.practicum.users.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "rate", nullable = false)
    private Integer rate;

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
