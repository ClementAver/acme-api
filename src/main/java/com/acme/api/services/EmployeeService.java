package com.acme.api.services;

import com.acme.api.dto.GetEmployeeDTO;
import com.acme.api.entities.Employee;
import com.acme.api.dto.EmployeeRequestBody;
import com.acme.api.mapper.GetEmployeeDTOMapper;
import com.acme.api.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;
import java.util.stream.Stream;

@Service
public class EmployeeService implements EmployeeInterface{

    private final EmployeeRepository employeeRepository;
    private final GetEmployeeDTOMapper getEmployeeDTOMapper;

    public EmployeeService(EmployeeRepository employeeRepository, GetEmployeeDTOMapper getEmployeeDTOMapper) {
        this.employeeRepository = employeeRepository;
        this.getEmployeeDTOMapper = getEmployeeDTOMapper;
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
    public Stream<GetEmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream().map(getEmployeeDTOMapper);
    }

    @Override
    public Employee getEmployee(long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public GetEmployeeDTO getEmployeeByUsername(String username) {
        Employee employee = employeeRepository.findByUsername(username);
        return new GetEmployeeDTO(employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getUsername());
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
