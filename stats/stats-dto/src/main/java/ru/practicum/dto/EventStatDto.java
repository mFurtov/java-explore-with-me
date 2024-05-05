package ru.practicum.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventStatDto {
    private String app;

    private String uri;

    private String ip;

    private String timestamp;
}
