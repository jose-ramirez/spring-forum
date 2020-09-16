package br.com.forum.controller.form;

import br.com.forum.config.security.TokenService;
import br.com.forum.controller.dto.TokenDto;
import br.com.forum.modelo.LoginForm;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AutenticacaoController {

    private TokenService tokenService;
    private AuthenticationManager authenticationManager;

    public AutenticacaoController(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth")
    public ResponseEntity<TokenDto> auth(@RequestBody @Valid LoginForm data){
        UsernamePasswordAuthenticationToken loginData = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
        try {
            Authentication auth = authenticationManager.authenticate(loginData);
            String token = tokenService.generateToken(auth);
            return ResponseEntity.ok(new TokenDto(token));
        } catch (AuthenticationException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
