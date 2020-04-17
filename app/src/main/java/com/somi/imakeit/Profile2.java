package com.somi.imakeit;

public class Profile2 {
    private String name;
    private String rango;
    private String profesion;
    private String recompensa;
    private String corona;
    private String imagen;
    private String id;
    private String disponible;

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    public String getId() {
        return id;
    }



    public void setId(String id) {
        this.id = id;
    }

    public Profile2() {
    }

    public Profile2(String name, String rango, String profesion, String recompensa, String corona, String imagen, String id, String disponible) {
        this.name = name;
        this.rango = rango;
        this.profesion = profesion;
        this.recompensa = recompensa;
        this.corona = corona;
        this.imagen = imagen;
        this.id = id;
        this.disponible = disponible;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(String recompensa) {
        this.recompensa = recompensa;
    }

    public String getCorona() {
        return corona;
    }

    public void setCorona(String corona) {
        this.corona = corona;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}