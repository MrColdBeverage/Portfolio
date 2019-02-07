// Noah Diamond
//
// Main.java
//
// Program that uses multithreading to find all of the prime numbers between 1 and 80000000, and prints them all into a single text file. 
// The program breaks the task into 4 different threads to speed up the process. Four GUI windows are created to show the progress of each thread.

import java.util.*;
import java.io.*;
import javax.swing.*;

public class Main {
	public static int threadCount = 4; // number of additional threads to be spawned
	public static int stop = 80000000; // maximum number to be calculated by the program

	public static void main(String[] args) {
		
		System.out.println("Spawning threads...");
		ArrayList<Thread> threads = new ArrayList<Thread>(); // arraylist to hold newly created threads
		int incrementAmount = stop / threadCount; // range of numbers that each threat will calculate
		int starting = 1;

		for (int i = 0; i < threadCount; i++) { // creates new threads that hold new instances of the Prime object as an argument
				threads.add(new Thread(new Prime(starting, starting	+ incrementAmount, i + ".txt")));
				starting += incrementAmount; // changes starting value for the next thread by the incrementAmount
		}

		try {
			Thread.sleep(3000); // simple Thread.sleep to make the console output look cool
		} catch (InterruptedException e) {
		}

		for (int i = 0; i < threads.size(); i++) { // loop to start all the threads at once
			threads.get(i).start();
		}

		for (int i = 0; i < threads.size(); i++) { // loop that now joins all the started threads to the main thread
			try {										// this is done so that the new threads can communicate with the main thread when they are finsihed
				threads.get(i).join();
				System.out.println("Thread " + i + " finished!");
			} catch (Exception e) {
			}
		}

		ArrayList<Integer> primes = new ArrayList<Integer>(); // creates an arraylist to hold the calulations of all aditional threads
																// each thread created it own text file holding its results
		for (int i = 0; i < threads.size(); i++) {
			try {

				File f = new File(i + ".txt"); // accessing each threads unique text file
				System.out.println("Reading Thread: " + i);
				Scanner scan = new Scanner(f);
				while (scan.hasNextInt()) {
					primes.add(scan.nextInt()); // adding data from the threads text file to the primes arraylist
				}
				scan.close();
				f.delete(); // deletes each threads unique text file once data has been drawn from it
			} catch (Exception e) {

			}
		}

		try {
			PrintWriter print = new PrintWriter(new File("primes.txt")); // creates a new file to hold the contents of the primes arraylist
			for (int i = 0; i < primes.size(); i++) {
				print.println(primes.get(i));

				
				if( ( ( (double)i/primes.size() * 100) - (int)((double)i/primes.size() * 100)) <= .00005)
				{
					System.out.println("%" + String.format("%.0f",(((double)i/primes.size()) * 100))); // prints the percentage of  file printing completed
				}
			}
			System.out.println("Done Writing!");
			print.close();
			System.out.println("Printer closed!");
		} catch (Exception e) {}
		System.out.println("Finished!"); // everything is closed and finished
	}

}
