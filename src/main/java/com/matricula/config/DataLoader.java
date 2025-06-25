package com.matricula.config;

import com.github.javafaker.Faker;
import com.matricula.entity.*;
import com.matricula.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    private final UbigeoRepository ubigeoRepo;
    private final UserRepository userRepo;
    private final CarreraRepository carreraRepo;
    private final DocenteRepository docenteRepo;
    private final PasswordEncoder passwordEncoder;
    private final AlumnoRepository alumnoRepository;
    private final CursoRepository cursoRepository;
    private final SeccionRepository seccionRepo;

    public DataLoader(UbigeoRepository ubigeoRepo, UserRepository userRepo, CarreraRepository carreraRepo, DocenteRepository docenteRepo, PasswordEncoder passwordEncoder, AlumnoRepository alumnoRepository, CursoRepository cursoRepository, SeccionRepository seccionRepo) {
        this.ubigeoRepo = ubigeoRepo;
        this.userRepo = userRepo;
        this.carreraRepo = carreraRepo;
        this.docenteRepo = docenteRepo;
        this.passwordEncoder = passwordEncoder;
        this.alumnoRepository = alumnoRepository;
        this.cursoRepository = cursoRepository;
        this.seccionRepo = seccionRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        // Aquí podrás añadir más loaders: cursos, docentes, alumnos, secciones...
        loadUsers();
        loadUbigeos();
        loadCarreras();
        loadDocentes();
        loadAlumnos();
        loadCursos();
        loadSecciones();
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

    private void loadAlumnos() {
        if (alumnoRepository.count() > 0) {
            return;
        }

        Ubigeo ubigeo = ubigeoRepo.findAll().stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("No hay ubigeos cargados"));

        Faker faker = new Faker(new Locale("es", "PE"));
        List<Alumno> alumnos = new ArrayList<>();

        for (int i = 1; i <= 55; i++) {
            Alumno alumno = new Alumno();
            alumno.setDniAlum(String.format("%09d", faker.number().numberBetween(100000000, 999999999)));
            alumno.setNombres(faker.name().firstName() + " " + faker.name().firstName());
            alumno.setApellidos(faker.name().lastName() + " " + faker.name().lastName());
            alumno.setGenero(i % 2 == 0 ? "Masculino" : "Femenino");
            alumno.setUbigeo(ubigeo);
            alumno.setCodigoCarrera(100L + (i % 5)); // 100 a 104
            alumno.setFechaRegistro(LocalDateTime.now());
            alumno.setUsuarioRegistro("Faker");
            alumno.setFechaUltModificacion(LocalDateTime.now());
            alumno.setUsuarioUltModificacion("Faker");
            alumno.setActivo(true);
            alumnos.add(alumno);
        }

        alumnoRepository.saveAll(alumnos);
    }

    private void loadCursos() {
        if (cursoRepository.count() > 0) {
            return;
        }

        Faker faker = new Faker(new Locale("es", "PE"));
        List<Curso> cursos = new ArrayList<>();

        // Nombres de carreras para usar con los códigos
        Map<Integer, String> carreras = new HashMap<>();
        carreras.put(1, "Ingeniería de Sistemas");
        carreras.put(2, "Administración de Empresas");
        carreras.put(3, "Contabilidad");
        carreras.put(4, "Marketing");
        carreras.put(5, "Ingeniería Industrial");
        carreras.put(6, "Arquitectura");
        carreras.put(7, "Psicología");
        carreras.put(8, "Derecho");
        carreras.put(9, "Medicina");
        carreras.put(10, "Enfermería");
        carreras.put(11, "Odontología");
        carreras.put(12, "Ingeniería Civil");
        carreras.put(13, "Ingeniería Mecánica");
        carreras.put(14, "Ingeniería Eléctrica");
        carreras.put(15, "Ingeniería Química");
        carreras.put(16, "Comunicaciones");
        carreras.put(17, "Diseño Gráfico");
        carreras.put(18, "Economía");
        carreras.put(19, "Turismo");
        carreras.put(20, "Gastronomía");
        carreras.put(21, "Educación");
        carreras.put(22, "Música");
        carreras.put(23, "Artes Plásticas");

        // Nombres base de cursos
        String[] basesCursos = {
                "Fundamentos de", "Introducción a", "Taller de", "Metodología de",
                "Análisis de", "Diseño de", "Gestión de", "Teoría de",
                "Práctica de", "Laboratorio de", "Seminario de", "Proyecto de",
                "Desarrollo de", "Evaluación de", "Investigación en", "Técnicas de",
                "Sistemas de", "Procesos de", "Estrategias de", "Planificación de"
        };

        // Complementos para nombres de cursos
        String[] complementosCursos = {
                "la Programación", "los Negocios", "la Investigación", "la Comunicación",
                "Proyectos", "Sistemas", "la Calidad", "la Innovación",
                "Desarrollo", "Marketing", "Tecnología", "Administración",
                "Diseño", "Procesos", "Gestión", "Estrategia",
                "Evaluación", "Metodología", "Planificación", "Optimización"
        };

        for (int i = 0; i < 20; i++) {
            Curso curso = new Curso();

            // Generar código de curso (ejemplo: CUR001)
            String codigoCurso = String.format("CUR%03d", i + 1);
            curso.setCodCurso(codigoCurso);

            // Asignar código de carrera aleatorio (1-23)
            Short codCarrera = (short) faker.number().numberBetween(1, 24);
            curso.setCodCarrera(codCarrera);

            // Asignar nombre de carrera correspondiente
            curso.setNomCarrera(carreras.get(codCarrera.intValue()));

            // Generar nombre de curso combinando bases y complementos
            String nombreCurso = basesCursos[i] + " " + complementosCursos[i];
            curso.setNomCurso(nombreCurso);

            // Asignar créditos aleatorios (1-5)
            curso.setCreditos(faker.number().numberBetween(1, 6));

            // Asignar ciclo (1-10)
            curso.setCiclo((short) faker.number().numberBetween(1, 11));

            // Datos de auditoría
            curso.setFechaRegistro(LocalDateTime.now());
            curso.setUsuarioRegistro("Faker");
            curso.setFechaUltModificacion(LocalDateTime.now());
            curso.setUsuarioUltModificacion("Faker");
            curso.setEstadoCurso(true);

            cursos.add(curso);
        }

        cursoRepository.saveAll(cursos);
    }

    private void loadSecciones() throws Exception {
        if (seccionRepo.count() > 0) {
            return;
        }
        ClassPathResource resource = new ClassPathResource("data/Seccion.csv");
        if (!resource.exists()) {
            throw new IllegalStateException(
                    "Seccion.csv no encontrado en classpath 'data/Seccion.csv'"
            );
        }
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSSS");

        // Create a random number generator for maxParticipantes
        Random random = new Random();

        try (var reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            List<Seccion> lista = reader.lines()
                    .skip(1)
                    .map(line -> {
                        String[] cols = line.split(",", -1);
                        Seccion s = new Seccion();
                        s.setSeccionNRC(cols[0]);
                        s.setDiaSemana(cols[1]);
                        s.setEstadoSeccion(cols[2].equals("1"));
                        s.setFechaInicio(LocalDate.parse(cols[3], dateFormatter));
                        s.setHoraFin(LocalTime.parse(cols[4], timeFormatter));
                        s.setHoraInicio(LocalTime.parse(cols[5], timeFormatter));
                        s.setIdAulaDR(cols[6]);
                        s.setModalidad(cols[7]);
                        Curso curso = cursoRepository.findById(cols[8])
                                .orElseThrow(() -> new IllegalStateException("Curso no encontrado con ID: " + cols[8]));
                        s.setCurso(curso);
                        Docente docente = docenteRepo.findById(cols[9])
                                .orElseThrow(() -> new IllegalStateException("Docente no encontrado con ID: " + cols[9]));
                        s.setDocente(docente);
                        s.setFechaRegistro(LocalDateTime.now());
                        s.setUsuarioRegistro("admin");

                        // Set random maxParticipantes between 10 and 30
                        s.setMaxParticipantes(random.nextInt(21) + 10);

                        return s;
                    })
                    .collect(Collectors.toList());
            seccionRepo.saveAll(lista);
        }
    }
}