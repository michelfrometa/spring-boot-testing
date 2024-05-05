package com.example.testing.domain.employee;

import com.example.testing.domain.employee.dtos.CreateEmployeeDto;
import com.example.testing.domain.employee.dtos.EmployeeDto;
import com.example.testing.domain.employee.dtos.UpdateEmployeeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static com.example.testing.domain.employee.mappers.EmployeeDtoMapper.EMPLOYEE_DTO_MAPPER;
import static com.example.testing.domain.employee.mappers.UpdateEmployeeDtoMapper.UPDATE_EMPLOYEE_DTO_MAPPER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    private final long id = 1L;
    private final String email = "email@gmail.com";
    private final String firstName = "firstName";
    private final String lastName = "lastName";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private ObjectMapper objectMapper;
    private EmployeeDto employee;

    @BeforeEach
    void setup() {
        employee = EmployeeDto.builder()
                .id(id)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }

    @DisplayName("Junit Test for createEmployee method")
    @Test
    void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        BDDMockito.given(employeeService.create(ArgumentMatchers.any(CreateEmployeeDto.class)))
                .willReturn(employee);

        ResultActions response = mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new CreateEmployeeDto(email, firstName, lastName))));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(email)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(firstName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(lastName)));
    }

    @DisplayName("Junit Test for getAllEmployees method")
    @Test
    void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {
        List<EmployeeDto> employees = new ArrayList<>();
        employees.add(EmployeeDto.builder().email("email1").firstName("firstName1").lastName("lastName1").build());
        employees.add(EmployeeDto.builder().email("email2").firstName("firstName2").lastName("lastName2").build());
        employees.add(EmployeeDto.builder().email("email3").firstName("firstName3").lastName("lastName3").build());
        BDDMockito.given(employeeService.getAll()).willReturn(employees);

        ResultActions response = mockMvc.perform(get("/employees"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(employees.size())));
    }

    @DisplayName("Junit Test for getEmployeeById method")
    @Test
    void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        BDDMockito.given(employeeService.getById(id)).willReturn(employee);

        ResultActions response = mockMvc.perform(get("/employees/{id}", id));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(email)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(firstName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(lastName)));
    }

    @DisplayName("Junit Test for getEmployeeById method (Negative Scenario)")
    @Test
    void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeEmpty() throws Exception {
        BDDMockito.given(employeeService.getById(id)).willReturn(null);

        ResultActions response = mockMvc.perform(get("/employees/{id}", id));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @DisplayName("Junit Test for updateEmployee method")
    @Test
    void givenUpdatedEmployeeId_whenUpdateEmployee_thenReturnUpdatedEmployeeObject() throws Exception {
        String updatedEmail = "updatedEmail@gmail.com";
        String updatedFirstName = "updatedFirstName";
        String updatedLastName = "updatedLastName";
        Employee updatedEmployee = Employee.builder().email(updatedEmail).firstName(updatedFirstName).lastName(updatedLastName).build();
        BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.eq(id), ArgumentMatchers.any(UpdateEmployeeDto.class)))
                .willReturn(EMPLOYEE_DTO_MAPPER.toDto(updatedEmployee));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/employees/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(UPDATE_EMPLOYEE_DTO_MAPPER.toDto(updatedEmployee))));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(updatedEmail)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(updatedFirstName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(updatedLastName)));
    }

    @DisplayName("Junit Test for updateEmployee method (negative scenario)")
    @Test
    void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception {
        String updatedEmail = "updatedEmail@gmail.com";
        String updatedFirstName = "updatedFirstName";
        String updatedLastName = "updatedLastName";
        Employee updatedEmployee = Employee.builder().email(updatedEmail).firstName(updatedFirstName).lastName(updatedLastName).build();
        BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.eq(id), ArgumentMatchers.any(UpdateEmployeeDto.class)))
                .willReturn(null);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/employees/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(UPDATE_EMPLOYEE_DTO_MAPPER.toDto(updatedEmployee))));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @DisplayName("Junit Test for deleteEmployee method")
    @Test
    void givenEmployeeId_whenDeleteEmployee_thenReturn204() throws Exception {
        BDDMockito.willDoNothing().given(employeeService).deleteEmployee(ArgumentMatchers.eq(id));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{id}", id));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}