package com.hibernate.gui;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.formdev.flatlaf.IntelliJTheme;
import com.hibernate.dao.CategoriaDAO;
import com.hibernate.dao.ClienteDAO;
import com.hibernate.dao.EjercicioDAO;
import com.hibernate.model.Categoria;
import com.hibernate.model.Cliente;
import com.hibernate.model.Ejercicio;

import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JPasswordField;

public class App {

	private JFrame frmFirePunch;
	private JLabel lblHomePage;
	private JComboBox<String> cbCategorias;
	private JTable tableClientes;
	private JScrollPane scrollPaneClientes;
	private JTable tableEjercicios;
	private JScrollPane scrollPaneEjercicios;
	private JButton btnInsertarCliente;
	private JButton btnInsertarEjercicio;
	private JButton btnActualizarEjercicio;
	private JButton btnActualizarCliente;
	private JButton btnBorrarCliente;
	private JButton btnBorrarEjercicio;
	private JTextField tfIDCliente;
	private JTextField tfNombreCliente;
	private JTextField tfEdadCliente;
	private JTextField tfAlturaCliente;
	private JTextField tfPesoCliente;
	private JTextField tfIDEjercicio;
	private JTextField tfNombreEjercicio;
	private JTextField tfSeriesEjercicio;
	private JTextField tfRepeticionesEjercicio;
	private JTextField tfCargaEjercicio;
	private JTextField tfApellidosCliente;
	private JLabel lblIDCliente;
	private JLabel lblNombreCliente;
	private JLabel lblApellidosCliente;
	private JLabel lblEdadCliente;
	private JLabel lblAlturaCliente;
	private JLabel lblPesoCliente;
	private JLabel lblCargakg;
	private JLabel lblRepeticiones;
	private JLabel lblSeries;
	private JLabel lblNombreEjercicio;
	private JLabel lblIDEjercicio;
	private JLabel lblCategoria;
	private JButton btnLimpiar;
	private JTable tableRutina;

	/**
	 * Se encarga de refrescar la interfaz, aplicando el tema que se seleccione en
	 * el apartado de Opciones.
	 * 
	 * @param frame
	 */

