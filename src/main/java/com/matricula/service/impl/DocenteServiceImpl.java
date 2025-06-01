package com.matricula.service.impl;

import com.matricula.dto.DocenteDto;
import com.matricula.entity.Carrera;
import com.matricula.entity.Docente;
import com.matricula.entity.Ubigeo;
import com.matricula.repository.CarreraRepository;
import com.matricula.repository.DocenteRepository;
import com.matricula.repository.UbigeoRepository;
import com.matricula.service.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DocenteServiceImpl implements DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private UbigeoRepository ubigeoRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    @Override
    public Page<DocenteDto> findAll(String dni, String nombre, String apellido, Pageable pageable) {
        Page<Docente> docentes = docenteRepository.findByDniDocenteStartingWithIgnoreCaseAndNombreDocStartingWithIgnoreCaseAndApellidoDocStartingWithIgnoreCase(dni,nombre,apellido,pageable);
        return docentes.map(this::ConvertToDocenteDto);
    }

    @Override
    public DocenteDto findById(String dni) {
        Optional<Docente> docente = docenteRepository.findById(dni);
        return docente.map(this::ConvertToDocenteDto).orElse(null);
    }

    @Override
    public void save(DocenteDto docenteDto) {
        Docente docente = this.ConvertToDocente(docenteDto);
        docenteRepository.save(docente);
    }

    @Override
    public void deleteById(String dni) {
        Optional<Docente> docente = docenteRepository.findById(dni);
        docente.ifPresent(d -> docenteRepository.delete(d));
    }

    /* Mapear el docente a un DocenteDto */
    private DocenteDto ConvertToDocenteDto(Docente docente){
        DocenteDto docenteDto = new DocenteDto();
        docenteDto.setDni(docente.getDniDocente());
        docenteDto.setFotoActual(docente.getFotoDocente());
        docenteDto.setNombre(docente.getNombreDoc());
        docenteDto.setApellido(docente.getApellidoDoc());
        docenteDto.setGenero(docente.getGeneroDoc());
        docenteDto.setFechaNacimiento(docente.getFechaNacimiento());
        docenteDto.setCelular(docente.getCelularDoc());
        docenteDto.setUbigeo(docente.getUbigeo().getIdUbigeo());
        docenteDto.setCarreraId(docente.getCarrera().getId());
        docenteDto.setCarreraNombre(docente.getCarrera().getNombre());
        docenteDto.setDireccion(docente.getDireccionDomicilio());
        docenteDto.setFechaRegistro(docente.getFechaRegistro());
        docenteDto.setUsuarioRegistro(docente.getUsuarioRegistro());
        docenteDto.setFechaModificacion(docente.getFechaUltModificacion());
        docenteDto.setUsuarioModficacion(docente.getUsuarioUltModificacion());
        docenteDto.setEstado(docente.getEstadoDoc());
        return docenteDto;
    }

    /* Mapear el DocenteDto a un docente */
    private Docente ConvertToDocente(DocenteDto docenteDto){
        Docente docente = new Docente();
        docente.setDniDocente( docenteDto.getDni());
        docente.setFotoDocente(docenteDto.getFotoActual());
        docente.setNombreDoc(docenteDto.getNombre());
        docente.setApellidoDoc(docenteDto.getApellido());
        docente.setGeneroDoc(docenteDto.getGenero());
        docente.setFechaNacimiento(docenteDto.getFechaNacimiento());
        docente.setCelularDoc(docenteDto.getCelular());
        Optional<Ubigeo> ubigeo = ubigeoRepository.findById(docenteDto.getUbigeo());
        ubigeo.ifPresent(docente::setUbigeo);
        Optional<Carrera> carrera = carreraRepository.findById(docenteDto.getCarreraId());
        carrera.ifPresent(docente::setCarrera);
        docente.setDireccionDomicilio(docenteDto.getDireccion());
        docente.setFechaRegistro(docenteDto.getFechaRegistro());
        docente.setUsuarioRegistro(docenteDto.getUsuarioRegistro());
        docente.setUsuarioUltModificacion(docenteDto.getUsuarioModficacion());
        docente.setFechaUltModificacion(docenteDto.getFechaModificacion());
        docente.setEstadoDoc(docenteDto.getEstado());
        return docente;
    }
}