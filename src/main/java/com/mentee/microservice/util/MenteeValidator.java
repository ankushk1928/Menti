package com.mentee.microservice.util;

import com.mentee.microservice.entity.Mentee;
import org.springframework.stereotype.Component;

@Component
public class MenteeValidator {

    public static boolean isValidMentee(Mentee mentee) {
        if (mentee.getFirstName() == null || mentee.getLastName() == null || mentee.getEmail() == null) {
            return false;
        }

        // Validate firstName format
        if (!mentee.getFirstName().matches("^[A-Za-z]+$")) {
            return false;
        }

        // Validate lastName format
        if (mentee.getLastName() != null && !mentee.getLastName().matches("^[A-Za-z]*$")) {
            return false;
        }

        // Validate email format
        if (!mentee.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return false;
        }

        // Add more validation checks if needed

        return true;
    }
}
