package ara.planificadorrecetas;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.filechooser.FileNameExtensionFilter;

import ara.planificadorrecetas.modelo.Cantidad;
import ara.planificadorrecetas.modelo.Configurador;
import ara.planificadorrecetas.modelo.ConfiguradorFileDAO;
import ara.planificadorrecetas.modelo.Criterio;
import ara.planificadorrecetas.modelo.Ingrediente;
import ara.planificadorrecetas.modelo.ItemListaCompra;
import ara.planificadorrecetas.modelo.ListaCompra;
import ara.planificadorrecetas.modelo.PeriodoDia;
import ara.planificadorrecetas.modelo.Planificador;
import ara.planificadorrecetas.modelo.Receta;
import ara.planificadorrecetas.modelo.Tiempo;
import ara.planificadorrecetas.modelo.TipoReceta;
import ara.planificadorrecetas.modelo.UnidadCantidad;
import ara.planificadorrecetas.vista.PanelAcercaDe;
import ara.planificadorrecetas.vista.PanelConfiguracion;
import ara.planificadorrecetas.vista.PanelIngrediente;
import ara.planificadorrecetas.vista.PanelListaCompra;
import ara.planificadorrecetas.vista.PanelMostrarCalendario;
import ara.planificadorrecetas.vista.PanelMostrarRecetas;
import ara.planificadorrecetas.vista.PanelReceta;
import ara.planificadorrecetas.vista.PanelTipo;
import ara.planificadorrecetas.vista.PanelUnidadCantidad;
import ara.planificadorrecetas.vista.TableModelConfiguradorCriterios;

public class ControladorPlanificadorRecetas {
    private static final String LA_UNIDAD = "La unidad ";
    private static final String YA_EXISTE = " ya existe";
    private static final String YA_EXISTE_PUNTO = YA_EXISTE + ".";
    private static final String ERROR = "Error";
    private static final String EL_TIPO = "El tipo ";
    private static final String EL_INGREDIENTE = "El ingrediente ";
    private static final String EDITAR_TIPO = "Editar Tipo";
    private static final String EDITAR = "Editar";
    private static final String BORRAR = "Borrar";
    private static final String CANCELAR = "Cancelar";
    private static final String DESEABA_BORRAR = " que se deseaba borrar.";
    private static final String LA_RECETA = "La receta ";
    private static final String NO_EXISTE = " no existe.";

    private ModeloPlanificadorRecetas modelo;
    private VistaPlanificadorRecetas vista;
    private PanelReceta panelAddReceta;
    private PanelTipo panelAddTipo;
    private JDialog dialogNuevoTipo;
    private PanelIngrediente panelAddIngrediente;
    private JDialog dialogNuevoIngrediente;
    private PanelUnidadCantidad panelAddUnidad;
    private JDialog dialogNuevaUnidad;
    private PanelConfiguracion panelConfiguracion;
    private PanelMostrarCalendario panelMostrarCalendario;
    private PanelListaCompra panelListaCompra;
    private PanelMostrarRecetas panelMostrarRecetas;
    
    public ControladorPlanificadorRecetas(final ModeloPlanificadorRecetas mpr, final VistaPlanificadorRecetas vpr) {
        this.modelo = mpr;
        this.vista = vpr;
        this.panelAddReceta = new PanelReceta(this.modelo);
        this.panelConfiguracion = new PanelConfiguracion(this.modelo);
        this.panelMostrarRecetas = new PanelMostrarRecetas(this.modelo);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final Dimension frameSize = this.vista.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        this.vista.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        this.vista.setDefaultCloseOperation(3);
        this.vista.setVisible(true);
        this.vista.addExitListener(this::exitActionPerformed);
        this.vista.addRecetaListener(this::addRecetaActionPerformed);
        this.panelAddReceta.addRecetaBotonNuevaUnidadListener(this::addRecetaBotonNuevaUnidadActionPerformed);
        this.panelAddReceta.addRecetaBotonNuevoTipoListener(this::addRecetaBotonNuevoTipoActionPerformed);
        this.panelAddReceta.addRecetaBotonNuevoIngredienteListener(this::addRecetaBotonNuevoIngredienteActionPerformed);
        this.panelAddReceta.addRecetaBotonListaListener(this::addRecetaBotonListaIngredientesActionPerformed);
        this.panelAddReceta.addRecetaBotonOKListener(this::addRecetaBotonOKActionPerformed);
        this.vista.addEditarTipoListener(this::editarTipoActionPerformed);
        this.vista.addEditarIngredienteListener(this::editarIngredienteActionPerformed);
        this.vista.addEditarUnidadListener(this::editarUnidadActionPerformed);
        this.panelConfiguracion.addDiaListener(this::nuevaConfiguracionAddDiaActionPerformed);
        this.panelConfiguracion.addComidaListener(this::nuevaConfiguracionAddComidaActionPerformed);
        this.panelConfiguracion.addCenaListener(this::nuevaConfiguracionAddCenaActionPerformed);
        this.panelConfiguracion.addGuardarListener(this::nuevaConfiguracionGuardarActionPerformed);
        this.panelConfiguracion.addPlanificarListener(this::nuevaConfiguracionPlanificarActionPerformed);
        this.vista.addNuevaConfiguracionListener(this::nuevaConfiguracionActionPerformed);
        this.vista.addCargarConfiguracionListener(this::cargarConfiguracionActionPerformed);
        this.vista.addMostrarRecetasListener(this::mostrarRecetasActionPerformed);
        this.vista.addAboutListener(this::aboutActionPerformed);
    }
    
    void exitActionPerformed(final ActionEvent e) {
        System.exit(0);
    }
    
