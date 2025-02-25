package com.workintech.bankauth.repository;

import com.workintech.bankauth.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}
