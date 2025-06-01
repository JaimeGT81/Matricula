package com.matricula.config;

import com.matricula.entity.Carrera;
import com.matricula.entity.Docente;
import com.matricula.entity.Ubigeo;
import com.matricula.entity.UserAccount;
import com.matricula.repository.CarreraRepository;
import com.matricula.repository.DocenteRepository;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    private final UbigeoRepository ubigeoRepo;
    private final UserRepository userRepo;
    private final CarreraRepository carreraRepo;
    private final DocenteRepository docenteRepo;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UbigeoRepository ubigeoRepo, UserRepository userRepo, CarreraRepository carreraRepo, DocenteRepository docenteRepo, PasswordEncoder passwordEncoder) {
        this.ubigeoRepo = ubigeoRepo;
        this.userRepo = userRepo;
        this.carreraRepo = carreraRepo;
        this.docenteRepo = docenteRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear un encoder localmente para evitar dependencia circular
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        // Aquí podrás añadir más loaders: cursos, docentes, alumnos, secciones...
        loadUsers();
        loadUbigeos();
        loadCarreras();
        loadDocentes();


    }

    private void loadUsers() {
        if (userRepo.count() == 0) {
            UserAccount admin = new UserAccount();
            admin.setUserId("admin");
            admin.setUserEmail("admin@entidad.com");
            admin.setUserPassword(passwordEncoder.encode( "12345"));
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

    private void loadCarreras() throws Exception {
        if (carreraRepo.count() > 0) {
            return;
        }
        ClassPathResource resource = new ClassPathResource("data/Carreras.csv");
        if (!resource.exists()) {
            throw new IllegalStateException(
                    "Carreras.csv no encontrado en classpath 'data/Carreras.csv'"
            );
        }
        try (var reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            List<Carrera> lista = reader.lines()
                    .skip(1)
                    .map(line -> {
                        String[] cols = line.split(",", -1);
                        Carrera c = new Carrera();
                        c.setNombre(cols[0]);
                        return c;
                    })
                    .collect(Collectors.toList());
            carreraRepo.saveAll(lista);
        }
    }

    private void loadDocentes() throws Exception {
        if (docenteRepo.count() > 0) {
            return;
        }
        ClassPathResource resource = new ClassPathResource("data/Docentes.csv");
        if (!resource.exists()) {
            throw new IllegalStateException(
                    "Docentes.csv no encontrado en classpath 'data/Docentes.csv'"
            );
        }
        try (var reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            List<Docente> lista = reader.lines()
                    .skip(1)
                    .map(line -> {
                        String[] cols = line.split(",", -1);
                        Docente d = new Docente();
                        d.setDniDocente(cols[0]);
                        d.setApellidoDoc(cols[1]);
                        d.setCelularDoc(Long.parseLong(cols[2]));
                        d.setDireccionDomicilio(cols[3]);
                        d.setEstadoDoc(cols[4].equals("1"));
                        d.setFechaNacimiento(LocalDate.parse(cols[5]));
                        d.setFechaRegistro(LocalDateTime.parse(cols[6] + "T00:00:00"));
                        d.setFotoDocente(cols[7].isEmpty() ? null : cols[7]);
                        d.setGeneroDoc(cols[8]);
                        d.setNombreDoc(cols[9]);
                        d.setUsuarioRegistro(cols[10]);
                        Carrera carrera = carreraRepo.findById(Integer.parseInt(cols[11]))
                                .orElseThrow(() -> new IllegalStateException("Carrera no encontrada con ID: " + cols[11]));
                        d.setCarrera(carrera);
                        Ubigeo ubigeo = ubigeoRepo.findById(Integer.parseInt(cols[12]))
                                .orElseThrow(() -> new IllegalStateException("Ubigeo no encontrado con ID: " + cols[12]));
                        d.setUbigeo(ubigeo);
                        return d;
                    })
                    .collect(Collectors.toList());
            docenteRepo.saveAll(lista);
        }
    }
}