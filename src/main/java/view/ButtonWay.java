package view;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import modele.Way;

/**
 * A custom button with a 'way' attribute, to allow the highlighting 
 * of a way when a click on a step is performed.
 * 
 * @author H4414
 * @see modele.Way
 * */
public class ButtonWay extends JButton {

	/**
	 * Recommended parameter to avoid unexpected InvalidClassExceptions.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The way of the step associated to this button.
	 */
	private Way way;
	
	/**
	 * Associating a button and a way to make steps of the textual view clickable.
	 * Creates the link between the graphical and the textual view. 
	 * 
	 * @param way The way of the step associated to this. 
	 * @param buttonListener The button listener of the application. 
	 * @param text The label to be displayed. 
	 */
	public ButtonWay(Way way, ActionListener buttonListener, String text) {
		super();
		this.way = way;
		this.setVerticalAlignment(JLabel.TOP);
		this.setText(text);
		this.setContentAreaFilled(false);
		this.setActionCommand(Window.CLICK_STEP);
		this.addActionListener(buttonListener);
	}

	/**
	 * Default getter.
	 * 
	 * @return The way of the step associated to this button.

	 */
	public Way getWay() {
		return way;
	}

	/**
	 * Default setter.
	 * 
	 * @param way The way to be updated.

	 */
	public void setWay(Way way) {
		this.way = way;
	}

}
