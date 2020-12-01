package view;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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
		setBorder(BorderFactory.createTitledBorder("Trajet"));
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
				text += "</html>";
				ButtonWay b = new ButtonWay(w, buttonListener, text);
				this.add(b);
				pointsJButtonList.add(b);
			}
		}
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