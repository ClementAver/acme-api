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
import javax.json.Json;
import javax.json.JsonObject;
import java.util.Optional;
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
        Optional<Employee> employeeInDB = employeeRepository.findByEmail(email);
        if (employeeInDB.isPresent()) {
            Employee employee = employeeInDB.get();
            return new EmployeeDTO(employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getUsername(), employee.getRole());
        } else {
            throw new NotFoundException("Employé non référencé.");
        }
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeRequestBody employeeRequestBody) throws AlreadyExistException {
        Optional<Employee> mailInDB = employeeRepository.findByEmail(employeeRequestBody.getEmail());
        if (mailInDB.isPresent()) {
            throw new AlreadyExistException("Cet email a déjà été renseigné.");
        }
        Optional<Employee> usernameInDB = employeeRepository.findByUsername(employeeRequestBody.getUsername());
        if (usernameInDB.isPresent()) {
            throw new AlreadyExistException("Ce pseudonyme n'est pas disponible.");
        }

        Employee employee = new Employee();
        employee.setFirstName(employeeRequestBody.getFirstName());
        employee.setLastName(employeeRequestBody.getLastName());
        employee.setEmail(employeeRequestBody.getEmail());
        employee.setUsername(employeeRequestBody.getUsername());
        employee.setPassword(passwordEncoder().encode(employeeRequestBody.getPassword()));
        employee.setRole(employeeRequestBody.getRole());

        employeeRepository.save(employee);
        return new EmployeeDTO(employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getUsername(), employee.getRole());
    }

    @Override
    public EmployeeDTO updateEmployee(String email, EmployeeRequestBody employeeRequestBody) throws NotFoundException {
        Optional<Employee> employeeToUpdate = employeeRepository.findByEmail(email);
        if (employeeToUpdate.isPresent()) {
            Employee employee = employeeToUpdate.get();

            if(employeeRequestBody.getFirstName() != null){
                employee.setFirstName(employeeRequestBody.getFirstName());
            }
            if(employeeRequestBody.getLastName() != null) {
                employee.setLastName(employeeRequestBody.getLastName());
            }
            if(employeeRequestBody.getEmail() != null){
                employee.setEmail(employeeRequestBody.getEmail());
            }
            if(employeeRequestBody.getUsername() != null){
                employee.setUsername(employeeRequestBody.getUsername());
            }
            if(employeeRequestBody.getPassword() != null){
                employee.setPassword(passwordEncoder().encode(employeeRequestBody.getPassword()));
            }
            if(employeeRequestBody.getRole() != null){
                employee.setRole(employeeRequestBody.getRole());
            }
            employeeRepository.save(employee);
            return new EmployeeDTO(employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getUsername(), employee.getRole());
        } else {
            throw new NotFoundException( "Client non référencé.");
        }
    }

    @Override
    public JsonObject deleteEmployee(String email) throws NotFoundException {
        Optional<Employee> employeeToDelete = employeeRepository.findByEmail(email);
        if (employeeToDelete.isPresent()) {
            Employee employee = employeeToDelete.get();
            employeeRepository.delete(employee);
            return Json.createObjectBuilder()
                    .add("deleted", employee.getEmail())
                    .build();
        } else {
            throw new NotFoundException("Employé non référencé.");
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