package huynhpham;

//
//	Name: Pham, Huynh
//	Project: 5
//	Due: 12/08/2023
//	Course: cs-2400-02-f23
//
//	Description:
//		The project uses a Graph to store airport and distance information. 
//		Use Dijkstra's algorithm to find the shortest distances between airports
//

import java.util.EmptyStackException;

/**
 * The StackInterface represent a generic stack data structure.
 * 
 * @param <T> The type of elements stored in the stack
 */
public interface StackInterface<T> {

	/**
	 * Pushes a new entry into the top of the stack
	 * 
	 * @param newEntry The entry to be pushed into the stack.
	 */
	public void push(T newEntry);

	/**
	 * Removes and returns the entry at the top of the stack
	 * 
	 * @return The entry removed from the top of the stack
	 * @throws EmptyStackException if the stack is empty.
	 */
	public T pop() throws EmptyStackException;

	/**
	 * Retrieves, but does not remove the entry at the top of the stack
	 * 
	 * @return The entry at the top of the stack
	 * @throws EmptyStackException if the stack is empty.
	 */
	public T peek() throws EmptyStackException;

	/**
	 * Check if the stack is empty
	 * 
	 * @return true if the stack is empty, false if not
	 */
	public boolean isEmpty();

	/**
	 * Remove all entries from the stack, make it empty
	 */
	public void clear();
}
