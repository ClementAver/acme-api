package com.acme.api.mappers;

import com.acme.api.dto.EmployeeDTO;
import com.acme.api.entities.Employee;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public class EmployeeDTOMapper implements Function<Employee, EmployeeDTO> {
    @Override
    public EmployeeDTO apply(Employee employee) {
        return new EmployeeDTO(employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getUsername(), employee.getRole());
    }
}
