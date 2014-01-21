package com.oyou.spring2.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import com.oyou.spring2.repository.Name;

public class CollectionsUtil {

	// Set not like List allow duplicated object
	public void findDups(String[] strs) {
		Set<String> s = new HashSet<String>();
		for (String a : strs)
			if (!s.add(a))
				System.out.println("Duplicate detected: " + a);

		System.out.println(s.size() + " distinct words: " + s);
	}

	// List allow null object and set may not allowed
	public void findDups2(String[] strs) {
		Set<String> uniques = new HashSet<String>();
		Set<String> dups = new HashSet<String>();

		for (String a : strs)
			if (!uniques.add(a))
				dups.add(a);

		// Destructive set-difference
		uniques.removeAll(dups);

		System.out.println("Unique words:    " + uniques);
		System.out.println("Duplicate words: " + dups);
	}

	public void shuffle(String[] strs) {
		List<String> list = Arrays.asList(strs);
		Collections.shuffle(list);
		System.out.println(list);

		list = new ArrayList<String>();
		for (String a : strs)
			list.add(a);
		Collections.shuffle(list, new Random());
		System.out.println(list);

	}

	public static <E> void replace(List<E> list, E val, E newVal) {
		for (ListIterator<E> it = list.listIterator(); it.hasNext();)
			if (val == null ? it.next() == null : val.equals(it.next()))
				it.set(newVal);
	}

	// queue
	public void countdown(String num) throws InterruptedException {
		int time = Integer.parseInt(num);
		Queue<Integer> queue = new LinkedList<Integer>();

		for (int i = time; i >= 0; i--)
			queue.add(i);

		while (!queue.isEmpty()) {
			System.out.println(queue.remove());
			Thread.sleep(1000);
		}
	}

	public void anagrams(String file, String size) {
		int minGroupSize = Integer.parseInt(size);

		// Read words from file and put into a simulated multimap
		Map<String, List<String>> m = new HashMap<String, List<String>>();

		try {
			//Default seperated by whitespaces
			Scanner s = new Scanner(new File(file));
			while (s.hasNext()) {
				String word = s.next();
				String alpha = alphabetize(word);
				List<String> l = m.get(alpha);
				if (l == null)
					m.put(alpha, l = new ArrayList<String>());
				l.add(word);
			}
		} catch (IOException e) {
			System.err.println(e);
			System.exit(1);
		}

		// Print all permutation groups above size threshold
		for (List<String> l : m.values())
			if (l.size() >= minGroupSize)
				System.out.println(l.size() + ": " + l);
	}

	private static String alphabetize(String s) {
		char[] a = s.toCharArray();
		Arrays.sort(a);
		return new String(a);
	}

	public void nameSort() {
		Name nameArray[] = { new Name("John", "Smith"), new Name("Karl", "Ng"), new Name("Jeff", "Smith"), new Name("Tom", "Rich") };
		List<Name> names = Arrays.asList(nameArray);
		Collections.sort(names);
		System.out.println(names);
	}

}
