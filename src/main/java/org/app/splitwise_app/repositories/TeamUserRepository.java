package org.app.splitwise_app.repositories;

import org.app.splitwise_app.entity.TeamUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamUserRepository extends JpaRepository<TeamUser, Long> {
    @Query("SELECT tu FROM TeamUser tu WHERE tu.team.teamId = :teamId")
    List<TeamUser> findMembersByTeamId(@Param("teamId") Long teamId);
}
