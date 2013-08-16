package com.mlj.retrovideo.web.employee;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import com.mlj.retrovideo.employee.AuthenticationFailedException;
import com.mlj.retrovideo.employee.EmployeeView;
import com.mlj.retrovideo.employee.domain.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class LoginController {

    private final EmployeeService employeeService;

    @Autowired
    public LoginController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "/login", method = POST)
    public void login(LoginDto loginDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            EmployeeView employee = employeeService.authenticate(loginDto.getUsername(), loginDto.getPassword());
            HttpSession session = request.getSession();
            session.invalidate();
            session = request.getSession(true);
            session.setAttribute("employeeId", employee.getEmployeeId());
            response.sendRedirect("/video.html");
        } catch (AuthenticationFailedException e) {
            throw new LoginFailedException();
        }
    }

    @ExceptionHandler(LoginFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void loginFailed() {
    }

}
