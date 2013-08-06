package com.mlj.retrovideo.web.employee;

import static org.apache.commons.lang3.text.WordUtils.capitalizeFully;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import com.mlj.retrovideo.domain.employee.AddEmployee;
import com.mlj.retrovideo.domain.employee.EmployeeService;
import com.mlj.retrovideo.domain.employee.EmployeeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping(method = POST, value = "/employee", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void addEmployee(@RequestBody EmployeeDto employee) {
        employeeService.addEmployee(new AddEmployee(UUID.randomUUID().toString(), capitalizeFully(employee.getFirstName()),
                capitalizeFully(employee.getLastName()), employee.getUsername(), employee.getPassword(),
                employee.isAdministrator()));
    }

    @RequestMapping(method = GET, value = "/employee/current")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public EmployeeView getEmployee(HttpServletRequest request) {
        return employeeService.getEmployee((String) request.getSession().getAttribute("employeeId"));
    }

}