    void addRecetaActionPerformed(final ActionEvent e) {
        this.panelAddReceta.reset();
        this.vista.paintIntoCenterPanel(this.panelAddReceta);
        final List<Receta> recetas = this.modelo.getRecetaDAO().getAllRecetas();
        final Iterator<Receta> i = recetas.iterator();
        while (i.hasNext()) {
            i.next().print();
        }
    }
    
    void addRecetaBotonNuevaUnidadActionPerformed(final ActionEvent e) {
        this.dialogNuevaUnidad = new JDialog(this.vista, "A\u00f1adir Nueva Unidad de Medida", true);
        this.panelAddUnidad = new PanelUnidadCantidad();
        this.panelAddUnidad.addUnidadBotonOKListener(this::addUnidadBotonOKActionPerformed);
        this.dialogNuevaUnidad.add(this.panelAddUnidad);
        this.dialogNuevaUnidad.setSize(this.dialogNuevaUnidad.getContentPane().getPreferredSize());
        this.dialogNuevaUnidad.setLocationRelativeTo(this.vista);
        this.dialogNuevaUnidad.setVisible(true);
    }
    
    void addUnidadBotonOKActionPerformed(final ActionEvent e) {
        final UnidadCantidad nuevaUnidad = this.obtenerUnidad();
        if (nuevaUnidad == null) {
            return;
        }
        if (this.modelo.getUnidadCantidadDAO().readUnidadCantidad(nuevaUnidad.getUnidad()) == null) {
            this.modelo.getUnidadCantidadDAO().createUnidadCantidad(nuevaUnidad);
            if (this.panelAddReceta.isVisible()) {
                this.panelAddReceta.getComboUnidad().addItem(nuevaUnidad.getUnidad());
            }
            this.dialogNuevaUnidad.setVisible(false);
        } else {
            VistaPlanificadorRecetas.errMess(this.dialogNuevaUnidad, LA_UNIDAD + nuevaUnidad.getUnidad() + YA_EXISTE_PUNTO, ERROR);
        }
    }
    
    UnidadCantidad obtenerUnidad() {
        final String unidad = this.panelAddUnidad.getFieldUnidad().getText();
        if (unidad.equals("")) {
            return null;
        }
        if (this.modelo.getUnidadCantidadDAO().readUnidadCantidad(unidad) == null) {
            final UnidadCantidad nuevaUnidad = new UnidadCantidad();
            nuevaUnidad.setUnidad(unidad);
            return nuevaUnidad;
        }
        return this.modelo.getUnidadCantidadDAO().readUnidadCantidad(unidad);
    }
    
    void addRecetaBotonNuevoTipoActionPerformed(final ActionEvent e) {
        this.dialogNuevoTipo = new JDialog(this.vista, "A\u00f1adir Nuevo Tipo Receta", true);
        this.panelAddTipo = new PanelTipo();
        this.panelAddTipo.addTipoBotonOKListener(this::addTipoBotonOKActionPerformed);
        this.dialogNuevoTipo.add(this.panelAddTipo);
        this.dialogNuevoTipo.setSize(this.dialogNuevoTipo.getContentPane().getPreferredSize());
        this.dialogNuevoTipo.setLocationRelativeTo(this.vista);
        this.dialogNuevoTipo.setVisible(true);
    }
    
    void addTipoBotonOKActionPerformed(final ActionEvent e) {
        final TipoReceta nuevoTipo = this.obtenerTipo();
        if (nuevoTipo == null) {
            return;
        }
        if (this.modelo.getTipoRecetaDAO().readTipoReceta(nuevoTipo.getNombre()) == null) {
            this.modelo.getTipoRecetaDAO().createTipoReceta(nuevoTipo);
            if (this.panelAddReceta.isVisible()) {
                this.panelAddReceta.getComboTipo().addItem(nuevoTipo.getNombre());
            }
            this.dialogNuevoTipo.setVisible(false);
        } else {
            VistaPlanificadorRecetas.errMess(this.dialogNuevoTipo, EL_TIPO + nuevoTipo.getNombre() + YA_EXISTE_PUNTO, ERROR);
        }
    }
    
    TipoReceta obtenerTipo() {
        final String nombre = this.panelAddTipo.getFieldNombre().getText();
        if (nombre.equals("")) {
            return null;
        }
        if (this.modelo.getTipoRecetaDAO().readTipoReceta(nombre) == null) {
            final TipoReceta tipo = new TipoReceta();
            tipo.setNombre(nombre);
            return tipo;
        }
        return this.modelo.getTipoRecetaDAO().readTipoReceta(nombre);
    }
    
    void addRecetaBotonNuevoIngredienteActionPerformed(final ActionEvent e) {
        this.dialogNuevoIngrediente = new JDialog(this.vista, "A\u00f1adir Nuevo Ingrediente", true);
        this.panelAddIngrediente = new PanelIngrediente();
        this.panelAddIngrediente.addIngredienteBotonOKListener(this::addIngredienteBotonOKActionPerformed);
        this.dialogNuevoIngrediente.add(this.panelAddIngrediente);
        this.dialogNuevoIngrediente.setSize(this.dialogNuevoIngrediente.getContentPane().getPreferredSize());
        this.dialogNuevoIngrediente.setLocationRelativeTo(this.vista);
        this.dialogNuevoIngrediente.setVisible(true);
    }
    
