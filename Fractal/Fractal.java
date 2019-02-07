// Noah Diamond
//
// Fractal.java
//
// This Program creates a multi-colored diamond fratal. The user will be prompted with a GUI
// interface to enter the number of branches the fractal will have.

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Class to establish the GUI that the fractal will be drawn on
abstract class GUI extends JFrame
{
	protected Canvas drawArea;
	protected int fractalDepth;
	protected JTextField myTextField;

	abstract public void fractalDrawHelper();

	GUI()
	{
		setSize(508,600);
		setLocationRelativeTo(null);
		setResizable(false);
	   	setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
  		setContentPane( new PaintingPanel() );

		setVisible(true);
	}

	class PaintingPanel extends JPanel
	{
		PaintingPanel()
		{
			setLayout( null );

			drawArea = new Canvas();
				drawArea.setBackground(Color.WHITE);
				drawArea.setSize(500,500);
			add(drawArea);

			JLabel title = new JLabel("Fractal Depth");
				title.setSize(120,25);
				title.setLocation(100,510);
				title.setHorizontalAlignment(JLabel.CENTER);
				title.setOpaque(true);
				title.setBackground(new Color(166,152,124));
			add( title );

			myTextField = new JTextField("1");
				myTextField.setSize(120,25);
				myTextField.setLocation(100,540);
				myTextField.setHorizontalAlignment(JLabel.CENTER);
			add( myTextField );

			JButton myButton = new JButton ("Start");
				myButton.setSize(160,55);
				myButton.setLocation(230,510);
				myButton.addActionListener(new ButtonAction());
			add( myButton );
		}

		public void paintComponent(Graphics g)
		{
		}
	}

	class ButtonAction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			fractalDepth = Integer.parseInt( myTextField.getText().trim() );
			fractalDrawHelper();
		}
	}

	public static void delay(int num)
	{
		try  {Thread.sleep(num);} catch (InterruptedException e) {}
	}
}



// Class that builds the fractal
public class Fractal{
	public static void main(String[] args) {
		new Environment();
}}

class Environment extends GUI
{
	public void fractalDrawHelper()
	{
		Graphics g = drawArea.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0,0,drawArea.getWidth(),drawArea.getHeight());

		int width = drawArea.getWidth();
		int height = drawArea.getHeight();
		int xCor = width/2;
		int yCor = height/2;
		int size = Math.min(width,height);

		diamondFractal(g,xCor,yCor,size, fractalDepth,0);
	}
	private void diamondFractal (Graphics g,int x,int y,int w,int d, int p)
	{
		drawPattern(g,x,y,w);

		if(d>1)
		{
			if(p!=1)diamondFractal(g,x-w/4,y-w/4,w/2,d-1,3);
			if(p!=2)diamondFractal(g,x-w/4,y+w/4,w/2,d-1,4);
			if(p!=3)diamondFractal(g,x+w/4,y+w/4,w/2,d-1,1);
			if(p!=4)diamondFractal(g,x+w/4,y-w/4,w/2,d-1,2);
		}
	}
	private void drawPattern(Graphics g, int x, int y, int w)
	{
		g.setColor(Color.RED);
		g.drawLine(x-w/4, y, x, y-w/4);
		g.setColor(Color.BLUE);
		g.drawLine(x-w/4, y, x, y+w/4);
		g.setColor(Color.GREEN);
		g.drawLine(x, y-w/4, x+w/4, y);
		g.setColor(Color.ORANGE);
		g.drawLine(x+w/4, y, x, y+w/4);
	}
}