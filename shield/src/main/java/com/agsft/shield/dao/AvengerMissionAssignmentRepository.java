package com.agsft.shield.dao;

import com.agsft.shield.entitiy.AvengerMissionAssignment;
import com.agsft.shield.entitiy.Avengers;
import com.agsft.shield.entitiy.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AvengerMissionAssignmentRepository extends JpaRepository<AvengerMissionAssignment, Long> {
    long countByMissionAndIsDelete(Mission mission, boolean isDeleted);
    Optional<AvengerMissionAssignment> findByMissionAndAvengers(Mission mission, Avengers avengers);
    long countByAvengersAndIsDelete(Avengers avengers, boolean isDeleted);

    @Query("SELECT AMA.avengers FROM AvengerMissionAssignment AMA WHERE AMA.mission =:m")
    List<Avengers> findAvengers(@Param("m") Mission mission);
}
