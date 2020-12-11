package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import controller.Controller;
import modele.Tour;
import modele.PointFactory;

/**
 * The main window of our program, divided into four main areas: a graphical 
 * view to display the map, a text view to display information about steps, a 
 * menu and a box to display messages.
 * 
 *  @author H4414
 * 
 * */


public class Window extends JFrame {

	private static final Logger logger = LoggerFactory.getLogger(Window.class);
	private static final long serialVersionUID = 1L;

	protected static final Color BACKGROUND_COLOR = new Color(224, 224, 224);

	// Buttons labels
	protected static final  String LOAD_MAP = "Load XML Map";
	protected static final  String LOAD_REQUEST = "Load XML Requests";
	protected static final  String COMPUTE_TOUR = "Compute tour";
	protected static final  String CLICK_STEP = "Display tour";
	protected static final  String MODIFY_TOUR = "Enter tour edition mode";
	protected static final  String EXIT_MODIFY_TOUR = "Exit tour edition mode";
	protected static final  String MODIFY_ORDER = "Change order";
	protected static final  String ADD_REQUEST = "Add a request";
	protected static final  String REMOVE_REQUEST = "Delete a step";
	protected static final  String UNDO = "<html><strong>Undo<html/><strong/>";
	protected static final  String REDO = "<html><strong>Redo<html/><strong/>";
	protected static final  String QUIT_TOUR_EDITION = " ";
	
	
	
	private String HEADER_TEXT = "PLD-AGILE 4IF-H4414 - "
									+ "Quentin Regaud - "
									+ "Sylvain de Verclos - "
									+ "Yohan Meyer - "
									+ "Jérome Hue - "
									+ "Lucie Clémenceau - "
									+ "Charly Poirier - "
									+ "2020 ";

	// Components
	private GraphicalView graphicalView;
	private TextualView textualView;
	private JLabel messageFrame;
	private JLabel streetFrame;
	private JLabel bottomBar;
	private JToolBar toolBar;
	private ArrayList<JButton> optionalsButtons;
	private JMenuItem switchmode;
	
	// Listeners
	private ButtonListener buttonListener;
	private MouseListener mouseListener;
	
	private boolean optionalsButtonsVisible;
	
	/**
	 * Create a window with menu bar, a graphical zone to display the map, the 
	 * request and the tour, a frame for displaying messages, a textual zone 
	 * for describing steps of the tour,
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
		optionalsButtons = new ArrayList<>();
		try { UIManager.setLookAndFeel(new NimbusLookAndFeel()); } catch (Exception e) {}

		JPanel contentPane = (JPanel) getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		contentPane.add(this.CreateSouthPanel(), BorderLayout.SOUTH);

		this.toolBar = createToolBar(controller);
		contentPane.add(toolBar, BorderLayout.NORTH);
		this.toolBar.setVisible(this.optionalsButtonsVisible);

		this.textualView = new TextualView(tour, this.buttonListener);
		contentPane.add(textualView.getScrollPane(), BorderLayout.WEST);

		this.graphicalView = new GraphicalView(tour);
		contentPane.add(this.createCenterPanel(), BorderLayout.CENTER);


        this.mouseListener = new MouseListener(this, graphicalView, controller);
        addMouseListener(mouseListener);
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
	 * @param message the message to display.
	 */
	public void setMessage(String message) {
		this.messageFrame.setText("<html><strong>" + 
				message + "<html/><strong/>");
	}
	
	public void setStreet(String street) {
		this.streetFrame.setText(street);
	}

	/**
	 * Return true if Optionals buttons are visible, wich means that we are
	 * in the 'Tour Modification' mode.
	 * @return A boolean variable indicating the visibility of the optional 
	 * buttons.
	 */
	public boolean isOptionalsButtonsVisible() {
		return optionalsButtonsVisible;
	}
	
