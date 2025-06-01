package com.matricula.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;

public interface UploadFileService {
    Resource load(String archivo, String entidad) throws MalformedURLException;
    String copy(MultipartFile archivo, String entidad);
    boolean delete(String archivo, String entidad);
}