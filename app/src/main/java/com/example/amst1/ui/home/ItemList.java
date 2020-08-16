package com.example.amst1.ui.home;

import java.io.Serializable;

public class ItemList implements Serializable {
    private String titulo;
    private String autor;
    private String editorial;
    private int imgResource;
    private String resumen;

    public ItemList(String titulo, String autor, String editorial, int imgResource, String resumen){
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.imgResource = imgResource;
        this.resumen = resumen;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public int getImgResource() { return imgResource; }

    public String getResumen() { return resumen; }

}
