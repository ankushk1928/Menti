package com.mentee.microservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Mentees")
public class Mentee {

    @NotNull
    @Indexed(unique = true)
    private Integer employeeId;

    @NotEmpty
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name should contain only letters")
    private String firstName;

    @Pattern(regexp = "^[A-Za-z]*$", message = "Last name should contain only letters")
    private String lastName;

    @NotEmpty
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid email format")
    @Indexed(unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "Password must not be null")
    private String password;

    private LocalDateTime createdAt;
    private boolean isActive;

}
