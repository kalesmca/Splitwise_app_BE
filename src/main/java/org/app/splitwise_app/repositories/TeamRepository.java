package org.app.splitwise_app.repositories;

import org.app.splitwise_app.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