    void addIngredienteBotonOKActionPerformed(final ActionEvent e) {
        final Ingrediente nuevoIngrediente = this.obtenerIngrediente();
        if (nuevoIngrediente == null) {
            return;
        }
        if (this.modelo.getIngredienteDAO().readIngrediente(nuevoIngrediente.getNombre()) == null) {
            this.modelo.getIngredienteDAO().createIngrediente(nuevoIngrediente);
            if (this.panelAddReceta.isVisible()) {
                this.panelAddReceta.addIngredienteComboIngredientes(nuevoIngrediente.getNombre());
                this.panelAddReceta.getComboIngredientes().setSelectedItem(nuevoIngrediente.getNombre());
            }
            this.dialogNuevoIngrediente.setVisible(false);
        } else {
            VistaPlanificadorRecetas.errMess(this.dialogNuevoIngrediente, EL_INGREDIENTE + nuevoIngrediente.getNombre() + YA_EXISTE_PUNTO, ERROR);
        }
    }
    
    Ingrediente obtenerIngrediente() {
        final String nombre = this.panelAddIngrediente.getFieldNombre().getText();
        if (nombre.equals("")) {
            return null;
        }
        final String textoDisponibilidad = this.panelAddIngrediente.getComboDisponibilidad().getSelectedItem().toString();
        final Ingrediente.Disponibilidad disponibilidad = Ingrediente.Disponibilidad.valueOf(textoDisponibilidad);
        final String textoSupermercado = this.panelAddIngrediente.getFieldSupermercado().getText();
        final Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNombre(nombre);
        ingrediente.setDisponibilidad(disponibilidad);
        ingrediente.setSupermercado(textoSupermercado);
        return ingrediente;
    }
    
    void addRecetaBotonListaIngredientesActionPerformed(final ActionEvent e) {
        final String ingredienteSeleccionadoIzquierda = (String)this.panelAddReceta.getComboIngredientes().getSelectedItem();
        final String cantidad = this.panelAddReceta.getFieldCantidad().getText();
        final String unidad = (String)this.panelAddReceta.getComboUnidad().getSelectedItem();
        final Boolean opcional = this.panelAddReceta.getCheckOpcional().isSelected();
        List<String> ingredientesSeleccionadosDerecha = null;
        if (this.panelAddReceta.getListSeleccionados().getSelectedValuesList() != null) {
            ingredientesSeleccionadosDerecha = this.panelAddReceta.getListSeleccionados().getSelectedValuesList();
        }
        if (e.getActionCommand().equals("Derecha")) {
            if (ingredienteSeleccionadoIzquierda != null && cantidad != null && cantidad.length() > 0 && unidad != null) {
                final String itemListaCompra = ingredienteSeleccionadoIzquierda + ";" + cantidad + ";" + unidad + ";" + opcional.toString();
                ((DefaultListModel<String>)this.panelAddReceta.getListSeleccionados().getModel()).addElement(itemListaCompra);
                this.panelAddReceta.getListSeleccionados().setAutoscrolls(true);
                this.panelAddReceta.removeIngredienteComboIngredientes(ingredienteSeleccionadoIzquierda);
                this.panelAddReceta.getCheckOpcional().setSelected(false);
            } else {
                VistaPlanificadorRecetas.errMess(this.vista, "La cantidad de ingrediente a a\u00f1adir no puede estar vac\u00eda.", ERROR);
            }
        } else if (e.getActionCommand().equals("Izquierda") && ingredientesSeleccionadosDerecha != null) {
            for (String ingrediente : ingredientesSeleccionadosDerecha) {
                final String[] itemListaCompra2 = ingrediente.split(";");
                this.panelAddReceta.addIngredienteComboIngredientes(itemListaCompra2[0]);
                ((DefaultListModel<String>)this.panelAddReceta.getListSeleccionados().getModel()).removeElement(ingrediente);
            }
        }
        this.panelAddReceta.getFieldCantidad().setText("");
        this.panelAddReceta.getComboUnidad().setSelectedIndex(0);
    }
    
    void addRecetaBotonOKActionPerformed(final ActionEvent e) {
        final Receta receta = this.obtenerReceta();
        if (receta == null) {
            return;
        }
        if (this.modelo.getRecetaDAO().readReceta(receta.getNombre()) != null) {
            VistaPlanificadorRecetas.errMess(this.vista, receta.getNombre() + YA_EXISTE_PUNTO, ERROR);
            return;
        }
        this.modelo.getRecetaDAO().createReceta(receta);
        this.vista.paintIntoCenterPanel(new JPanel());
    }
    
