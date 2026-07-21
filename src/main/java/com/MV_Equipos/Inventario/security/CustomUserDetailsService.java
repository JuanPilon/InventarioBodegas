package com.MV_Equipos.Inventario.security;
import com.MV_Equipos.Inventario.entity.Usuario;
import com.MV_Equipos.Inventario.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(
            UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Usuario user =
                userRepository.findByUsername(
                        username.trim().toUpperCase());
        System.out.println("LOGIN USERNAME: " + username);
        if (user == null) {
            throw new UsernameNotFoundException(
                    "Usuario no encontrado");
        }
        System.out.println("USUARIO ENCONTRADO: " + user.getUsername());
        System.out.println("HASH BD: " + user.getPassword());


        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRol().name())
                .build();


    }
}
