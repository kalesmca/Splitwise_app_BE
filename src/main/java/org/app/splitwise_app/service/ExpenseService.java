package org.app.splitwise_app.service;

import org.app.splitwise_app.DTO.CreateExpenseRequest;
import org.app.splitwise_app.DTO.SplitDTO;
import org.app.splitwise_app.entity.*;
import org.app.splitwise_app.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseSplitRepository splitRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public void createExpense(CreateExpenseRequest request) {

        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new RuntimeException("Team not found"));

        User paidBy = userRepository.findById(request.getPaidBy())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Expense expense = Expense.builder()
                .team(team)
                .paidBy(paidBy)
                .amount(request.getAmount())
                .description(request.getDescription())
                .build();

        expense = expenseRepository.save(expense);

        List<ExpenseSplit> splits = new ArrayList<>();

        for (SplitDTO dto : request.getSplits()) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            ExpenseSplit split = ExpenseSplit.builder()
                    .expense(expense)
                    .user(user)
                    .amountOwed(dto.getAmount())
                    .build();

            splits.add(split);
        }

        splitRepository.saveAll(splits);
    }
}