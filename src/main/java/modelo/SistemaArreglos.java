package modelo;

import java.util.ArrayList;
import java.util.List;

public class SistemaArreglos {
    private List<Cliente> listaClientes;
    private List<Postulante> listaPostulantes;

    public SistemaArreglos() {
        this.listaClientes = new ArrayList<>();
        this.listaPostulantes = new ArrayList<>();
    }

    public void registrarCliente(Cliente c) { this.listaClientes.add(c); }
    public void registrarPostulante(Postulante p) { this.listaPostulantes.add(p); }
    public List<Cliente> getListaClientes() { return listaClientes; }

    public Cliente buscarCliente(String email, String clave) {
        for (Cliente c : listaClientes) {
            if (c.getEmail().equalsIgnoreCase(email) && c.getClave().equals(clave)) return c;
        }
        return null;
    }

    public Postulante buscarPostulante(String email, String clave) {
        for (Postulante p : listaPostulantes) {
            if (p.getEmail().equalsIgnoreCase(email) && p.getClave().equals(clave)) return p;
        }
        return null;
    }
}