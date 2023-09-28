package com.mentee.microservice.service.impl;


import com.mentee.microservice.config.SecurityConfig;
import com.mentee.microservice.constants.MessageConstants;
import com.mentee.microservice.entity.Mentee;
import com.mentee.microservice.exception.CustomeMessageException;
import com.mentee.microservice.repository.MenteeRepository;
import com.mentee.microservice.service.MenteeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class MenteeServiceImpl implements MenteeService {

    @Autowired
    private MenteeRepository menteeRepository;

    @Autowired
    private SecurityConfig securityConfig;



    public String registerMentee(Mentee mentee){
        log.info("register Mentee called");
        Optional<Mentee> menteeRegisterByEmployeeId = menteeRepository.findByEmployeeId(mentee.getEmployeeId());
        Optional<Mentee> menteeRegisterByEmail = menteeRepository.findByEmail(mentee.getEmail());

        if (menteeRegisterByEmployeeId.isEmpty() && menteeRegisterByEmail.isEmpty()) {
            mentee.setPassword(securityConfig.encryptPassword(mentee.getPassword()));
            mentee.setCreatedAt(LocalDateTime.now());
            mentee.setActive(true);
            menteeRepository.save(mentee);
            log.info("Mentee register successfully");
            return MessageConstants.REGISTRATION_SUCCESS;
        } else if (menteeRegisterByEmployeeId.isPresent()) {
            throw new CustomeMessageException(MessageConstants.EMPLOYEEID_REGISTERD);
        } else if (menteeRegisterByEmail.isPresent()) {
            throw new CustomeMessageException(MessageConstants.EMAIL_REGISTERD);
        }
        throw new CustomeMessageException("An unexpected error occurred");
    }

    public String loginMentee(String email, String password) {
        Optional<Mentee> mentee = menteeRepository.findByEmail(email);
        if (mentee.isPresent()) {
            if (securityConfig.matchPassword(password, mentee.get().getPassword())) {
                log.info("Mentee login successfully");
                return MessageConstants.LOGIN_SUCCESS;
            } else {
                log.warn("Mentee password incorrect");
                throw new CustomeMessageException(MessageConstants.PASSWORD_INVALID);
            }
        } else {
            log.warn("Mentee username incorrect");
            throw new CustomeMessageException(MessageConstants.EMAIL_INVALID);
        }
    }

    public Mentee getMenteeByEmployeeId(Integer employeeId) {
        Optional<Mentee> mentee = menteeRepository.findByEmployeeId(employeeId);
        if (mentee.isPresent()) {
            log.info("Mentee found by employeeId: {}", employeeId);
            return mentee.get();
        } else {
            log.warn("Mentee not found for employeeId: {}", employeeId);
            throw new CustomeMessageException(MessageConstants.MENTEE_NOT_FOUND);
        }
    }
}
	
	