    Receta obtenerReceta() {
        final Receta receta = new Receta();
        final String nombre = this.panelAddReceta.getFieldNombre().getText();
        if (nombre.equals("")) {
            return null;
        }
        receta.setNombre(this.panelAddReceta.getFieldNombre().getText());
        if (this.panelAddReceta.getFieldLocalizacion().getText().equals("")) {
            return null;
        }
        receta.setLocalizacion(this.panelAddReceta.getFieldLocalizacion().getText());
        receta.setDificultad(Receta.Dificultad.valueOf(this.panelAddReceta.getComboDificultad().getSelectedItem().toString()));
        if (this.panelAddReceta.getFieldTiempo().getText().equals("")) {
            return null;
        }
        final Tiempo tiempo = new Tiempo();
        tiempo.setCantidad(Integer.valueOf(this.panelAddReceta.getFieldTiempo().getText()));
        tiempo.setUnidadDeMedida(Tiempo.UnidadDeMedidaTiempo.valueOf(this.panelAddReceta.getComboTiempo().getSelectedItem().toString()));
        receta.setTiempoPreparacion(tiempo);
        receta.setEstacion(Receta.Estacion.valueOf(this.panelAddReceta.getComboEstacion().getSelectedItem().toString()));
        final TipoReceta tipo = new TipoReceta();
        tipo.setNombre(this.panelAddReceta.getComboTipo().getSelectedItem().toString());
        receta.setTipo(tipo);
        if (this.panelAddReceta.getListSeleccionados().getModel().getSize() < 1) {
            return null;
        }
        final Set<ItemListaCompra> ingredientes = new HashSet<>();
        final DefaultListModel<String> ingredientesActuales = (DefaultListModel<String>)this.panelAddReceta.getListSeleccionados().getModel();
        for (int i = 0; i < ingredientesActuales.size(); ++i) {
            final String[] item = ingredientesActuales.get(i).split(";");
            final Ingrediente ingrediente = this.modelo.getIngredienteDAO().readIngrediente(item[0]);
            if (ingrediente != null) {
                final Cantidad cantidad = new Cantidad();
                cantidad.setNumero(Integer.valueOf(item[1]));
                final UnidadCantidad unidad = new UnidadCantidad();
                unidad.setUnidad(item[2]);
                cantidad.setUnidad(unidad);
                final ItemListaCompra itemLista = new ItemListaCompra();
                itemLista.setIngrediente(ingrediente);
                itemLista.setCantidad(cantidad);
                itemLista.setOpcional(Boolean.valueOf(item[3]));
                ingredientes.add(itemLista);
            }
        }
        receta.setIngredientes(ingredientes);
        if (this.modelo.getRecetaDAO().readReceta(nombre) != null) {
            receta.setFrecuencia(this.modelo.getRecetaDAO().readReceta(nombre).getFrecuencia());
        }
        return receta;
    }

    void editarTipo(final TipoReceta tipo) {
        final TipoReceta tipoModificado = this.obtenerTipo();
        if (this.modelo.getTipoRecetaDAO().readTipoReceta(tipoModificado.getNombre()) == null) {
            final List<Receta> listaRecetas = this.modelo.getRecetaDAO().getAllRecetas();
            for (final Receta receta : listaRecetas) {
                if (receta.getTipo().getNombre().equals(tipo.getNombre())) {
                    receta.setTipo(tipoModificado);
                    this.modelo.getRecetaDAO().updateReceta(receta);
                }
            }
            this.modelo.getTipoRecetaDAO().deleteTipoReceta(tipo.getNombre());
            this.modelo.getTipoRecetaDAO().createTipoReceta(tipoModificado);
        } else {
            VistaPlanificadorRecetas.errMess(this.vista, EL_TIPO + tipoModificado.getNombre() + YA_EXISTE, ERROR);
        }
    }

    void borrarTipo(final TipoReceta tipo) {
        final TipoReceta tipoModificado = this.obtenerTipo();
        if (tipo.getNombre().equals(tipoModificado.getNombre())) {
            final List<Receta> listaRecetas = this.modelo.getRecetaDAO().getAllRecetas();
            for (final Receta receta : listaRecetas) {
                if (receta.getTipo().getNombre().equals(tipo.getNombre())) {
                    VistaPlanificadorRecetas.errMess(this.vista, EL_TIPO + tipoModificado.getNombre() + " no puede borrarse por estar siendo utilizado en alguna receta.", ERROR);
                    return;
                }
            }
            this.modelo.getTipoRecetaDAO().deleteTipoReceta(tipoModificado.getNombre());
        } else {
            VistaPlanificadorRecetas.errMess(this.vista, EL_TIPO + tipoModificado.getNombre() + " no es el tipo original " + tipo.getNombre() + DESEABA_BORRAR, ERROR);
        }
    }
    
    void editarTipoActionPerformed(final ActionEvent e) {
        this.vista.paintIntoCenterPanel(new JPanel());
        final List<TipoReceta> tipos = this.modelo.getTipoRecetaDAO().getAllTiposRecetas();
        final Object[] nombresTipos = new Object[tipos.size()];
        final Iterator<TipoReceta> i = tipos.iterator();
        int j = 0;
        while (i.hasNext()) {
            nombresTipos[j] = i.next().getNombre();
            ++j;
        }
        final String s = (String)JOptionPane.showInputDialog(this.vista, "Elige un tipo a editar: ", EDITAR_TIPO, -1, null, nombresTipos, nombresTipos[0]);
        if (s != null && s.length() > 0) {
            final TipoReceta tipo = this.modelo.getTipoRecetaDAO().readTipoReceta(s);
            if (tipo != null) {
                this.panelAddTipo = new PanelTipo();
                this.panelAddTipo.cargarTipo(tipo);
                final Object[] options = { EDITAR, BORRAR, CANCELAR };
                final int n = JOptionPane.showOptionDialog(this.vista, this.panelAddTipo.getPanelDatos(), EDITAR_TIPO, 1, 3, null, options, options[2]);
                if (n == 0) {
                    this.editarTipo(tipo);
                } else if (n == 1) {
                    this.borrarTipo(tipo);
                }
            }
        }
    }

    void editarIngrediente(final Ingrediente ingrediente) {
        final Ingrediente ingredienteModificado = this.obtenerIngrediente();
        if (ingrediente.getNombre().equals(ingredienteModificado.getNombre())) {
            this.modelo.getIngredienteDAO().updateIngrediente(ingredienteModificado);
        } else if (this.modelo.getIngredienteDAO().readIngrediente(ingredienteModificado.getNombre()) == null) {
            final List<Receta> listaRecetas = this.modelo.getRecetaDAO().getAllRecetas();
            for (final Receta receta : listaRecetas) {
                for (final ItemListaCompra ingredienteActual : receta.getIngredientes()) {
                    if (ingredienteActual.getIngrediente().getNombre().equals(ingrediente.getNombre())) {
                        ingredienteActual.setIngrediente(ingredienteModificado);
                        this.modelo.getRecetaDAO().updateReceta(receta);
                    }
                }
            }
            this.modelo.getIngredienteDAO().deleteIngrediente(ingrediente.getNombre());
            this.modelo.getIngredienteDAO().createIngrediente(ingredienteModificado);
        } else {
            VistaPlanificadorRecetas.errMess(this.vista, EL_INGREDIENTE + ingredienteModificado.getNombre() + YA_EXISTE, ERROR);
        }
    }

