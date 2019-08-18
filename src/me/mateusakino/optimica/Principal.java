package me.mateusakino.optimica;

import java.awt.FlowLayout;

import javax.swing.JFrame;

public class Principal {
	public Principal() {
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		OptimicaCanvas canvas = new OptimicaCanvas();
		frame.getContentPane().add(canvas);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String args[]) {
		new Principal();
	}
}