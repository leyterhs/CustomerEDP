package com.customeredp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for user registration")
public class RegisterRequest {

    @Schema(description = "Username of the new user", example = "john_doe", required = true)
    private String username;

    @Schema(description = "Email of the new user", example = "john@example.com", required = true)
    private String email;

    @Schema(description = "Password for the new user", example = "secure123", required = true)
    private String password;
}