    void borrarIngrediente(final Ingrediente ingrediente) {
        final Ingrediente ingredienteModificado = this.obtenerIngrediente();
        if (ingrediente.getNombre().equals(ingredienteModificado.getNombre())) {
            final List<Receta> listaRecetas = this.modelo.getRecetaDAO().getAllRecetas();
            for (final Receta receta : listaRecetas) {
                for (final ItemListaCompra ingredienteActual : receta.getIngredientes()) {
                    if (ingredienteActual.getIngrediente().getNombre().equals(ingrediente.getNombre())) {
                        VistaPlanificadorRecetas.errMess(this.vista, EL_INGREDIENTE + ingredienteModificado.getNombre() + " no puede borrarse por estar siendo utilizado en alguna receta.", ERROR);
                        return;
                    }
                }
            }
            this.modelo.getIngredienteDAO().deleteIngrediente(ingredienteModificado.getNombre());
        } else {
            VistaPlanificadorRecetas.errMess(this.vista, EL_INGREDIENTE + ingredienteModificado.getNombre() + " no es el ingrediente original " + ingrediente.getNombre() + DESEABA_BORRAR, ERROR);
        }
    }
    
    void editarIngredienteActionPerformed(final ActionEvent e) {
        this.vista.paintIntoCenterPanel(new JPanel());
        final List<Ingrediente> ingredientes = this.modelo.getIngredienteDAO().getAllIngredientes();
        final Object[] nombresIngredientes = new Object[ingredientes.size()];
        final Iterator<Ingrediente> i = ingredientes.iterator();
        int j = 0;
        while (i.hasNext()) {
            nombresIngredientes[j] = i.next().getNombre();
            ++j;
        }
        final String s = (String)JOptionPane.showInputDialog(this.vista, "Elige un tipo a editar: ", EDITAR_TIPO, -1, null, nombresIngredientes, nombresIngredientes[0]);
        if (s != null && s.length() > 0) {
            final Ingrediente ingrediente = this.modelo.getIngredienteDAO().readIngrediente(s);
            if (ingrediente != null) {
                this.panelAddIngrediente = new PanelIngrediente();
                this.panelAddIngrediente.cargarIngrediente(ingrediente);
                final Object[] options = { EDITAR, BORRAR, CANCELAR };
                final int n = JOptionPane.showOptionDialog(this.vista, this.panelAddIngrediente.getPanelDatos(), "Editar Ingrediente", 1, 3, null, options, options[2]);
                if (n == 0) {
                    this.editarIngrediente(ingrediente);
                } else if (n == 1) {
                    this.borrarIngrediente(ingrediente);
                }
            }
        }
    }

    void editarUnidad(final UnidadCantidad unidad) {
        final UnidadCantidad unidadModificada = this.obtenerUnidad();
        if (this.modelo.getUnidadCantidadDAO().readUnidadCantidad(unidadModificada.getUnidad()) == null) {
            final List<Receta> listaRecetas = this.modelo.getRecetaDAO().getAllRecetas();
            for (final Receta receta : listaRecetas) {
                for (final ItemListaCompra ingredienteActual : receta.getIngredientes()) {
                    if (ingredienteActual.getCantidad().getUnidad().getUnidad().equals(unidad.getUnidad())) {
                        ingredienteActual.getCantidad().setUnidad(unidadModificada);
                        this.modelo.getRecetaDAO().updateReceta(receta);
                    }
                }
            }
            this.modelo.getUnidadCantidadDAO().deleteUnidadCantidad(unidad.getUnidad());
            this.modelo.getUnidadCantidadDAO().createUnidadCantidad(unidadModificada);
        } else {
            VistaPlanificadorRecetas.errMess(this.vista, LA_UNIDAD + unidadModificada.getUnidad() + YA_EXISTE, ERROR);
        }
    }

    void borrarUnidad(final UnidadCantidad unidad) {
        final UnidadCantidad unidadModificada = this.obtenerUnidad();
        if (unidad.getUnidad().equals(unidadModificada.getUnidad())) {
            final List<Receta> listaRecetas = this.modelo.getRecetaDAO().getAllRecetas();
            for (final Receta receta : listaRecetas) {
                for (final ItemListaCompra ingredienteActual : receta.getIngredientes()) {
                    if (ingredienteActual.getCantidad().getUnidad().getUnidad().equals(unidad.getUnidad())) {
                        VistaPlanificadorRecetas.errMess(this.vista, LA_UNIDAD + unidadModificada.getUnidad() + " no puede borrarse por estar siendo utilizada en alguna receta.", ERROR);
                        return;
                    }
                }
            }
            this.modelo.getUnidadCantidadDAO().deleteUnidadCantidad(unidadModificada.getUnidad());
        } else {
            VistaPlanificadorRecetas.errMess(this.vista, LA_UNIDAD + unidadModificada.getUnidad() + " no es la unidad original " + unidad.getUnidad() + DESEABA_BORRAR, ERROR);
        }
    }
    
