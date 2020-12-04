package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import controller.Controller;
import modele.Tour;
import modele.PointFactory;

public class Window extends JFrame{

	private static final long serialVersionUID = 1L;
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
    protected final static String LOAD_MAP 			= "Load a map";
    protected final static String LOAD_REQUEST 		= "Load requests";
    protected final static String COMPUTE_TOUR 		= "Compute tour";
    protected final static String CLICK_STEP		= "Display tour";
    protected final static String MODIFY_TOUR 		= "Change the tour";
    protected final static String MODIFY_ORDER 		= "Change the tour's order";
    protected final static String MODIFY_REQUEST	= "Change a request";
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

        messageFrame = createMessageFrame();
        contentPane.add(messageFrame,BorderLayout.SOUTH);

        toolBar = createToolBar(controller);
        contentPane.add(toolBar,BorderLayout.NORTH);
        
        textualView = new TextualView(tour, this.buttonListener);
        JScrollPane scrollPane = new JScrollPane( textualView ,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contentPane.add(scrollPane,BorderLayout.WEST);
        
        graphicalView = new GraphicalView(tour);
        contentPane.add(graphicalView,BorderLayout.CENTER);

        mouseListener = new MouseListener( controller,  this, graphicalView);
		addMouseMotionListener(mouseListener);
        
		JMenuBar menuBar = createMenuBar();
		this.setJMenuBar(menuBar);
		
        this.setVisible(true);
        
        
        // To be place after the set visible.
        PointFactory.initPointFactory(graphicalView, null);

    }
    
