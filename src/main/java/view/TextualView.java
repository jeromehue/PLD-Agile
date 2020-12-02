package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
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
        this.setPreferredSize(new Dimension(600,2000));
        setLayout(new FlowLayout(FlowLayout.LEFT));
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
			Way currentWay;
			int currentCount = 0;
			Segment currentArrival;
			while (itwaysInTour.hasNext()) {
				currentWay = itwaysInTour.next();
				currentArrival = currentWay.getSegmentList().get(currentWay.getSegmentList().size()-1);
				++currentCount;
				String text = "<html><u><strong> Etape n°" + currentCount + ":</strong></u> <br />";
				text += "(" + currentWay.getStayingDurationDeparture() + " secondes sur place)";
				text += ". <br />";
				text += "Départ par "; 
				text += "<strong>" + currentWay.getSegmentList().get(0).getName() + "</strong>";
				text += " à "; 
				text += currentWay.getDepartureTime().getHour() + "h"+ currentWay.getDepartureTime().getMinute();
				text += " de l'adresse ";
				text += currentWay.getSegmentList().get(0).getOrigin().getId() +  ".";
				text += ". <br />";
				text += "Arrivée à l'adresse  "; 
				text += currentArrival.getDestination().getId();
				text += " à " + currentWay.getArrivalTime().getHour() + "h"+ currentWay.getArrivalTime().getMinute();
				text += " par <strong>" + currentArrival.getName() + "</strong>.<br />";
				text += "</html>";
				ButtonWay b = new ButtonWay(currentWay, buttonListener, text);
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