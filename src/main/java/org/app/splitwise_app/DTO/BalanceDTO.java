package org.app.splitwise_app.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BalanceDTO {
    private Long userId;
    private String userName;
    private BigDecimal balance;
}
