package com.example.testing.domain.employee.dtos;

import com.example.testing.domain.employee.Employee;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link Employee}
 */
@Getter
@Setter
@Builder
public class EmployeeDto implements Serializable {

    private long id;

    private String email;

    private String firstName;

    private String lastName;

}