package ru.practicum.service;

import ru.practicum.dto.StatsDtoRequest;
import ru.practicum.dto.StatsDtoResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsServerService {
    void postStats(StatsDtoRequest statsDtoRequest);

    List<StatsDtoResponse> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
