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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
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

public class Window extends JFrame{

	private static final long serialVersionUID = 1L;
	
	//Components
	public GraphicalView graphicalView;
	public TextualView textualView;
	private JLabel messageFrame;
    private JToolBar toolBar;
    
    
    private ArrayList<JButton> optionalsButtons;
    private boolean optionalsButtonsVisible;
    
    //Listeners 
    private ButtonListener buttonListener;
    private MouseListener mouseListener;
    
    protected final static Color BACKGROUND_COLOR = new Color(224,224,224);
    
    //Buttons titles
    protected final static String LOAD_MAP 			= "Load XML Map...";
    protected final static String LOAD_REQUEST 		= "Load XML Requests...";
    protected final static String COMPUTE_TOUR 		= "Compute tour";
    protected final static String CLICK_STEP		= "Display tour";
    protected final static String MODIFY_TOUR 		= "Set tour edition mode";
    protected final static String MODIFY_ORDER 		= "Modify tour order";
    protected final static String MODIFY_REQUEST	= "Modify a request";
    protected final static String ADD_REQUEST 		= "Add a request";
    protected final static String REMOVE_REQUEST	= "Delete a request";
    
    public Window(Controller controller, Tour tour){
        super("Deliver'IF");
        this.buttonListener = new ButtonListener(controller);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(1800,1020);
        this.setLocationRelativeTo(null);
        this.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        
        this.optionalsButtonsVisible = false;
        optionalsButtons = new ArrayList<JButton>();
        try { UIManager.setLookAndFeel(new NimbusLookAndFeel()); }
        catch(Exception e){}
    
        JPanel contentPane = (JPanel)getContentPane();
        contentPane.setLayout( new BorderLayout());

        this.messageFrame = createMessageFrame();
        contentPane.add(messageFrame,BorderLayout.SOUTH);

        this.toolBar = createToolBar(controller);
        contentPane.add(toolBar,BorderLayout.NORTH);
        this.toolBar.setVisible(this.optionalsButtonsVisible);
        
        this.textualView = new TextualView(tour, this.buttonListener);
        JScrollPane scrollPane = new JScrollPane( textualView ,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contentPane.add(scrollPane,BorderLayout.WEST);
        
        this.graphicalView = new GraphicalView(tour);
        contentPane.add(graphicalView,BorderLayout.CENTER);

        this.mouseListener = new MouseListener(this, graphicalView);
		addMouseMotionListener(mouseListener);
        
		JMenuBar menuBar = createMenuBar();
		this.setJMenuBar(menuBar);
		
        this.setVisible(true);
        
        
        // To be place after the set visible.
        PointFactory.initPointFactory(graphicalView, null);

    }
    
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        // Load
        JMenu mnuLoad = new JMenu( " Load... " );
        mnuLoad.setMnemonic( 'L' );
        mnuLoad.setForeground(Color.black);
        
        JMenuItem mnuLoadMap = new JMenuItem( LOAD_MAP );
        mnuLoadMap.setSize(new Dimension(50, 50));
        mnuLoadMap.setMnemonic( 'L' );
        mnuLoadMap.addActionListener(this.buttonListener);
        mnuLoad.add(mnuLoadMap);
        mnuLoad.addSeparator();

        JMenuItem mnuLoadRequest = new JMenuItem( LOAD_REQUEST );
        mnuLoadRequest.setMnemonic( 'L' );
        mnuLoadRequest.addActionListener(this.buttonListener);
        mnuLoad.add(mnuLoadRequest);
        
        menuBar.add(mnuLoad);
        
        // Compute
        JMenu mnuCompute = new JMenu( " Compute " );
        mnuCompute.setMnemonic( 'C' );
        
        JMenuItem mnuComputeTour = new JMenuItem( COMPUTE_TOUR );
        mnuComputeTour.setMnemonic( 'C' );
        mnuComputeTour.addActionListener(this.buttonListener);
        mnuCompute.add(mnuComputeTour);
        
        menuBar.add( mnuCompute );
        
        // Edit 
        JMenu mnuEdit = new JMenu( " Edit " );
        mnuEdit.setMnemonic( 'E' );
        
        JMenuItem modifyTour = new JMenuItem( MODIFY_TOUR );
        modifyTour.setMnemonic( 'S' );
        modifyTour.addActionListener(this.buttonListener);
        
        mnuEdit.add(modifyTour);
        menuBar.add( mnuEdit );
        return menuBar;
    }

    
    JToolBar createToolBar(Controller controller)
    {
        JToolBar toolBar = new JToolBar();
        toolBar.setPreferredSize(new Dimension(100,30));
        
        JButton addRequestButton = new JButton(ADD_REQUEST);
        addRequestButton.addActionListener(buttonListener);
        addRequestButton.setForeground(Color.darkGray);
        addRequestButton.setVisible(false);
        optionalsButtons.add(addRequestButton);
        toolBar.add(addRequestButton);
        
        JButton modifyRequestButton = new JButton(MODIFY_REQUEST);
        modifyRequestButton.addActionListener(buttonListener);
        modifyRequestButton.setForeground(Color.darkGray);
        modifyRequestButton.setVisible(false);
        optionalsButtons.add(modifyRequestButton);
        toolBar.add(modifyRequestButton);
        
        JButton modifyOrderButton = new JButton(MODIFY_ORDER);
        modifyOrderButton.addActionListener(buttonListener);
        modifyOrderButton.setVisible(false);
        modifyOrderButton.setForeground(Color.darkGray);
        optionalsButtons.add(modifyOrderButton);
        toolBar.add(modifyOrderButton);
       
        JButton removeRequestButton = new JButton(REMOVE_REQUEST);
        removeRequestButton.addActionListener(buttonListener);
        removeRequestButton.setForeground(Color.darkGray);
        removeRequestButton.setVisible(false);
        optionalsButtons.add(removeRequestButton);
        toolBar.add(removeRequestButton);
        
        return toolBar;
    }

