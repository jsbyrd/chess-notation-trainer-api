package com.jsbyrd02.chess_notation_trainer.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="enabled")
    private boolean enabled;

    @Column(name="date_created")
    private Date dateCreated;

    @Column(name="last_login_date")
    private Date lastLoginDate;
}
