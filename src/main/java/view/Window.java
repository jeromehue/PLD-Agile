package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;

import controller.Controller;

public class Window extends JFrame{

	private static final long serialVersionUID = 1L;
	public GraphicalView graphicalView;
	private TextualView textualView;
	private JLabel messageFrame;
    private JToolBar toolBar;
    
    //Listeners 
    private ButtonListener buttonListener;
    
    //Buttons titles
    protected final static String LOAD_MAP = "Charger une carte";
    protected final static String LOAD_REQUEST = "Charger des requêtes";
    protected final static String COMPUTE_TOUR = "Calculer la tournée";
    
    

    public Window(Controller controller){
        super( "Hubert If");
        buttonListener = new ButtonListener(controller);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000,1000);
        setLocationRelativeTo(null);
    
        JPanel contentPane = (JPanel)getContentPane();
        contentPane.setLayout( new BorderLayout());

        messageFrame = createMessageFrame();
        contentPane.add(messageFrame,BorderLayout.SOUTH);

        toolBar = createToolBar(controller);
        contentPane.add(toolBar,BorderLayout.NORTH);
        
        textualView = new TextualView();
        contentPane.add(textualView,BorderLayout.WEST);
        
        graphicalView = new GraphicalView(controller.getCityMap());
        contentPane.add(graphicalView,BorderLayout.CENTER);

        setVisible(true);

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
        toolBar.add(calcuateTourButton);

        return toolBar;
    }

    JLabel createMessageFrame()
    {
        messageFrame = new JLabel();
		messageFrame.setBorder(BorderFactory.createTitledBorder("Messages"));
        messageFrame.setPreferredSize(new Dimension(50,150));
        return messageFrame;
    } 
    
    public String createDialogBoxToGetFilePath() {
		  final JFileChooser fc = new JFileChooser();
		  FileNameExtensionFilter filter = new FileNameExtensionFilter("XML files", "xml");
		  	fc.setFileFilter(filter);
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
        
        
    