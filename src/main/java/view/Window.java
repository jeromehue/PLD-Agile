package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

import controller.Controller;
import modele.Tour;
import modele.PointFactory;

public class Window extends JFrame {

	private static final Logger logger = LoggerFactory.getLogger(Window.class);
	private static final long serialVersionUID = 1L;

	protected final static Color BACKGROUND_COLOR = new Color(224, 224, 224);

	// Buttons labels
	protected final static String LOAD_MAP = "Load XML Map";
	protected final static String LOAD_REQUEST = "Load XML Requests";
	protected final static String COMPUTE_TOUR = "Compute tour";
	protected final static String CLICK_STEP = "Display tour";
	protected final static String MODIFY_TOUR = "Enter tour edition mode";
	protected final static String EXIT_MODIFY_TOUR = "Exit tour edition mode";
	protected final static String MODIFY_ORDER = "Change order";
	protected final static String MODIFY_REQUEST = "Move a request";
	protected final static String ADD_REQUEST = "Add a request";
	protected final static String REMOVE_REQUEST = "Delete a request";
	protected final static String UNDO = "<html><strong>Undo<html/><strong/>";
	protected final static String REDO = "<html><strong>Redo<html/><strong/>";

	// Components
	private GraphicalView graphicalView;
	private TextualView textualView;
	private JLabel messageFrame;
	private JToolBar toolBar;
	private ArrayList<JButton> optionalsButtons;
	private JMenuItem switchmode;
	
	// Listeners
	private ButtonListener buttonListener;
	private MouseListener mouseListener;
	
	private boolean optionalsButtonsVisible;
	
	/**
	 * Create a window with menu bar, a graphical zone to display the map, the request and the tour, 
	 * a frame for displaying messages, a textual zone for describing steps of the tour,
	 * and listeners which catch events and forward them to controller 
	 * @param tour the tour
	 * @param controller the controller
	 */
	public Window(Controller controller, Tour tour) {
		super("Deliver'IF");
		this.buttonListener = new ButtonListener(controller);
		this.setSize(1400,1000);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);

		this.optionalsButtonsVisible = false;
		optionalsButtons = new ArrayList<JButton>();
		try { UIManager.setLookAndFeel(new NimbusLookAndFeel()); } catch (Exception e) {}

		JPanel contentPane = (JPanel) getContentPane();
		contentPane.setLayout(new BorderLayout());

		this.messageFrame = createMessageFrame();
		contentPane.add(messageFrame, BorderLayout.SOUTH);

		this.toolBar = createToolBar(controller);
		contentPane.add(toolBar, BorderLayout.NORTH);
		this.toolBar.setVisible(this.optionalsButtonsVisible);

		this.textualView = new TextualView(tour, this.buttonListener);
		JScrollPane scrollPane = new JScrollPane(textualView
				,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
				,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		contentPane.add(scrollPane, BorderLayout.WEST);

		this.graphicalView = new GraphicalView(tour);
		contentPane.add(graphicalView, BorderLayout.CENTER);

		this.mouseListener = new MouseListener(this, graphicalView, controller);
		addMouseMotionListener(mouseListener);

		JMenuBar menuBar = createMenuBar();
		this.setJMenuBar(menuBar);

		this.setVisible(true);

		// To be placed after the set visible.
		PointFactory.initPointFactory(graphicalView, null);
	}
	
	public GraphicalView getGraphicalView() {
		return graphicalView;
	}

	public TextualView getTextualView() {
		return textualView;
	}
	
	/**
	 * Displays a message in the message frame
	 * @param message the message to display
	 */
	public void setMessage(String message) {
		messageFrame.setText(message);
	}

	public boolean isOptionalsButtonsVisible() {
		return optionalsButtonsVisible;
	}
	
	/**
	 * Method called to create the menu bar component
	 */
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		// Load
		JMenu mnuLoad = new JMenu(" File ");
		mnuLoad.setMnemonic('F');
		mnuLoad.setForeground(Color.black);
		JMenuItem mnuLoadMap = new JMenuItem(LOAD_MAP);
		mnuLoadMap.setSize(new Dimension(50, 50));
		mnuLoadMap.setMnemonic('M');
		mnuLoadMap.addActionListener(this.buttonListener);
		mnuLoad.add(mnuLoadMap);
		JMenuItem mnuLoadRequest = new JMenuItem(LOAD_REQUEST);
		mnuLoadRequest.setMnemonic('R');
		mnuLoadRequest.addActionListener(this.buttonListener);
		mnuLoad.add(mnuLoadRequest);
		menuBar.add(mnuLoad);

