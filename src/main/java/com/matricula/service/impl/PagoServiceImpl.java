package com.matricula.service.impl;

import com.matricula.entity.Cuota;
import com.matricula.entity.Pago;
import com.matricula.repository.CuotaRepository;
import com.matricula.repository.PagoRepository;
import com.matricula.service.PagoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PagoServiceImpl
        extends BaseServiceImpl<Pago, String>
        implements PagoService {

    private final PagoRepository pagoRepo;
    private final CuotaRepository cuotaRepo;

    public PagoServiceImpl(PagoRepository pagoRepo, CuotaRepository cuotaRepo) {
        super(pagoRepo);
        this.pagoRepo = pagoRepo;
        this.cuotaRepo = cuotaRepo;
    }

    @Override
    protected Pago createNewVersion(Pago entity) {
        Pago copia = new Pago();
        copia.setCuota(entity.getCuota());
        copia.setFechaPago(entity.getFechaPago());
        copia.setCodigoOperacion(entity.getCodigoOperacion());
        return copia;
    }

    @Override
    @Transactional
    public Pago registrarPago(
            String cuotaId,
            String codigoOperacion,
            String usuario
    ) {
        // actualiza cuota (ya pagada)
        Cuota c = cuotaRepo.findById(cuotaId).orElseThrow();
        c.setPagado(true);
        c.setUsuarioModificacion (usuario);
        c.setFechaModificacion(LocalDateTime.now());
        cuotaRepo.save(c);

        // crea el Pago
        Pago p = new Pago();
        p.setCuota(c);
        p.setFechaPago(LocalDateTime.now());
        p.setCodigoOperacion(codigoOperacion);
        p.setUsuarioRegistro(usuario);
        p.setFechaRegistro(LocalDateTime.now());
        return save(p, usuario);
    }

    @Override
    public Page<Pago> findAllByAlumnoDni(String dniAlum, Pageable pageable) {
        return pagoRepo.findByCuotaAlumnoDniAlum(dniAlum, pageable);
    }

    @Override
    public boolean hasValidPayment(String dniAlum) {
        // Find any paid cuotas for the student that haven't expired
        return pagoRepo.existsByCuota_Alumno_DniAlum(dniAlum);
    }
}