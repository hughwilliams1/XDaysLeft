package com.xdays.game.assets;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A list the loops round to the first element when
 * {@link CircularList#get(int)} is called with an index that is greater than
 * the max index of the list and vice versa.
 */

public class CircularList<E> extends ArrayList<E> {

	private static final long serialVersionUID = 1L;

	public CircularList() {
		super();
	}

	public CircularList(int initialCapacity) {
		super(initialCapacity);
	}

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