		// Compute
		JMenu mnuCompute = new JMenu(" Action ");
		mnuCompute.setMnemonic('A');
		JMenuItem mnuComputeTour = new JMenuItem(COMPUTE_TOUR);
		mnuComputeTour.setMnemonic('C');
		mnuComputeTour.addActionListener(this.buttonListener);
		mnuCompute.add(mnuComputeTour);
		menuBar.add(mnuCompute);

		// Edit
		JMenu mnuEdit = new JMenu(" Edit ");
		mnuEdit.setMnemonic('E');
		JMenuItem modifyTour = new JMenuItem(MODIFY_TOUR);
		modifyTour.setMnemonic('S');
		modifyTour.addActionListener(this.buttonListener);
		this.switchmode = modifyTour;
		mnuEdit.add(modifyTour);
		menuBar.add(mnuEdit);
		
		return menuBar;
	}
	
	/**
	 * Method called to create a button of the menu bar
	 */
	private JButton createButton(Color foregroundColor, String label) {
		JButton createdButton = new JButton(label);
		createdButton.addActionListener(buttonListener);
		createdButton.setForeground(foregroundColor);
		createdButton.setVisible(false);
		this.optionalsButtons.add(createdButton);
		return createdButton;
	}
	
	/**
	 * Method called to create the tool bar component (edition mode) which is hided when application starts
	 * @param controller the controller
	 */
	private JToolBar createToolBar(Controller controller) {
		JToolBar toolBar = new JToolBar();
		toolBar.setPreferredSize(new Dimension(100, 30));
		toolBar.add(this.createButton(Color.black, UNDO));
		toolBar.add(this.createButton(Color.black, REDO));
		toolBar.add(this.createButton(Color.darkGray, ADD_REQUEST));
		toolBar.add(this.createButton(Color.darkGray, MODIFY_REQUEST));
		toolBar.add(this.createButton(Color.darkGray, MODIFY_ORDER));
		toolBar.add(this.createButton(Color.darkGray, REMOVE_REQUEST));
		return toolBar;
	}
	
	/**
	 * Method called to create the message frame component 
	 */
	private JLabel createMessageFrame() {
		messageFrame = new JLabel();
		messageFrame.setPreferredSize(new Dimension(50, 150));
		messageFrame.setBorder(BorderFactory.createTitledBorder("Messages"));
		messageFrame.setHorizontalAlignment(SwingConstants.CENTER);
		messageFrame.setVerticalAlignment(SwingConstants.TOP);
		messageFrame.setText("To begin, please load a map (XML file).");
		messageFrame.setBackground(BACKGROUND_COLOR);
		return messageFrame;
	}

	/**
	 * Method called hide or display the tool bar 
	 */
	public void changeOptionalsButtonsVisibility() {
		this.optionalsButtonsVisible = !this.optionalsButtonsVisible;
		for (JButton b : optionalsButtons)
			b.setVisible(optionalsButtonsVisible);
		if (this.optionalsButtonsVisible) {
			this.switchmode.setText(EXIT_MODIFY_TOUR);
		} else {
			this.switchmode.setText(MODIFY_TOUR);
		}
		this.toolBar.setVisible(optionalsButtonsVisible);
	}
	
	/**
	 * Method called to open a dialog frame and select a file path  
	 */
	public String createDialogBoxToGetFilePath() {
		final JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML files (*.xml)", "xml");
		fc.setFileFilter(filter);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setCurrentDirectory(new File("./src/main"));
		int returnVal = fc.showOpenDialog(null);
		String absPath = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			logger.info("You chose totv open this file: " + fc.getSelectedFile().getAbsolutePath());
			absPath = fc.getSelectedFile().getAbsolutePath();
		}
		return absPath;
	}
	
	/**
	 * Method called to open a dialog frame and select a number
	 */
	public int displaySelectOrderDialog() {
		logger.info("Entering displaySelectOrderDialog");
		int i = 0;
		while (i == 0) {
			String str = JOptionPane.showInputDialog(this, "Enter the new index of this step");
			logger.info("String from showInput dialog {}", str);
			if (str == null) {
				return 0;
			}
			try {
				i = Integer.parseInt(str);
			} catch (Exception e) {
				logger.error("You did not enter a valid number, or it was equal to zero");
			}
		}
		return i;
	}
}
