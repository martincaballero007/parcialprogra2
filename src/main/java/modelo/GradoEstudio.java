package modelo;

public class GradoEstudio {
    private String descripcion;

    public GradoEstudio() {}

    public GradoEstudio(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}