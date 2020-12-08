package observer;
/**
 * This class is an interface to force Graphical and textual 
 * views to update with observer / observable design pattern
 * @author H4414
 *
 */
public interface Observer {
	/**
	 * Method called each time an observed object notifies his observers
	 * @param observed
	 */
	public void update(Observable observed);
}
