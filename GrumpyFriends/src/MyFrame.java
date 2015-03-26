import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;


public class MyFrame extends JFrame 
{
	public MyFrame()
	{
		super();
		MyPanel panel = new MyPanel();
		this.setSize(new Dimension((int)panel.getPreferredSize().getWidth(), (int)panel.getPreferredSize().getHeight()));
		this.setTitle("Manuel è cretino XD");
		JScrollPane p = new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		p.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		this.setContentPane(panel);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) 
	{
		JFrame f=new MyFrame();
	}

	

}
