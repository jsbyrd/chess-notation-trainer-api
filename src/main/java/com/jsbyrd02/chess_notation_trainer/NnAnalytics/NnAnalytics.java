package com.jsbyrd02.chess_notation_trainer.NnAnalytics;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "nn_analytics")
public class NnAnalytics {
    // Note: NnAnalytics stands for "Name that Notation Analytics"

    @Id
    @Column(name="game_id")
    private String gameId = UUID.randomUUID().toString();

    @Column(name="username")
    private String username;

    @Column(name="date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name="score")
    private int score;

    @Column(name="total")
    private int total;

    @Column(name="skips")
    private int skips;
}