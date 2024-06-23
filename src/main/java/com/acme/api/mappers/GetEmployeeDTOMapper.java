package com.acme.api.mappers;

import com.acme.api.dto.GetEmployeeDTO;
import com.acme.api.entities.Employee;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public class GetEmployeeDTOMapper implements Function<Employee, GetEmployeeDTO> {
    @Override
    public GetEmployeeDTO apply(Employee employee) {
        return new GetEmployeeDTO(employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getUsername());
    }
}
