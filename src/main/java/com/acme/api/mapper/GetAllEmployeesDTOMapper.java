package com.acme.api.mapper;

import com.acme.api.dto.GetAllEmployeesDTO;
import com.acme.api.entities.Employee;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class GetAllEmployeesDTOMapper implements Function<Employee, GetAllEmployeesDTO> {
    @Override
    public GetAllEmployeesDTO apply(Employee employee) {
        return new GetAllEmployeesDTO(employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getUsername());
    }
}
