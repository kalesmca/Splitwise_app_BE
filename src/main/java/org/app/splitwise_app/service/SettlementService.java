package org.app.splitwise_app.service;

import org.app.splitwise_app.DTO.SettlementRequest;
import org.app.splitwise_app.entity.*;
import org.app.splitwise_app.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettlementService {

    private final SettlementRepository settlementRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public void settle(SettlementRequest request) {

        User from = userRepository.findById(request.getFromUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        User to = userRepository.findById(request.getToUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new RuntimeException("Team not found"));

        Settlement settlement = Settlement.builder()
                .fromUser(from)
                .toUser(to)
                .team(team)
                .amount(request.getAmount())
                .build();

        settlementRepository.save(settlement);
    }
}