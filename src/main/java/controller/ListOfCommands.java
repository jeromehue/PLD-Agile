package controller;

import java.util.LinkedList;

/**
 * Class that keeps a record of all the commands the user has executed, in order
 * to be able to under/redo previous commands.
 * 
 * @author H4414
 */

public class ListOfCommands {

	/**
	 * The linked list of recorded commands.
	 */
	private LinkedList<Command> list;

	/**
	 * The current command index in the linked list.
	 */
	private int currentIndex;

	/**
	 * Constructor to create a new list of commands.
	 */
	public ListOfCommands() {
		currentIndex = -1;
		list = new LinkedList<Command>();
	}

	/**
	 * Add command c to this.
	 * 
	 * @param c the command to add
	 */
	public void add(Command c) {
		int i = currentIndex + 1;
		while (i < list.size()) {
			list.remove(i);
		}
		currentIndex++;
		list.add(currentIndex, c);
		c.doCommand();
	}

	/**
	 * Temporary remove the last added command (this command may be reinserted again
	 * with redo).
	 */
	public void undo() {
		if (currentIndex >= 0) {
			Command cde = list.get(currentIndex);
			currentIndex--;
			cde.undoCommand();
		}
	}

	/**
	 * Permanently remove the last added command (this command cannot be reinserted
	 * again with redo).
	 */
	public void cancel() {
		if (currentIndex >= 0) {
			Command cde = list.get(currentIndex);
			list.remove(currentIndex);
			currentIndex--;
			cde.undoCommand();
		}
	}

	/**
	 * Reinsert the last command removed by undo.
	 */
	public void redo() {
		if (currentIndex < list.size() - 1) {
			currentIndex++;
			Command cde = list.get(currentIndex);
			cde.doCommand();
		}
	}

	/**
	 * Permanently remove all commands from the list.
	 */
	public void reset() {
		currentIndex = -1;
		list.clear();
	}
}
