package com.example.examenandroid.Model;

public class MovieModelClass {
    String peliculaId;
    String peliculaNumero;
    String peliculaNom;
    String peliculaImg;
    String peliculaDesc;
    public MovieModelClass(String peliculaId,String peliculaNumero, String name, String img,String PeliculaDesc) {
        this.peliculaId = peliculaId;
        this.peliculaNumero=peliculaNumero;
        this.peliculaNom = name;
        this.peliculaImg = img;
        this.peliculaDesc = PeliculaDesc;

    }

    public MovieModelClass() {
    }

    public String getPeliculaId() {
        return peliculaId;
    }

    public void setPeliculaId(String peliculaId) {
        this.peliculaId = peliculaId;
    }

    public String getPeliculaNumero() {
        return peliculaNumero;
    }

    public void setPeliculaNumero(String peliculaNumero) {
        this.peliculaNumero = peliculaNumero;
    }

    public String getPeliculaNom() {
        return peliculaNom;
    }

    public void setPeliculaNom(String peliculaNom) {
        this.peliculaNom = peliculaNom;
    }

    public String getPeliculaImg() {
        return peliculaImg;
    }

    public void setPeliculaImg(String peliculaImg) {
        this.peliculaImg = peliculaImg;
    }

    public String getPeliculaDesc() {
        return peliculaDesc;
    }

    public void setPeliculaDesc(String peliculaDesc) {
        this.peliculaDesc = peliculaDesc;
    }
}
