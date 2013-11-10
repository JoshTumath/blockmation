package cs124.blockmation;

/**
 * Signals that a blockmation file is corrupted.
 * 
 * @author Josh Tumath (jmt14@aber.ac.uk)
 */
public class InvalidBlockmationFileException extends Exception {
	private static final long serialVersionUID = 3724251420870843997L;

	public InvalidBlockmationFileException() {
		super();
	}
}
