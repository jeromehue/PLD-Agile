package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modele.Segment;
import modele.Tour;
import modele.Way;
import observer.Observable;
import observer.Observer;

/**
 * The textual view of the window, that indicates the steps when a tour
 * is computed.
 * 
 * @author H4414
 * 
 * */

public class TextualView extends JPanel implements Observer {

	private static final Logger logger = LoggerFactory.getLogger(TextualView.class);
	
	private static final long serialVersionUID = 1L;
	
	private ButtonListener buttonListener;
	private Tour tour;
	private ArrayList<JButton> pointsJButtonList;
	private JScrollPane scrollPane;
	private JLabel tourEditionLabel;
	private int width;
	private int height;
	
	/**
	 * Create a textual view of the computed tour in window
	 * @param buttonListener the buttonListener
	 * @param tour the computed tour from controller 
	 */
	public TextualView(Tour tour, ButtonListener buttonListener) {
		super();
		setBorder(BorderFactory.createTitledBorder("Itinerary"));
		this.width = 600;
		this.height = 500;
		this.setPreferredSize(new Dimension(this.width, this.height));
		setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBackground(Window.BACKGROUND_COLOR);
		this.tour = tour;
		this.scrollPane = new JScrollPane(this
				,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
				,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.tour.addObserver(this);
		this.scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		this.pointsJButtonList = new ArrayList<JButton>();
		this.buttonListener = buttonListener;
		this.tourEditionLabel = createTourEditionLabel();
		this.add(this.tourEditionLabel);
	}
	
	public void setTourEditionLabelVisibility(boolean visible) {
		this.tourEditionLabel.setVisible(visible);
	}
	

	/**
	 * Method called by tour observed by this each time it is modified
	 * @param observed an updated object which is observed by textual view
	 */
	@Override
	public void update(Observable observed) {
		this.clearPointJButtonList();
		this.displaySteps();
		this.setPreferredSize(new Dimension(600, this.pointsJButtonList.size()*100));
		logger.info(observed.getClass() + " object was modified: textual view updated");
	}
	
	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}
	
	/**
	 * Method called to initialize the button edition mode label 
	 */
	public JLabel createTourEditionLabel() {
		JLabel label = new JLabel("[ Edition mode ]");
		int labelHeight = 15;
		int labelWidth = this.width - 100;
		label.setVisible(false);
		label.setForeground(Color.red);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(labelWidth,labelHeight));
		return label;
	}

	/**
	 * Delete button components of the textual view.
	 */
	private void clearPointJButtonList() {
		for (JButton button : this.pointsJButtonList) {
			button.setVisible(false);
			this.remove(button);
		}
		this.pointsJButtonList.clear();
	}
	
	/**
	 * Reset graphical selection effect around text areas   
	 */
	public void clearAllTextArea() {
		for (JButton b : pointsJButtonList) {
			b.setContentAreaFilled(false);
		}
	}
	
	/**
	 * Method called to display steps of the tour on the graphical view
	 */
	private void displaySteps() {
		if (this.tour != null) {
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
}