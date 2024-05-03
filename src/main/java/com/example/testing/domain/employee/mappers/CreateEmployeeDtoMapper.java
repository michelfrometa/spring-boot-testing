package com.example.testing.domain.employee.mappers;

import com.example.testing.domain.common.EntityMapper;
import com.example.testing.domain.employee.Employee;
import com.example.testing.domain.employee.dtos.CreateEmployeeDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CreateEmployeeDtoMapper extends EntityMapper<CreateEmployeeDto, Employee> {
    CreateEmployeeDtoMapper CREATE_EMPLOYEE_DTO_MAPPER = Mappers.getMapper(CreateEmployeeDtoMapper.class);
}