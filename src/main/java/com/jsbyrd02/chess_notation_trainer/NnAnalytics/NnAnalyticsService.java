package com.jsbyrd02.chess_notation_trainer.NnAnalytics;

import com.jsbyrd02.chess_notation_trainer.NnAnalytics.NnAnalytics;
import com.jsbyrd02.chess_notation_trainer.NnAnalytics.NnAnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NnAnalyticsService {

    private final NnAnalyticsRepository repository;

    public NnAnalytics createAnalytics(NnAnalytics nnAnalytics) {
        nnAnalytics.setGameId(UUID.randomUUID().toString());
        nnAnalytics.setDate(Date.valueOf(LocalDate.now()));

        return repository.save(nnAnalytics);
    }

    public List<NnAnalytics> getAllAnalyticsByUsername(String username) {
        return repository.findByUsername(username);
    }

    public Optional<NnAnalytics> updateAnalytics(String gameId, NnAnalytics updatedAnalytics) {
        return repository.findById(gameId).map(existingAnalytics -> {
            existingAnalytics.setScore(updatedAnalytics.getScore());
            existingAnalytics.setTotal(updatedAnalytics.getTotal());
            return repository.save(existingAnalytics);
        });
    }

    public void deleteAnalytics(String gameId) {
        repository.deleteById(gameId);
    }
}
