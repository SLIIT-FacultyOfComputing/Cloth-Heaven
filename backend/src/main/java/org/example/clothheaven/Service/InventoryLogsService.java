package org.example.clothheaven.Service;

import org.example.clothheaven.DTO.InventoryLogsCreateDTO;
import org.example.clothheaven.DTO.InventoryLogsResponseDTO;
import org.example.clothheaven.Exception.EmptyLogsException;
import org.example.clothheaven.Exception.InventoryLogNotFoundException;
import org.example.clothheaven.Mapper.InventoryLogsMapper;
import org.example.clothheaven.Model.InventoryLogs;
import org.example.clothheaven.Repository.InventoryLogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryLogsService {

    private final InventoryLogsRepository inventoryLogsRepository;
    private final InventoryLogsMapper inventoryLogsMapper;

    @Autowired
    public InventoryLogsService(InventoryLogsRepository inventoryLogsRepository, InventoryLogsMapper inventoryLogsMapper) {
        this.inventoryLogsRepository = inventoryLogsRepository;
        this.inventoryLogsMapper = inventoryLogsMapper;
    }

    public InventoryLogsResponseDTO addInventoryLog(InventoryLogsCreateDTO dto) {
        InventoryLogs inventoryLogs = inventoryLogsMapper.toEntity(dto);
        InventoryLogs savedLog = inventoryLogsRepository.save(inventoryLogs);
        return inventoryLogsMapper.toResponseDTO(savedLog);
    }

    public List<InventoryLogsResponseDTO> getAllInventoryLogs() {
        List<InventoryLogs> inventoryLogs = inventoryLogsRepository.findAll();
        if (inventoryLogs.isEmpty()) {
            throw new EmptyLogsException("No inventory logs found in the system");
        }
        return inventoryLogs.stream()
                .map(inventoryLogsMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public InventoryLogsResponseDTO getLogById(Long id) {
        InventoryLogs log = inventoryLogsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Log with ID " + id + " not found"));
        return inventoryLogsMapper.toResponseDTO(log);
    }

    public List<InventoryLogsResponseDTO> getLogsByProductId(String productId) {
        List<InventoryLogs> logs = inventoryLogsRepository.findByProductId(productId);
        if (logs.isEmpty()) {
            throw new EmptyLogsException("No logs found for product ID: " + productId);
        }
        return logs.stream()
                .map(inventoryLogsMapper::toResponseDTO)
                .collect(Collectors.toList());
    }


    public List<InventoryLogsResponseDTO> getLogsByProductIdAndDateRange(String productId, LocalDate startDate, LocalDate endDate) {
        List<InventoryLogs> logs = inventoryLogsRepository.findByProductIdAndLogDateBetween(productId, startDate, endDate);
        if (logs.isEmpty()) {
            throw new EmptyLogsException("No logs found for product ID: " + productId + " in the specified date range");
        }
        return logs.stream()
                .map(inventoryLogsMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<InventoryLogsResponseDTO> getLogsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<InventoryLogs> logs = inventoryLogsRepository.findByLogDateBetween(startDate, endDate);
        if (logs.isEmpty()) {
            throw new EmptyLogsException("No logs found in the specified date range");
        }
        return logs.stream()
                .map(inventoryLogsMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<InventoryLogsResponseDTO> getLogsByChangeType(String changeType) {
        List<InventoryLogs> logs = inventoryLogsRepository.findByChangeType(changeType);
        if (logs.isEmpty()) {
            throw new EmptyLogsException("No logs found for change type: " + changeType);
        }
        return logs.stream()
                .map(inventoryLogsMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<InventoryLogsResponseDTO> getLogsByChangeTypeAndDateRange(String changeType, LocalDate startDate, LocalDate endDate) {
        List<InventoryLogs> logs = inventoryLogsRepository.findByChangeTypeAndLogDateBetween(changeType, startDate, endDate);
        if (logs.isEmpty()) {
            throw new EmptyLogsException("No logs found for change type: " + changeType + " in the specified date range");
        }
        return logs.stream()
                .map(inventoryLogsMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<InventoryLogsResponseDTO> getLogsByProductIdAndChangeType(String productId, String changeType) {
        List<InventoryLogs> logs = inventoryLogsRepository.findByProductIdAndChangeType(productId, changeType);
        if (logs.isEmpty()) {
            throw new EmptyLogsException("No logs found for product ID: " + productId + " and change type: " + changeType);
        }
        return logs.stream()
                .map(inventoryLogsMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<InventoryLogsResponseDTO> getLogsByProductIdAndChangeTypeAndDateRange(String productId, String changeType, LocalDate startDate, LocalDate endDate) {
        List<InventoryLogs> logs = inventoryLogsRepository.findByProductIdAndChangeTypeAndLogDateBetween(productId, changeType, startDate, endDate);
        if (logs.isEmpty()) {
            throw new EmptyLogsException("No logs found for product ID: " + productId + " and change type: " + changeType + " in the specified date range");
        }
        return logs.stream()
                .map(inventoryLogsMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public InventoryLogsResponseDTO updateInventoryLog(Long id, InventoryLogsCreateDTO dto) {
        InventoryLogs existingLog = inventoryLogsRepository.findById(id)
                .orElseThrow(() -> new InventoryLogNotFoundException("Log with ID " + id + " not found"));

        // Update the existing log with new data
        existingLog.setProduct(dto.getProduct());
        existingLog.setChangeType(dto.getChangeType());
        existingLog.setQuantityChanged(dto.getQuantityChanged());
        existingLog.setQuantityChanged(dto.getQuantityChanged());
        existingLog.setInventoryLogsDate(dto.getInventoryLogsDate());

        InventoryLogs updatedLog = inventoryLogsRepository.save(existingLog);
        return inventoryLogsMapper.toResponseDTO(updatedLog);
    }

    public void deleteInventoryLog(Long id) {
        InventoryLogs log = inventoryLogsRepository.findById(id)
                .orElseThrow(() -> new InventoryLogNotFoundException("Log with ID " + id + " not found"));
        inventoryLogsRepository.delete(log);
    }



}
