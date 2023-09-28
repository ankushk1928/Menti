package com.mentii;

import com.mentee.microservice.MenteeServiceAppApplication;
import com.mentee.microservice.config.SecurityConfig;
import com.mentee.microservice.constants.MessageConstants;
import com.mentee.microservice.entity.Mentee;
import com.mentee.microservice.exception.CustomeMessageException;
import com.mentee.microservice.repository.MenteeRepository;
import com.mentee.microservice.service.impl.MenteeServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MenteeServiceAppApplication.class)
public class MenteeServiceImplTest {

    @Mock
    private MenteeRepository menteeRepository;

    @Mock
    private SecurityConfig securityConfig;

    @InjectMocks
    private MenteeServiceImpl menteeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterMentee_Success() {
        Mentee mentee = new Mentee();
        mentee.setEmployeeId(123);
        mentee.setEmail("test@example.com");
        mentee.setPassword("password");

        when(menteeRepository.findByEmployeeId(mentee.getEmployeeId())).thenReturn(Optional.empty());
        when(menteeRepository.findByEmail(mentee.getEmail())).thenReturn(Optional.empty());
        when(securityConfig.encryptPassword(mentee.getPassword())).thenReturn("encryptedPassword");

        String result = menteeService.registerMentee(mentee);

        assertEquals(MessageConstants.REGISTRATION_SUCCESS, result);
        verify(menteeRepository, times(1)).save(mentee);
        assertTrue(mentee.isActive());
        assertNotNull(mentee.getCreatedAt());
    }

    @Test
    public void testRegisterMentee_DuplicateEmployeeId() {
        Mentee mentee = new Mentee();
        mentee.setEmployeeId(123);

        when(menteeRepository.findByEmployeeId(mentee.getEmployeeId())).thenReturn(Optional.of(mentee));

        assertThrows(CustomeMessageException.class, () -> menteeService.registerMentee(mentee));
    }

    @Test
    public void testRegisterMentee_DuplicateEmail() {
        Mentee mentee = new Mentee();
        mentee.setEmail("test@example.com");

        when(menteeRepository.findByEmployeeId(mentee.getEmployeeId())).thenReturn(Optional.empty());
        when(menteeRepository.findByEmail(mentee.getEmail())).thenReturn(Optional.of(mentee));

        assertThrows(CustomeMessageException.class, () -> menteeService.registerMentee(mentee));
    }


    @Test
    public void testLoginMentee_Success() {
//        int employeeId = 123;
        String email = "test@example.com";

        String password = "password";
        Mentee mentee = new Mentee();
        mentee.setPassword("encryptedPassword"); // Assuming password is already encrypted

        when(menteeRepository.findByEmail(email)).thenReturn(Optional.of(mentee));
        when(securityConfig.matchPassword(password, mentee.getPassword())).thenReturn(true);

        String result = menteeService.loginMentee(email, password);

        assertEquals(MessageConstants.LOGIN_SUCCESS, result);
    }

    @Test
    public void testLoginMentee_IncorrectPassword() {
        int employeeId = 123;
        String email = "test@example.com";

        String password = "incorrectPassword";
        Mentee mentee = new Mentee();
        mentee.setPassword("encryptedPassword"); // Assuming password is already encrypted

        when(menteeRepository.findByEmployeeId(employeeId)).thenReturn(Optional.of(mentee));
        when(securityConfig.matchPassword(password, mentee.getPassword())).thenReturn(false);

        assertThrows(CustomeMessageException.class, () -> menteeService.loginMentee(email, password));
    }

    @Test
    public void testLoginMentee_IncorrectUsername() {
//        int employeeId = 123;
        String email = "test@example.com";

        String password = "password";

        when(menteeRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(CustomeMessageException.class, () -> menteeService.loginMentee(email, password));
    }


    @Test
    public void testGetMenteeByEmployeeId_Success() {
        int employeeId = 123;
        Mentee expectedMentee = new Mentee();
        expectedMentee.setEmployeeId(employeeId);

        when(menteeRepository.findByEmployeeId(employeeId)).thenReturn(Optional.of(expectedMentee));

        Mentee resultMentee = menteeService.getMenteeByEmployeeId(employeeId);

        assertEquals(expectedMentee, resultMentee);
    }

    @Test
    public void testGetMenteeByEmployeeId_NotFound() {
        int employeeId = 123;

        when(menteeRepository.findByEmployeeId(employeeId)).thenReturn(Optional.empty());

        assertThrows(CustomeMessageException.class, () -> menteeService.getMenteeByEmployeeId(employeeId));
    }


}


