package com.dh.clase36.integradora.service;

import com.dh.clase36.integradora.entities.AppUser;
import com.dh.clase36.integradora.entities.AppUsuarioRoles;
import com.dh.clase36.integradora.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {
    @Autowired
    UserRepository userRepository;

    //ingresar credenciales de usuarios con sus respectivos roles
    @Override
    public void run(ApplicationArguments args) throws Exception {
        BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();

        String pass=passwordEncoder.encode("digital");
        userRepository.save(new AppUser("Rodolfo","Rodolfo","rodolfo@gmail.com",pass, AppUsuarioRoles.ROLE_USER));

        String pass2 = passwordEncoder.encode(("digital2"));
        userRepository.save(new AppUser("Andres", "Vallejo","correoAndres@gmail.com",pass2,AppUsuarioRoles.ROLE_ADMIN ));
    }
}
