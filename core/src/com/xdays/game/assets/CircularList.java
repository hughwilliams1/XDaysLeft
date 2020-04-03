package com.xdays.game.assets;

import java.util.ArrayList;
import java.util.Collection;

/**
 * CircularList<E>.java - A list the loops round to the first element 
 * when {@link CircularList#get(int)} is called with an index that is greater that 
 * the max index of the list and vice versa.
 *
 * @version 1.0 
 * @see ArrayList<E>
 */
public class CircularList<E> extends ArrayList<E> {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Constructor for the circular list
	 */
	public CircularList() {
		super();
	}
	
	/**
	 * Constructor for the circular list with an initial capacity
	 * 
	 * @param initialCapacity
	 */
	public CircularList(int initialCapacity) {
		super(initialCapacity);
	}
	
	/**
	 * Constructor for circular list with a collectgion
	 * 
	 * @param c A collection with items that extend E
	 */
	public CircularList(Collection<? extends E> c) {
		super(c);
	}

	@Override
	public E get(int index) {
		if (isEmpty()) {
			throw new IndexOutOfBoundsException("The list is empty");
		}

		while (index < 0) {
			index = size() + index;
		}

		return super.get(index % size());
	}
	
}
