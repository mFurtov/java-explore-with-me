package ru.practicum.events.dto;

import lombok.Data;

import java.util.List;

@Data
public class EventRequestStatusUpdateRequestDto {
    private List<Integer> requestIds;

    private StateActionEventUpdate status;

    public enum StateActionEventUpdate {
        CONFIRMED,
        REJECTED;
    }

}
