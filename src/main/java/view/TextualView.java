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

public class TextualView extends JPanel implements Observer, Visitor {

	private static final long serialVersionUID = 1L;

	private ButtonListener buttonListener;
	private Tour tour;
	private ArrayList<JButton> pointsJButtonList;

	public TextualView(Tour tour, ButtonListener buttonListener) {
		super();
		setBorder(BorderFactory.createTitledBorder("Itinerary"));
		this.setPreferredSize(new Dimension(600, 2000));
		setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBackground(Window.BACKGROUND_COLOR);
		this.tour = tour;
		this.tour.addObserver(this);
		this.pointsJButtonList = new ArrayList<>();
		this.buttonListener = buttonListener;
	}

	@Override
	public void update(Observable observed, Object arg) {
		if (arg != null) { // arg is a shape that has been added to the tour
			Shape f = (Shape) arg;
			f.addObserver(this);
		}

		if (this.tour != null) {
			clearPointJButtonList();
			Iterator<Way> itwaysInTour = tour.getWaysListIterator();
			Way currentWay = null;
			int currentCount = 0;
			Segment currentArrival;
			String text;
			String alert = "";

			// start point
			if (itwaysInTour.hasNext()) {
				currentWay = itwaysInTour.next();
				currentArrival = currentWay.getSegmentList().get(currentWay.getSegmentList().size() - 1);
				text = "<html><u><strong> Start: </strong></u> <br />";
				text += "Leave the starting point (address n°" + currentWay.getSegmentList().get(0).getOrigin().getId();
				text += ") at " + currentWay.getDepartureTime().getHour() + ":";
				if (currentWay.getDepartureTime().getMinute() < 10) {
					text += "0";
				}
				text += currentWay.getDepartureTime().getMinute();
				text += " following " + "<strong>" + currentWay.getSegmentList().get(0).getName() + "</strong>";
				text += ". <br /></html>";
				ButtonWay b = new ButtonWay(currentWay, buttonListener, text);
				this.add(b);
				pointsJButtonList.add(b);
			}

			while (itwaysInTour.hasNext() && currentWay != null) {
				currentArrival = currentWay.getSegmentList().get(currentWay.getSegmentList().size() - 1);
				alert = "";

				++currentCount;
				text = "<html><u><strong> Step n°" + currentCount + ":</strong></u> <br />";
				if (!tour.isPositionConsistent(currentArrival.getDestination().getId())) {
					alert = "Warning : this delivery point is placed before its pick-up point !";
				}
				text += "<p style='color:red'>" + alert + "</p>";
				if (tour.getRequest().isPickUp(currentArrival.getDestination().getId())) {
					text += "Arrival to pick-up point n°" + currentArrival.getDestination().getId();
				} else {
					text += "Arrival to delivery point n°" + currentArrival.getDestination().getId();
				}

				text += " at " + currentWay.getArrivalTime().getHour() + ":";
				if (currentWay.getArrivalTime().getMinute() < 10) {
					text += "0";
				}
				text += currentWay.getArrivalTime().getMinute();
				text += " from <strong>" + currentArrival.getName() + "</strong>. <br />";

				currentWay = itwaysInTour.next();

				text += "Time spent on the spot : " + currentWay.getStayingDurationForDeparturePoint()
						+ " minute(s). <br />";
				text += "Leave at " + currentWay.getDepartureTime().getHour() + ":";
				if (currentWay.getDepartureTime().getMinute() < 10) {
					text += "0";
				}
				text += currentWay.getDepartureTime().getMinute();
				text += " following <strong>" + currentWay.getSegmentList().get(0).getName() + "</strong>";
				text += ". <br /> </html>";
				ButtonWay newStepButton = new ButtonWay(currentWay, buttonListener, text);

				this.add(newStepButton);
				pointsJButtonList.add(newStepButton);
			}
			// Displaying last point
			if (currentWay != null) {
				currentArrival = currentWay.getSegmentList().get(currentWay.getSegmentList().size() - 1);
				text = "<html><u><strong> End : </strong></u> <br />";
				text += "Return to the starting point (address n°" + currentWay.getSegmentList()
						.get(currentWay.getSegmentList().size() - 1).getDestination().getId();
				text += ") at " + currentWay.getArrivalTime().getHour() + ":" + currentWay.getArrivalTime().getMinute();
				text += " from <strong>" + currentArrival.getName() + "</strong>";
				text += ". <br /></html>";
				ButtonWay b = new ButtonWay(currentWay, buttonListener, text);
				this.add(b);
				pointsJButtonList.add(b);
			}
		}
	}

	private void clearPointJButtonList() {
		for (JButton button : this.pointsJButtonList) {
			button.setVisible(false);
			this.remove(button);
		}
		this.pointsJButtonList.clear();
	}

	public void clearAllTextArea() {
		for (JButton b : pointsJButtonList) {
			b.setContentAreaFilled(false);
		}
	}

	@Override
	public void display(Segment s) {
		/*
		 * text += s.getName() + " sur  " + (int)s.getLength() +" mètres <br />"; if
		 * (s.getIsSelected()) { text += "est selectionné"; }
		 */
	}
}