package com.matricula.service.impl;

import com.matricula.entity.Alumno;
import com.matricula.entity.Cuota;
import com.matricula.entity.Pago;
import com.matricula.repository.CuotaRepository;
import com.matricula.repository.PagoRepository;
import com.matricula.service.CuotaService;
import com.matricula.service.PagoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CuotaServiceImpl
        extends BaseServiceImpl<Cuota, String>
        implements CuotaService {

    private final CuotaRepository cuotaRepo;
    private final PagoService pagoService;

    @Override
    public Cuota findById(String id) {
        return cuotaRepo
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cuota no encontrada: " + id));
    }

    public CuotaServiceImpl(
            CuotaRepository cuotaRepo,
            PagoRepository pagoRepo // inyectamos para registrar pagos
    ) {
        super(cuotaRepo);
        this.cuotaRepo   = cuotaRepo;
        // creamos el servicio de pago in-line
        this.pagoService = new PagoServiceImpl(pagoRepo, cuotaRepo);
    }

    @Override
    protected Cuota createNewVersion(Cuota entity) {
        Cuota copia = new Cuota();
        copia.setAlumno(entity.getAlumno());
        copia.setNumeroCuota(entity.getNumeroCuota());
        copia.setMonto(entity.getMonto());
        copia.setFechaGeneracion(entity.getFechaGeneracion());
        copia.setPagado(entity.getPagado());
        return copia;
    }

    @Override
    @Transactional
    public List<Cuota> generarCuotas(
            String dniAlum,
            int cantidad,
            BigDecimal montoMatricula,
            BigDecimal montoMensual,
            String usuario
    ) {
        List<Cuota> lista = new ArrayList<>();
        LocalDateTime ahora = LocalDateTime.now();

        // cuota 0
        Cuota c0 = new Cuota();
        Alumno alumnoProxy = new Alumno();
        alumnoProxy.setDniAlum(dniAlum);
        c0.setAlumno(alumnoProxy);
        c0.setNumeroCuota(0);
        c0.setMonto(montoMatricula);
        c0.setFechaGeneracion(ahora);
        c0.setPagado(false);
        c0.setUsuarioRegistro(usuario);
        c0.setFechaRegistro(ahora);
        lista.add(c0);

        // cuotas mensuales 1..N
        for (int i = 1; i <= cantidad; i++) {
            Cuota c = new Cuota();
            Alumno a = new Alumno();
            a.setDniAlum(dniAlum);
            c.setAlumno(a);
            c.setNumeroCuota(i);
            c.setMonto(montoMensual);
            c.setFechaGeneracion(ahora.plusMonths(i));
            c.setPagado(false);
            c.setUsuarioRegistro(usuario);
            c.setFechaRegistro(ahora);
            lista.add(c);
        }

        return cuotaRepo.saveAll(lista);
    }

    @Override
    public Page<Cuota> listarPendientes(
            String dniAlum, Pageable pageable
    ) {
        return cuotaRepo.findByAlumnoDniAlumAndPagadoFalse(
                dniAlum, pageable);
    }

    @Override
    @Transactional
    public Cuota pagarCuota(
            String cuotaId,
            String codigoOperacion,
            String usuario
    ) {
        // marca como pagada
        Cuota c = cuotaRepo.findById(cuotaId)
                .orElseThrow(() -> new IllegalArgumentException("Cuota no encontrada"));
        c.setPagado(true);
        c.setUsuarioModificacion(usuario);
        c.setFechaModificacion(LocalDateTime.now());
        cuotaRepo.save(c);

        // registramos el pago
        pagoService.registrarPago(cuotaId, codigoOperacion, usuario);

        return c;
    }
}
