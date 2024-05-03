package com.example.testing.domain.employee;

import com.example.testing.domain.employee.dtos.CreateEmployeeDto;
import com.example.testing.domain.employee.dtos.EmployeeDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.testing.domain.employee.mappers.CreateEmployeeDtoMapper.CREATE_EMPLOYEE_DTO_MAPPER;
import static com.example.testing.domain.employee.mappers.UpdateEmployeeDtoMapper.UPDATE_EMPLOYEE_DTO_MAPPER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

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

    @DisplayName("Junit Test for getAllEmployees method")
    @Test
    void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeeList() {

        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("firstname")
                .lastName("lastname")
                .email("email1@gmail.com")
                .build();

        BDDMockito.given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        List<EmployeeDto> employeeDtos = employeeService.getAllEmployees();

        Assertions.assertNotNull(employeeDtos);
        Assertions.assertEquals(2, employeeDtos.size());
    }

    @DisplayName("Junit Test for getAllEmployees method (negative scenario)")
    @Test
    void givenEmptyEmployeeList_whenGetAllEmployees_thenReturnEmptyEmployeeList() {

        BDDMockito.given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        List<EmployeeDto> employeeDtos = employeeService.getAllEmployees();

        Assertions.assertTrue(employeeDtos.isEmpty());
    }

    @DisplayName("Junit Test for getEmployee method")
    @Test
    void givenId_whenGetByid_thenReturnEmployee() {
        BDDMockito.given(employeeRepository.findById(anyLong())).willReturn(Optional.of(employee));

        EmployeeDto existing = employeeService.getEmployee(1L);

        Assertions.assertNotNull(existing);
    }

    @DisplayName("Junit Test for getEmployee method (negative scenario)")
    @Test
    void givenId_whenGetByid_thenReturnNull() {
        BDDMockito.given(employeeRepository.findById(anyLong())).willReturn(Optional.empty());

        EmployeeDto existing = employeeService.getEmployee(1L);

        Assertions.assertNull(existing);
    }

    @DisplayName("Junit Test for createEmployee method")
    @Test
    void givenEmployee_whenCreateEmployee_thenReturnEmployee() {
        BDDMockito.given(employeeRepository.save(any())).willReturn(employee);
        CreateEmployeeDto createEmployeeDto = CREATE_EMPLOYEE_DTO_MAPPER.toDto(employee);

        EmployeeDto savedEmployee = employeeService.createEmployee(createEmployeeDto);

        Assertions.assertNotNull(savedEmployee);
    }

    @DisplayName("Junit Test for updateEmployee method")
    @Test
    void givenEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee() {

        BDDMockito.given(employeeRepository.findById(any())).willReturn(Optional.of(employee));

        BDDMockito.given(employeeRepository.save(any())).willReturn(employee);
        String updatedEmail = "updatedEmail@gmail.com";
        String updatedFirstName = "updatedFirstName";
        String updatedLastName = "updatedLastName";

        employee.setEmail(updatedEmail);
        employee.setFirstName(updatedFirstName);
        employee.setLastName(updatedLastName);

        EmployeeDto updatedEmployee = employeeService.updateEmployee(1L, UPDATE_EMPLOYEE_DTO_MAPPER.toDto(employee));

        Assertions.assertEquals(updatedEmployee.getEmail(), updatedEmail);
        Assertions.assertEquals(updatedEmployee.getFirstName(), updatedFirstName);
        Assertions.assertEquals(updatedEmployee.getLastName(), updatedLastName);
    }


    @DisplayName("Junit Test for deleteEmployee method")
    @Test
    void givenEmployeeId_whenDeleteEmployee_thenNothing() {
        BDDMockito.willDoNothing().given(employeeRepository).deleteById(employee.getId());

        employeeService.deleteEmployee(employee.getId());

        BDDMockito.verify(employeeRepository, Mockito.times(1)).deleteById(employee.getId());

    }
}