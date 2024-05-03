package com.example.testing.domain.employee.dtos;

import com.example.testing.domain.employee.Employee;

import java.io.Serializable;

/**
 * DTO for {@link Employee}
 */
public record CreateEmployeeDto(String email, String firstName, String lastName) implements Serializable {
}