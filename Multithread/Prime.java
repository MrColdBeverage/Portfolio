// Noah Diamond
//
// Prime.java


import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class Prime extends JFrame implements Runnable
{
	int start;
	int stop;
	String currentnum;
	String percent;
	String file;
	private final int threadNum;
	
	public void run() // method from Runnable required to be overwritten for Thread use
	{
		ArrayList<Integer> primes = new ArrayList<Integer>(); // creates an arraylist to hold the primes for that individual thread
		for (int i = start; i <= stop; i+=2) // loop to go through each threads individual number range
		{
			if (isPrime(i)) // checks if number is prime
			{
				primes.add(i);
			}
			if ((i+1)%100 == 0) // checks how many numbers have been calculated, to get a percentage to display in the GUI
			{
				percent = "" + Math.round((double)(i - start)/(stop - start) * 100);
				currentnum = String.valueOf(i);

				if((stop / (stop - start) - 1) == threadNum)
				{
					repaint();
				}
			}
		}
		try
		{
			PrintWriter print = new PrintWriter(new File(file)); // creates new file with the name that was passed to the Prime class when the thread was instanciated 
			for (int i = 0; i < primes.size(); i++)
			{
				print.println(primes.get(i)); // writes arraylist to the file
			}
			print.close();
		} catch (Exception e){}

		try{
			Thread.sleep(2000); // simple thread.sleep for dramatic effect
		}	catch(InterruptedException e){}
		setVisible(false); // make the GUI invisible until the main thread closes
		dispose();
	}

	public Prime(int startValue, int stopValue, String filename) // constructor
	{
		start = startValue;
		stop = stopValue;
		file = filename;
		percent = "0";
		currentnum = "0";
		threadNum = stop / (stop - start) - 1;
		Point p = new Point(threadNum * 200, 400);

		setSize(160,150);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(p);

		setContentPane(new MyPanel());
		setVisible(true);
	}

	public static boolean isPrime(int toTest) // tests to see if number is prime
	{
		if (toTest == 2)
		{
			return true;
		}
		for (int n = 3; n < (int) Math.sqrt(toTest) + 1; n+=2)
		{
			if (toTest%n==0)
			{
				return false;
			}
		}
		return true;
	}

	class MyPanel extends JPanel // GUI stuff
	{
		JLabel threadnum;
		JLabel label;
		JLabel current;
		boolean stop = false;

		public MyPanel()
		{
			threadnum = new JLabel( "Thread " + threadNum + ": %");
			threadnum.setFont(new Font("Times New Roman", Font.BOLD, 20));
			add(threadnum);
			label = new JLabel();
			label.setFont(new Font("Times New Roman", Font.BOLD, 20));
			add(label);
			current = new JLabel();
			current.setFont(new Font("Times New Roman", Font.BOLD, 20));
			add(current);
		}

		public void paintComponent(Graphics g)
		{
			if(!stop)
			{
				if(!percent.equals("0"))
				{
					label.setText(percent);
					current.setText(currentnum);
					if(percent.equalsIgnoreCase("100"))
					{
						threadnum.setText("");
						label.setText("Done!");
						current.setText("");
						stop = true;
					}
				}
			}

		}
	}
}

