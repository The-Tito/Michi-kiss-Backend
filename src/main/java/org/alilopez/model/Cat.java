package org.alilopez.model;

public class Cat {
    private int id_gato;
    private int edad;
    private String nombre;
    private String descripcion;
    private int id_status_gato;
    private int id_sexo;
    private int id_estado_salud;
    private String url_imagenPrincipal;
    private String url_imagenExtra1;
    private String url_imagenExtra2;
    private String url_imagenExtra3;

    public Cat() {
    }

    public Cat(int edad, String nombre, String descripcion, int id_status_gato, int id_sexo, int id_estado_salud) {
        this.edad = edad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id_status_gato = id_status_gato;
        this.id_sexo = id_sexo;
        this.id_estado_salud = id_estado_salud;
    }

    public int getId_gato() {
        return id_gato;
    }

    public void setId_gato(int id_gato) {
        this.id_gato = id_gato;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId_status_gato() {
        return id_status_gato;
    }

    public void setId_status_gato(int id_status_gato) {
        this.id_status_gato = id_status_gato;
    }

    public int getId_sexo() {
        return id_sexo;
    }

    public void setId_sexo(int id_sexo) {
        this.id_sexo = id_sexo;
    }

    public int getId_estado_salud() {
        return id_estado_salud;
    }

    public void setId_estado_salud(int id_estado_salud) {
        this.id_estado_salud = id_estado_salud;
    }

    public String getUrl_imagenPrincipal() {
        return url_imagenPrincipal;
    }

    public void setUrl_imagenPrincipal(String url_imagenPrincipal) {
        this.url_imagenPrincipal = url_imagenPrincipal;
    }

    public String getUrl_imagenExtra1() {
        return url_imagenExtra1;
    }

    public void setUrl_imagenExtra1(String url_imagenExtra1) {
        this.url_imagenExtra1 = url_imagenExtra1;
    }

    public String getUrl_imagenExtra2() {
        return url_imagenExtra2;
    }

    public void setUrl_imagenExtra2(String url_imagenExtra2) {
        this.url_imagenExtra2 = url_imagenExtra2;
    }

    public String getUrl_imagenExtra3() {
        return url_imagenExtra3;
    }

    public void setUrl_imagenExtra3(String url_imagenExtra3) {
        this.url_imagenExtra3 = url_imagenExtra3;
    }
}