    JLabel createMessageFrame()
    {
        messageFrame = new JLabel();
		messageFrame.setPreferredSize(new Dimension(50,150));
		messageFrame.setBorder(BorderFactory.createTitledBorder("Messages"));
        messageFrame.setHorizontalAlignment(SwingConstants.CENTER);
        messageFrame.setVerticalAlignment(SwingConstants.TOP);
        messageFrame.setText("To begin, please load a map (XML file).");
        messageFrame.setBackground(BACKGROUND_COLOR);
        return messageFrame;
    } 
    
    public void setMessage (String message) {
    	messageFrame.setText(message);
    }
    
    public boolean isOptionalsButtonsVisible() {
		return optionalsButtonsVisible;
	}

	public void changeOptionalsButtonsVisibility() {
    	this.optionalsButtonsVisible = !this.optionalsButtonsVisible;
    	for(JButton b : optionalsButtons)
    		b.setVisible(optionalsButtonsVisible);
    	this.toolBar.setVisible(optionalsButtonsVisible);
    }
    
    
    public String createDialogBoxToGetFilePath() {
		  final JFileChooser fc = new JFileChooser();
		  FileNameExtensionFilter filter = new FileNameExtensionFilter("XML files (*.xml)", "xml");
		  	fc.setFileFilter(filter);
		  	fc.setAcceptAllFileFilterUsed(false);
		    fc.setCurrentDirectory(new File("./src/main"));
		    int returnVal = fc.showOpenDialog(null);
		    String absPath = null;
		    if(returnVal == JFileChooser.APPROVE_OPTION) 
		    {
		      System.out.println("You chose to open this file: " + fc.getSelectedFile().getAbsolutePath()) ;
		      absPath = fc.getSelectedFile().getAbsolutePath();
		    } 
		    return absPath;
	}
    
    
    public int displaySelectOrderDialog() {
    	System.out.println("Entering displaySelectOrderDialog");
    	int i = 0;
    	while (i == 0) {
    		String str = JOptionPane.showInputDialog(this, "Entrez la nouvelle place de cette étape");
    		if (str == null) {return -1;}
    		try {
    			i = Integer.parseInt(str);
    		} catch(Exception e) {
    			System.out.println("Window:displaySelectOrder Erreur :  la saisie n'est pas un entier valide, ou est égale à 0");
    		}
    	}
    	return i;
    }
    
}
        
        
    