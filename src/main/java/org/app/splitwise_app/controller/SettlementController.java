package org.app.splitwise_app.controller;

import org.app.splitwise_app.DTO.SettlementRequest;
import org.app.splitwise_app.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/settlements")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService settlementService;

    @PostMapping
    public String settle(@RequestBody SettlementRequest request) {
        settlementService.settle(request);
        return "Settlement successful";
    }
}