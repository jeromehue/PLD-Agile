package view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import modele.Segment;

public class GraphicalView extends JPanel{

	private static final long serialVersionUID = 1L;
	private int scale;
	private int viewHeight;
	private int viewWidth;

	public GraphicalView() {
		super();
		this.setBorder(BorderFactory.createTitledBorder("Vue Graphique"));
		this.scale = 200;
		this.viewHeight = 20;
		this.viewWidth = 20;
		setLayout(null);
		setBackground(Color.white);
		setSize(viewWidth, viewHeight);
	}
	
	public int getScale(){
		return scale;
	}

	public void setScale(int e) {
		viewWidth = (viewWidth/scale)*e;
		viewHeight = (viewHeight/scale)*e;
		setSize(viewWidth, viewHeight);
		scale = e;
	}

	public int getViewHeight() {
		return viewHeight;
	}

	public int getViewWidth() {
		return viewWidth;
	}

	
	/**
	 * Method called by a visited segment each time it receives the message "display" 
	 */
	public void display(Segment s) {
		
	}

	/*
	@Override
	public void display(Circle c) {
		int r = scale*c.getRadius();
		if (c.getIsSelected())
			g.drawOval(scale*c.getCenter().getX()-r, scale*c.getCenter().getY()-r, 2*r, 2*r);
		else
			g.fillOval(scale*c.getCenter().getX()-r, scale*c.getCenter().getY()-r, 2*r, 2*r);
	}


	@Override
	public void display(Rectangle r) {
		if (r.getIsSelected())
			g.drawRect(scale*r.getCorner().getX(),scale*r.getCorner().getY(),scale*(r.getWidth()),scale*(r.getHeight()));
		else
			g.fillRect(scale*r.getCorner().getX(),scale*r.getCorner().getY(),scale*(r.getWidth()),scale*(r.getHeight()));
	}*/

}
