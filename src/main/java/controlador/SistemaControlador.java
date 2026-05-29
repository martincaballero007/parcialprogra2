package controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.swing.JOptionPane;

import modelo.Cliente;
import modelo.GradoEstudio;
import modelo.Oferta;
import modelo.Postulante;
import modelo.Requisito;
import modelo.Rubro;
import modelo.SistemaArreglos;
import vista.SistemaVista;

public class SistemaControlador {
    private SistemaVista vista;
    private SistemaArreglos baseDatos;
    
    public SistemaControlador(SistemaVista vista) {
        this.vista = vista;
        this.baseDatos = new SistemaArreglos();
        cargarDatosPrueba(); // Carga datos para evaluar rápido
        inicializarEventos();
    }
    
    private void cargarDatosPrueba() {
        Cliente empresa = new Cliente("20123456789", "Tech Corp", "rrhh@tech.com", "Ana", "999888777", "123", new Rubro("TI", true));
        Oferta oferta = new Oferta("Dev Java", "Backend", "Sistemas", new Date(), new Date(System.currentTimeMillis() + 864000000));
        oferta.agregarRequisito(2, "Manejo de SQL");
        oferta.agregarRequisito(1, "Java Avanzado"); // Se ordenará primero automáticamente
        empresa.agregarOferta(oferta);
        baseDatos.registrarCliente(empresa);
        
        Postulante candidato = new Postulante("cand@email.com", "Juan", "Perez", "987654321", "Lima", new Date(), "clave1");
        candidato.asignarGradoEstudio(new GradoEstudio("Bachiller"));
        baseDatos.registrarPostulante(candidato);
    }
    
    private void inicializarEventos() {
        // 1. REGISTRO EMPRESAS
        vista.addRegistroEmpresasListener(e -> {
            try {
                String[] opciones = {"Ingresar", "Registrar Empresa"};
                int sel = JOptionPane.showOptionDialog(vista, "Empresas", "Seleccione", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
                
                if (sel == 0) { // Login
                    String email = JOptionPane.showInputDialog(vista, "Correo Institucional:");
                    if (email == null) return;
                    String clave = JOptionPane.showInputDialog(vista, "Contraseña:");
                    
                    Cliente c = baseDatos.buscarCliente(email, clave);
                    if (c == null) throw new Exception("Credenciales incorrectas.");
                    vista.mostrarMensaje("¡Bienvenido, " + c.getRazonSocial() + "!");
                    
                } else if (sel == 1) { // Registro
                    String ruc = JOptionPane.showInputDialog(vista, "RUC:");
                    String razon = JOptionPane.showInputDialog(vista, "Razón Social:");
                    String email = JOptionPane.showInputDialog(vista, "Correo:");
                    String contacto = JOptionPane.showInputDialog(vista, "Persona Contacto:");
                    String telf = JOptionPane.showInputDialog(vista, "Teléfono:");
                    String clave = JOptionPane.showInputDialog(vista, "Contraseña:");
                    
                    baseDatos.registrarCliente(new Cliente(ruc, razon, email, contacto, telf, clave, new Rubro("General", true)));
                    vista.mostrarMensaje("Empresa registrada con éxito.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 2. OFERTAS DE TRABAJO
        vista.addOfertasTrabajoListener(e -> {
            try {
                String email = JOptionPane.showInputDialog(vista, "Valide su correo de Empresa:");
                if (email == null) return;
                String clave = JOptionPane.showInputDialog(vista, "Contraseña:");
                
                Cliente c = baseDatos.buscarCliente(email, clave);
                if (c == null) throw new Exception("Autenticación fallida.");
                
                String puesto = JOptionPane.showInputDialog(vista, "Puesto:");
                String desc = JOptionPane.showInputDialog(vista, "Descripción:");
                String area = JOptionPane.showInputDialog(vista, "Área:");
                
                Oferta nueva = new Oferta(puesto, desc, area, new Date(), new Date(System.currentTimeMillis() + 864000000));
                
                int numReqs = Integer.parseInt(JOptionPane.showInputDialog(vista, "¿Cuántos requisitos agregará?"));
                for (int i = 0; i < numReqs; i++) {
                    int orden = Integer.parseInt(JOptionPane.showInputDialog(vista, "Orden de prioridad (ej. 1, 2):"));
                    String detalle = JOptionPane.showInputDialog(vista, "Detalle del requisito:");
                    nueva.agregarRequisito(orden, detalle);
                }
                c.agregarOferta(nueva);
                vista.mostrarMensaje("Oferta publicada correctamente.");
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Debe ingresar un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 3. CANDIDATOS Y POSTULACIÓN
        vista.addCandidatosListener(e -> {
            try {
                String[] opciones = {"Ingresar y Postular", "Registrarse"};
                int sel = JOptionPane.showOptionDialog(vista, "Candidatos", "Seleccione", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
                
                if (sel == 0) {
                    String email = JOptionPane.showInputDialog(vista, "Email:");
                    if (email == null) return;
                    String clave = JOptionPane.showInputDialog(vista, "Clave autogenerada:");
                    
                    Postulante p = baseDatos.buscarPostulante(email, clave);
                    if (p == null) throw new Exception("Credenciales incorrectas.");
                    
                    ArrayList<Oferta> disp = new ArrayList<>();
                    StringBuilder sb = new StringBuilder("Ofertas Disponibles:\n");
                    int idx = 1;
                    
                    for (Cliente c : baseDatos.getListaClientes()) {
                        for (Oferta o : c.getOfertas()) {
                            disp.add(o);
                            sb.append(idx).append(". ").append(o.getPuesto()).append(" (").append(c.getRazonSocial()).append(")\n");
                            for (Requisito r : o.getRequisitos()) {
                                sb.append("   - [").append(r.getOrden()).append("] ").append(r.getDescripcion()).append("\n");
                            }
                            idx++;
                        }
                    }
                    if (disp.isEmpty()) {
                        vista.mostrarMensaje("No hay ofertas activas.");
                        return;
                    }
                    
                    String input = JOptionPane.showInputDialog(vista, sb.toString() + "\nIngrese el número de la oferta para postular:");
                    if (input != null) {
                        int seleccion = Integer.parseInt(input) - 1;
                        if (seleccion < 0 || seleccion >= disp.size()) throw new IndexOutOfBoundsException("Oferta inválida.");
                        
                        p.postular(disp.get(seleccion));
                        vista.mostrarMensaje("¡Postulación registrada con fecha " + new Date() + "!");
                    }
                    
                } else if (sel == 1) {
                    String email = JOptionPane.showInputDialog(vista, "Email:");
                    String nom = JOptionPane.showInputDialog(vista, "Nombres:");
                    String ape = JOptionPane.showInputDialog(vista, "Apellidos:");
                    String telf = JOptionPane.showInputDialog(vista, "Teléfono:");
                    String dir = JOptionPane.showInputDialog(vista, "Dirección:");
                    
                    // El ejercicio pide clave autogenerada
                    String claveGen = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
                    
                    Postulante nuevo = new Postulante(email, nom, ape, telf, dir, new Date(), claveGen);
                    nuevo.asignarGradoEstudio(new GradoEstudio("Estudiante"));
                    baseDatos.registrarPostulante(nuevo);
                    vista.mostrarMensaje("Registrado. Se envió a su email su clave autogenerada: " + claveGen);
                }
            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(vista, "Selección inválida.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // SALIR
        vista.addSalirListener(e -> {
            if (JOptionPane.showConfirmDialog(vista, "¿Desea salir?", "Salir", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
    
    public void iniciar() { vista.setVisible(true); }
}