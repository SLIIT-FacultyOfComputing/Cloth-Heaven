package org.example.clothheaven.Service;

import org.example.clothheaven.DTO.StaffResponseDTO;
import org.example.clothheaven.Exception.EmptyStaffException;
import org.example.clothheaven.Exception.StaffMemberNotFound;
import org.example.clothheaven.Mapper.StaffMapper;
import org.example.clothheaven.Model.Staff;
import org.example.clothheaven.Repository.StaffRepository;
import org.example.clothheaven.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaffService {

    private final StaffRepository staffRepository;
    private final UserRepository userRepository;
    private final StaffMapper staffMapper;

    @Autowired
    public StaffService(StaffRepository staffRepository, UserRepository userRepository, StaffMapper staffMapper) {
        this.staffRepository = staffRepository;
        this.userRepository = userRepository;
        this.staffMapper = staffMapper;
    }

    public List<StaffResponseDTO> getAllStaff() {
        List<Staff> staffList = staffRepository.findAll();
        if (staffList.isEmpty()) {
            throw new EmptyStaffException("No staff members found in the system");
        }
        return staffList.stream()
                .map(staffMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public StaffResponseDTO getStaffById(Long staffId) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new StaffMemberNotFound("Staff member not found with ID: " + staffId));
        return staffMapper.toResponseDTO(staff);
    }

    public void deleteStaffById(Long staffId) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new StaffMemberNotFound("Staff member not found with ID: " + staffId));
        staffRepository.delete(staff);
    }

    public boolean existsById(Long staffId) {
        return staffRepository.existsById(staffId);
    }
}