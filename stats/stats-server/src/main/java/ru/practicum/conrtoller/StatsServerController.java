package ru.practicum.conrtoller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Create;
import ru.practicum.dto.StatsDtoRequest;
import ru.practicum.dto.StatsDtoResponse;
import ru.practicum.service.StatsServerService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatsServerController {
    private final StatsServerService statsServerService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void postStats(@RequestBody @Validated(Create.class) StatsDtoRequest statsDtoRequest) {
        statsServerService.postStats(statsDtoRequest);
    }

    @GetMapping("/stats")
    public List<StatsDtoResponse> getStats(@RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start, @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end, @RequestParam(value = "uris", required = false) List<String> uris, @RequestParam(value = "unique", defaultValue = "false") boolean unique) {
        return statsServerService.getStats(start, end, uris, unique);
    }
}
