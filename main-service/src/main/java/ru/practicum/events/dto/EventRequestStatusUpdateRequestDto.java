package ru.practicum.events.dto;

import lombok.Data;

import java.util.List;

@Data
public class EventRequestStatusUpdateRequestDto {
    List<Integer> requestIds;

    StateActionEventUpdate status;

    public enum StateActionEventUpdate {
        CONFIRMED,
        REJECTED;
    }

}
