package modelo;

public class Cliente {
    private String RUC;
    private String razonSocial;
    private String email;
    private String contacto;
    private String telefono;
    private String clave;
    private Rubro rubro;
    
    // Arreglo nativo según UML
    private Oferta[] ofertas;
    private int contadorOfertas;

    public Cliente() {
        this.ofertas = new Oferta[50];
        this.contadorOfertas = 0;
    }

    public Cliente(String RUC, String razonSocial, String email, String contacto, String telefono, String clave, Rubro rubro) {
        this.RUC = RUC;
        this.razonSocial = razonSocial;
        this.email = email;
        this.contacto = contacto;
        this.telefono = telefono;
        this.clave = clave;
        this.rubro = rubro;
        this.ofertas = new Oferta[50];
        this.contadorOfertas = 0;
    }

    public boolean agregarOferta(Oferta oferta) {
        if (contadorOfertas >= ofertas.length) return false;
        this.ofertas[contadorOfertas] = oferta;
        contadorOfertas++;
        return true;
    }

    public boolean eliminarOferta(Oferta oferta) {
        int posicion = -1;
        for (int i = 0; i < contadorOfertas; i++) {
            if (ofertas[i] == oferta) {
                posicion = i;
                break;
            }
        }
        if (posicion == -1) return false;
        
        for (int i = posicion; i < contadorOfertas - 1; i++) {
            ofertas[i] = ofertas[i + 1];
        }
        ofertas[contadorOfertas - 1] = null;
        contadorOfertas--;
        return true;
    }

    // Firma exacta del UML: Retorna Oferta[]
    public Oferta[] getOfertas() {
        Oferta[] actuales = new Oferta[contadorOfertas];
        System.arraycopy(this.ofertas, 0, actuales, 0, contadorOfertas);
        return actuales;
    }

    public String getRUC() { return RUC; }
    public void setRUC(String RUC) { this.RUC = RUC; }
    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
    public Rubro getRubro() { return rubro; }
    public void setRubro(Rubro rubro) { this.rubro = rubro; }
}