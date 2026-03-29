package org.app.splitwise_app.repositories;

import org.app.splitwise_app.entity.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {
}
