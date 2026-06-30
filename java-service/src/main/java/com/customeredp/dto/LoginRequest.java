package com.customeredp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for user login")
public class LoginRequest {

    @Schema(description = "Username of the user", example = "admin", required = true)
    private String username;

    @Schema(description = "Password of the user", example = "password", required = true)
    private String password;
}