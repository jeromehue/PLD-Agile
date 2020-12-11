package xml;

/**
 * This class allows the handling of invalid XML files that should represent a
 * map.
 * 
 * @author H4414
 * 
 */
public class InvalidMapException extends Exception {

	/**
	 * Recommended parameter to avoid unexpected InvalidClassExceptions.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor for this class.
	 * 
	 * @param errorMessage The warning to be displayed to the user.
	 */
	public InvalidMapException(String errorMessage) {
		super(errorMessage);
	}

}
