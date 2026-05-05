package eva.pet.upc.evapet.serviceImplements;


import eva.pet.upc.evapet.enums.UserRol;
import eva.pet.upc.evapet.models.Rol;
import eva.pet.upc.evapet.models.User;
import eva.pet.upc.evapet.repositories.IRolRepository;
import eva.pet.upc.evapet.repositories.IUsersRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//comnadLineRinner: lo necesario para tener un admin
@Component
@RequiredArgsConstructor
public class InitialDataLoader implements CommandLineRunner {

    private final IRolRepository rolRepository;
    private final IUsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        Rol adminRol = rolRepository
                .findByNameRol(UserRol.ADMIN.name())
                .orElseGet(() -> {

                    Rol rol = new Rol();

                    rol.setNameRol(UserRol.ADMIN);
                    rol.setDescriptionRol("eduardo");

                    return rolRepository.save(rol);
                });

        boolean existsAdmin = userRepository
                .findUserByMail("eduardodelaguila10@gmail.com")
                .isPresent();

        if (!existsAdmin) {

            User admin = new User();

            admin.setName("eduardo");
            admin.setLastName("del aguila");
            admin.setMail("eduardodelaguila10@gmail.com");

            admin.setPassword(
                    passwordEncoder.encode("lehia2006")
            );

            admin.setPhoneNumber("920698576");

            admin.setImage_url("");

            admin.setRol(adminRol);

            admin.setActive(true);

            admin.setCreateAt(LocalDateTime.now());

            userRepository.save(admin);

            System.out.println("ADMIN creado correctamente");
        }
    }
}