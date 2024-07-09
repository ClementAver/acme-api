package com.acme.api.controllers;

import com.acme.api.configuration.SpringSecurityConfig;
import com.acme.api.dto.EmployeeDTO;
import com.acme.api.dto.EmployeeRequestBody;
import com.acme.api.exceptions.AlreadyExistException;
import com.acme.api.exceptions.NotFoundException;
import com.acme.api.services.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Stream;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
@RequestMapping("/api")
public class EmployeeController {

    // @Autowired if no constructor.
    final private EmployeeService employeeService;

    private static final Logger logger = LoggerFactory.getLogger(SpringSecurityConfig.class);

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public Stream<EmployeeDTO> getEmployees(HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        logger.info("Session : {}", request.getSession());
        return employeeService.getEmployees();
    }

    @GetMapping("/employee")
    public EmployeeDTO getEmployee(@RequestParam(value="email") @Email(message = "L'adresse email passée en paramètre de la requête n'est pas valide.") String email) throws NotFoundException {
            return employeeService.getEmployeeByEmail(email);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/employee", consumes = APPLICATION_JSON_VALUE)
    public EmployeeDTO createEmployee(@Valid @RequestBody EmployeeRequestBody employeeRequestBody) throws AlreadyExistException {
            return employeeService.createEmployee(employeeRequestBody);
    }

    @PutMapping(value = "/employee", consumes = APPLICATION_JSON_VALUE)
    public EmployeeDTO updateEmployee(@RequestParam(value="email") @Email(message = "L'adresse email passée en paramètre de la requête n'est pas valide.") String email, @Valid @RequestBody EmployeeRequestBody employeeRequestBody) throws NotFoundException {
            return employeeService.updateEmployee(email, employeeRequestBody);
    }

    @DeleteMapping("/employee")
    public String deleteEmployee(@RequestParam(value="email") @Email(message = "L'adresse email passée en paramètre de la requête n'est pas valide.") String email) throws NotFoundException {
            return employeeService.deleteEmployee(email);
    }
}



