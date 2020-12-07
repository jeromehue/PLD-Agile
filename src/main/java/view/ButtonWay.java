package view;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import modele.Way;

public class ButtonWay extends JButton {

	private static final long serialVersionUID = 1L;

	private Way way;
	
	/**
	 * Association of a button and a way to make steps of the textual view clickables
	 * Does the link between graphical and textual view  
	 * @param way the way 
	 * @param buttonListener the button listener 
	 * @param text the label 
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

	public Way getWay() {
		return way;
	}

	public void setWay(Way way) {
		this.way = way;
	}

}
