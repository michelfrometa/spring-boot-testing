package com.example.testing.domain.employee.impl;

import com.example.testing.domain.employee.EmployeeRepository;
import com.example.testing.domain.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

}
