package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatsDtoResponse {
    private String app;
    private String uri;
    private long hits;
}
