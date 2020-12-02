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
import javax.swing.BorderFactory;

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
        
        this.setVisible(true);
        
        // To be place after the set visible.
        PointFactory.initPointFactory(graphicalView, null);

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
    
}
        
        
    