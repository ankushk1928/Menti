package com.mentee.microservice.repository;

import com.mentee.microservice.entity.Mentee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MenteeRepository extends MongoRepository<Mentee, String> {

    Optional<Mentee> findByEmployeeId(Integer employeeId);

    Optional<Mentee> findByEmail(String email);

}
