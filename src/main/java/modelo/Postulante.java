package modelo;

import java.util.Date;

public class Postulante {
    private String email;
    private String nombres;
    private String apellidos;
    private String telefono; // Mantiene los 7 parámetros de tu corrección exitosa
    private String direccion;
    private Date nacimiento;
    private String clave;
    private GradoEstudio gradoEstudio;
    
    // Arreglo nativo según UML
    private Postulacion[] postulaciones;
    private int contadorPostulaciones;

    public Postulante() {
        this.postulaciones = new Postulacion[100];
        this.contadorPostulaciones = 0;
    }

    public Postulante(String email, String nombres, String apellidos, String telefono, String direccion, Date nacimiento, String clave) {
        this.email = email;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.direccion = direccion;
        this.nacimiento = nacimiento;
        this.clave = clave;
        this.postulaciones = new Postulacion[100];
        this.contadorPostulaciones = 0;
    }

    public boolean asignarGradoEstudio(GradoEstudio grado) {
        this.gradoEstudio = grado;
        return true;
    }

    public boolean postular(Oferta oferta) {
        if (contadorPostulaciones >= postulaciones.length) return false;
        Postulacion postulacion = new Postulacion(new Date(), oferta);
        this.postulaciones[contadorPostulaciones] = postulacion;
        contadorPostulaciones++;
        return true;
    }

    public boolean anularPostulacion(Postulacion postulacion) {
        int posicion = -1;
        for (int i = 0; i < contadorPostulaciones; i++) {
            if (postulaciones[i] == postulacion) {
                posicion = i;
                break;
            }
        }
        if (posicion == -1) return false;

        postulaciones[posicion].setAnulado(true);
        postulaciones[posicion].setFechaAnulacion(new Date());
        return true;
    }

    // Firma exacta del UML: Retorna Postulacion[]
    public Postulacion[] getPostulaciones() {
        Postulacion[] actuales = new Postulacion[contadorPostulaciones];
        System.arraycopy(this.postulaciones, 0, actuales, 0, contadorPostulaciones);
        return actuales;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public Date getNacimiento() { return nacimiento; }
    public void setNacimiento(Date nacimiento) { this.nacimiento = nacimiento; }
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
    public GradoEstudio getGradoEstudio() { return gradoEstudio; }
}