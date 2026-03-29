package org.app.splitwise_app.controller;


import org.app.splitwise_app.DTO.CreateExpenseRequest;
import org.app.splitwise_app.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public String createExpense(@RequestBody CreateExpenseRequest request) {
        expenseService.createExpense(request);
        return "Expense created successfully";
    }
}
