package com.mvp.crudmicroservice.user.domain.user;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "telegram")
@Data
public class Telegram implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String telegramId;

    @OneToMany(mappedBy = "telegram", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private List<Account> accounts;
}
