package view;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import modele.Segment;
import modele.Shape;
import modele.Tour;
import modele.Visitor;
import observer.Observable;
import observer.Observer;

public class TextualView extends JLabel implements Observer, Visitor{

	private static final long serialVersionUID = 1L;
	private String text;
	private Tour tour;

	public TextualView(Tour tour){
		super();
		setBorder(BorderFactory.createTitledBorder("Vue textuelle"));
		this.setVerticalTextPosition(TOP);
		this.setVerticalAlignment(TOP);
        this.setPreferredSize(new Dimension(500,30));
        this.tour = tour;
        this.tour.addObserver(this);
	}

	public Tour getTour(Tour tour) {
		return this.tour;
	}

	@Override
	public void update(Observable observed, Object arg) {
		if (arg != null){ // arg is a shape that has been added to the tour
			Shape f = (Shape)arg;
			f.addObserver(this);
		}
		
		if(this.tour != null)
		{
			Iterator<Way> itwaysInTour = tour.getwaysListIterrator();
			text = "<html><ul>Trajet: <br />";
			Way w;
			int count = 0;
			while (itwaysInTour.hasNext()) {
				w = itwaysInTour.next();
				count++;
				text += count + ": " + w.getSegmentList().get(0).getName() + "<br />"; 
				text += "Arrivée: " + w.getArrivalTime();
				text += " ; Départ: " + w.getDepartureTime() + "<br />";
				text += "<br />";
			}
			text += "</ul></html>";
			setText(text);
		}
	}
	
	public void display(Way w) {
		
	}
	
	@Override
	public void display(Segment s) {
		text += s.getName() + " sur  " + (int)s.getLength() +" mètres <br />";
		if (s.getIsSelected())
		{
			text += "est selectionné";
		}
	}
}