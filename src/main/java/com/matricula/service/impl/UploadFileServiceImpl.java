package com.matricula.service.impl;

import com.matricula.service.UploadFileService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileServiceImpl implements UploadFileService {

    /* Nombre del directorio */
    private final static String UPLOADS_FOLDER = "public";

    /* Obtenemos el recurso para el ArchivoController */
    @Override
    public Resource load(String archivo, String entidad) throws MalformedURLException {
        Path ruta = Paths.get(UPLOADS_FOLDER).resolve(entidad).resolve(archivo);
        Resource recurso = new UrlResource(ruta.toUri());
        if(!recurso.exists() && !recurso.isReadable()){
            throw new RuntimeException("No se puede leer: " + archivo);
        }
        return recurso;
    }

    /* Obtenemos el nombre a guardar en la BD */
    @Override
    public String copy(MultipartFile archivo, String entidad) {
        String nombre = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
        Path carpeta = Paths.get(UPLOADS_FOLDER).resolve(entidad);
        try{
            Files.createDirectories(carpeta); /*Crea el directorio si no existe en la raíz del proyecto -> public/entidad (public/docente | public/alumno)*/
            archivo.transferTo(carpeta.resolve(nombre));
            return nombre;
        }catch(IOException e){
            System.err.println("Error guardando el archivo: " + e.getMessage());
            return null;
        }
    }

    /* Elimina el archivo */
    @Override
    public boolean delete(String archivo, String entidad) {
        Path archivoRuta = Paths.get(UPLOADS_FOLDER).resolve(entidad).resolve(archivo);
        File file = archivoRuta.toFile();
        if(file.exists() && file.canRead()){
            return file.delete();
        }
        return false;
    }
}