package com.example.testing.integration;

import com.example.testing.domain.employee.Employee;
import com.example.testing.domain.employee.EmployeeRepository;
import com.example.testing.domain.employee.EmployeeService;
import com.example.testing.domain.employee.dtos.CreateEmployeeDto;
import com.example.testing.domain.employee.dtos.EmployeeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static com.example.testing.domain.employee.mappers.CreateEmployeeDtoMapper.CREATE_EMPLOYEE_DTO_MAPPER;
import static com.example.testing.domain.employee.mappers.EmployeeDtoMapper.EMPLOYEE_DTO_MAPPER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class EmployeeControllerIT extends AbstractContainerBaseTest {

    private final long id = 1L;
    private final String email = "email@gmail.com";
    private final String firstName = "firstName";
    private final String lastName = "lastName";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EmployeeService employeeService;
    private Employee employee;

    @BeforeEach
    public void setUp() {
        employeeRepository.deleteAll();

        employee = Employee.builder()
                .id(id)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }

    @Test
    void testContainerCreation() {
        Assertions.assertTrue(MY_SQL_CONTAINER.isRunning());
    }

    @DisplayName("Junit Test for createEmployee method")
    @Test
    void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        CreateEmployeeDto newEmployee = CREATE_EMPLOYEE_DTO_MAPPER.toDto(employee);

        ResultActions response = mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newEmployee)));

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
        employeeRepository.saveAll(EMPLOYEE_DTO_MAPPER.toEntityList(employees));

        ResultActions response = mockMvc.perform(get("/employees"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(employees.size())));
    }


    @DisplayName("Junit Test for getEmployeeById method")
    @Test
    void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        employeeRepository.save(employee);

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

        ResultActions response = mockMvc.perform(get("/employees/{id}", id));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
