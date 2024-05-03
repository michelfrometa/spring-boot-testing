package com.example.testing.domain.employee;

import com.example.testing.domain.employee.dtos.CreateEmployeeDto;
import com.example.testing.domain.employee.dtos.EmployeeDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.testing.domain.employee.mappers.CreateEmployeeDtoMapper.CREATE_EMPLOYEE_DTO_MAPPER;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setup() {
        employeeService = new EmployeeService(employeeRepository);

        employee = Employee.builder()
                .email("email@gmail.com")
                .firstName("firstName")
                .lastName("lastName")
                .build();
    }


    @Test
    void getAllEmployees() {
    }

    @Test
    void getEmployee() {
    }

    @Test
    void givenEmployee_whenSaveEmployee_thenReturnEmployee() {
        BDDMockito.given(employeeRepository.save(any())).willReturn(employee);
        CreateEmployeeDto createEmployeeDto = CREATE_EMPLOYEE_DTO_MAPPER.toDto(employee);

        EmployeeDto savedEmployee = employeeService.createEmployee(createEmployeeDto);

        Assertions.assertNotNull(savedEmployee);
    }

    @Test
    void updateEmployee() {
    }

    @Test
    void deleteEmployee() {
    }
}