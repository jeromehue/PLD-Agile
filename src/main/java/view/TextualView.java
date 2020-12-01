package view;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.Segment;
import modele.Shape;
import modele.Tour;
import modele.Visitor;
import modele.Way;
import observer.Observable;
import observer.Observer;

public class TextualView extends JPanel implements Observer, Visitor{

	private static final long serialVersionUID = 1L;
	
	private ButtonListener buttonListener; 
	private Tour tour;
	private ArrayList<JButton> pointsJButtonList;

	public TextualView(Tour tour, ButtonListener buttonListener){
		super();
		setBorder(BorderFactory.createTitledBorder("Vue textuelle"));
        this.setPreferredSize(new Dimension(500,30));
        this.setBackground(Window.BACKGROUND_COLOR);
        this.tour = tour;
        this.tour.addObserver(this);
        this.pointsJButtonList = new ArrayList<JButton>();
        this.buttonListener = buttonListener;
        
	}
	
	@Override
	public void update(Observable observed, Object arg) {
		if (arg != null){ // arg is a shape that has been added to the tour
			Shape f = (Shape)arg;
			f.addObserver(this);
		}
		
		if(this.tour != null)
		{
			clearPointJButtonList();
			Iterator<Way> itwaysInTour = tour.getwaysListIterrator();
			Way w;
			int count = 0;
			while (itwaysInTour.hasNext()) {
				w = itwaysInTour.next();
				count++;
				String text = "<html>";
				text += count + ": " + w.getSegmentList().get(0).getName() + "<br />"; 
				int hour=w.getArrivalTime().getHour();
				int minute=w.getArrivalTime().getMinute();
				int second=w.getArrivalTime().getSecond();
				text += "Arrivée: "+hour+":"+minute+":"+second;
				int time = w.getStayingDurationDeparture();
				text += " ; Temps passé sur place: " +time + " secondes<br />";
				//text += "<br />";
				text += "</html>";
				createClickabletextArea(text);
			}
		}
	}
	
	private void createClickabletextArea(String text) { 
		JButton clickableTextArea = new JButton();
		pointsJButtonList.add(clickableTextArea);
		this.add(clickableTextArea);
		clickableTextArea.setVerticalAlignment(JLabel.TOP);
		clickableTextArea.setText(text);
		clickableTextArea.setContentAreaFilled(false);
		clickableTextArea.setActionCommand(Window.DISPLAY_WAY);
		clickableTextArea.addActionListener(buttonListener);
	}
	
	private void clearPointJButtonList() {
		for(JButton button : this.pointsJButtonList) {
			button.setVisible(false);
			this.remove(button);
		}
		this.pointsJButtonList.clear();
	}
	
	public void display(Way w) {
		
	}
	
	@Override
	public void display(Segment s) {
		/*text += s.getName() + " sur  " + (int)s.getLength() +" mètres <br />";
		if (s.getIsSelected())
		{
			text += "est selectionné";
		}*/
	}
}