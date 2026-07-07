package com.customeredp.controller;

import com.customeredp.dto.AuthRequest;
import com.customeredp.dto.AuthResponse;
import com.customeredp.model.Member;
import com.customeredp.repository.MemberRepository;
import com.customeredp.security.JwtUtil;   // ✅ ΑΛΛΑΓΗ
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final MemberRepository memberRepository;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request) {
		try {

			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
			);

			final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
			final String token = jwtUtil.generateToken(userDetails);

			Member member = memberRepository.findByUsername(request.getUsername())
					.orElseThrow(() -> new RuntimeException("User not found"));

			return ResponseEntity.ok(new AuthResponse(token, member.getUsername(), member.getRole()));

		} catch (Exception e) {
			e.printStackTrace(); // ← ΑΥΤΟ ΘΑ ΤΥΠΩΣΕΙ ΤΟ ΣΦΑΛΜΑ ΣΤΟ TERMINAL
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("Login failed: " + e.getMessage()));
		}
	}

    static class ErrorResponse {
        private String error;
        public ErrorResponse(String error) { this.error = error; }
        public String getError() { return error; }
        public void setError(String error) { this.error = error; }
    }
}