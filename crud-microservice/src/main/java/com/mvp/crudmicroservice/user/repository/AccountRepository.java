package com.mvp.crudmicroservice.user.repository;

import com.mvp.crudmicroservice.user.domain.user.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByUser_Id(Long userId);
}
