package ru.practicum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.dto.StatsDtoResponse;
import ru.practicum.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsServerRepository extends JpaRepository<Stats, Integer> {
    @Query("select new ru.practicum.dto.StatsDtoResponse(s.app, s.uri, count(s.uri) as hit) from Stats s where s.timestamp >= ?1 and s.timestamp <= ?2 GROUP BY s.uri, s.app order by hit desc")
    List<StatsDtoResponse> searchStatsFromData(LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.dto.StatsDtoResponse(s.app, s.uri, count(s.uri) as hit) from Stats s where s.timestamp >= ?1 and s.timestamp <= ?2 and s.uri in (?3) GROUP BY s.uri, s.app order by hit desc")
    List<StatsDtoResponse> searchStatsFromDataAndUris(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select new ru.practicum.dto.StatsDtoResponse(s.app, s.uri, count(distinct s.ip) as hit) from Stats s where s.timestamp >= ?1 and s.timestamp <= ?2 and s.uri in (?3) GROUP BY s.uri, s.app order by hit desc")
    List<StatsDtoResponse> searchStatsFromDataAndUrisAndUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select new ru.practicum.dto.StatsDtoResponse(s.app, s.uri, count(distinct s.ip) as hit) from Stats s where s.timestamp >= ?1 and s.timestamp <= ?2 GROUP BY s.uri, s.app order by hit desc")
    List<StatsDtoResponse> searchStatsFromDataAndUniqueIp(LocalDateTime start, LocalDateTime end);
}
