package com.MV_Equipos.Inventario.controller;

    import com.MV_Equipos.Inventario.security.jwt.JwtService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

    @RestController
    public class JwtTestController {

        private final JwtService jwtService;

        public JwtTestController(JwtService jwtService) {
            this.jwtService = jwtService;
        }

        @GetMapping("/jwt/test")
        public String generarToken() {

            UserDetails user = User.builder()
                    .username("ADMIN")
                    .password("123")
                    .roles("ADMIN")
                    .build();

            return jwtService.generateToken(user);
        }
    }

