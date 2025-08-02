package org.alilopez.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.UploadedFile;
import org.alilopez.model.Cat;
import org.alilopez.repository.CatsRepository;
import org.alilopez.service.CatsService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import java.util.function.BiFunction;


public class CatsController {
    private final CatsService catsService;
    private final CatsRepository catsRepository;
    public CatsController(CatsService catsService,  CatsRepository catsRepository) {
        this.catsService = catsService;
        this.catsRepository = new CatsRepository();
    }

    public void create(Context ctx) {
        try {

            int edad = Integer.parseInt(ctx.formParam("edad"));
            String nombre = ctx.formParam("nombre");
            String descripcion = ctx.formParam("descripcion");
            int id_status_gato  = Integer.parseInt(ctx.formParam("id_status_gato"));
            int id_sexo  = Integer.parseInt(ctx.formParam("id_sexo"));
            int id_estado_salud  = Integer.parseInt(ctx.formParam("id_estado_salud"));
            Cat createCat = new Cat(edad,nombre,descripcion,id_status_gato,id_sexo, id_estado_salud);
            int id_gato = catsService.create(createCat);

            UploadedFile imagenPrincipal = ctx.uploadedFile("img_principal");
            UploadedFile imagenExtra1 = ctx.uploadedFile("img_extra1");
            UploadedFile imagenExtra2 = ctx.uploadedFile("img_extra2");
            UploadedFile imagenExtra3 = ctx.uploadedFile("img_extra3");
            String basePath = "uploads/gatos"; // Ruta base (debe existir en tu proyecto)
            Path carpetaGato = Paths.get(basePath, String.valueOf(id_gato));

            if (!Files.exists(carpetaGato)) {
                Files.createDirectories(carpetaGato);
            }

            BiFunction<UploadedFile, String, String> guardarImagen = (archivo, nombreArchivo) -> {
                if (archivo != null && archivo.content() != null && archivo.contentType().startsWith("image")) {
                    Path destino = carpetaGato.resolve(nombreArchivo);
                    try (InputStream input = archivo.content()) {
                        Files.copy(input, destino, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        throw new RuntimeException("Error al guardar imagen: " + nombreArchivo, e);
                    }
                    // Devuelve la URL relativa que puedes guardar en la base de datos
                    return "gatos/" + id_gato + "/" + nombreArchivo;
                }
                return null;
            };

            String urlPrincipal = guardarImagen.apply(imagenPrincipal, "principal.jpg");
            String urlExtra1 = guardarImagen.apply(imagenExtra1, "extra1.jpg");
            String urlExtra2 = guardarImagen.apply(imagenExtra2, "extra2.jpg");
            String urlExtra3 = guardarImagen.apply(imagenExtra3, "extra3.jpg");

            catsRepository.saveImgsCat(id_gato, urlPrincipal, true); // principal
            catsRepository.saveImgsCat(id_gato, urlExtra1, false);
            catsRepository.saveImgsCat(id_gato, urlExtra2, false);
            catsRepository.saveImgsCat(id_gato, urlExtra3, false);

            limpiarArchivosTemporales(imagenPrincipal, imagenExtra1, imagenExtra2, imagenExtra3);
            ctx.status(HttpStatus.CREATED).result("El gato fue registrado");
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result(e.getMessage());
        }
    }


    private void limpiarArchivosTemporales(UploadedFile... archivos) {
        for (UploadedFile archivo : archivos) {
            if (archivo != null && archivo.content() != null) {
                try {
                    archivo.content().close();
                } catch (IOException e) {
                    // Log el error pero no fallar
                    System.err.println("Error cerrando stream: " + e.getMessage());
                }
            }
        }
    }

}
