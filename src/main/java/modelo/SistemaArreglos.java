package modelo;

public class SistemaArreglos {
    private Cliente[] listaClientes;
    private int countClientes;
    private Postulante[] listaPostulantes;
    private int countPostulantes;

    public SistemaArreglos() {
        this.listaClientes = new Cliente[100];
        this.countClientes = 0;
        this.listaPostulantes = new Postulante[100];
        this.countPostulantes = 0;
    }

    public void registrarCliente(Cliente c) { 
        if (countClientes < listaClientes.length) {
            listaClientes[countClientes++] = c;
        }
    }
    
    public void registrarPostulante(Postulante p) { 
        if (countPostulantes < listaPostulantes.length) {
            listaPostulantes[countPostulantes++] = p;
        }
    }

    public Cliente[] getListaClientes() {
        Cliente[] actuales = new Cliente[countClientes];
        System.arraycopy(listaClientes, 0, actuales, 0, countClientes);
        return actuales;
    }

    public Cliente buscarCliente(String email, String clave) {
        for (int i = 0; i < countClientes; i++) {
            if (listaClientes[i].getEmail().equalsIgnoreCase(email) && listaClientes[i].getClave().equals(clave)) {
                return listaClientes[i];
            }
        }
        return null;
    }

    public Postulante buscarPostulante(String email, String clave) {
        for (int i = 0; i < countPostulantes; i++) {
            if (listaPostulantes[i].getEmail().equalsIgnoreCase(email) && listaPostulantes[i].getClave().equals(clave)) {
                return listaPostulantes[i];
            }
        }
        return null;
    }
}