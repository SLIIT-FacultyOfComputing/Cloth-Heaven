package org.example.clothheaven.Repository;

import org.example.clothheaven.Model.InventoryLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InventoryLogsRepository extends JpaRepository<InventoryLogs, Long> {
    List<InventoryLogs> findByProductId(String productId);

    List<InventoryLogs> findByProductIdAndLogDateBetween(String productId, LocalDate startDate, LocalDate endDate);

    List<InventoryLogs> findByLogDateBetween(LocalDate startDate, LocalDate endDate);

    List<InventoryLogs> findByChangeTypeAndLogDateBetween(String changeType, LocalDate startDate, LocalDate endDate);
    
    List<InventoryLogs> findByChangeType(String changeType);

    List<InventoryLogs> findByProductIdAndChangeType(String productId, String changeType);

    List<InventoryLogs> findByProductIdAndChangeTypeAndLogDateBetween(String productId, String changeType, LocalDate startDate, LocalDate endDate);
}
