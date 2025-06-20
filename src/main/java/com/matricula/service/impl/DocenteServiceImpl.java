package com.matricula.service.impl;

import com.matricula.dto.DocenteDto;
import com.matricula.entity.Carrera;
import com.matricula.entity.Docente;
import com.matricula.entity.Ubigeo;
import com.matricula.repository.CarreraRepository;
import com.matricula.repository.DocenteRepository;
import com.matricula.repository.UbigeoRepository;
import com.matricula.service.BaseService;
import com.matricula.service.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DocenteServiceImpl implements DocenteService {

    private final DocenteRepository docenteRepository;
    private final UbigeoRepository ubigeoRepository;
    private final CarreraRepository carreraRepository;
    private final BaseService<Docente, String> baseService;

    @Autowired
    public DocenteServiceImpl(DocenteRepository docenteRepository,
                              UbigeoRepository ubigeoRepository,
                              CarreraRepository carreraRepository) {
        this.docenteRepository = docenteRepository;
        this.ubigeoRepository  = ubigeoRepository;
        this.carreraRepository = carreraRepository;

        // Inicializamos aquí nuestro BaseService con la lógica de soft-delete/versionado
        this.baseService = new BaseServiceImpl<Docente, String>(docenteRepository) {
            @Override
            protected Docente createNewVersion(Docente entity) {
                Docente copy = new Docente();
                copy.setDniDocente(entity.getDniDocente());
                copy.setFotoDocente(entity.getFotoDocente());
                copy.setNombreDoc(entity.getNombreDoc());
                copy.setApellidoDoc(entity.getApellidoDoc());
                copy.setGeneroDoc(entity.getGeneroDoc());
                copy.setFechaNacimiento(entity.getFechaNacimiento());
                copy.setCelularDoc(entity.getCelularDoc());
                copy.setUbigeo(entity.getUbigeo());
                copy.setCarrera(entity.getCarrera());
                copy.setDireccionDomicilio(entity.getDireccionDomicilio());
                return copy;
            }
        };
    }

    @Override
    public Docente save(Docente entity, String username) {
        // Retornamos el Docente guardado por baseService
        return baseService.save(entity, username);
    }

    @Override
    public void softDelete(String id, String username) {
        baseService.softDelete(id, username);
    }

    @Override
    public Docente reactivate(String id, String username) {
        return baseService.reactivate(id, username);
    }

    @Override
    public Page<Docente> findAllActive(Pageable pageable) {
        return baseService.findAllActive(pageable);
    }

    @Override
    public Page<Docente> findAllInactive(Pageable pageable) {
        return baseService.findAllInactive(pageable);
    }

    @Override
    public Page<DocenteDto> findAll(String dni, String nombre, String apellido, Pageable pageable) {
        Page<Docente> page = docenteRepository
                .findByDniDocenteStartingWithIgnoreCaseAndNombreDocStartingWithIgnoreCaseAndApellidoDocStartingWithIgnoreCase(
                        dni, nombre, apellido, pageable
                );
        return page.map(this::convertToDto);
    }

    @Override
    public Page<DocenteDto> findAllActive(String dni, String nombre, String apellido, Pageable pageable) {
        Page<Docente> page = docenteRepository
                .findByDniDocenteStartingWithIgnoreCaseAndNombreDocStartingWithIgnoreCaseAndApellidoDocStartingWithIgnoreCaseAndEstadoTrue(
                        dni, nombre, apellido, pageable
                );
        return page.map(this::convertToDto);
    }

    @Override
    public DocenteDto findById(String dni) {
        Optional<Docente> opt = docenteRepository.findById(dni);
        return opt.map(this::convertToDto).orElse(null);
    }

    @Override
    public void save(DocenteDto dto) {
        Docente entidad = convertToEntity(dto);
        docenteRepository.save(entidad);
    }

    @Override
    public void deleteById(String dni) {
        docenteRepository.findById(dni)
                .ifPresent(docenteRepository::delete);
    }

    // --- Métodos privados de mapeo ---

    private DocenteDto convertToDto(Docente d) {
        DocenteDto dto = new DocenteDto();
        dto.setDni(d.getDniDocente());
        dto.setFotoActual(d.getFotoDocente());
        dto.setNombre(d.getNombreDoc());
        dto.setApellido(d.getApellidoDoc());
        dto.setGenero(d.getGeneroDoc());
        dto.setFechaNacimiento(d.getFechaNacimiento());
        dto.setCelular(d.getCelularDoc());
        dto.setUbigeo(d.getUbigeo().getIdUbigeo());
        dto.setCarreraId(d.getCarrera().getId());
        dto.setCarreraNombre(d.getCarrera().getNombre());
        dto.setDireccion(d.getDireccionDomicilio());
        dto.setFechaRegistro(d.getFechaRegistro());
        dto.setUsuarioRegistro(d.getUsuarioRegistro());
        dto.setFechaModificacion(d.getFechaUltModificacion());
        dto.setUsuarioModficacion(d.getUsuarioUltModificacion());
        dto.setEstado(d.getEstadoDoc());
        return dto;
    }

    private Docente convertToEntity(DocenteDto dto) {
        Docente d = new Docente();
        d.setDniDocente(dto.getDni());
        d.setFotoDocente(dto.getFotoActual());
        d.setNombreDoc(dto.getNombre());
        d.setApellidoDoc(dto.getApellido());
        d.setGeneroDoc(dto.getGenero());
        d.setFechaNacimiento(dto.getFechaNacimiento());
        d.setCelularDoc(dto.getCelular());
        ubigeoRepository.findById(dto.getUbigeo()).ifPresent(d::setUbigeo);
        carreraRepository.findById(dto.getCarreraId()).ifPresent(d::setCarrera);
        d.setDireccionDomicilio(dto.getDireccion());
        d.setFechaRegistro(dto.getFechaRegistro());
        d.setUsuarioRegistro(dto.getUsuarioRegistro());
        d.setFechaUltModificacion(dto.getFechaModificacion());
        d.setUsuarioUltModificacion(dto.getUsuarioModficacion());
        d.setEstadoDoc(dto.getEstado());
        return d;
    }
}
