package ru.practicum.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.events.model.State;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto {

    private String created;

    private int event;

    private int id;

    private int requester;

    private State status;

    public ParticipationRequestDto(LocalDateTime created, int event, int id, int requester, State status) {
        this.created = formatData(created);
        this.event = event;
        this.id = id;
        this.requester = requester;
        this.status = status;
    }

    private String formatData(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
}
