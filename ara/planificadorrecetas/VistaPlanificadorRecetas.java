package ara.planificadorrecetas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

public class VistaPlanificadorRecetas extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(VistaPlanificadorRecetas.class.getName());

    private BorderLayout layoutMain;
    private BorderLayout layoutPanelCenter;
    private JPanel panelCenter;
    private JPanel panelCenterActual;
    private JScrollPane scrollPane;
    private JMenuBar myMenuBar;
    private JMenu menuFile;
    private JMenuItem menuFileExit;
    private JMenu menuAdministracion;
    private JMenuItem menuAdministracionAddReceta;
    private JMenuItem menuAdministracionEditarTipo;
    private JMenuItem menuAdministracionEditarIngrediente;
    private JMenuItem menuAdministracionEditarUnidad;
    private JMenu menuPlanificacion;
    private JMenuItem menuPlanificacionNueva;
    private JMenuItem menuPlanificacionCargar;
    private JMenu menuBusqueda;
    private JMenuItem menuBusquedaRecetas;
    private JMenu menuHelp;
    private JMenuItem menuHelpAbout;
    private JLabel statusBar;
    private JToolBar toolBar;
    private JButton buttonOpen;
    private JButton buttonClose;
    private JButton buttonHelp;
    private ImageIcon imageOpen;
    private ImageIcon imageClose;
    private ImageIcon imageHelp;
    
    public VistaPlanificadorRecetas() {
        this.layoutMain = new BorderLayout();
        this.layoutPanelCenter = new BorderLayout();
        this.panelCenter = new JPanel();
        this.panelCenterActual = new JPanel();
        this.scrollPane = new JScrollPane();
        this.myMenuBar = new JMenuBar();
        this.menuFile = new JMenu();
        this.menuFileExit = new JMenuItem();
        this.menuAdministracion = new JMenu();
        this.menuAdministracionAddReceta = new JMenuItem();
        this.menuAdministracionEditarTipo = new JMenuItem();
        this.menuAdministracionEditarIngrediente = new JMenuItem();
        this.menuAdministracionEditarUnidad = new JMenuItem();
        this.menuPlanificacion = new JMenu();
        this.menuPlanificacionNueva = new JMenuItem();
        this.menuPlanificacionCargar = new JMenuItem();
        this.menuBusqueda = new JMenu();
        this.menuBusquedaRecetas = new JMenuItem();
        this.menuHelp = new JMenu();
        this.menuHelpAbout = new JMenuItem();
        this.statusBar = new JLabel();
        this.toolBar = new JToolBar();
        this.buttonOpen = new JButton();
        this.buttonClose = new JButton();
        this.buttonHelp = new JButton();
        this.imageOpen = new ImageIcon(VistaPlanificadorRecetas.class.getResource("openfile.gif"));
        this.imageClose = new ImageIcon(VistaPlanificadorRecetas.class.getResource("closefile.gif"));
        this.imageHelp = new ImageIcon(VistaPlanificadorRecetas.class.getResource("help.gif"));
        try {
            this.jbInit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "", e);
        }
    }
    
    private void jbInit() {
        this.setJMenuBar(this.myMenuBar);
        this.getContentPane().setLayout(this.layoutMain);
        this.panelCenter.setLayout(this.layoutPanelCenter);
        this.panelCenter.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        this.panelCenter.add(this.panelCenterActual, BorderLayout.CENTER);
        this.scrollPane = new JScrollPane(this.panelCenter);
        this.setSize(new Dimension(600, 400));
        this.setTitle("Planificador de Recetas");
        this.menuFile.setText("Archivo");
        this.menuFileExit.setText("Salir");
        this.menuAdministracion.setText("Administraci\u00f3n");
        this.menuAdministracionAddReceta.setText("A\u00f1adir Receta");
        this.menuAdministracionEditarTipo.setText("Editar Tipo");
        this.menuAdministracionEditarIngrediente.setText("Editar Ingrediente");
        this.menuAdministracionEditarUnidad.setText("Editar Unidad");
        this.menuPlanificacion.setText("Planificaci\u00f3n");
        this.menuPlanificacionNueva.setText("Nueva Configuraci\u00f3n");
        this.menuPlanificacionCargar.setText("Cargar Configuraci\u00f3n");
        this.menuBusqueda.setText("B\u00fasqueda");
        this.menuBusquedaRecetas.setText("Mostrar Recetas");
        this.menuHelp.setText("Ayuda");
        this.menuHelpAbout.setText("Acerca de...");
        this.statusBar.setText("");
        this.buttonOpen.setToolTipText("Abrir Archivo");
        this.buttonOpen.setIcon(this.imageOpen);
        this.buttonClose.setToolTipText("Cerrar Archivo");
        this.buttonClose.setIcon(this.imageClose);
        this.buttonHelp.setToolTipText("Acerca de...");
        this.buttonHelp.setIcon(this.imageHelp);
        this.menuFile.add(this.menuFileExit);
        this.myMenuBar.add(this.menuFile);
        this.menuAdministracion.add(this.menuAdministracionAddReceta);
        this.menuAdministracion.add(this.menuAdministracionEditarTipo);
        this.menuAdministracion.add(this.menuAdministracionEditarIngrediente);
        this.menuAdministracion.add(this.menuAdministracionEditarUnidad);
        this.myMenuBar.add(this.menuAdministracion);
        this.menuPlanificacion.add(this.menuPlanificacionNueva);
        this.menuPlanificacion.add(this.menuPlanificacionCargar);
        this.myMenuBar.add(this.menuPlanificacion);
        this.menuBusqueda.add(this.menuBusquedaRecetas);
        this.myMenuBar.add(this.menuBusqueda);
        this.menuHelp.add(this.menuHelpAbout);
        this.myMenuBar.add(this.menuHelp);
        this.getContentPane().add(this.statusBar, BorderLayout.SOUTH);
        this.toolBar.add(this.buttonOpen);
        this.toolBar.add(this.buttonClose);
        this.toolBar.add(this.buttonHelp);
        this.getContentPane().add(this.toolBar, BorderLayout.NORTH);
        this.getContentPane().add(this.scrollPane, BorderLayout.CENTER);
    }
    
    public void paintIntoCenterPanel(final JPanel panel) {
        this.panelCenterActual = panel;
        this.panelCenter.removeAll();
        this.panelCenter.setLayout(new BorderLayout());
        this.panelCenter.add(this.panelCenterActual, BorderLayout.NORTH);
        this.scrollPane = new JScrollPane(this.panelCenter);
        this.getContentPane().removeAll();
        this.getContentPane().add(this.scrollPane, BorderLayout.CENTER);
        this.panelCenter.updateUI();
        this.panelCenter.repaint();
        this.getContentPane().validate();
        this.getContentPane().update(this.getGraphics());
        this.repaint();
    }
    
    public static void errMess(final Component propietario, final String mensaje, final String titulo) {
        JOptionPane.showMessageDialog(propietario, mensaje, titulo, 0);
    }
    
    public void addExitListener(final ActionListener al) {
        this.menuFileExit.addActionListener(al);
    }
    
    public void addRecetaListener(final ActionListener al) {
        this.menuAdministracionAddReceta.addActionListener(al);
    }
    
    public void addEditarTipoListener(final ActionListener al) {
        this.menuAdministracionEditarTipo.addActionListener(al);
    }
    
    public void addEditarIngredienteListener(final ActionListener al) {
        this.menuAdministracionEditarIngrediente.addActionListener(al);
    }
    
    public void addEditarUnidadListener(final ActionListener al) {
        this.menuAdministracionEditarUnidad.addActionListener(al);
    }
    
    public void addNuevaConfiguracionListener(final ActionListener al) {
        this.menuPlanificacionNueva.addActionListener(al);
    }
    
    public void addCargarConfiguracionListener(final ActionListener al) {
        this.menuPlanificacionCargar.addActionListener(al);
        this.buttonOpen.addActionListener(al);
    }
    
    public void addMostrarRecetasListener(final ActionListener al) {
        this.menuBusquedaRecetas.addActionListener(al);
    }
    
    public void addAboutListener(final ActionListener al) {
        this.menuHelpAbout.addActionListener(al);
        this.buttonHelp.addActionListener(al);
    }
}
