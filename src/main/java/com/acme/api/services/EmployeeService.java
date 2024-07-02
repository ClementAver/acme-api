package com.acme.api.services;

import com.acme.api.dto.EmployeeDTO;
import com.acme.api.entities.Employee;
import com.acme.api.dto.EmployeeRequestBody;
import com.acme.api.exceptions.AlreadyExistException;
import com.acme.api.exceptions.NotFoundException;
import com.acme.api.mappers.EmployeeDTOMapper;
import com.acme.api.repositories.EmployeeRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.stream.Stream;

@Service
public class EmployeeService implements EmployeeInterface{

    private final EmployeeRepository employeeRepository;
    private final EmployeeDTOMapper employeeDTOMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeDTOMapper employeeDTOMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeDTOMapper = employeeDTOMapper;
    }

    @Override
    public Stream<EmployeeDTO> getEmployees() {
        return employeeRepository.findAll()
                .stream().map(employeeDTOMapper);
    }

    @Override
    public EmployeeDTO getEmployeeByEmail(String email) throws NotFoundException {
        Employee employeeInDB = employeeRepository.findByEmail(email);
        if (employeeInDB == null) {
            throw new NotFoundException("Email inconnu.");
        }
        return new EmployeeDTO(employeeInDB.getFirstName(), employeeInDB.getLastName(), employeeInDB.getEmail(), employeeInDB.getUsername());
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeRequestBody employeeRequestBody) throws AlreadyExistException {
        Employee employee = new Employee();
        employee.setFirstName(employeeRequestBody.getFirstName());
        employee.setLastName(employeeRequestBody.getLastName());
        employee.setEmail(employeeRequestBody.getEmail());
        employee.setUsername(employeeRequestBody.getUsername());
        employee.setPassword(passwordEncoder().encode(employeeRequestBody.getPassword()));

        Employee mailInDB = employeeRepository.findByEmail(employee.getEmail());
        if (mailInDB != null) {
            throw new AlreadyExistException("Cet email a déjà été renseigné.");
        }
        Employee usernameInDB = employeeRepository.findByUsername(employee.getUsername());
        if (usernameInDB != null) {
            throw new AlreadyExistException("Ce pseudonyme n'est pas disponible.");
        }
        employeeRepository.save(employee);
        return new EmployeeDTO(employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getUsername());
    }

    @Override
    public EmployeeDTO updateEmployee(String email, EmployeeRequestBody employeeRequestBody) throws NotFoundException {
        Employee employeeToUpdate = employeeRepository.findByEmail(email);
        if (employeeToUpdate == null) {
            throw new NotFoundException("Employé inconnu.");
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
            employeeToUpdate.setPassword(passwordEncoder().encode(employeeRequestBody.getPassword()));
        }
        employeeRepository.save(employeeToUpdate);
        return new EmployeeDTO(employeeToUpdate.getFirstName(), employeeToUpdate.getLastName(), employeeToUpdate.getEmail(), employeeToUpdate.getUsername());
    }

    @Override
    public String deleteEmployee(String email) throws NotFoundException {
        Employee employeeToDelete = employeeRepository.findByEmail(email);
        if (employeeToDelete != null) {
            employeeRepository.delete(employeeToDelete);
            return employeeToDelete.getEmail();
        } else {
            throw new NotFoundException("Employé inconnu.");
        }
    }

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Tools

//    @Override
//    public Employee getOrCreateEmployee(Employee employee) {
//        Employee employeeInDB = employeeRepository.findByEmail(employee.getEmail());
//        if (employeeInDB == null) {
//            employeeInDB = employeeRepository.save(employee);
//        }
//        return employeeInDB;
//    }
}