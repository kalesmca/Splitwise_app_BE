package org.app.splitwise_app.repositories;

import org.app.splitwise_app.entity.ExpenseSplit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseSplitRepository extends JpaRepository<ExpenseSplit, Long> {
    List<ExpenseSplit> findByExpense_ExpenseId(Long expenseId);

}
