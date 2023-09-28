package com.mentee.microservice.controller;

import com.mentee.microservice.constants.Constants;
import com.mentee.microservice.entity.Mentee;
import com.mentee.microservice.entity.Response;
import com.mentee.microservice.service.impl.MenteeServiceImpl;
import com.mentee.microservice.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping(Constants.BASE_PATH)
@CrossOrigin(origins="*")
public class MenteeController {

    @Autowired
    private MenteeServiceImpl menteeServiceImpl;

    @Autowired
    private CommonUtil commonUtil;


    public MenteeController(MenteeServiceImpl menteeService, CommonUtil commonUtil) {
    }

    @Operation(
            description = "Register mentee",
            summary = "Register mentee to the database",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Internal server",
                            responseCode = "500"
                    )
            })

    @PostMapping(Constants.REGISTER_MENTEE)
    public ResponseEntity<Response> registerMentee(@Valid @RequestBody Mentee mentee, HttpServletRequest request) {
        String response = menteeServiceImpl.registerMentee(mentee);
        Response setResponse = commonUtil.successResponse(mentee, response, HttpStatus.OK.value(), request.getRequestURI());
        return ResponseEntity.ok(setResponse);
    }


    @Operation(
            description = "validates mentee userName and password",
            summary = "checks if mentee credentials are valid",
            responses = {
                    @ApiResponse(
                            description = "Valid User",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorised User",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Internal server",
                            responseCode = "500"
                    )
            })

    @GetMapping(Constants.LOGIN_MENTEE)
    public ResponseEntity loginMentee(@RequestParam String email, String password, HttpServletRequest request) {
        String loginStatus = menteeServiceImpl.loginMentee(email, password);
        Response response = commonUtil.LoginSucessResponse(loginStatus, HttpStatus.OK.value(), request.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @Operation(
            description = "Get mentee By Id",
            summary = "checks if mentee is present",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Internal server",
                            responseCode = "500"
                    )
            })

    @GetMapping(Constants.VIEW_MENTEE)
    public ResponseEntity<Response> viewMenteeProfile(@RequestParam Integer employeeId, HttpServletRequest request) {
        Mentee response = menteeServiceImpl.getMenteeByEmployeeId(employeeId);
        Response setResponse = commonUtil.ViewProfileSuccessResponse(response, HttpStatus.OK.value(), request.getRequestURI());
        return ResponseEntity.ok(setResponse);
    }


}
