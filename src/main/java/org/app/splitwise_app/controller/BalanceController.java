package org.app.splitwise_app.controller;

import org.app.splitwise_app.DTO.BalanceDTO;
import org.app.splitwise_app.DTO.SettlementDTO;
import org.app.splitwise_app.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @GetMapping("/{teamId}/balances")
    public List<BalanceDTO> getBalances(@PathVariable Long teamId) {
        return balanceService.getBalances(teamId);
    }

    @GetMapping("/{teamId}/simplify")
    public List<SettlementDTO> simplify(@PathVariable Long teamId) {
        return balanceService.simplifyDebts(teamId);
    }
}