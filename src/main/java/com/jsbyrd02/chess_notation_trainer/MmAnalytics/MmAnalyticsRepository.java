package com.jsbyrd02.chess_notation_trainer.MmAnalytics;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MmAnalyticsRepository extends JpaRepository<MmAnalytics, String> {
    List<MmAnalytics> findByUsername(String username);
}
