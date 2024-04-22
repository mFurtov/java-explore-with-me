package ru.practicum.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.dao.StatsServerRepository;
import ru.practicum.dto.StatsDtoRequest;
import ru.practicum.dto.StatsDtoResponse;
import ru.practicum.model.Stats;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DirtiesContext
class StatsServerServiceImplTest {
    @Mock
    StatsServerRepository statsServerRepository;
    @Autowired
    private StatsServerService service;

    @BeforeEach
    public void setUp() {
        service = new StatsServerServiceImpl(statsServerRepository);
    }


    @Test
    void postStats() {
        StatsDtoRequest statsDtoRequest = new StatsDtoRequest("test", "/test", "192.0.0.1", "1111-11-11 11:11:11");
        doAnswer(invocation -> {
            Stats stats = invocation.getArgument(0);
            return null;
        }).when(statsServerRepository).save(any(Stats.class));

        service.postStats(statsDtoRequest);

        verify(statsServerRepository).save(any(Stats.class));

    }

    @Test
    void getStats() {
        LocalDateTime start = LocalDateTime.now().minusDays(7);
        LocalDateTime end = LocalDateTime.now();
        List<String> uris = List.of("/test");
        Boolean unique = true;
        List<StatsDtoResponse> expectedResponse = Collections.singletonList(new StatsDtoResponse("ewm-main-service", "/events/1", 6));

        when(statsServerRepository.searchStatsFromDataAndUrisAndUniqueIp(start, end, uris)).thenReturn(expectedResponse);
        when(statsServerRepository.searchStatsFromDataAndUniqueIp(start, end)).thenReturn(expectedResponse);
        when(statsServerRepository.searchStatsFromDataAndUris(start, end, uris)).thenReturn(expectedResponse);
        when(statsServerRepository.searchStatsFromData(start, end)).thenReturn(expectedResponse);

        List<StatsDtoResponse> result1 = service.getStats(start, end, uris, unique);
        List<StatsDtoResponse> result2 = service.getStats(start, end, null, unique);
        List<StatsDtoResponse> result3 = service.getStats(start, end, uris, false);
        List<StatsDtoResponse> result4 = service.getStats(start, end, null, false);

        assertEquals(expectedResponse, result1);
        assertEquals(expectedResponse, result2);
        assertEquals(expectedResponse, result3);
        assertEquals(expectedResponse, result4);
        verify(statsServerRepository).searchStatsFromDataAndUrisAndUniqueIp(start, end, uris);
        verify(statsServerRepository).searchStatsFromDataAndUniqueIp(start, end);
        verify(statsServerRepository).searchStatsFromDataAndUris(start, end, uris);
        verify(statsServerRepository).searchStatsFromData(start, end);
    }

}