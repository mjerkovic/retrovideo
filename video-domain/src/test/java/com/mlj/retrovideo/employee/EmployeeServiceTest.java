package com.mlj.retrovideo.employee;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mlj.retrovideo.employee.command.AddEmployee;
import com.mlj.retrovideo.employee.domain.EmployeeService;
import com.mlj.retrovideo.employee.repository.EmployeeReadRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.Before;
import org.junit.Test;

public class EmployeeServiceTest {

    private EmployeeService service;
    private CommandGateway commandGateway;
    private EmployeeReadRepository repository;

    @Before
    public void givenAnEmployeeService() {
        commandGateway = mock(CommandGateway.class);
        repository = mock(EmployeeReadRepository.class);
        service = new EmployeeService(commandGateway, repository);
    }

    @Test
    public void itShouldSendAnAddEmployeeCommandToTheCommandGateway() {
        AddEmployee addEmployee = mock(AddEmployee.class);

        service.addEmployee(addEmployee);

        verify(commandGateway).send(addEmployee);
    }

    @Test
    public void itShouldAuthenticateUserCredentials() {
        EmployeeView employee = mock(EmployeeView.class);
        when(repository.authenticate("bob", "password")).thenReturn(employee);

        assertThat(service.authenticate("bob", "password"), equalTo(employee));
    }

    @Test(expected = AuthenticationFailedException.class)
    public void itShouldFailWhenUserCannotBeAuthenticated() {
        doThrow(new AuthenticationFailedException()).when(repository).authenticate("bob", "password");

        service.authenticate("bob", "password");
    }

}
