package me.mateusakino.optimica;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComponent;

public class OptimicaCanvas extends JComponent implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;
	int w = 800, h = 600;
	boolean[][] mat = new boolean[w][h];
	
	int x;
    int y;
    int posX;
    int posY;
    ArrayList<Vertice> substancia = new ArrayList<Vertice>();
    
	public OptimicaCanvas() {
		Dimension d = new Dimension(w, h);
		setSize(d);
		setMinimumSize(d);
		setMaximumSize(d);
		setPreferredSize(d);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	protected void paint(int x, int y){
		if (x>w || x<0 || y>h || y<0)
			return;
		Graphics g = getGraphics();
		if (R==-1) {
			p0x = posX;
			p0y = posY;
		} else if (R>blocoR) {
			double a = Math.atan2(y-p0y, x-p0x)*180.0/Math.PI;
			if (angulo!=-1 && distR > minDistRs && Math.abs(angulo-a)>thresholdAngulo){
				ident(x, y);
				distR = -1;
			}
			
			distR++;
			angulo = a;
			
			R = -2;
		}
		R++;
	    g.drawLine(posX, posY, x, y);
	    posX = x;
	    posY = y;
	}

	double angulo = -1;
	int R = -1, distR = -1, p0x = -1, p0y = -1;
	
	// Configs
	int blocoR = 13;
	int minDistRs = 2;
	int thresholdAngulo = 18;
	
	@Override
	public void mouseDragged(MouseEvent e) {
		paint(e.getX(), e.getY());
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		posX = e.getX();
		posY = e.getY();
		ident(posX, posY);
	}
	
	boolean verbose = true;
	int raioConectado = 20;
	Vertice ultimo = null;
	boolean primeira = true;
	private void ident(int x, int y){
		Graphics g = getGraphics();
		if (verbose) {
			g.setColor(new Color(220, 20, 210));
			int d = raioConectado;
			g.drawArc(x-d/2, y-d/2, d, d, 0, 360);
			g.setColor(Color.BLACK);
		}
		
		Vertice C = new Vertice(x, y);
		if (primeira) {
			ultimo = C;
			substancia.add(C);
			primeira = false;
		} else {
			boolean encontrou = false;
			for (Vertice v : substancia)
				if (v.distancia(C)<=raioConectado){
					if (ultimo==null)
						ultimo = v;
					else {
						v.liga(ultimo);
						ultimo.liga(v);
						ultimo = v;
					}
					encontrou = true;
					break;
				}
			if (!encontrou) {
//				double maisProx = Integer.MAX_VALUE;
//				Vertice maisProxV = null;
//				for (Vertice v : substancia)
//					if (v.distancia(C)<maisProx){
//						maisProx = v.distancia(C);
//						maisProxV = v;
//					}
//				maisProxV.liga(C);
//				C.liga(maisProxV);
//				substancia.add(C);
				if (ultimo!=null){
					C.liga(ultimo);
					ultimo.liga(C);
				}
				substancia.add(C);
				ultimo = C;
			}
		}
	}
	
	@Override public void mouseReleased(MouseEvent e) {
		R = -1;
		angulo = -1;
		distR = -1;
		ident(e.getX(), e.getY());
		ultimo = null;
		
		// RESULTADO
		CodificadorSMILES cs = new CodificadorSMILES();
		cs.codificar(substancia);
		String smiles = cs.get();
		System.out.println("Encontrado: " + smiles);
		new Thread(()->{
			try {
				Principal.instance.info.setProcurando();
				new ChemSpider().search(smiles);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}).start();;
	}

	@Override public void mouseMoved(MouseEvent e) {}
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
}