package ru.practicum.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "stats")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Stats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "app", nullable = false)
    private String app;
    @Column(name = "uri", nullable = false)
    private String uri;
    @Column(name = "ip", nullable = false)
    private String ip;
    @Column(name = "data_time")
    private LocalDateTime timestamp;

    public Stats(String app, String uri, String ip, String dataTime) {
        this.app = app;
        this.uri = uri;
        this.ip = ip;
        this.timestamp = formatterData(dataTime);
    }

    private LocalDateTime formatterData(String dataTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dataTime, formatter);
    }

}
