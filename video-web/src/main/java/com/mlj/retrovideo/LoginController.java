package com.mlj.retrovideo;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mlj.retrovideo.domain.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class LoginController {

    private final SecurityService securityService;

    @Autowired
    public LoginController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @RequestMapping(value = "/login", method = POST)
    @ResponseStatus(HttpStatus.OK)
    public void login(LoginDto loginDto, HttpServletRequest request) {
        if (securityService.authenticate(loginDto.getUserName(), loginDto.getPassword())) {
            HttpSession session = request.getSession();
            session.invalidate();
            request.getSession(true);
        } else {
            throw new LoginFailedException();
        }
    }

    @ExceptionHandler(LoginFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void loginFailed() {
    }

}