    void editarUnidadActionPerformed(final ActionEvent e) {
        this.vista.paintIntoCenterPanel(new JPanel());
        final List<UnidadCantidad> unidades = this.modelo.getUnidadCantidadDAO().getAllUnidadesCantidades();
        final Object[] nombresUnidades = new Object[unidades.size()];
        final Iterator<UnidadCantidad> i = unidades.iterator();
        int j = 0;
        while (i.hasNext()) {
            nombresUnidades[j] = i.next().getUnidad();
            ++j;
        }
        final String s = (String)JOptionPane.showInputDialog(this.vista, "Elige una unidad a editar: ", "Editar Unidad", -1, null, nombresUnidades, nombresUnidades[0]);
        if (s != null && s.length() > 0) {
            final UnidadCantidad unidad = this.modelo.getUnidadCantidadDAO().readUnidadCantidad(s);
            if (unidad != null) {
                this.panelAddUnidad = new PanelUnidadCantidad();
                this.panelAddUnidad.cargarUnidad(unidad);
                final Object[] options = { EDITAR, BORRAR, CANCELAR };
                final int n = JOptionPane.showOptionDialog(this.vista, this.panelAddUnidad.getPanelDatos(), "Editar Unidad", 1, 3, null, options, options[2]);
                if (n == 0) {
                    this.editarUnidad(unidad);
                } else if (n == 1) {
                    this.borrarUnidad(unidad);
                }
            }
        }
    }
    
    void nuevaConfiguracionActionPerformed(final ActionEvent e) {
        this.panelConfiguracion.reset();
        final int preferredWidth = (int)(this.vista.getSize().getWidth() * 0.4);
        final int preferredHeight = (int)(this.vista.getSize().getHeight() * 0.44);
        this.panelConfiguracion.getTable().setPreferredScrollableViewportSize(new Dimension(preferredWidth, preferredHeight));
        this.vista.paintIntoCenterPanel(this.panelConfiguracion);
    }
    
    void nuevaConfiguracionAddDiaActionPerformed(final ActionEvent e) {
        final TableModelConfiguradorCriterios modeloTabla = (TableModelConfiguradorCriterios)this.panelConfiguracion.getTable().getModel();
        modeloTabla.addDia();
    }
    
    void nuevaConfiguracionAddComidaActionPerformed(final ActionEvent e) {
        final TableModelConfiguradorCriterios modeloTabla = (TableModelConfiguradorCriterios)this.panelConfiguracion.getTable().getModel();
        modeloTabla.addPeriodo(PeriodoDia.NombrePeriodo.COMIDA);
    }
    
    void nuevaConfiguracionAddCenaActionPerformed(final ActionEvent e) {
        final TableModelConfiguradorCriterios modeloTabla = (TableModelConfiguradorCriterios)this.panelConfiguracion.getTable().getModel();
        modeloTabla.addPeriodo(PeriodoDia.NombrePeriodo.CENA);
    }
    
    void nuevaConfiguracionGuardarActionPerformed(final ActionEvent e) {
        final Configurador configurador = this.obtenerCriterios();
        if (configurador != null) {
            final String s = JOptionPane.showInputDialog(this.vista, "Nombre del fichero: ", "Guardar Configuraci\u00f3n", -1);
            if (s != null && s.length() > 0) {
                this.modelo.getConfiguradorDAO().createConfigurador(configurador, s);
            }
        }
    }
    
    void nuevaConfiguracionPlanificarActionPerformed(final ActionEvent e) {
        final Configurador configurador = this.obtenerCriterios();
        if (configurador != null) {
            final Planificador planificador = new Planificador();
            planificador.planificarCalendario(configurador, this.modelo.getRecetaDAO());
            this.panelMostrarCalendario = new PanelMostrarCalendario(planificador);
            this.panelMostrarCalendario.addListaCompraListener(this::mostrarCalendarioIngredientesActionPerformed);
            this.panelMostrarCalendario.addImprimirListener(this::mostrarCalendarioImprimirActionPerformed);
            final int preferredWidth = (int)(this.vista.getSize().getWidth() * 0.5);
            final int preferredHeight = (int)(this.vista.getSize().getHeight() * 0.68);
            this.panelMostrarCalendario.getTable().setPreferredScrollableViewportSize(new Dimension(preferredWidth, preferredHeight));
            this.vista.paintIntoCenterPanel(this.panelMostrarCalendario);
        }
    }
    
    private Configurador obtenerCriterios() {
        final Configurador configurador = new Configurador();
        final TableModelConfiguradorCriterios modeloTabla = (TableModelConfiguradorCriterios)this.panelConfiguracion.getTable().getModel();
        final JCheckBox[] estacion = this.panelConfiguracion.getButtonsEstacion();
        for (int i = 0; i < estacion.length; ++i) {
            if (estacion[i].isSelected()) {
                configurador.addEstacion(Receta.Estacion.valueOf(estacion[i].getText()));
            }
        }
        final String dificultad = this.panelConfiguracion.getGroupDificultad().getSelection().getActionCommand();
        configurador.setDificultad(Receta.Dificultad.valueOf(dificultad));
        final String disponibilidad = this.panelConfiguracion.getGroupDisponibilidad().getSelection().getActionCommand();
        configurador.setDisponibilidad(Ingrediente.Disponibilidad.valueOf(disponibilidad));
        for (int i = 0; i < modeloTabla.getRowCount(); ++i) {
            if (modeloTabla.getValueAt(i, 1) == null) {
                VistaPlanificadorRecetas.errMess(this.vista, "El periodo no puede estar vaci\u00f3.", ERROR);
                return null;
            }
        }
        for (int i = 0; i < modeloTabla.getRowCount(); ++i) {
            final Criterio criterio = new Criterio();
            criterio.setPeriodo(PeriodoDia.NombrePeriodo.valueOf((String)modeloTabla.getValueAt(i, 1)));
            if (modeloTabla.getValueAt(i, 2) != null) {
                criterio.addTipoReceta(this.modelo.getTipoRecetaDAO().readTipoReceta((String)modeloTabla.getValueAt(i, 2)));
            }
            if (modeloTabla.getValueAt(i, 3) != null) {
                criterio.addIngrediente(this.modelo.getIngredienteDAO().readIngrediente((String)modeloTabla.getValueAt(i, 3)));
            }
            configurador.addCriterios(criterio);
        }
        return configurador;
    }
    