	/**
	 * Method called to create the menu bar component.
	 * @return a JMenuBar object 
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
	 * Method called to create a button of the menu bar.
	 * @param foregroundColor The foreground color of the Jbutton.
	 * @param label The label of the Jbutton.
	 * @return The Jbutton created
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
	 * Method called to create the tool bar component (edition mode) which 
	 * is hidden when application starts.
	 * @param controller the controller
	 * @return a JToolBar object
	 */
	private JToolBar createToolBar(Controller controller) {
		JToolBar toolBar = new JToolBar();
		toolBar.setPreferredSize(new Dimension(100, 30));
		JButton cancelButton = createButton(Color.black, QUIT_TOUR_EDITION);
		Icon icon = new ImageIcon("src/main/resources/icons/icons8-cancel-16.png");
	    cancelButton.setIcon(icon);
	    cancelButton.setPreferredSize(new Dimension(50,20));
	    cancelButton.setContentAreaFilled(false);
		toolBar.add(cancelButton);
		toolBar.add(this.createButton(Color.black, UNDO));
		toolBar.add(this.createButton(Color.black, REDO));
		toolBar.add(this.createButton(Color.darkGray, ADD_REQUEST));
		toolBar.add(this.createButton(Color.darkGray, MODIFY_ORDER));
		toolBar.add(this.createButton(Color.darkGray, REMOVE_REQUEST));
		return toolBar;
	}
	
	/**
	 * Method called to create the message frame component 
	 * @param title The title of the message frame.
	 * @return a JLabel object
	 */
	private JLabel createMessageFrame(String title) {
		JLabel newFrame = new JLabel();
		newFrame.setPreferredSize(new Dimension(50, 80));
		newFrame.setBorder(BorderFactory.createTitledBorder(title));
		newFrame.setHorizontalAlignment(SwingConstants.CENTER);
		newFrame.setVerticalAlignment(SwingConstants.CENTER);
		newFrame.setBackground(BACKGROUND_COLOR);
		return newFrame;
	}
	
	/**
	 * Method called to initialize the bottom bar. 
	 * @return a JLabel object
	 */
	private JLabel createBottomBar(){
		JLabel newBottomBar = new JLabel();
		newBottomBar.setPreferredSize(new Dimension(50,20));
		newBottomBar.setHorizontalAlignment(SwingConstants.CENTER);
		newBottomBar.setVerticalAlignment(SwingConstants.CENTER);
		newBottomBar.setText(this.HEADER_TEXT);
		newBottomBar.setFont(new Font("", 0, 10));
		return newBottomBar;
	}
	
	/**
	 * Method called initialize south panel. 
	 * @return a JLabel object
	 */
	private JPanel CreateSouthPanel() {
		JPanel southPanel = new JPanel(new BorderLayout());
		southPanel.setPreferredSize(new Dimension(50,100));
		this.messageFrame = createMessageFrame("Messages");
		this.bottomBar = createBottomBar();
		this.setMessage("To begin, please load a map (XML file).");
		southPanel.add(this.messageFrame, BorderLayout.CENTER);
		southPanel.add(this.bottomBar, BorderLayout.SOUTH);
		return southPanel;
	}
	
	/**
	 * Method called initialize center panel. 
	 * @return a JLabel object
	 */
	private JPanel createCenterPanel() {
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBackground(Color.white);
		this.streetFrame = createMessageFrame("Street");
		this.streetFrame.setPreferredSize(new Dimension(50,50));
		centerPanel.add(this.graphicalView, BorderLayout.CENTER);
		centerPanel.add(this.streetFrame, BorderLayout.SOUTH);
		return centerPanel;
	}

	/**
	 * Method called hide or display the tool bar.
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
		this.textualView.setTourEditionLabelVisibility(optionalsButtonsVisible);
	}
    
    
    /**
     * Method called to display a number input dialog window.
     * @param msg The message displayed to the user.
     * @return The duration of pickup/delivery, as an int.
     * */
    public int displaySelectTimeDialog(String msg) {
    	logger.info("displaySelectTimeDialog()");
    	int i = -1;
    	while (i < 0) {
    		String response = JOptionPane.showInputDialog(this, msg);
    		logger.info("String from showInput dialog {}", response);
    		if ((response != null) && (response.length() > 0)) {
    			try {
        			i = Integer.parseInt(response);
        		} catch(Exception e) {
        			logger.error("Number entered is not a valid number");
        			return -2;
        		}
    		} else  {
    			logger.info("Cancel button clicked");
    			return -1;
    		}
    	}
    	return i;
    }


	
	/**
	 * Method called to open a dialog frame and select a file path. 
	 * @return The file to be later parse, as a string.
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
			logger.info("You chose totv open this file: " + 
					fc.getSelectedFile().getAbsolutePath());
			absPath = fc.getSelectedFile().getAbsolutePath();
		}
		return absPath;
	}
	
	/**
	 * Method called to open a dialog frame and select a number.
	 * @return the number entered by user
	 */
	public int displaySelectOrderDialog() {
		logger.info("Entering displaySelectOrderDialog");
		int i = 0;
		
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
		
		return i;
	}
}
