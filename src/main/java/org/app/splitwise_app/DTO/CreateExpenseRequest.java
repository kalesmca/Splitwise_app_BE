package org.app.splitwise_app.DTO;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateExpenseRequest {
    private Long teamId;
    private Long paidBy;
    private BigDecimal amount;
    private String description;
    private List<SplitDTO> splits;
}