    void mostrarCalendarioIngredientesActionPerformed(final ActionEvent ae) {
        final String[] nombres = new String[this.panelMostrarCalendario.getTable().getRowCount()];
        final List<Receta> recetas = new ArrayList<>();
        final ListaCompra lista = new ListaCompra();
        for (int i = 0; i < this.panelMostrarCalendario.getTable().getRowCount(); ++i) {
            nombres[i] = (String)this.panelMostrarCalendario.getTable().getValueAt(i, 3);
            recetas.add(this.modelo.getRecetaDAO().readReceta(nombres[i]));
        }
        final Iterator<Receta> it = recetas.iterator();
        while (it.hasNext()) {
            for (final ItemListaCompra item : it.next().getIngredientes()) {
                lista.addItem(item);
            }
        }
        if (!lista.getItems().isEmpty()) {
            final JDialog dialogListaCompra = new JDialog(this.vista, "Lista de la Compra", true);
            this.panelListaCompra = new PanelListaCompra(lista);
            this.panelListaCompra.addImprimirListener(this::listaCompraImprimirActionPerformed);
            final int preferredWidth = (int)(this.vista.getSize().getWidth() * 0.5);
            final int preferredHeight = (int)(this.vista.getSize().getHeight() * 0.5);
            this.panelListaCompra.getTable().setPreferredScrollableViewportSize(new Dimension(preferredWidth, preferredHeight));
            this.panelListaCompra.setPreferredSize(new Dimension(preferredWidth, preferredHeight + 100));
            dialogListaCompra.add(this.panelListaCompra);
            dialogListaCompra.setSize(dialogListaCompra.getContentPane().getPreferredSize());
            dialogListaCompra.setLocationRelativeTo(this.vista);
            dialogListaCompra.setVisible(true);
        }
    }
    
