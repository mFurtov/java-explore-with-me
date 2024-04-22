package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dao.StatsServerRepository;
import ru.practicum.dto.StatsDtoRequest;
import ru.practicum.dto.StatsDtoResponse;
import ru.practicum.mapper.StatsMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServerServiceImpl implements StatsServerService {
    private final StatsServerRepository statsServerRepository;

    @Transactional
    @Override
    public void postStats(StatsDtoRequest statsDtoRequest) {
        statsServerRepository.save(StatsMapper.mapFromDto(statsDtoRequest));
    }

    @Transactional
    @Override
    public List<StatsDtoResponse> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (unique) {
            if (uris != null) {
                return statsServerRepository.searchStatsFromDataAndUrisAndUniqueIp(start, end, uris);
            } else {
                return statsServerRepository.searchStatsFromDataAndUniqueIp(start, end);
            }
        } else {
            if (uris != null) {
                return statsServerRepository.searchStatsFromDataAndUris(start, end, uris);
            } else {
                return statsServerRepository.searchStatsFromData(start, end);
            }
        }
    }
}
