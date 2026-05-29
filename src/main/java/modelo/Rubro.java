package modelo;

public class Rubro {
    private String nombre;
    private boolean estado;

    public Rubro() {}

    public Rubro(String nombre, boolean estado) {
        this.nombre = nombre;
        this.estado = estado;
    }

    public boolean habilitar() {
        this.estado = true;
        return true;
    }

    public boolean deshabilitar() {
        this.estado = false;
        return true;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}