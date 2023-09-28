package com.mentii;

import com.mentee.microservice.controller.MenteeController;
import com.mentee.microservice.entity.Mentee;
import com.mentee.microservice.entity.Response;
import com.mentee.microservice.repository.MenteeRepository;
import com.mentee.microservice.service.impl.MenteeServiceImpl;
import com.mentee.microservice.util.CommonUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MenteeControllerTest {

    @InjectMocks
    private MenteeController menteeController;

    @Mock
    private MenteeServiceImpl menteeService;

    @Mock
    private CommonUtil commonUtil;

    @Mock
    private MenteeRepository menteeRepository;


    @Mock
    private HttpServletRequest request;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }



    @Test
    public void testLoginMentee_Success() {
        String email = "test@example.com";
        String password = "password";
        String loginStatus = "Login Successful";

        // Create a mock of MenteeServiceImpl
        MenteeServiceImpl menteeServiceMock = Mockito.mock(MenteeServiceImpl.class);

        // Set up the behavior of the mock
        when(menteeServiceMock.loginMentee(email, password)).thenReturn(loginStatus);

        // Inject the mock into the controller
//        menteeController.setMenteeServiceImpl(menteeServiceMock);

        // Rest of the test remains the same
        when(commonUtil.LoginSucessResponse(loginStatus, HttpStatus.OK.value(), "/login")).thenReturn(new Response());

        ResponseEntity<Response> responseEntity = menteeController.loginMentee(email, password, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(menteeServiceMock, times(1)).loginMentee(email, password);
        verify(commonUtil, times(1)).LoginSucessResponse(loginStatus, HttpStatus.OK.value(), "/login");
    }

    @Test
    public void testRegisterMentee() {
        Mentee mentee = new Mentee(); // Create a sample Mentee object
        when(menteeService.registerMentee(mentee)).thenReturn("Registration Successful");

        when(commonUtil.successResponse(mentee, "Registration Successful", HttpStatus.OK.value(), "/register")).thenReturn(new Response());

        ResponseEntity<Response> responseEntity = menteeController.registerMentee(mentee, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(menteeService, times(1)).registerMentee(mentee);
        verify(commonUtil, times(1)).successResponse(mentee, "Registration Successful", HttpStatus.OK.value(), "/register");
    }

    @Test
    public void testLoginMentee() {
        String email = "test@example.com";
        String password = "password";
        Mentee mentee=new Mentee();
        when(menteeRepository.findByEmail(email)).thenReturn(Optional.of(mentee));
        when(menteeService.loginMentee(email, password)).thenReturn("loginSuccessfull");
        when(commonUtil.LoginSucessResponse("Login Successful", HttpStatus.OK.value(), "/login")).thenReturn(new Response());
        ResponseEntity<Response> responseEntity = menteeController.loginMentee(email, password, request);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        verify(menteeService, times(1)).loginMentee(email, password);
//        verify(commonUtil, times(1)).LoginSucessResponse("Login Successful", HttpStatus.OK.value(), "/login");
    }

    @Test
    public void testViewMenteeProfile() {
        Integer employeeId = 123;
        Mentee mentee = new Mentee(); // Create a sample Mentee object
        when(menteeService.getMenteeByEmployeeId(employeeId)).thenReturn(mentee);
        when(commonUtil.ViewProfileSuccessResponse(mentee, HttpStatus.OK.value(), "/profile")).thenReturn(new Response());

        ResponseEntity<Response> responseEntity = menteeController.viewMenteeProfile(employeeId, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(menteeService, times(1)).getMenteeByEmployeeId(employeeId);
        verify(commonUtil, times(1)).ViewProfileSuccessResponse(mentee, HttpStatus.OK.value(), "/profile");
    }
}
