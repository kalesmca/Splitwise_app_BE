package org.app.splitwise_app.DTO;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SettlementRequest {
    private Long fromUserId;
    private Long toUserId;
    private Long teamId;
    private BigDecimal amount;
}
