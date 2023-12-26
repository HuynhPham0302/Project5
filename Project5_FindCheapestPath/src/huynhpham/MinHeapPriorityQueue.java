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
import java.util.Arrays;

public class MinHeapPriorityQueue<T extends Comparable<? super T>> implements PriorityQueueInterface<T>{
	
	private T[] heap;
	private int lastIndex;
	
	private boolean integrityOK = false;
	private static final int DEFAULT_CAPACITY = 100;
	private static final int MAX_CAPACITY = 10000;
	
	
	private void checkCapacity(int capacity) {
		if(capacity > MAX_CAPACITY) {
			throw new IllegalStateException("Attemp to create a heap "
					+ "with a capacity exceeding the maximum allow capacity");
		}
	}
	
	private void checkIntegrity() {
		if(!integrityOK) {
			throw new SecurityException("Heap integrity is compremised");
		}
	}
	
	private void ensureCapacity() {
		if(lastIndex == heap.length - 1) {
			int newCapacity = heap.length * 2;
			
			heap = Arrays.copyOf(heap, newCapacity);
		}
	}
	
	public MinHeapPriorityQueue() {
		this(DEFAULT_CAPACITY);
	}
	
	public MinHeapPriorityQueue(int initialCapacity) {
		if(initialCapacity < DEFAULT_CAPACITY) {
			initialCapacity = DEFAULT_CAPACITY;
		}else {
			checkCapacity(initialCapacity);
		}
		
		@SuppressWarnings("unchecked")
		T[] temp = (T[]) new Comparable[initialCapacity + 1];
		heap = temp;
		lastIndex = 0;
		integrityOK = true;
	}

	@Override
	public void add(T newEntry) {
		checkIntegrity();
		int newIndex = lastIndex + 1;
		int parentIndex = newIndex / 2;
		while((parentIndex > 0) && newEntry.compareTo(heap[parentIndex]) < 0) {
			heap[newIndex] = heap[parentIndex];
			newIndex = parentIndex;
			parentIndex = newIndex / 2;
		}
		heap[newIndex] = newEntry;
		lastIndex++;
		ensureCapacity();
	}

	@Override
	public T remove() {
		checkIntegrity();
		T root = null;
		
		if(!isEmpty()) {
			root = heap[1];
			heap[1] = heap[lastIndex];
			heap[lastIndex] = null;
			lastIndex--;
			reheap(1);
		}
		return root;
	}
	
	private void reheap(int rootIndex) {
		boolean done = false;
		T orphan = heap[rootIndex];
		int leftChildIndex = 2 * rootIndex;
		
		while(!done && leftChildIndex <= lastIndex) {
			int smallerChildIndex = leftChildIndex;
			int rightChildIndex = leftChildIndex + 1;
			
			if(rightChildIndex <= lastIndex && heap[rightChildIndex].compareTo(heap[leftChildIndex]) < 0) {
				smallerChildIndex = rightChildIndex;
			}
			
			if(orphan.compareTo(heap[smallerChildIndex]) > 0) {
				heap[rootIndex] = heap[smallerChildIndex];
				rootIndex = smallerChildIndex;
				leftChildIndex = 2 * rootIndex;
			}else {
				done = true;
			}
		}
		heap[rootIndex] = orphan;
	}

	@Override
	public T peek() {
		checkIntegrity();
		T root = null;
		if(!isEmpty()) {
			root = heap[1];
		}
		
		return root;
	}

	@Override
	public boolean isEmpty() {
		return lastIndex < 1;
	}

	@Override
	public int getSize() {
		return lastIndex;
	}

	@Override
	public void clear() {
		checkIntegrity();
		while(lastIndex > -1) {
			heap[lastIndex] = null;
			lastIndex--;
		}
		
		lastIndex = 0;
	}
}
