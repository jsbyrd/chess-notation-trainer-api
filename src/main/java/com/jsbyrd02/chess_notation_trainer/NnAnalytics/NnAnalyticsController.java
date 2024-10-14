package com.jsbyrd02.chess_notation_trainer.NnAnalytics;

import com.jsbyrd02.chess_notation_trainer.NnAnalytics.NnAnalytics;
import com.jsbyrd02.chess_notation_trainer.NnAnalytics.NnAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nn-analytics")
@RequiredArgsConstructor
public class NnAnalyticsController {
    // Note: NnAnalytics means Name Notation Analytics

    private final NnAnalyticsService service;

    @PostMapping
    public ResponseEntity<NnAnalytics> createAnalytics(@RequestBody NnAnalytics nnAnalytics) {
        NnAnalytics createdAnalytics = service.createAnalytics(nnAnalytics);
        return ResponseEntity.ok(createdAnalytics);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<NnAnalytics>> getAnalyticsByUsername(@PathVariable String username) {
        List<NnAnalytics> analytics = service.getAllAnalyticsByUsername(username);
        return ResponseEntity.ok(analytics);
    }

    @PutMapping("/{gameId}")
    public ResponseEntity<NnAnalytics> updateAnalytics(
            @PathVariable String gameId, @RequestBody NnAnalytics updatedAnalytics) {
        return service.updateAnalytics(gameId, updatedAnalytics)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<Void> deleteAnalytics(@PathVariable String gameId) {
        service.deleteAnalytics(gameId);
        return ResponseEntity.noContent().build();
    }
}
