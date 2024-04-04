package com.mvp.crudmicroservice.user.domain.user;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "user_account_invest")
@Data
public class Account {
    @Id
    @Column(name = "invest_account_id", unique = true, nullable = false)
    private String investAccountId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "telegram_id")
    private Telegram telegram;
}
