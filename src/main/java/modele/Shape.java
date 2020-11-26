package modele;

import observer.Observable;

public abstract class Shape extends Observable {
	
	private boolean isSelected;
	
	public boolean getIsSelected(){
		return isSelected;
	}
	
	public void setIsSelected(boolean b) {
		isSelected = b;
		notifyObservers(this);
	}
	
	public Shape(){
		isSelected = false;
	}
	
	/**
	 * Decide whether point p is contained in this
	 * @param p the point
	 * @return true if p belongs to this, false otherwise
	 */
	public abstract boolean contains(Point p);
	
	/**
	 * Call method display of v for this (Visitor design pattern)
	 * @param v the visitor
	 */
	public abstract void display(Visitor v);
}

	/**
	 * Move this of (deltaX,deltaY)
	 * Precondition : possibleMove(deltaX, deltaY, plan) == true
	 * @param deltaX 
	 * @param deltaY 
	 */
	/*
	public void move(int deltaX, int deltaY){
		notifyObservers();
	}
	
	/**
	 * @param r a rectangle
	 * @return true if this is completely contained in r
	 */
	//public abstract boolean containedIn(Rectangle r);
	
	/**
	 * @param s a shape
	 * @return true the intersection between this and s is empty
	 */
	//public abstract boolean disjoint(Shape s);
	

