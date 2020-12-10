package observer;
/**
 * This class is an interface to force Graphical and textual 
 * views to update with observer / observable design pattern.
 * 
 * @author H4414
 *
 */
public interface Observer {
	/**
	 * Method called each time an object observed by this notifies his observers.
	 * 
	 * @param observed The observed object notifying this.
	 */
	public void update(Observable observed);
}
