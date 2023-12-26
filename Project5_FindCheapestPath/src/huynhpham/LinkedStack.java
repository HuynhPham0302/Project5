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

public class LinkedStack<T> implements StackInterface<T> {
	private Node topNode;
	private int size;
	
	private class Node{
		private T data;
		private Node next;
		
		private Node(T dataPortion) {
			this(dataPortion, null);
		}
		private Node(T dataPortion, Node nextNode) {
			this.data = dataPortion;
			this.next = nextNode;
		}
	}
	
	public LinkedStack() {
		topNode = null;
		size = 0;
	}
	
	@Override
	public void push(T newEntry) {
		Node newNode = new Node(newEntry);
		newNode.next = topNode;
		topNode = newNode;
		size++;
	}
	
	@Override
	public boolean isEmpty() {
		return topNode == null;
	}
	@Override
	public T pop() {
		if(isEmpty()) {
			throw new EmptyStackException();
		}
		T data = topNode.data;
		topNode = topNode.next;
		size--;
		return data;
	}
	
	@Override
	public T peek() {
		if(isEmpty()) {
			throw new EmptyStackException();
		}else {
			return topNode.data;
		}
	}
	
	@Override
	public void clear() {
		topNode = null;
		size = 0;
	}
}

