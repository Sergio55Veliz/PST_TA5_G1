package com.example.amst1.ui.home;

import java.io.Serializable;

public class ItemList implements Serializable {
    private String titulo;
    private String autor;
    private String editorial;
    private String imgURL;
    private String resumen;

    public ItemList(String titulo, String autor, String editorial, String imgURL, String resumen){
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.imgURL = imgURL;
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

    public String getImgURL() {
        return "https://flightsregister.000webhostapp.com/img/"+imgURL+".jpg";
    }

    public String getResumen() { return resumen; }

}
