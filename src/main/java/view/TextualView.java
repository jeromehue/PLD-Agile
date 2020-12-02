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
			Way currentWay = null;
			int currentCount = 0;
			Segment currentArrival;
			String text;
			
			//start point
			if(itwaysInTour.hasNext()) {
				currentWay = itwaysInTour.next();
				currentArrival = currentWay.getSegmentList().get(currentWay.getSegmentList().size()-1);
				text = "<html><u><strong> Start: </strong></u> <br />";
				text += "Leave the starting point (address n°"+currentWay.getSegmentList().get(0).getOrigin().getId();
				text += ") at "+ currentWay.getDepartureTime().getHour() + ":"+ currentWay.getDepartureTime().getMinute();
				text += " following "+ "<strong>" + currentWay.getSegmentList().get(0).getName() + "</strong>";
				text += ". <br /></html>";
				ButtonWay b = new ButtonWay(currentWay, buttonListener, text);
				this.add(b);
				pointsJButtonList.add(b);
			}
			
			while (itwaysInTour.hasNext() && currentWay!= null) {
				currentArrival = currentWay.getSegmentList().get(currentWay.getSegmentList().size()-1);
				
				++currentCount;
				text = "<html><u><strong> Step n°" + currentCount + ":</strong></u> <br />";
				if (tour.getRequest().isPickUp (currentArrival.getDestination().getId()))
				{
					text += "Arrival to pick-up point n°"+ currentArrival.getDestination().getId();
				}
				else
				{
					text += "Arrival to delivery point n°"+ currentArrival.getDestination().getId();
				}
				
				
				text += " at " + currentWay.getArrivalTime().getHour() + ":"+ currentWay.getArrivalTime().getMinute();
				text += " from <strong>"+ currentArrival.getName() + "</strong>. <br />";
				
				currentWay = itwaysInTour.next();
				
				text += "Time spent on the spot : "+ currentWay.getStayingDurationDeparture() + " seconds. <br />";
				text += "Leave at " + currentWay.getDepartureTime().getHour() + ":" + currentWay.getDepartureTime().getMinute();
				text += " following <strong>" + currentWay.getSegmentList().get(0).getName() + "</strong>";
				text += ". <br /> </html>";
				ButtonWay newStepButton = new ButtonWay(currentWay, buttonListener, text);
				
				this.add(newStepButton);
				pointsJButtonList.add(newStepButton);
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