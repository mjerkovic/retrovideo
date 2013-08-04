package com.mlj.retrovideo.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class SecurityServiceTest {

    private SecurityService service;
    @Mock
    private JdbcVideoRepository videoRepository;

    @Before
    public void givenASecurityService() throws Exception {
        initMocks(this);
        service = new SecurityService(videoRepository);
    }

    @Test
    public void itShouldReturnTrueWhenUserIsValid() {
        when(videoRepository.userExists("admin", "admin")).thenReturn(true);

        assertTrue(service.authenticate("admin", "admin"));

    }

    @Test
    public void itShouldReturnFalseWhenUserIsInvalid() {
        when(videoRepository.userExists("bob", "letmein")).thenReturn(false);

        assertFalse(service.authenticate("bob", "letmein"));
    }

}
