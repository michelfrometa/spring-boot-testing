package com.example.testing.domain.employee;

import com.example.testing.domain.employee.dtos.CreateEmployeeDto;
import com.example.testing.domain.employee.dtos.EmployeeDto;
import com.example.testing.domain.employee.dtos.UpdateEmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody CreateEmployeeDto employee) {
        return new ResponseEntity<>(employeeService.create(employee), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") long employeeId) {
        return Optional.ofNullable(employeeService.getById(employeeId))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") long id,
                                                      @RequestBody UpdateEmployeeDto employee) {
        return Optional.ofNullable(employeeService.updateEmployee(id, employee))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }

}
