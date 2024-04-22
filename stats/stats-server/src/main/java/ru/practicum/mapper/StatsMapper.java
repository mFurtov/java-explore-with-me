package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.StatsDtoRequest;
import ru.practicum.model.Stats;

@UtilityClass
public class StatsMapper {
    public Stats mapFromDto(StatsDtoRequest statsDtoRequest) {
        return new Stats(statsDtoRequest.getApp(), statsDtoRequest.getUri(), statsDtoRequest.getIp(), statsDtoRequest.getTimestamp());
    }
}
