package org.app.splitwise_app.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SettlementDTO {
    private String fromUser;
    private String toUser;
    private BigDecimal amount;
}