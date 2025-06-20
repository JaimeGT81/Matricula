// src/main/java/com/matricula/service/impl/BaseServiceImpl.java
package com.matricula.service.impl;

import com.matricula.entity.BaseEntity;
import com.matricula.repository.BaseRepository;
import com.matricula.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

public abstract class BaseServiceImpl<T extends BaseEntity, ID> implements BaseService<T, ID> {

    protected final BaseRepository<T, ID> repository;

    protected BaseServiceImpl(BaseRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public T save(T entity, String username) {
        if (entity.getFechaRegistro() == null) {
            entity.setFechaRegistro(LocalDateTime.now());
            entity.setUsuarioRegistro(username);
            entity.setEstado(true);
            entity.setVersion(1);
        } else {
            entity.setFechaModificacion(LocalDateTime.now());
            entity.setUsuarioModificacion(username);
        }
        return repository.save(entity);
    }

    @Override
    public void softDelete(ID id, String username) {
        T entity = repository.findById(id).orElseThrow();
        entity.setEstado(false);
        entity.setFechaBaja(LocalDateTime.now());
        entity.setUsuarioBaja(username);
        repository.save(entity);
    }

    @Override
    public T reactivate(ID id, String username) {
        T entity = repository.findById(id).orElseThrow();
        T newEntity = createNewVersion(entity);
        newEntity.setEstado(true);
        newEntity.setFechaRegistro(LocalDateTime.now());
        newEntity.setUsuarioRegistro(username);
        newEntity.setVersion(entity.getVersion() + 1);
        return repository.save(newEntity);
    }

    protected abstract T createNewVersion(T entity);

    @Override
    public Page<T> findAllActive(Pageable pageable) {
        return repository.findByEstadoTrue(pageable);
    }

    @Override
    public Page<T> findAllInactive(Pageable pageable) {
        return repository.findByEstadoFalseOrderByFechaBajaDesc(pageable);
    }
}