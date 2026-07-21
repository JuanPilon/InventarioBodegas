package com.MV_Equipos.Inventario.config;

import com.MV_Equipos.Inventario.security.CustomUserDetailsService;
import com.MV_Equipos.Inventario.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // indica a spring que esta clase contendra parametros de configuracion
@EnableWebSecurity//Indicara que no se utilizara la seguridad propia de spring security si no que sera una personalizada
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    public SecurityConfig(CustomUserDetailsService customUserDetailsService,  JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    @Bean//Es una pre instancia de objetos o metodos que podran ser utilizados despues
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(//indica que las siguientes rutas tendran acceso dependiendo de que parametros tengamos
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/auth/**"
                        ).permitAll()

                        // Para las rutas de usuario  que tengas el rol de admin sin restricciones de endpoints
                        .requestMatchers(HttpMethod.GET,"/usuario/**")
                        .hasAnyRole("SUPERADMIN", "ADMIN")
                        //Para los post unicamente super usuarios
                        .requestMatchers(HttpMethod.POST,"/usuario/**")
                        .hasRole("SUPERADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/usuario/**")
                        .hasRole("SUPERADMIN")

                        // PRODUCTOS CONSULTA con este request matchers encapsulamos que admin y consulta accedan solo a metodos tipo get de las rutas productos
                        .requestMatchers(HttpMethod.GET,
                                "/producto/**")
                        .hasAnyRole("ADMIN","CONSULTA","SUPERADMIN")

                        // PRODUCTOS MODIFICACION respecto a los metodos post aqui solo se le va a permitir a aquellos usuarios con el rol de administrador
                        .requestMatchers(HttpMethod.POST,
                                "/producto/**")
                        .hasAnyRole("ADMIN","SUPERADMIN")

                        //Productos Modificaciones para que los metodos patch correspondan a admin
                        .requestMatchers(HttpMethod.PATCH,
                                "/producto/**")
                        .hasAnyRole("ADMIN","SUPERADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/producto/**")
                        .hasAnyRole("ADMIN","SUPERADMIN")

                        // MOVIMIENTOS CONSULTA para que los metodos de tipo get sean acesibles para admin y consulta
                        .requestMatchers(HttpMethod.GET,
                                "/Movimiento/**")
                        .hasAnyRole("ADMIN","CONSULTA","SUPERADMIN")

                        // MOVIMIENTOS MODIFICACION
                        .requestMatchers(HttpMethod.POST,
                                "/Movimiento/**")
                        .hasAnyRole("ADMIN","SUPERADMIN")

                        .anyRequest().authenticated()//Indica quecualquier solicitud se necesitara tenerse autenticado
                );


        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {



        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(
                        customUserDetailsService);

        provider.setPasswordEncoder(
                passwordEncoder());

        return provider;
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config)
            throws Exception {

        return config.getAuthenticationManager();
    }


}
