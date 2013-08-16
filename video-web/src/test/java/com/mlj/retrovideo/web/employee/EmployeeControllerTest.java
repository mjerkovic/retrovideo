package com.mlj.retrovideo.web.employee;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.mlj.retrovideo.employee.command.AddEmployee;
import com.mlj.retrovideo.employee.domain.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class EmployeeControllerTest {

    private EmployeeController controller;
    @Mock
    private EmployeeService employeeService;

    @Before
    public void givenAnEmployeeController() {
        MockitoAnnotations.initMocks(this);
        controller = new EmployeeController(employeeService);
    }

    @Test
    public void itShouldDelegateToEmployeeServiceWhenAddingAnEmployee() {
        controller.addEmployee(mock(EmployeeDto.class));

        verify(employeeService).addEmployee(any(AddEmployee.class));
    }

}
