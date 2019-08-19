package me.mateusakino.optimica;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Principal {
	final SubstInfo info;
	public static Principal instance;
	public Principal() {
		instance = this;
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		OptimicaCanvas canvas = new OptimicaCanvas();
		frame.getContentPane().add(canvas);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(frame.getLocation().x+80, frame.getLocation().y+80);

		JFrame frame2 = new JFrame();
		frame2.getContentPane().setLayout(new FlowLayout());
		info = new SubstInfo();
	    frame2.add(new JScrollPane(info));
		frame2.pack();
		frame2.setVisible(true);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setLocation(frame.getLocation().x+frame.getSize().width, frame2.getLocation().y+80);
		
	}

	public static void main(String args[]) {
		new Principal();
	}
}