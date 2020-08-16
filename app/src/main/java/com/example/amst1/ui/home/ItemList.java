package com.example.amst1.ui;

import java.io.Serializable;

public class ItemList implements Serializable {
    private String titulo;
    private String autor;
    private String editorial;
    private int imgResource;

    public ItemList(String titulo, String autor, String editorial, int imgResource){
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.imgResource = imgResource;
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
}
