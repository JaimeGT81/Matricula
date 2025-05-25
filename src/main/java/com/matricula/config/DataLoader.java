package com.matricula.config;

import com.matricula.entity.Ubigeo;
import com.matricula.entity.UserAccount;
import com.matricula.repository.UbigeoRepository;
import com.matricula.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    private final UbigeoRepository ubigeoRepo;
    private final UserRepository userRepo;

    public DataLoader(UbigeoRepository ubigeoRepo, UserRepository userRepo) {
        this.ubigeoRepo = ubigeoRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear un encoder localmente para evitar dependencia circular
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        // Cargar usuario admin si no existe
        if (userRepo.count() == 0) {
            UserAccount admin = new UserAccount();
            admin.setUserId("admin");
            admin.setUserEmail("admin@entidad.com");
            admin.setUserPassword(encoder.encode("12345"));
            admin.setFechaRegistro(LocalDateTime.now());
            admin.setFechaConexion(LocalDateTime.now());
            admin.setRol((short) 1);
            userRepo.save(admin);
        }

        // Cargar ubigeos desde CSV en classpath
        ClassPathResource resource = new ClassPathResource("data/Ubigeos.csv");
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            List<Ubigeo> lista = br.lines()
                    .skip(1)
                    .map(line -> {
                        String[] campos = line.split(",");
                        Ubigeo u = new Ubigeo();
                        u.setIdDepa(campos[0]);
                        u.setIdProv(campos[1]);
                        u.setIdDist(campos[2]);
                        u.setDepartamento(campos[3]);
                        u.setProvincia(campos[4]);
                        u.setDistrito(campos[5]);
                        return u;
                    })
                    .collect(Collectors.toList());
            ubigeoRepo.saveAll(lista);
        }
    }
}