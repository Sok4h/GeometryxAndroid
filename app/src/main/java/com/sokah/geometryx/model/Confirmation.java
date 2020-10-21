package com.sokah.geometryx.model;

public class Confirmation {
    private String type ="Confirmation";
    boolean confirmacion;

    public Confirmation(boolean confirmacion){

        this.confirmacion=confirmacion;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(boolean confirmacion) {
        this.confirmacion = confirmacion;
    }
}
