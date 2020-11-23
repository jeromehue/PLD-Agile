package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;

public class Window extends JFrame{

	private static final long serialVersionUID = 1L;
	private GraphicalView graphicalView;
	private TextualView textualView;
	private JLabel messageFrame;
    private JToolBar toolBar;

    public Window(){
        super( "Hubert If");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000,1000);
        setLocationRelativeTo(null);
    
        JPanel contentPane = (JPanel)getContentPane();
        contentPane.setLayout( new BorderLayout());

        messageFrame = createMessageFrame();
        contentPane.add(messageFrame,BorderLayout.SOUTH);

        toolBar = createToolBar();
        contentPane.add(toolBar,BorderLayout.NORTH);
        
        textualView = new TextualView();
        contentPane.add(textualView,BorderLayout.WEST);
        
        graphicalView = new GraphicalView();
        contentPane.add(graphicalView,BorderLayout.CENTER);

        setVisible(true);

    }

    JToolBar createToolBar()
    {
        JToolBar toolBar = new JToolBar();
        toolBar.setPreferredSize(new Dimension(100,30));

        JButton loadMapButton = new JButton("Charger une carte");
        toolBar.add(loadMapButton);

        JButton loadRequestsButton = new JButton("Charger des requêtes");
        toolBar.add(loadRequestsButton);

        JButton calcuateTourButton = new JButton("Calculer la tournée");
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
}
        
        
    