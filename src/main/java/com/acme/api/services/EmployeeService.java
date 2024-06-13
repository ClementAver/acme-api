package com.acme.api.services;

import com.acme.api.dto.GetAllEmployeesDTO;
import com.acme.api.entities.Customer;
import com.acme.api.entities.Employee;
import com.acme.api.dto.EmployeeRequestBody;
import com.acme.api.mapper.GetAllEmployeesDTOMapper;
import com.acme.api.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class EmployeeService implements EmployeeInterface{

    private final EmployeeRepository employeeRepository;
    private final GetAllEmployeesDTOMapper getAllEmployeesDTOMapper;

    public EmployeeService(EmployeeRepository employeeRepository, GetAllEmployeesDTOMapper getAllEmployeesDTOMapper) {
        this.employeeRepository = employeeRepository;
        this.getAllEmployeesDTOMapper = getAllEmployeesDTOMapper;
    }

    @Override
    public Employee createEmployee(EmployeeRequestBody employeeRequestBody) {
        Employee employee = new Employee();
        employee.setFirstName(employeeRequestBody.getFirstName());
        employee.setLastName(employeeRequestBody.getLastName());
        employee.setEmail(employeeRequestBody.getEmail());
        employee.setUsername(employeeRequestBody.getUsername());
        employee.setPassword(employeeRequestBody.getPassword());
        return employeeRepository.save(employee);
    }

    @Override
    public Stream<GetAllEmployeesDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream().map(getAllEmployeesDTOMapper);
    }

    @Override
    public Employee getEmployee(long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee updateEmployee(Long id, EmployeeRequestBody employeeRequestBody) {
        Employee employeeToUpdate = employeeRepository.getReferenceById(id);

        if(employeeRequestBody.getFirstName() != null){
            employeeToUpdate.setFirstName(employeeRequestBody.getFirstName());
        }
        if(employeeRequestBody.getLastName() != null) {
            employeeToUpdate.setLastName(employeeRequestBody.getLastName());
        }
        if(employeeRequestBody.getEmail() != null){
            employeeToUpdate.setEmail(employeeRequestBody.getEmail());
        }
        if(employeeRequestBody.getUsername() != null){
            employeeToUpdate.setUsername(employeeRequestBody.getUsername());
        }
        if(employeeRequestBody.getPassword() != null){
            employeeToUpdate.setPassword(employeeRequestBody.getPassword());
        }
        return employeeRepository.save(employeeToUpdate);
    }

    @Override
    public Employee getOrCreateEmployee(Employee employee) {
        Employee employeeInDB = employeeRepository.findByEmail(employee.getEmail());
        if (employeeInDB == null) {
            employeeInDB = employeeRepository.save(employee);
        }
        return employeeInDB;
    }
}
