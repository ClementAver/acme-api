package com.acme.api.services;

import com.acme.api.dto.GetCustomerDTO;
import com.acme.api.dto.GetEmployeeDTO;
import com.acme.api.entities.Customer;
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
    public Stream<GetEmployeeDTO> getEmployees() {
        return employeeRepository.findAll()
                .stream().map(getEmployeeDTOMapper);
    }

    @Override
    public GetEmployeeDTO getEmployeeByEmail(String email) throws Exception {
        Employee employeeInDB = employeeRepository.findByEmail(email);
        if (employeeInDB == null) {
            throw new Exception("Email inconnu.");
        }
        return new GetEmployeeDTO(employeeInDB.getFirstName(), employeeInDB.getLastName(), employeeInDB.getEmail(), employeeInDB.getUsername());
    }

    @Override
    public GetEmployeeDTO getEmployeeByUsername(String username) throws Exception {
        Employee employeeInDB = employeeRepository.findByUsername(username);
        if (employeeInDB == null) {
            throw new Exception("Pseudonyme inconnu.");
        }
        return new GetEmployeeDTO(employeeInDB.getFirstName(), employeeInDB.getLastName(), employeeInDB.getEmail(), employeeInDB.getUsername());
    }

    @Override
    public void createEmployee(EmployeeRequestBody employeeRequestBody) throws Exception {
        Employee employee = new Employee();
        employee.setFirstName(employeeRequestBody.getFirstName());
        employee.setLastName(employeeRequestBody.getLastName());
        employee.setEmail(employeeRequestBody.getEmail());
        employee.setUsername(employeeRequestBody.getUsername());
        employee.setPassword(employeeRequestBody.getPassword());

        Employee mailInDB = employeeRepository.findByEmail(employee.getEmail());
        if (mailInDB != null) {
            throw new Exception("Cet email a déjà été renseigné.");
        }
        Employee usernameInDB = employeeRepository.findByUsername(employee.getUsername());
        if (usernameInDB != null) {
            throw new Exception("Ce pseudonyme n'est pas disponible.");
        }
        employeeRepository.save(employee);
    }

    @Override
    public void updateEmployee(String email, EmployeeRequestBody employeeRequestBody) throws Exception {
        Employee employeeToUpdate = employeeRepository.findByEmail(email);
        if (employeeToUpdate == null) {
            throw new Exception("Employé inconnu.");
        }
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
        employeeRepository.save(employeeToUpdate);
    }

    @Override
    public void deleteEmployee(String email) throws Exception {
        Employee employeeToDelete = employeeRepository.findByEmail(email);
        if (employeeToDelete != null) {
            employeeRepository.delete(employeeToDelete);
        } else {
            throw new Exception("Employé inconnu.");
        }

    }

    // Tools

    @Override
    public Employee getOrCreateEmployee(Employee employee) {
        Employee employeeInDB = employeeRepository.findByEmail(employee.getEmail());
        if (employeeInDB == null) {
            employeeInDB = employeeRepository.save(employee);
        }
        return employeeInDB;
    }
}