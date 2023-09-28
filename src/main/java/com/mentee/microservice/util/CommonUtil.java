package com.mentee.microservice.util;

import com.mentee.microservice.constants.MessageConstants;
import com.mentee.microservice.entity.Mentee;
import com.mentee.microservice.entity.Response;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommonUtil {


    public Response successResponse(Mentee mentee, String message, int statusCode, String path) {
        Response response = new Response();
        response.setMessage(message);
        response.setStatus(statusCode);
        response.setPath(path);
        response.setData(mentee);
        response.setTimeStamp(LocalDateTime.now());
        return response;
    }

    public Response ViewProfileSuccessResponse(Mentee mentee, int statusCode, String path) {
        Response response = new Response();
        response.setMessage(MessageConstants.PROFILE_RETRIVE);
        response.setStatus(statusCode);
        response.setPath(path);
        response.setTimeStamp(LocalDateTime.now());
        response.setData(mentee);
        return response;
    }

    public Response LoginSucessResponse(String message, int statusCode, String path) {
        Response response = new Response();
        response.setMessage(message);
        response.setStatus(statusCode);
        response.setPath(path);
        response.setTimeStamp(LocalDateTime.now());
        return response;
    }

    public Response errorResponse(String errorMessage, int statusCode, String requestUri) {
        Response response = new Response();
        response.setMessage(errorMessage);
        response.setStatus(statusCode);
        response.setPath(requestUri);
        return response;
    }


}
