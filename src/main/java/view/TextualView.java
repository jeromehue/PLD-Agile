package view;

import java.awt.Dimension;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import modele.Segment;
import modele.Shape;
import modele.Tour;
import modele.Visitor;
import modele.Way;
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
			/*Iterator<Way> itSegmentTour = tour.getSegmentsIterator();
			text = "<html><ul>Trajet: <br />";
			while (itSegmentTour.hasNext())
				itSegmentTour.next().display(this);
			text += "</ul></html>";
			setText(text);*/
		}
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