	private static void updateUI(JFrame frame) {
		SwingUtilities.updateComponentTreeUI(frame);
		frame.validate();
		frame.repaint();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IntelliJTheme.setup(App.class.getResourceAsStream("temas/claros/arc-theme-orange.theme.json"));
					App window = new App();
					window.frmFirePunch.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		/**
		 * Declaración de expresiones regulares para hacer las comprobaciones de
		 * errores.
		 * 
		 * patNombre: Cadena de texto que empiece por mayúscula, que tenga como mínimo 2
		 * letras y no hay máximo.
		 * 
		 * patApellido: Cadena de texto que empiece por mayúscula, que tenga como mínimo
		 * 2 letras y no hay máximo, seguida de otra cadena opcional de las mismas
		 * características.
		 * 
		 * patEdad: Cadena de dos dígitos. Más adelante se comprueba que haya un mínimo
		 * y máximo de edad para realizar ciertas acciones.
		 * 
		 * patEmail: Cadena de texto que obliga a que se pueda poner cualquier dígito o
		 * letra seguida de @, después otra cadea de cualquier dígito o letra seguida de
		 * punto y después 2 0 3 letras minúsculas.
		 * 
		 * patDecimal: Comprobación de que el número sea decimal.
		 * 
		 * patEntero: Comprobación de que el número sea entero, limitándolo a 1 cifra
		 * como mínimo y 4 cifras como máximo.
		 */
		Pattern patNombre = Pattern.compile("^[A-Za-z]{2,}$");
		Pattern patApellido = Pattern.compile("^[A-Za-z]{2,}(\\s[A-Za-z]{2,})?$");
		Pattern patEdad = Pattern.compile("^\\d{2}$");
		Pattern patEmail = Pattern.compile("\\w+@\\w+\\.[a-z]{2,3}");
		Pattern patDecimal = Pattern.compile("^\\d+(\\.\\d+)?$");
		Pattern patEntero = Pattern.compile("^\\d{1,4}$");

		/**
		 * Declaración de objetos de las clases DAO que se usan en la aplicación.
		 * 
		 * clienteDAO: clase DAO con las acciones relativas a los clientes que se
		 * realizan sobre la bbdd.
		 * 
		 * categoriaDAO: clase DAO con las acciones relativas a las categorías que se
		 * realizan sobre la bbdd.
		 * 
		 * ejercicioDAO: clase DAO con las acciones relativas a los ejercicios que se
		 * realizan sobre la bbdd.
		 * 
		 * entrenadorDAO: clase DAO con las acciones relativas a los entrenadores que se
		 * realizan sobre la bbdd.
		 */
		ClienteDAO clienteDAO = new ClienteDAO();
		CategoriaDAO categoriaDAO = new CategoriaDAO();
		EjercicioDAO ejercicioDAO = new EjercicioDAO();

		/**
		 * Declaración a nulo de las listas de datos relativos a las clases del
		 * proyecto. Más adelante se les asigna valor.
		 * 
		 * List<Cliente> clientes: lista de todos los clientes obtenidos en la bbdd.
		 * 
		 * List<Cliente> clientesDeEntrenador: lista de los clientes filtrados por id de
		 * entrenador.
		 * 
		 * List<Ejercicio> ejercicios: lista de todos los ejercicios obtenidos en la
		 * bbdd.
		 * 
		 * List<Entrenador> entrenadores: lista de todos los entrenadores obtenidos en
		 * la bbdd.
		 * 
		 * List<Categoria> categorias: lista de todos las categorías obtenidos en la
		 * bbdd.
		 */
		List<Cliente> clientes = null;
		List<Cliente> clientesDeEntrenador = null;
		List<Ejercicio> ejercicios = null;
		List<Categoria> categorias = null;

		/**
		 * Inicialización del frame que contiene la aplicación
		 */
		frmFirePunch = new JFrame();
		frmFirePunch.setBounds(25, 25, 1470, 860);
		frmFirePunch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFirePunch.getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		frmFirePunch.setJMenuBar(menuBar);
		
		JMenu mnApariencia = new JMenu("Apariencia");
		menuBar.add(mnApariencia);

		JMenuItem mntmOpcionFP = new JMenuItem("FirePunch");
		mntmOpcionFP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * Personalización con un tema de FlatLaf almacenado en los paquetes dentro del
				 * paquete gui.
				 */
				IntelliJTheme.setup(App.class.getResourceAsStream("temas/claros/arc-theme-orange.theme.json"));
				updateUI(frmFirePunch);

				JOptionPane.showMessageDialog(frmFirePunch, "Aspecto cambiado a FirePunch");
			}
		});
		mnApariencia.add(mntmOpcionFP);

		JMenuItem mntmOpcionEM = new JMenuItem("Esmeralda");
		mntmOpcionEM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * Personalización con un tema de FlatLaf almacenado en los paquetes dentro del
				 * paquete gui.
				 */
				IntelliJTheme.setup(App.class.getResourceAsStream("temas/colores/Gradianto_Nature_Green.theme.json"));
				updateUI(frmFirePunch);

				JOptionPane.showMessageDialog(frmFirePunch, "Aspecto cambiado a Esmeralda");
			}
		});
		mnApariencia.add(mntmOpcionEM);

		JMenuItem mntmOpcionAZ = new JMenuItem("Azerbaiyán");
		mntmOpcionAZ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * Personalización con un tema de FlatLaf almacenado en los paquetes dentro del
				 * paquete gui.
				 */
				IntelliJTheme.setup(App.class.getResourceAsStream("temas/claros/Github Contrast.theme.json"));
				updateUI(frmFirePunch);

				JOptionPane.showMessageDialog(frmFirePunch, "Aspecto cambiado a Azerbaiyán");
			}
		});
		mnApariencia.add(mntmOpcionAZ);

		JMenuItem mntmOpcionMM = new JMenuItem("Mismagius");
		mntmOpcionMM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * Personalización con un tema de FlatLaf almacenado en los paquetes dentro del
				 * paquete gui.
				 */
				IntelliJTheme.setup(App.class.getResourceAsStream("temas/colores/Material Palenight.theme.json"));
				updateUI(frmFirePunch);

				JOptionPane.showMessageDialog(frmFirePunch, "Aspecto cambiado a Mismagius");
			}
		});
		mnApariencia.add(mntmOpcionMM);

		JMenuItem mntmOpcionLM = new JMenuItem("Lemon Milk");
		mntmOpcionLM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * Personalización con un tema de FlatLaf almacenado en los paquetes dentro del
				 * paquete gui.
				 */
				IntelliJTheme.setup(App.class.getResourceAsStream("temas/oscuros/Monokai Pro Contrast.theme.json"));
				updateUI(frmFirePunch);

				JOptionPane.showMessageDialog(frmFirePunch, "Aspecto cambiado a Lemon Milk");
			}
		});
		mnApariencia.add(mntmOpcionLM);

		JMenuItem mntmOpcionAM = new JMenuItem("Azumarill");
		mntmOpcionAM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * Personalización con un tema de FlatLaf almacenado en los paquetes dentro del
				 * paquete gui.
				 */
				IntelliJTheme.setup(App.class.getResourceAsStream("temas/colores/Moonlight.theme.json"));
				updateUI(frmFirePunch);

				JOptionPane.showMessageDialog(frmFirePunch, "Aspecto cambiado a Azumarill");
			}
		});
		mnApariencia.add(mntmOpcionAM);

		JMenuItem mntmOpcionFR = new JMenuItem("Frambuesa");
		mntmOpcionFR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * Personalización con un tema de FlatLaf almacenado en los paquetes dentro del
				 * paquete gui.
				 */
				IntelliJTheme.setup(App.class.getResourceAsStream("temas/colores/Solarized Light.theme.json"));
				updateUI(frmFirePunch);

				JOptionPane.showMessageDialog(frmFirePunch, "Aspecto cambiado a Frambuesa");
			}
		});
		mnApariencia.add(mntmOpcionFR);

		JMenuItem mntmOpcionN2 = new JMenuItem("Naranjito 2");
		mntmOpcionN2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * Personalización con un tema de FlatLaf almacenado en los paquetes dentro del
				 * paquete gui.
				 */
				IntelliJTheme.setup(App.class.getResourceAsStream("temas/oscuros/arc_theme_dark_orange.theme.json"));
				updateUI(frmFirePunch);

				JOptionPane.showMessageDialog(frmFirePunch, "Aspecto cambiado a Naranjito 2");
			}
		});
		mnApariencia.add(mntmOpcionN2);

		lblHomePage = new JLabel("Inicio de la aplicación");
		lblHomePage.setHorizontalAlignment(SwingConstants.CENTER);
		lblHomePage.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblHomePage.setBounds(621, 35, 307, 33);
		frmFirePunch.getContentPane().add(lblHomePage);

		cbCategorias = new JComboBox<String>();
		cbCategorias.setBounds(1042, 447, 220, 24);
		categorias = categoriaDAO.selectAllCategoria();
		cbCategorias.removeAllItems();

		/**
		 * Se crea un contador con valor 1 para rellenar los valores del desplegable de
		 * las categorías obtenidas.
		 */

		int numeral2 = 1;
		for (Categoria c : categorias) {
			cbCategorias.addItem(numeral2 + ". " + c.getNombreCategoria());
			numeral2++;
		}

		frmFirePunch.getContentPane().add(cbCategorias);

		lblCategoria = new JLabel("Categoria");
		lblCategoria.setBounds(931, 451, 93, 18);
		frmFirePunch.getContentPane().add(lblCategoria);

		/**
		 * Botón oculto que sirve para limpiar los datos de los campos de texto e
		 * imágenes de clientes y ejercicios.
		 */
		btnLimpiar = new JButton("New button");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tfIDCliente.setText("");
				tfIDEjercicio.setText("");
				tfNombreCliente.setText("");
				tfNombreEjercicio.setText("");
				tfApellidosCliente.setText("");
				tfEdadCliente.setText("");
				tfAlturaCliente.setText("");
				tfPesoCliente.setText("");
				tfSeriesEjercicio.setText("");
				tfRepeticionesEjercicio.setText("");
				tfCargaEjercicio.setText("");
				cbCategorias.setSelectedIndex(0);
				tableClientes.clearSelection();
				tableEjercicios.clearSelection();
			}
		});
		btnLimpiar.setBounds(287, -52, 117, 25);
		frmFirePunch.getContentPane().add(btnLimpiar);
		btnLimpiar.setVisible(false);

		/**
		 * Declaración de los modelos de las tablas que contendrán los valores de la
		 * bbdd.
		 * 
		 * modelCliente: modelo relativo a la tabla de los clientes.
		 * 
		 * modelEjercicio: modelo relativo a la tabla de los ejercicios.
		 */
		DefaultTableModel modelCliente = new DefaultTableModel() {
			public boolean isCellEditable(int fila, int columna) {
				return false; // No permitir la edición de las celdas
			}
		};

		DefaultTableModel modelEjercicio = new DefaultTableModel() {
			public boolean isCellEditable(int fila, int columna) {
				return false; // No permitir la edición de las celdas
			}
		};

		modelCliente.addColumn("ID");
		modelCliente.addColumn("Nombre");
		modelCliente.addColumn("Apellidos");
		modelCliente.addColumn("Edad");
		modelCliente.addColumn("Altura");
		modelCliente.addColumn("Peso");

		clientes = clienteDAO.selectAllCliente();

		for (Cliente c : clientes) {
			Object[] row = new Object[6];
			row[0] = c.getIdCliente();
			row[1] = c.getNombre();
			row[2] = c.getApellidos();
			row[3] = c.getEdad();
			row[4] = c.getAltura();
			row[5] = c.getPeso();
			modelCliente.addRow(row);
		}

		/**
		 * Los datos de la tabla de los clientes se rellena más adelante, cuando se
		 * realiza el login, porque dependiendo del usuario registrado, se mostrará unos
		 * clientes u otros
		 */

		tableClientes = new JTable(modelCliente);
		tableClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int index = tableClientes.getSelectedRow();
				TableModel model = tableClientes.getModel();

				tfIDCliente.setText(model.getValueAt(index, 0).toString());
				tfNombreCliente.setText(model.getValueAt(index, 1).toString());
				tfApellidosCliente.setText(model.getValueAt(index, 2).toString());
				tfEdadCliente.setText(model.getValueAt(index, 3).toString());
				tfAlturaCliente.setText(model.getValueAt(index, 4).toString());
				tfPesoCliente.setText(model.getValueAt(index, 5).toString());

			}

		});
		tableClientes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		TableColumnModel columnModelClientes = tableClientes.getColumnModel();

		/**
		 * Declaración y asignación de valores relativos al ancho y centrado de datos de
		 * las columnas de la tabla de clientes
		 */
		DefaultTableCellRenderer centerRenderClientes = new DefaultTableCellRenderer();
		centerRenderClientes.setHorizontalAlignment(JLabel.CENTER);

		columnModelClientes.getColumn(0).setPreferredWidth(60);
		columnModelClientes.getColumn(0).setCellRenderer(centerRenderClientes);
		columnModelClientes.getColumn(1).setPreferredWidth(100);
		columnModelClientes.getColumn(1).setCellRenderer(centerRenderClientes);
		columnModelClientes.getColumn(2).setPreferredWidth(130);
		columnModelClientes.getColumn(2).setCellRenderer(centerRenderClientes);
		columnModelClientes.getColumn(3).setPreferredWidth(60);
		columnModelClientes.getColumn(3).setCellRenderer(centerRenderClientes);
		columnModelClientes.getColumn(4).setPreferredWidth(90);
		columnModelClientes.getColumn(4).setCellRenderer(centerRenderClientes);
		columnModelClientes.getColumn(5).setPreferredWidth(90);
		columnModelClientes.getColumn(5).setCellRenderer(centerRenderClientes);

		frmFirePunch.getContentPane().add(tableClientes);
		tableClientes.setDefaultEditor(Cliente.class, null);

		scrollPaneClientes = new JScrollPane(tableClientes);
		scrollPaneClientes.setBounds(90, 86, 630, 181);
		frmFirePunch.getContentPane().add(scrollPaneClientes);

		modelEjercicio.addColumn("ID");
		modelEjercicio.addColumn("Nombre");
		modelEjercicio.addColumn("Series");
		modelEjercicio.addColumn("Repeticiones");
		modelEjercicio.addColumn("Carga (Kg)");
		modelEjercicio.addColumn("Categoria");

		/**
		 * Obtención de los datos para rellenar la tabla de clientes.
		 * 
		 * ejercicios: Lista de ejercicios con todos los valores de la bbdd.
		 * 
		 * categorias: Lista de categorías con todos los valores de la bbdd.
		 */
		ejercicios = ejercicioDAO.selectAllEjercicio();
		categorias = categoriaDAO.selectAllCategoria();

		for (Ejercicio e : ejercicios) {
			Object[] row = new Object[6];
			row[0] = e.getIdEjercicio();
			row[1] = e.getNombre();
			row[2] = e.getSeries();
			row[3] = e.getRepeticiones();
			row[4] = e.getCargaKg();
			for (Categoria c : categorias) {
				c = categoriaDAO.selectCategoriaById(e.getCategoria().getIdCategoria());
				row[5] = c.getNombreCategoria();
			}
			modelEjercicio.addRow(row);
		}

		tableEjercicios = new JTable(modelEjercicio);
		tableEjercicios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int indiceCategoria = 0;

				int index = tableEjercicios.getSelectedRow();
				TableModel model = tableEjercicios.getModel();

				tfIDEjercicio.setText(model.getValueAt(index, 0).toString());
				tfNombreEjercicio.setText(model.getValueAt(index, 1).toString());
				tfSeriesEjercicio.setText(model.getValueAt(index, 2).toString());
				tfRepeticionesEjercicio.setText(model.getValueAt(index, 3).toString());
				tfCargaEjercicio.setText(model.getValueAt(index, 4).toString());
				switch (model.getValueAt(index, 5).toString()) {
				case "Pierna":
					indiceCategoria = 0;
					break;
				case "Pecho":
					indiceCategoria = 1;
					break;
				case "Espalda":
					indiceCategoria = 2;
					break;
				case "Brazo":
					indiceCategoria = 3;
					break;
				case "Abdomen":
					indiceCategoria = 4;
					break;
				default:
					indiceCategoria = 10;
				}
				cbCategorias.setSelectedIndex(indiceCategoria);
			}

		});
		tableEjercicios.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		TableColumnModel columnModelEjercicios = tableEjercicios.getColumnModel();

		/**
		 * Declaración y asignación de valores relativos al ancho y centrado de datos de
		 * las columnas de la tabla de clientes
		 */
		DefaultTableCellRenderer centerRenderEjercicios = new DefaultTableCellRenderer();
		centerRenderEjercicios.setHorizontalAlignment(JLabel.CENTER);

		columnModelEjercicios.getColumn(0).setPreferredWidth(60);
		columnModelEjercicios.getColumn(0).setCellRenderer(centerRenderEjercicios);
		columnModelEjercicios.getColumn(1).setPreferredWidth(240);
		columnModelEjercicios.getColumn(1).setCellRenderer(centerRenderEjercicios);
		columnModelEjercicios.getColumn(2).setPreferredWidth(60);
		columnModelEjercicios.getColumn(2).setCellRenderer(centerRenderEjercicios);
		columnModelEjercicios.getColumn(3).setPreferredWidth(80);
		columnModelEjercicios.getColumn(3).setCellRenderer(centerRenderEjercicios);
		columnModelEjercicios.getColumn(4).setPreferredWidth(70);
		columnModelEjercicios.getColumn(4).setCellRenderer(centerRenderEjercicios);
		columnModelEjercicios.getColumn(5).setPreferredWidth(70);
		columnModelEjercicios.getColumn(5).setCellRenderer(centerRenderEjercicios);
		frmFirePunch.getContentPane().add(tableEjercicios);
		tableEjercicios.setDefaultEditor(Cliente.class, null);

		scrollPaneEjercicios = new JScrollPane(tableEjercicios);
		scrollPaneEjercicios.setBounds(810, 90, 570, 181);
		frmFirePunch.getContentPane().add(scrollPaneEjercicios);

		btnInsertarCliente = new JButton("Insertar");
		btnInsertarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/**
				 * Comprobación de errores de los campos de cliente. Si alguno de los campos
				 * está en blanco no deja continuar y si está todo en el formato correcto se
				 * puede realizar la inserción de datos.
				 */
				if (tfNombreCliente.getText().isEmpty() || tfApellidosCliente.getText().isEmpty()
						|| tfAlturaCliente.getText().isEmpty() || tfPesoCliente.getText().isEmpty()
						|| tfEdadCliente.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Rellena todos los campos, por favor.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					Matcher matNombre = patNombre.matcher(tfNombreCliente.getText());
					Matcher matApellidos = patApellido.matcher(tfApellidosCliente.getText());
					Matcher matEdad = patEdad.matcher(tfEdadCliente.getText());
					Matcher matAltura = patEntero.matcher(tfAlturaCliente.getText());
					Matcher matPeso = patDecimal.matcher(tfPesoCliente.getText());

					if (!matNombre.matches()) {
						JOptionPane.showMessageDialog(null, "El nombre tiene un formato incorrecto.\nEj: Pablo.",
								"Error", JOptionPane.ERROR_MESSAGE);
					} else if (!matApellidos.matches()) {
						JOptionPane.showMessageDialog(null,
								"El apellido tiene un formato incorrecto.\nEj: Molero Marín.", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else if (!matEdad.matches()) {
						JOptionPane.showMessageDialog(null,
								"La edad no tiene un formato correcto. Prueba con dos dígitos.\nEj: 25.", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else if ((Integer.parseInt(tfEdadCliente.getText()) < 15)) {
						JOptionPane.showMessageDialog(null, "No pueden entrar personas menores de 15 años.", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else if ((Integer.parseInt(tfEdadCliente.getText()) > 90)) {
						JOptionPane.showMessageDialog(null, "No pueden entrar personas mayores de 90 años.", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else if (!matAltura.matches()) {
						JOptionPane.showMessageDialog(null, "La altura no tiene el formato correcto.\nEj: 180.",
								"Error", JOptionPane.ERROR_MESSAGE);
					} else if (!matPeso.matches()) {
						JOptionPane.showMessageDialog(null, "La altura no tiene el formato correcto.\\nEj:45.7.",
								"Error", JOptionPane.ERROR_MESSAGE);
					} else {
						Cliente cliente = new Cliente(tfNombreCliente.getText(), tfApellidosCliente.getText(),
								Integer.parseInt(tfEdadCliente.getText()), Integer.parseInt(tfAlturaCliente.getText()),
								Double.parseDouble(tfPesoCliente.getText()));
						clienteDAO.insertCliente(cliente);
						btnLimpiar.doClick();
						JOptionPane.showMessageDialog(null, "Cliente creado");
					}
				}
			}
		});
		btnInsertarCliente.setBounds(227, 515, 117, 25);
		frmFirePunch.getContentPane().add(btnInsertarCliente);

		btnInsertarEjercicio = new JButton("Insertar");
		btnInsertarEjercicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/**
				 * Comprobación de errores de los campos de ejercicio. Si alguno de los campos
				 * está en blanco no deja continuar y si está todo en el formato correcto se
				 * puede realizar la inserción de datos.
				 */
				if (tfCargaEjercicio.getText().isEmpty() || tfRepeticionesEjercicio.getText().isEmpty()
						|| tfSeriesEjercicio.getText().isEmpty() || tfNombreEjercicio.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Rellena todos los campos, por favor.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					Matcher matCarga = patDecimal.matcher(tfCargaEjercicio.getText());
					Matcher matRepticiones = patEntero.matcher(tfRepeticionesEjercicio.getText());
					Matcher matSeries = patEntero.matcher(tfSeriesEjercicio.getText());
					if (!matCarga.matches()) {
						JOptionPane.showMessageDialog(null,
								"La carga del ejercicio tiene un formato incorrecto.\nEj: 12.0", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else if (!matRepticiones.matches()) {
						JOptionPane.showMessageDialog(null,
								"Las repeticiones del ejercicio tiene un formato incorrecto.\nEj: 10", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else if (!matSeries.matches()) {
						JOptionPane.showMessageDialog(null,
								"Las series del ejercicio tiene un formato incorrecto.\nEj: 4", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {

						Categoria categoria = categoriaDAO.selectCategoriaById(cbCategorias.getSelectedIndex() + 1);

						Ejercicio ejercicio = new Ejercicio(tfNombreEjercicio.getText(),
								Integer.parseInt(tfSeriesEjercicio.getText()),
								Integer.parseInt(tfRepeticionesEjercicio.getText()),
								Double.parseDouble(tfCargaEjercicio.getText()), categoria);
						ejercicioDAO.insertEjercicio(ejercicio);
						btnLimpiar.doClick();
						JOptionPane.showMessageDialog(null, "Ejercicio creado");
					}
				}
			}
		});
		btnInsertarEjercicio.setBounds(908, 515, 117, 25);
		frmFirePunch.getContentPane().add(btnInsertarEjercicio);

		btnActualizarEjercicio = new JButton("Actualizar");
		btnActualizarEjercicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/**
				 * Comprobación de errores de los campos de ejercicio. Si alguno de los campos
				 * está en blanco no deja continuar y si está todo en el formato correcto se
				 * puede realizar la inserción de datos.
				 */
				if (tfCargaEjercicio.getText().isEmpty() || tfRepeticionesEjercicio.getText().isEmpty()
						|| tfSeriesEjercicio.getText().isEmpty() || tfNombreEjercicio.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Rellena todos los campos, por favor.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					Matcher matCarga = patDecimal.matcher(tfCargaEjercicio.getText());
					Matcher matRepticiones = patEntero.matcher(tfRepeticionesEjercicio.getText());
					Matcher matSeries = patEntero.matcher(tfSeriesEjercicio.getText());
					if (!matCarga.matches()) {
						JOptionPane.showMessageDialog(null,
								"La carga del ejercicio tiene un formato incorrecto.\nEj: 12.0", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else if (!matRepticiones.matches()) {
						JOptionPane.showMessageDialog(null,
								"Las repeticiones del ejercicio tiene un formato incorrecto.\nEj: 10", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else if (!matSeries.matches()) {
						JOptionPane.showMessageDialog(null,
								"Las series del ejercicio tiene un formato incorrecto.\nEj: 4", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						Categoria categoria = categoriaDAO.selectCategoriaById(cbCategorias.getSelectedIndex() + 1);

						Ejercicio ejercicioActualizar = ejercicioDAO
								.selectEjercicioById(Integer.parseInt(tfIDEjercicio.getText()));
						ejercicioActualizar.setIdEjercicio(Integer.parseInt(tfIDEjercicio.getText()));
						ejercicioActualizar.setNombre(tfNombreEjercicio.getText());
						ejercicioActualizar.setSeries(Integer.parseInt(tfSeriesEjercicio.getText()));
						ejercicioActualizar.setRepeticiones(Integer.parseInt(tfRepeticionesEjercicio.getText()));
						ejercicioActualizar.setCargaKg(Double.parseDouble(tfCargaEjercicio.getText()));
						ejercicioActualizar.setCategoria(categoria);
						ejercicioDAO.updateEjercicio(ejercicioActualizar);
						btnLimpiar.doClick();
						JOptionPane.showMessageDialog(null, "Ejercicio actualizado");
					}
				}
			}
		});
		btnActualizarEjercicio.setBounds(1056, 515, 117, 25);
		frmFirePunch.getContentPane().add(btnActualizarEjercicio);

		btnActualizarCliente = new JButton("Actualizar");
		btnActualizarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/**
				 * Comprobación de errores de los campos de cliente. Si alguno de los campos
				 * está en blanco no deja continuar y si está todo en el formato correcto se
				 * puede realizar la inserción de datos.
				 */
				if (tfNombreCliente.getText().isEmpty() || tfApellidosCliente.getText().isEmpty()
						|| tfAlturaCliente.getText().isEmpty() || tfPesoCliente.getText().isEmpty()
						|| tfEdadCliente.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Rellena todos los campos, por favor.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					// Matcher matNombre = patNombre.matcher(tfNombreEjercicio.getText());
					Matcher matNombre = patNombre.matcher(tfNombreCliente.getText());
					Matcher matApellidos = patApellido.matcher(tfApellidosCliente.getText());
					Matcher matEdad = patEdad.matcher(tfEdadCliente.getText());
					Matcher matAltura = patEntero.matcher(tfAlturaCliente.getText());
					Matcher matPeso = patDecimal.matcher(tfPesoCliente.getText());

					if (!matNombre.matches()) {
						JOptionPane.showMessageDialog(null, "El nombre tiene un formato incorrecto.\nEj: Pablo.",
								"Error", JOptionPane.ERROR_MESSAGE);
					} else if (!matApellidos.matches()) {
						JOptionPane.showMessageDialog(null,
								"El apellido tiene un formato incorrecto.\nEj: Molero Marín.", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else if (!matEdad.matches()) {
						JOptionPane.showMessageDialog(null,
								"La edad no tiene un formato correcto. Prueba con dos dígitos.\nEj: 25.", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else if ((Integer.parseInt(tfEdadCliente.getText()) < 15)) {
						JOptionPane.showMessageDialog(null, "No pueden entrar personas menores de 15 años.", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else if ((Integer.parseInt(tfEdadCliente.getText()) > 90)) {
						JOptionPane.showMessageDialog(null, "No pueden entrar personas mayores de 90 años.", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else if (!matAltura.matches()) {
						JOptionPane.showMessageDialog(null, "La altura no tiene el formato correcto.\nEj: 180.",
								"Error", JOptionPane.ERROR_MESSAGE);
					} else if (!matPeso.matches()) {
						JOptionPane.showMessageDialog(null, "La altura no tiene el formato correcto.\\nEj:45.7.",
								"Error", JOptionPane.ERROR_MESSAGE);
					} else {

						Cliente clienteActualizar = clienteDAO
								.selectClienteById(Integer.parseInt(tfIDCliente.getText()));

						clienteActualizar.setIdCliente(Integer.parseInt(tfIDCliente.getText()));
						clienteActualizar.setNombre(tfNombreCliente.getText());
						clienteActualizar.setApellidos(tfApellidosCliente.getText());
						clienteActualizar.setEdad(Integer.parseInt(tfEdadCliente.getText()));
						clienteActualizar.setAltura(Integer.parseInt(tfAlturaCliente.getText()));
						clienteActualizar.setPeso(Double.parseDouble(tfPesoCliente.getText()));
						clienteDAO.updateCliente(clienteActualizar);
						btnLimpiar.doClick();
						JOptionPane.showMessageDialog(null, "Cliente actualizado");
					}
				}
			}
		});
		btnActualizarCliente.setBounds(375, 515, 117, 25);
		frmFirePunch.getContentPane().add(btnActualizarCliente);

		btnBorrarCliente = new JButton("Borrar");
		btnBorrarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/**
				 * Si el campo del ID está vacío no deja realizar el borrado.
				 */
				if (tfIDCliente.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Selecciona un cliente para eliminarlo.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					clienteDAO.deleteCliente(Integer.parseInt(tfIDCliente.getText()));
					btnLimpiar.doClick();
					JOptionPane.showMessageDialog(null, "Cliente borrado");
				}
			}
		});
		btnBorrarCliente.setBounds(520, 515, 117, 25);
		frmFirePunch.getContentPane().add(btnBorrarCliente);

		btnBorrarEjercicio = new JButton("Borrar");
		btnBorrarEjercicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/**
				 * Si el campo del ID está vacío no deja realizar el borrado.
				 */
				if (tfIDEjercicio.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Selecciona un ejercicio para eliminarlo.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					ejercicioDAO.deleteEjercicio(Integer.parseInt(tfIDEjercicio.getText()));
					btnLimpiar.doClick();
					JOptionPane.showMessageDialog(null, "Ejercicio borrado");
				}
			}
		});
		btnBorrarEjercicio.setBounds(1198, 515, 117, 25);
		frmFirePunch.getContentPane().add(btnBorrarEjercicio);

		/**
		 * Declaración de los campos relativos a clientes y ejercicios.
		 */

		tfIDCliente = new JTextField();
		tfIDCliente.setEditable(false);
		tfIDCliente.setBounds(354, 291, 220, 21);
		frmFirePunch.getContentPane().add(tfIDCliente);
		tfIDCliente.setColumns(10);

		tfNombreCliente = new JTextField();
		tfNombreCliente.setColumns(10);
		tfNombreCliente.setBounds(354, 323, 220, 21);
		frmFirePunch.getContentPane().add(tfNombreCliente);

		tfEdadCliente = new JTextField();
		tfEdadCliente.setColumns(10);
		tfEdadCliente.setBounds(356, 385, 220, 21);
		frmFirePunch.getContentPane().add(tfEdadCliente);

		tfAlturaCliente = new JTextField();
		tfAlturaCliente.setColumns(10);
		tfAlturaCliente.setBounds(356, 416, 220, 21);
		frmFirePunch.getContentPane().add(tfAlturaCliente);

		tfPesoCliente = new JTextField();
		tfPesoCliente.setColumns(10);
		tfPesoCliente.setBounds(356, 449, 220, 21);
		frmFirePunch.getContentPane().add(tfPesoCliente);

		tfIDEjercicio = new JTextField();
		tfIDEjercicio.setEditable(false);
		tfIDEjercicio.setColumns(10);
		tfIDEjercicio.setBounds(1042, 291, 220, 21);
		frmFirePunch.getContentPane().add(tfIDEjercicio);

		tfNombreEjercicio = new JTextField();
		tfNombreEjercicio.setColumns(10);
		tfNombreEjercicio.setBounds(1042, 323, 220, 21);
		frmFirePunch.getContentPane().add(tfNombreEjercicio);

		tfSeriesEjercicio = new JTextField();
		tfSeriesEjercicio.setColumns(10);
		tfSeriesEjercicio.setBounds(1042, 354, 220, 21);
		frmFirePunch.getContentPane().add(tfSeriesEjercicio);

		tfRepeticionesEjercicio = new JTextField();
		tfRepeticionesEjercicio.setColumns(10);
		tfRepeticionesEjercicio.setBounds(1042, 385, 220, 21);
		frmFirePunch.getContentPane().add(tfRepeticionesEjercicio);

		tfCargaEjercicio = new JTextField();
		tfCargaEjercicio.setColumns(10);
		tfCargaEjercicio.setBounds(1042, 418, 220, 21);
		frmFirePunch.getContentPane().add(tfCargaEjercicio);

		tfApellidosCliente = new JTextField();
		tfApellidosCliente.setColumns(10);
		tfApellidosCliente.setBounds(356, 354, 220, 21);
		frmFirePunch.getContentPane().add(tfApellidosCliente);

		lblIDCliente = new JLabel("ID");
		lblIDCliente.setBounds(261, 293, 70, 15);
		frmFirePunch.getContentPane().add(lblIDCliente);

		lblNombreCliente = new JLabel("Nombre");
		lblNombreCliente.setBounds(261, 325, 70, 15);
		frmFirePunch.getContentPane().add(lblNombreCliente);

		lblApellidosCliente = new JLabel("Apellidos");
		lblApellidosCliente.setBounds(261, 356, 70, 15);
		frmFirePunch.getContentPane().add(lblApellidosCliente);

		lblEdadCliente = new JLabel("Edad");
		lblEdadCliente.setBounds(261, 387, 70, 15);
		frmFirePunch.getContentPane().add(lblEdadCliente);

		lblAlturaCliente = new JLabel("Altura (cm)");
		lblAlturaCliente.setBounds(261, 418, 82, 15);
		frmFirePunch.getContentPane().add(lblAlturaCliente);

		lblPesoCliente = new JLabel("Peso (Kg)");
		lblPesoCliente.setBounds(261, 451, 70, 15);
		frmFirePunch.getContentPane().add(lblPesoCliente);

		lblCargakg = new JLabel("Carga (Kg)");
		lblCargakg.setBounds(931, 425, 89, 15);
		frmFirePunch.getContentPane().add(lblCargakg);

		lblRepeticiones = new JLabel("Repeticiones");
		lblRepeticiones.setBounds(931, 388, 106, 15);
		frmFirePunch.getContentPane().add(lblRepeticiones);

		lblSeries = new JLabel("Series");
		lblSeries.setBounds(931, 357, 70, 15);
		frmFirePunch.getContentPane().add(lblSeries);

		lblNombreEjercicio = new JLabel("Nombre");
		lblNombreEjercicio.setBounds(931, 326, 70, 15);
		frmFirePunch.getContentPane().add(lblNombreEjercicio);

		lblIDEjercicio = new JLabel("ID");
		lblIDEjercicio.setBounds(931, 294, 70, 15);
		frmFirePunch.getContentPane().add(lblIDEjercicio);
		
		
		
		DefaultTableModel modelRealiza = new DefaultTableModel() {
			public boolean isCellEditable(int fila, int columna) {
				return false; // No permitir la edición de las celdas
			}
		};
		
		modelRealiza.addColumn("Cliente");
		modelRealiza.addColumn("Ejercicio");
		modelRealiza.addColumn("Categoría");
		
		tableRutina = new JTable(modelRealiza);
		tableRutina.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int index = tableRutina.getSelectedRow();
				TableModel model = tableRutina.getModel();

			}

		});
		tableRutina.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		TableColumnModel columnModelRealiza = tableRutina.getColumnModel();

		DefaultTableCellRenderer centerRenderRealiza = new DefaultTableCellRenderer();
		centerRenderRealiza.setHorizontalAlignment(JLabel.CENTER);

		columnModelRealiza.getColumn(0).setPreferredWidth(60);
		columnModelRealiza.getColumn(0).setCellRenderer(centerRenderRealiza);
		columnModelRealiza.getColumn(1).setPreferredWidth(200);
		columnModelRealiza.getColumn(1).setCellRenderer(centerRenderRealiza);
		columnModelRealiza.getColumn(2).setPreferredWidth(60);
		columnModelRealiza.getColumn(2).setCellRenderer(centerRenderRealiza);
		frmFirePunch.getContentPane().add(tableRutina);
		
		JScrollPane scrollPaneRutina = new JScrollPane(tableRutina);
		scrollPaneRutina.setBounds(420, 566, 630, 181);
		frmFirePunch.getContentPane().add(scrollPaneRutina);
		
		JButton btnEntrenar = new JButton("Entrenar");
		btnEntrenar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object[] row = new Object[3];

				Cliente clienteNombre = clienteDAO.selectClienteById(Integer.parseInt(tfIDCliente.getText()));
				row[0] = clienteNombre.getNombre();

				Ejercicio ejercicioNombre = ejercicioDAO
						.selectEjercicioById(Integer.parseInt(tfIDEjercicio.getText()));
				row[1] = ejercicioNombre.getNombre();

				Categoria c = categoriaDAO.selectCategoriaById(ejercicioNombre.getCategoria().getIdCategoria());
				row[2] = c.getNombreCategoria();
				
				modelRealiza.addRow(row);

				JOptionPane.showMessageDialog(null, "Cliente entrenando");
			}
		});
		btnEntrenar.setBounds(716, 355, 117, 25);
		frmFirePunch.getContentPane().add(btnEntrenar);
		
		JButton btnDejarDeEntrenar = new JButton("Dejar de entrenar");
		btnDejarDeEntrenar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = tableRutina.getSelectedRow();
				DefaultTableModel model = (DefaultTableModel) tableRutina.getModel();
				model.removeRow(index);
				btnLimpiar.doClick();
			}
		});
		btnDejarDeEntrenar.setBounds(420, 758, 161, 25);
		frmFirePunch.getContentPane().add(btnDejarDeEntrenar);
		
		JButton btnAcabarSesion = new JButton("Acabar sesión");
		btnAcabarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultTableModel model = (DefaultTableModel) tableRutina.getModel();
				model.setRowCount(0);
				btnLimpiar.doClick();
			}
		});
		btnAcabarSesion.setBounds(889, 758, 161, 25);
		frmFirePunch.getContentPane().add(btnAcabarSesion);


	}
}