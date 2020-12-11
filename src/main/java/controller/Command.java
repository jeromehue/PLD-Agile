package controller;

/**
 * An interface that describes the minimum methods a command should implement.
 * @author H4414
 */

public interface Command {

	/**
	 * Execute the command this
	 */
	void doCommand();

	/**
	 * Execute the reverse command of this
	 */
	void undoCommand();
}
