package test.cso.example;

import java.util.Scanner;
import java.util.TreeSet;

public class TreeSetDemo {

	public static void main(String[] args) {
		
		for (String s: args) {
            System.out.println(s);
        }
		
		System.out.println("Please input numbers:");

		Scanner scanInput = new Scanner(System.in);
		String data = scanInput.nextLine();

		scanInput.close();
		System.out.println("The input numbers:" + data);

		Scanner scanLong = new Scanner(data);

		// Create a tree set
		TreeSet<Long> ts = new TreeSet<Long>();

		while (scanLong.hasNextLong()) {
			// Add elements to the tree set
			ts.add(scanLong.nextLong());
		}
		
		System.out.println("All: " + ts);
		System.out.println("First: " + ts.first());
		System.out.println("Last: " + ts.last());

	}
}