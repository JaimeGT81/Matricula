package com.matricula.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<T, ID> {
    T save(T entity, String username);
    void softDelete(ID id, String username);
    T reactivate(ID id, String username);
    Page<T> findAllActive(Pageable pageable);
    Page<T> findAllInactive(Pageable pageable);
}