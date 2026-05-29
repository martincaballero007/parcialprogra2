package controlador;

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
        cargarDatosPrueba(); 
        inicializarEventos();
    }
    
    private void cargarDatosPrueba() {
        Cliente empresa = new Cliente("20100300400", "Tech Solutions", "contacto@tech.com", "M. Caballero", "999555444", "123", new Rubro("TI", true));
        Oferta oferta = new Oferta("Analista QA", "Pruebas de software automáticas", "Sistemas", new Date(), new Date(System.currentTimeMillis() + 900000000));
        
        oferta.agregarRequisito(2, "Conocimientos de Selenium / JUnit");
        oferta.agregarRequisito(1, "Lógica de programación sólida"); // Se ordenará primero por el algoritmo de inserción
        
        empresa.agregarOferta(oferta);
        baseDatos.registrarCliente(empresa);
        
        Postulante p = new Postulante("postulante@unmsm.edu.pe", "Martín", "Caballero", "999888777", "Av. Universitaria", new Date(), "clave123");
        p.asignarGradoEstudio(new GradoEstudio("Ciencia de la Computación"));
        baseDatos.registrarPostulante(p);
    }
    
    private void inicializarEventos() {
        // 1. GESTIÓN DE EMPRESAS
        vista.addRegistroEmpresasListener(e -> {
            try {
                String[] opciones = {"Ingresar", "Registrar Empresa"};
                int sel = JOptionPane.showOptionDialog(vista, "Seleccione Acción", "Módulo Empresas", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
                
                if (sel == 0) {
                    String email = JOptionPane.showInputDialog(vista, "Correo Corporativo:");
                    if (email == null) return;
                    String clave = JOptionPane.showInputDialog(vista, "Contraseña:");
                    
                    Cliente c = baseDatos.buscarCliente(email, clave);
                    if (c == null) throw new Exception("Credenciales incorrectas. Empresa no registrada.");
                    vista.mostrarMensaje("¡Acceso concedido a la empresa " + c.getRazonSocial() + "!");
                } else if (sel == 1) {
                    String ruc = JOptionPane.showInputDialog(vista, "RUC (11 dígitos):");
                    if (ruc == null || ruc.trim().length() != 11) throw new Exception("El RUC debe poseer 11 caracteres.");
                    String razon = JOptionPane.showInputDialog(vista, "Razón Social:");
                    String email = JOptionPane.showInputDialog(vista, "Correo:");
                    String contacto = JOptionPane.showInputDialog(vista, "Contacto:");
                    String telf = JOptionPane.showInputDialog(vista, "Teléfono:");
                    String clave = JOptionPane.showInputDialog(vista, "Contraseña:");
                    
                    baseDatos.registrarCliente(new Cliente(ruc, razon, email, contacto, telf, clave, new Rubro("General", true)));
                    vista.mostrarMensaje("Empresa registrada correctamente.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, ex.getMessage(), "Fallo de Operación", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 2. PUBLICACIÓN DE OFERTAS
        vista.addOfertasTrabajoListener(e -> {
            try {
                String email = JOptionPane.showInputDialog(vista, "Autentique el correo de su Empresa:");
                if (email == null) return;
                String clave = JOptionPane.showInputDialog(vista, "Contraseña:");
                
                Cliente c = baseDatos.buscarCliente(email, clave);
                if (c == null) throw new Exception("Empresa no encontrada.");
                
                String puesto = JOptionPane.showInputDialog(vista, "Nombre del Puesto:");
                String desc = JOptionPane.showInputDialog(vista, "Descripción:");
                String area = JOptionPane.showInputDialog(vista, "Área organizativa:");
                
                Oferta nueva = new Oferta(puesto, desc, area, new Date(), new Date(System.currentTimeMillis() + 864000000));
                
                int numReqs = Integer.parseInt(JOptionPane.showInputDialog(vista, "¿Cuántos requisitos registrará?"));
                for (int i = 0; i < numReqs; i++) {
                    int orden = Integer.parseInt(JOptionPane.showInputDialog(vista, "Orden de prioridad para el requisito " + (i + 1) + ":"));
                    String detalle = JOptionPane.showInputDialog(vista, "Descripción del requisito:");
                    nueva.agregarRequisito(orden, detalle);
                }
                c.agregarOferta(nueva);
                vista.mostrarMensaje("Oferta laboral publicada exitosamente.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Error: Debe ingresar valores enteros en las casillas de cantidades.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 3. SECCIÓN POSTULANTES
        vista.addCandidatosListener(e -> {
            try {
                String[] opciones = {"Ingresar y Postular", "Registrarse"};
                int sel = JOptionPane.showOptionDialog(vista, "Seleccione Acción", "Módulo Candidatos", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
                
                if (sel == 0) {
                    String email = JOptionPane.showInputDialog(vista, "Email del postulante:");
                    if (email == null) return;
                    String clave = JOptionPane.showInputDialog(vista, "Clave autogenerada:");
                    
                    Postulante p = baseDatos.buscarPostulante(email, clave);
                    if (p == null) throw new Exception("Datos de postulante no válidos.");
                    
                    // Utilizamos un arreglo dinámico local solo para manejar las opciones visuales del usuario en el JOptionPane
                    java.util.ArrayList<Oferta> disponibles = new java.util.ArrayList<>();
                    StringBuilder sb = new StringBuilder("Ofertas Activas de Trabajo:\n");
                    int num = 1;
                    
                    // Iteramos sobre los ARREGLOS ESTÁTICOS exigidos por el UML
                    Cliente[] clientesActuales = baseDatos.getListaClientes();
                    for (int i = 0; i < clientesActuales.length; i++) {
                        Cliente c = clientesActuales[i];
                        Oferta[] ofertasEmpresa = c.getOfertas();
                        
                        for (int j = 0; j < ofertasEmpresa.length; j++) {
                            Oferta o = ofertasEmpresa[j];
                            disponibles.add(o);
                            sb.append(num).append(". ").append(o.getPuesto()).append(" (").append(c.getRazonSocial()).append(")\n");
                            
                            Requisito[] reqs = o.getRequisitos();
                            for (int k = 0; k < reqs.length; k++) {
                                sb.append("   - [").append(reqs[k].getOrden()).append("] ").append(reqs[k].getDescripcion()).append("\n");
                            }
                            num++;
                        }
                    }
                    
                    if (disponibles.isEmpty()) {
                        vista.mostrarMensaje("No hay ofertas de trabajo registradas por el momento.");
                        return;
                    }
                    
                    String input = JOptionPane.showInputDialog(vista, sb.toString() + "\nEscriba el número de la oferta a postular:");
                    if (input != null) {
                        int index = Integer.parseInt(input) - 1;
                        if (index < 0 || index >= disponibles.size()) throw new IndexOutOfBoundsException("La oferta seleccionada está fuera de los rangos.");
                        
                        p.postular(disponibles.get(index));
                        vista.mostrarMensaje("¡Postulación formalizada con éxito!\nFecha de registro: " + new Date());
                    }
                } else if (sel == 1) {
                    String email = JOptionPane.showInputDialog(vista, "Email de contacto:");
                    String nom = JOptionPane.showInputDialog(vista, "Nombres:");
                    String ape = JOptionPane.showInputDialog(vista, "Apellidos:");
                    String telf = JOptionPane.showInputDialog(vista, "Teléfono:");
                    String dir = JOptionPane.showInputDialog(vista, "Dirección actual:");
                    
                    String claveGenerada = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
                    
                    Postulante nuevo = new Postulante(email, nom, ape, telf, dir, new Date(), claveGenerada);
                    nuevo.asignarGradoEstudio(new GradoEstudio("Pregrado"));
                    
                    baseDatos.registrarPostulante(nuevo);
                    vista.mostrarMensaje("Registro exitoso. Guarde su clave autogenerada de acceso: " + claveGenerada);
                }
            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(vista, "Selección inválida.", "Advertencia de Rango", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 0. SALIR
        vista.addSalirListener(e -> {
            if (JOptionPane.showConfirmDialog(vista, "¿Desea cerrar el sistema?", "Confirmación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
    
    public void iniciar() { vista.setVisible(true); }
}