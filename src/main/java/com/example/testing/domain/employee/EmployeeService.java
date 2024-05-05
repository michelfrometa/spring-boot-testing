package com.example.testing.domain.employee;

import com.example.testing.domain.employee.dtos.CreateEmployeeDto;
import com.example.testing.domain.employee.dtos.EmployeeDto;
import com.example.testing.domain.employee.dtos.UpdateEmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.testing.domain.employee.mappers.CreateEmployeeDtoMapper.CREATE_EMPLOYEE_DTO_MAPPER;
import static com.example.testing.domain.employee.mappers.EmployeeDtoMapper.EMPLOYEE_DTO_MAPPER;
import static com.example.testing.domain.employee.mappers.UpdateEmployeeDtoMapper.UPDATE_EMPLOYEE_DTO_MAPPER;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    /**
     * Retrieves a list of all employees.
     *
     * @return A {@link List}<{@link EmployeeDto}> representing all employees. If no employees are found, returns an empty list.
     */
    public List<EmployeeDto> getAll() {
        return Optional.of(employeeRepository.findAll())
                .map(EMPLOYEE_DTO_MAPPER::toDtoList)
                .orElseGet(Collections::emptyList);
    }

    /**
     * Retrieves a single employee by their ID.
     *
     * @param id {@link Long} the ID of the employee to retrieve.
     * @return an {@link EmployeeDto} object representing the employee, or null if the employee is not found.
     */
    public EmployeeDto getById(Long id) {
        return employeeRepository.findById(id)
                .map(EMPLOYEE_DTO_MAPPER::toDto)
                .orElse(null);
    }

    /**
     * Creates a new employee.
     *
     * @param employeeDto {@link CreateEmployeeDto} the data transfer object containing the details of the employee to create.
     * @return an {@link EmployeeDto} object representing the newly created employee, or null if the creation was unsuccessful.
     */
    public EmployeeDto create(CreateEmployeeDto employeeDto) {
        return Optional.of(employeeDto)
                .map(CREATE_EMPLOYEE_DTO_MAPPER::toEntity)
                .map(employeeRepository::save)
                .map(EMPLOYEE_DTO_MAPPER::toDto)
                .orElse(null);
    }

    /**
     * Updates an existing employee's information.
     *
     * @param employeeId  {@link Long} the ID of the employee to update.
     * @param employeeDto {@link EmployeeDto}  data transfer object containing the updated details of the employee.
     * @return an {@link EmployeeDto} object representing the updated employee, or null if the update was unsuccessful.
     */
    public EmployeeDto updateEmployee(Long employeeId, UpdateEmployeeDto employeeDto) {
        return employeeRepository.findById(employeeId)
                .map(employee -> UPDATE_EMPLOYEE_DTO_MAPPER.partialUpdate(employee, employeeDto))
                .map(employeeRepository::save)
                .map(EMPLOYEE_DTO_MAPPER::toDto)
                .orElse(null);
    }

    /**
     * Deletes an employee by their ID.
     *
     * @param employeeId {@link Long} the ID of the employee to delete.
     */
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