    private JMenuBar createMenuBar() {

        // La barre de menu à proprement parler
        JMenuBar menuBar = new JMenuBar();

        // Définition du menu déroulant "File" et de son contenu
        JMenu mnuFile = new JMenu( "File" );
        mnuFile.setMnemonic( 'F' );

        JMenuItem mnuNewFile = new JMenuItem( "New File" );
        //mnuNewFile.setIcon( new ImageIcon( "icons/new.png" ) );
        mnuNewFile.setMnemonic( 'N' );
        mnuNewFile.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK) );
        //mnuNewFile.addActionListener( this::mnuNewListener );
        mnuFile.add(mnuNewFile);

        mnuFile.addSeparator();

        JMenuItem mnuOpenFile = new JMenuItem( "Open File ..." );
        mnuOpenFile.setIcon( new ImageIcon( "icons/open.png" ) );
        mnuOpenFile.setMnemonic( 'O' );
        mnuOpenFile.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK) );
        mnuFile.add(mnuOpenFile);

        JMenuItem mnuSaveFile = new JMenuItem( "Save File ..." );
        mnuSaveFile.setIcon( new ImageIcon( "icons/save.png" ) );
        mnuSaveFile.setMnemonic( 'S' );
        mnuSaveFile.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK) );
        mnuFile.add(mnuSaveFile);

        JMenuItem mnuSaveFileAs = new JMenuItem( "Save File As ..." );
        mnuSaveFileAs.setIcon( new ImageIcon( "icons/save_as.png" ) );
        mnuSaveFileAs.setMnemonic( 'A' );
        mnuFile.add(mnuSaveFileAs);

        mnuFile.addSeparator();

        JMenuItem mnuExit = new JMenuItem( "Exit" );
        mnuExit.setIcon( new ImageIcon( "icons/exit.png" ) );
        mnuExit.setMnemonic( 'x' );
        mnuExit.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK) );
        mnuFile.add(mnuExit);
        
        menuBar.add(mnuFile);
        
        // Définition du menu déroulant "Edit" et de son contenu
        JMenu mnuEdit = new JMenu( "Edit" );
        mnuEdit.setMnemonic( 'E' );
        
        JMenuItem mnuUndo = new JMenuItem( "Undo" );
        mnuUndo.setIcon( new ImageIcon( "icons/undo.png" ) );
        mnuUndo.setMnemonic( 'U' );
        mnuUndo.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK) );
        mnuEdit.add(mnuUndo);
        
        JMenuItem mnuRedo = new JMenuItem( "Redo" );
        mnuRedo.setIcon( new ImageIcon( "icons/redo.png" ) );
        mnuRedo.setMnemonic( 'R' );
        mnuRedo.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK) );
        mnuEdit.add(mnuRedo);
        
        mnuEdit.addSeparator();
        
        JMenuItem mnuCopy = new JMenuItem( "Copy" );
        mnuCopy.setIcon( new ImageIcon( "icons/copy.png" ) );
        mnuCopy.setMnemonic( 'C' );
        mnuCopy.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK) );
        mnuEdit.add(mnuCopy);
        
        JMenuItem mnuCut = new JMenuItem( "Cut" );
        mnuCut.setIcon( new ImageIcon( "icons/cut.png" ) );
        mnuCut.setMnemonic( 't' );
        mnuCut.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK) );
        mnuEdit.add(mnuCut);
        
        JMenuItem mnuPaste = new JMenuItem( "Paste" );
        mnuPaste.setIcon( new ImageIcon( "icons/paste.png" ) );
        mnuPaste.setMnemonic( 'P' );
        mnuPaste.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK) );
        mnuEdit.add(mnuPaste);

        menuBar.add(mnuEdit);

        // Définition du menu déroulant "Help" et de son contenu
        JMenu mnuHelp = new JMenu( "Help" );
        mnuHelp.setMnemonic( 'H' );
        
        menuBar.add( mnuHelp );
        
        return menuBar;
    }

    
    JToolBar createToolBar(Controller controller)
    {
        JToolBar toolBar = new JToolBar();
        toolBar.setPreferredSize(new Dimension(100,30));

        JButton loadMapButton = new JButton(LOAD_MAP);
        loadMapButton.addActionListener(buttonListener);
        toolBar.add(loadMapButton);

        JButton loadRequestsButton = new JButton(LOAD_REQUEST);
        loadRequestsButton.addActionListener(buttonListener);
        toolBar.add(loadRequestsButton);

        JButton calcuateTourButton = new JButton(COMPUTE_TOUR);
        calcuateTourButton.addActionListener(buttonListener);
        toolBar.add(calcuateTourButton);
        
        JButton modifyTourButton = new JButton(MODIFY_TOUR);
        modifyTourButton.addActionListener(buttonListener);
        toolBar.add(modifyTourButton);
        
        JButton modifyOrderButton = new JButton(MODIFY_ORDER);
        modifyOrderButton.addActionListener(buttonListener);
        modifyOrderButton.setVisible(false);
        optionalsButtons.add(modifyOrderButton);
        toolBar.add(modifyOrderButton);

        JButton addRequestButton = new JButton(ADD_REQUEST);
        addRequestButton.addActionListener(buttonListener);
        addRequestButton.setVisible(false);
        optionalsButtons.add(addRequestButton);
        toolBar.add(addRequestButton);

        JButton removeRequestButton = new JButton(REMOVE_REQUEST);
        removeRequestButton.addActionListener(buttonListener);
        removeRequestButton.setVisible(false);
        optionalsButtons.add(removeRequestButton);
        toolBar.add(removeRequestButton);
        
        JButton modifyRequestButton = new JButton(MODIFY_REQUEST);
        modifyRequestButton.addActionListener(buttonListener);
        modifyRequestButton.setVisible(false);
        optionalsButtons.add(modifyRequestButton);
        toolBar.add(modifyRequestButton);


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
    	int i = -1;
    	while (i == -1) {
    		String str = JOptionPane.showInputDialog(this, "Entrez la nouvelle place de cette étape");
    		if (str == null) {return -1;}
    		try {
    			i = Integer.parseInt(str);
    		} catch(Exception e) {
    			System.out.println("Erreur :  la saisie n'est pas un entier valide ");
    		}
    	}
    	return i;
    }
    
}
        
        
    