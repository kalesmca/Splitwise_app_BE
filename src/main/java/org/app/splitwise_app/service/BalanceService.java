package org.app.splitwise_app.service;

import org.app.splitwise_app.DTO.BalanceDTO;
import org.app.splitwise_app.DTO.SettlementDTO;
import org.app.splitwise_app.entity.*;
import org.app.splitwise_app.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseSplitRepository splitRepository;
    private final TeamUserRepository teamUserRepository;

    public List<BalanceDTO> getBalances(Long teamId) {

        // Step 1: Get all team members
        List<TeamUser> members = teamUserRepository.findMembersByTeamId(teamId);

        Map<Long, BigDecimal> balanceMap = new HashMap<>();
        Map<Long, String> userNames = new HashMap<>();

        // Initialize balances
        for (TeamUser member : members) {
            Long userId = member.getUser().getUserId();
            balanceMap.put(userId, BigDecimal.ZERO);
            userNames.put(userId, member.getUser().getName());
        }

        // Step 2: Get all expenses
        List<Expense> expenses = expenseRepository.findByTeam_TeamId(teamId);

        for (Expense expense : expenses) {

            Long paidBy = expense.getPaidBy().getUserId();
            BigDecimal amount = expense.getAmount();

            // Add to payer
            balanceMap.put(paidBy,
                    balanceMap.get(paidBy).add(amount));

            // Step 3: Subtract splits
            List<ExpenseSplit> splits =
                    splitRepository.findByExpense_ExpenseId(expense.getExpenseId());

            for (ExpenseSplit split : splits) {
                Long userId = split.getUser().getUserId();
                BigDecimal owed = split.getAmountOwed();

                balanceMap.put(userId,
                        balanceMap.get(userId).subtract(owed));
            }
        }

        // Step 4: Convert to DTO
        List<BalanceDTO> result = new ArrayList<>();

        for (Long userId : balanceMap.keySet()) {
            result.add(new BalanceDTO(
                    userId,
                    userNames.get(userId),
                    balanceMap.get(userId)
            ));
        }

        return result;
    }

    public List<SettlementDTO> simplifyDebts(Long teamId) {

        List<BalanceDTO> balances = getBalances(teamId);

        PriorityQueue<BalanceDTO> creditors =
                new PriorityQueue<>((a, b) -> b.getBalance().compareTo(a.getBalance()));

        PriorityQueue<BalanceDTO> debtors =
                new PriorityQueue<>(Comparator.comparing(BalanceDTO::getBalance));

        // Separate creditors & debtors
        for (BalanceDTO b : balances) {
            if (b.getBalance().compareTo(BigDecimal.ZERO) > 0) {
                creditors.add(b);
            } else if (b.getBalance().compareTo(BigDecimal.ZERO) < 0) {
                debtors.add(b);
            }
        }

        List<SettlementDTO> result = new ArrayList<>();

        while (!creditors.isEmpty() && !debtors.isEmpty()) {

            BalanceDTO creditor = creditors.poll();
            BalanceDTO debtor = debtors.poll();

            BigDecimal minAmount =
                    creditor.getBalance().min(debtor.getBalance().abs());

            result.add(new SettlementDTO(
                    debtor.getUserName(),
                    creditor.getUserName(),
                    minAmount
            ));

            // Update balances
            creditor.setBalance(creditor.getBalance().subtract(minAmount));
            debtor.setBalance(debtor.getBalance().add(minAmount));

            if (creditor.getBalance().compareTo(BigDecimal.ZERO) > 0) {
                creditors.add(creditor);
            }

            if (debtor.getBalance().compareTo(BigDecimal.ZERO) < 0) {
                debtors.add(debtor);
            }
        }

        return result;
    }
}