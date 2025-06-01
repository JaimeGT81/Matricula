package com.matricula.controller;

import com.matricula.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/* Controlador para obtener la foto */
@Controller
@RequestMapping("/fotos")
public class ArchivoController {

    private final static String UPLOADS_FOLDER = "public";

    @Autowired
    private UploadFileService uploadFileService;

    /* Obtener la foto y mostrarla al editar */
    @GetMapping("/{entidad}/{nombre}")
    @ResponseBody
    public ResponseEntity<Resource> showFoto(@PathVariable("entidad") String entidad, @PathVariable("nombre")String nombre){
        try{
            Resource recurso = uploadFileService.load(nombre,entidad);
            Path ruta = Paths.get(UPLOADS_FOLDER).resolve(entidad).resolve(nombre);
            String mime = Files.probeContentType(ruta);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mime))
                    .body(recurso);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}