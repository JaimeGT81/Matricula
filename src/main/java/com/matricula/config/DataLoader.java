package com.matricula.config;

import com.matricula.entity.Ubigeo;
import com.matricula.entity.UserAccount;
import com.matricula.repository.UbigeoRepository;
import com.matricula.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Carga datos de ejemplo en desarrollo (perfil "dev").
 */
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
        loadUsers();
        loadUbigeos();
        // Aquí podrás añadir más loaders: cursos, docentes, alumnos, secciones...
    }

    private void loadUsers() {
        if (userRepo.count() == 0) {
            UserAccount admin = new UserAccount();
            admin.setUserId("admin");
            admin.setUserEmail("admin@entidad.com");
            admin.setUserPassword("12345");
            admin.setFechaRegistro(LocalDateTime.now());
            admin.setFechaConexion(LocalDateTime.now());
            admin.setRol((short)1);
            userRepo.save(admin);
        }
    }

    private void loadUbigeos() throws Exception {
        if (ubigeoRepo.count() > 0) {
            return;
        }
        ClassPathResource resource = new ClassPathResource("data/Ubigeos.csv");
        if (!resource.exists()) {
            throw new IllegalStateException(
                    "Ubigeos.csv no encontrado en classpath 'data/Ubigeos.csv'"
            );
        }
        try (var reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            List<Ubigeo> lista = reader.lines()
                    .skip(1)
                    .map(line -> {
                        String[] cols = line.split(",", -1);
                        Ubigeo u = new Ubigeo();
                        u.setIdDepa(cols[0]);
                        u.setIdProv(cols[1]);
                        u.setIdDist(cols[2]);
                        u.setDepartamento(cols[3]);
                        u.setProvincia(cols[4]);
                        u.setDistrito(cols[5]);
                        return u;
                    })
                    .collect(Collectors.toList());
            ubigeoRepo.saveAll(lista);
        }
    }
}
