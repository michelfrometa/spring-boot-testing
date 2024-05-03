package com.example.testing.domain.employee.mappers;

import com.example.testing.domain.common.EntityMapper;
import com.example.testing.domain.employee.Employee;
import com.example.testing.domain.employee.dtos.UpdateEmployeeDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UpdateEmployeeDtoMapper extends EntityMapper<UpdateEmployeeDto, Employee> {
    UpdateEmployeeDtoMapper UPDATE_EMPLOYEE_DTO_MAPPER = Mappers.getMapper(UpdateEmployeeDtoMapper.class);
}