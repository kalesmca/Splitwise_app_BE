package org.app.splitwise_app.repositories;

import org.app.splitwise_app.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByTeam_TeamId(Long teamId);
}
