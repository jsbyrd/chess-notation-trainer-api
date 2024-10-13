package com.jsbyrd02.chess_notation_trainer.MmAnalytics;

import com.jsbyrd02.chess_notation_trainer.MmAnalytics.MmAnalytics;
import com.jsbyrd02.chess_notation_trainer.MmAnalytics.MmAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mm-analytics")
@RequiredArgsConstructor
public class MmAnalyticsController {
    // Note: MmAnalytics means Make Move Analytics

    private final MmAnalyticsService service;

    @PostMapping
    public ResponseEntity<MmAnalytics> createAnalytics(@RequestBody MmAnalytics mmAnalytics) {
        MmAnalytics createdAnalytics = service.createAnalytics(mmAnalytics);
        return ResponseEntity.ok(createdAnalytics);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<MmAnalytics>> getAnalyticsByUsername(@PathVariable String username) {
        List<MmAnalytics> analytics = service.getAllAnalyticsByUsername(username);
        return ResponseEntity.ok(analytics);
    }

    @PutMapping("/{gameId}")
    public ResponseEntity<MmAnalytics> updateAnalytics(
            @PathVariable String gameId, @RequestBody MmAnalytics updatedAnalytics) {
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
