package com.mentee.microservice.service;


import com.mentee.microservice.entity.Mentee;

public interface MenteeService {

    public String registerMentee(Mentee mentee);

    public String loginMentee(String email, String password);

    public Mentee getMenteeByEmployeeId(Integer employeeId);
}
	
	

