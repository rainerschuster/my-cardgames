package com.rainerschuster.cardgames.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Various utility methods.
 * @author Rainer Schuster
 */
public final class Utils {

	private static final Random RANDOM = new Random();

	/**
	 * In-place shuffling algorithm.
	 * http://en.wikipedia.org/wiki/Fisher-Yates_shuffle
	 * @param items List of items that will be shuffled in-place.
	 */
	public static <T> void fisherYates(final List<T> items) {
		final int n = items.size();
		for (int i = n - 1; i > 0; i--) {
			final int position = RANDOM.nextInt(i + 1);
			final T item = items.get(position);
			items.set(position, items.get(i));
			items.set(i, item);
		}
	}

	/**
	 * Selects a random subset of m items from the specified list.
	 * @param items The list of items to select from.
	 * @param m The number of items to be selected.
	 * @return A random subset of m items from the specified list.
	 */
	public static <T> List<T> floydList(final List<T> items, final int m) {
		final List<T> result = new ArrayList<T>(m);
		final int n = items.size();
		for (int i = n - m; i < n; i++) {
			final int position = RANDOM.nextInt(i + 1);
			final T item = items.get(position);
			if (result.contains(item)) {
				result.add(items.get(i));
			} else {
				result.add(item);
			}
		}
		return result;
	}

}
