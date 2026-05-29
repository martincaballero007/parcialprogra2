package modelo;

import java.util.Date;

public class Oferta {
    private String puesto;
    private String descripcion;
    private String area;
    private Date fechaInicio;
    private Date fechaTermino;
    
    // Arreglo nativo según el diagrama UML
    private Requisito[] requisitos;
    private int contadorRequisitos;

    public Oferta() {
        this.requisitos = new Requisito[20]; // Tamaño límite de requisitos
        this.contadorRequisitos = 0;
    }

    public Oferta(String puesto, String descripcion, String area, Date fechaInicio, Date fechaTermino) {
        this.puesto = puesto;
        this.descripcion = descripcion;
        this.area = area;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
        this.requisitos = new Requisito[20];
        this.contadorRequisitos = 0;
    }

    public boolean agregarRequisito(int orden, String descripcion) {
        if (contadorRequisitos >= requisitos.length) return false;
        
        Requisito nuevo = new Requisito(orden, descripcion, true);
        
        // Algoritmo de Inserción: Mantiene el "orden determinado" desplazando elementos
        int i = contadorRequisitos - 1;
        while (i >= 0 && requisitos[i].getOrden() > orden) {
            requisitos[i + 1] = requisitos[i];
            i--;
        }
        requisitos[i + 1] = nuevo;
        contadorRequisitos++;
        return true;
    }

    public boolean eliminarRequisito(int orden) {
        int posicion = -1;
        for (int i = 0; i < contadorRequisitos; i++) {
            if (requisitos[i].getOrden() == orden) {
                posicion = i;
                break;
            }
        }
        if (posicion == -1) return false;
        
        for (int i = posicion; i < contadorRequisitos - 1; i++) {
            requisitos[i] = requisitos[i + 1];
        }
        requisitos[contadorRequisitos - 1] = null;
        contadorRequisitos--;
        return true;
    }

    // Firma exacta del UML: Retorna Requisito[]
    public Requisito[] getRequisitos() {
        Requisito[] actuales = new Requisito[contadorRequisitos];
        System.arraycopy(this.requisitos, 0, actuales, 0, contadorRequisitos);
        return actuales;
    }

    public String getPuesto() { return puesto; }
    public void setPuesto(String puesto) { this.puesto = puesto; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }
    public Date getFechaTermino() { return fechaTermino; }
    public void setFechaTermino(Date fechaTermino) { this.fechaTermino = fechaTermino; }
}