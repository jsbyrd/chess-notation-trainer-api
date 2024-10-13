package com.jsbyrd02.chess_notation_trainer.MmAnalytics;

import com.jsbyrd02.chess_notation_trainer.MmAnalytics.MmAnalytics;
import com.jsbyrd02.chess_notation_trainer.MmAnalytics.MmAnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MmAnalyticsService {

    private final MmAnalyticsRepository repository;

    public MmAnalytics createAnalytics(MmAnalytics mmAnalytics) {
        mmAnalytics.setDate(Date.valueOf(LocalDate.now()));
        return repository.save(mmAnalytics);
    }

    public List<MmAnalytics> getAllAnalyticsByUsername(String username) {
        return repository.findByUsername(username);
    }

    public Optional<MmAnalytics> updateAnalytics(String gameId, MmAnalytics updatedAnalytics) {
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
