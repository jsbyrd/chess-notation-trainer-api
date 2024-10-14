package com.jsbyrd02.chess_notation_trainer.NnAnalytics;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NnAnalyticsRepository extends JpaRepository<NnAnalytics, String> {
    List<NnAnalytics> findByUsername(String username);
}