    void listaCompraImprimirActionPerformed(final ActionEvent ae) {
        try {
            this.panelListaCompra.getTable().print();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void mostrarCalendarioImprimirActionPerformed(final ActionEvent ae) {
        try {
            this.panelMostrarCalendario.getTable().print();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void cargarConfiguracionActionPerformed(final ActionEvent e) {
        final String directorio = "./" + ((ConfiguradorFileDAO)this.modelo.getConfiguradorDAO()).getDirectorioConfiguradores();
        final JFileChooser chooser = new JFileChooser(directorio);
        final String extension = ConfiguradorFileDAO.getExtensionConfigurador();
        final FileNameExtensionFilter filter = new FileNameExtensionFilter("CONF", extension);
        chooser.setFileFilter(filter);
        final int returnVal = chooser.showOpenDialog(this.vista);
        if (returnVal == 0) {
            final String fichero = chooser.getSelectedFile().getName();
            final Configurador configurador = this.modelo.getConfiguradorDAO().readConfigurador(fichero);
            this.panelConfiguracion.cargarConfiguracion(configurador);
            final int preferredWidth = (int)(this.vista.getSize().getWidth() * 0.4);
            final int preferredHeight = (int)(this.vista.getSize().getHeight() * 0.44);
            this.panelConfiguracion.getTable().setPreferredScrollableViewportSize(new Dimension(preferredWidth, preferredHeight));
            this.vista.paintIntoCenterPanel(this.panelConfiguracion);
        }
    }
    
    void mostrarRecetasActionPerformed(final ActionEvent e) {
        this.panelMostrarRecetas = new PanelMostrarRecetas(this.modelo);
        this.panelMostrarRecetas.addEditarListener(this::mostrarRecetasEditarActionPerformed);
        this.panelMostrarRecetas.addFrecuenciaListener(this::mostrarRecetasCambiarFrecuenciaActionPerformed);
        this.panelMostrarRecetas.addBorrarListener(this::mostrarRecetasBorrarActionPerformed);
        final int preferredWidth = (int)(this.vista.getSize().getWidth() * 0.5);
        final int preferredHeight = (int)(this.vista.getSize().getHeight() * 0.68);
        this.panelMostrarRecetas.getTable().setPreferredScrollableViewportSize(new Dimension(preferredWidth, preferredHeight));
        this.vista.paintIntoCenterPanel(this.panelMostrarRecetas);
    }
    
    void mostrarRecetasEditarActionPerformed(final TableModelEvent e) {
        if (e.getColumn() == 8 && (boolean)this.panelMostrarRecetas.getTable().getModel().getValueAt(e.getFirstRow(), 8)) {
            final String nombre = (String)this.panelMostrarRecetas.getTable().getModel().getValueAt(e.getFirstRow(), 0);
            final Receta receta = this.modelo.getRecetaDAO().readReceta(nombre);
            this.panelAddReceta.cargarReceta(receta);
            final int opcion = JOptionPane.showConfirmDialog(this.vista, this.panelAddReceta.getPanelDatos(), "Editar Receta", 2);
            if (opcion == 0) {
                final Receta recetaModificada = this.obtenerReceta();
                this.modelo.getRecetaDAO().updateReceta(recetaModificada);
                this.panelMostrarRecetas = new PanelMostrarRecetas(this.modelo);
                this.panelMostrarRecetas.addEditarListener(this::mostrarRecetasEditarActionPerformed);
                this.panelMostrarRecetas.addBorrarListener(this::mostrarRecetasBorrarActionPerformed);
                this.panelMostrarRecetas.addFrecuenciaListener(this::mostrarRecetasCambiarFrecuenciaActionPerformed);
                final int preferredWidth = (int)(this.vista.getSize().getWidth() * 0.5);
                final int preferredHeight = (int)(this.vista.getSize().getHeight() * 0.68);
                this.panelMostrarRecetas.getTable().setPreferredScrollableViewportSize(new Dimension(preferredWidth, preferredHeight));
                this.vista.paintIntoCenterPanel(this.panelMostrarRecetas);
            }
            else {
                this.panelMostrarRecetas.getTable().getModel().setValueAt(false, e.getFirstRow(), 8);
            }
        }
    }
    
    void mostrarRecetasBorrarActionPerformed(final ActionEvent e) {
        final String nombre = JOptionPane.showInputDialog(this.vista, "Nombre receta a borrar: ", "Borrar Receta", 3);
        if (this.modelo.getRecetaDAO().readReceta(nombre) != null) {
            this.modelo.getRecetaDAO().deleteReceta(nombre);
            this.panelMostrarRecetas = new PanelMostrarRecetas(this.modelo);
            this.panelMostrarRecetas.addEditarListener(this::mostrarRecetasEditarActionPerformed);
            this.panelMostrarRecetas.addBorrarListener(this::mostrarRecetasBorrarActionPerformed);
            this.panelMostrarRecetas.addFrecuenciaListener(this::mostrarRecetasCambiarFrecuenciaActionPerformed);
            final int preferredWidth = (int)(this.vista.getSize().getWidth() * 0.5);
            final int preferredHeight = (int)(this.vista.getSize().getHeight() * 0.68);
            this.panelMostrarRecetas.getTable().setPreferredScrollableViewportSize(new Dimension(preferredWidth, preferredHeight));
            this.vista.paintIntoCenterPanel(this.panelMostrarRecetas);
        }
        else {
            VistaPlanificadorRecetas.errMess(this.vista, LA_RECETA + nombre + NO_EXISTE, ERROR);
        }
    }

    int reiniciarFrecuencia() {
        int opcion = JOptionPane.showConfirmDialog(this.vista, "¿Est\u00e1 seguro de reiniciar las frecuencias de las recetas?", "Reiniciar Frecuencias", 0);
        if (opcion == 0) {
            for (final Receta receta : this.modelo.getRecetaDAO().getAllRecetas()) {
                receta.setFrecuencia(0);
                this.modelo.getRecetaDAO().updateReceta(receta);
            }
        }
        return opcion;
    }

    int aumentarFrecuencia() {
        final String nombre = JOptionPane.showInputDialog(this.vista, "Nombre receta a aumentar frecuencia: ", "Aumentar Frecuencia", 3);
        if (nombre != null && nombre.length() > 0) {
            final Receta receta = this.modelo.getRecetaDAO().readReceta(nombre);
            if (receta != null) {
                receta.aumentarFrecuencia();
                this.modelo.getRecetaDAO().updateReceta(receta);
                return 0;
            } else {
                VistaPlanificadorRecetas.errMess(this.vista, LA_RECETA + nombre + NO_EXISTE, ERROR);
            }
        }
        return 1;
    }

    int disminuirFrecuencia() {
        final String nombre = JOptionPane.showInputDialog(this.vista, "Nombre receta a disminuir frecuencia: ", "Disminuir Frecuencia", 3);
        if (nombre != null && nombre.length() > 0) {
            final Receta receta = this.modelo.getRecetaDAO().readReceta(nombre);
            if (receta != null) {
                receta.disminuirFrecuencia();
                this.modelo.getRecetaDAO().updateReceta(receta);
                return 0;
            } else {
                VistaPlanificadorRecetas.errMess(this.vista, LA_RECETA + nombre + NO_EXISTE, ERROR);
            }
        }
        return 1;
    }
    
    void mostrarRecetasCambiarFrecuenciaActionPerformed(final ActionEvent e) {
        int opcion = 1;
        if (e.getActionCommand().equals("Reiniciar")) {
            opcion = this.reiniciarFrecuencia();
        } else if (e.getActionCommand().equals("Aumentar")) {
            opcion = this.aumentarFrecuencia();
        } else if (e.getActionCommand().equals("Disminuir")) {
            opcion = this.disminuirFrecuencia();
        }
        if (opcion == 0) {
            this.panelMostrarRecetas = new PanelMostrarRecetas(this.modelo);
            this.panelMostrarRecetas.addEditarListener(this::mostrarRecetasEditarActionPerformed);
            this.panelMostrarRecetas.addBorrarListener(this::mostrarRecetasBorrarActionPerformed);
            this.panelMostrarRecetas.addFrecuenciaListener(this::mostrarRecetasCambiarFrecuenciaActionPerformed);
            final int preferredWidth = (int)(this.vista.getSize().getWidth() * 0.5);
            final int preferredHeight = (int)(this.vista.getSize().getHeight() * 0.68);
            this.panelMostrarRecetas.getTable().setPreferredScrollableViewportSize(new Dimension(preferredWidth, preferredHeight));
            this.vista.paintIntoCenterPanel(this.panelMostrarRecetas);
        }
    }
    
    void aboutActionPerformed(final ActionEvent e) {
        JOptionPane.showMessageDialog(this.vista, new PanelAcercaDe(), "About", -1);
    }
}
