package observer;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class manages updating of graphical and textual views when this observable is modified. 
 * An observable object can have several observers.
 * 
 * @author H4414
 *
 */
public class Observable {
	/**
	 * The collection of objects observing this class.
	 * These observers can be notified by this.
	 */
	private Collection<Observer> obs;

	/**
	 * Default constructor.
	 */
	public Observable() {
		obs = new ArrayList<Observer>();
	}

	/**
	 * Add an observer on this object.
	 * 
	 * @param o The observer to be added. 
	 */
	public void addObserver(Observer o) {
		if (!obs.contains(o))
			obs.add(o);
	}
	
	/**
	 * Called to inform observers that the observed object has been modified.
	 */
	public void notifyObservers() {
		for (Observer o : obs)
		o.update(this);
	}
}
