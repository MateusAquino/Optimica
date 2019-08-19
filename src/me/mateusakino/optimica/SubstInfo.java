package me.mateusakino.optimica;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

class SubstInfo extends JComponent {
	private static final long serialVersionUID = -5910982898773671755L;
	private BufferedImage img = new BufferedImage(250, 250, BufferedImage.TYPE_INT_RGB);;

    public SubstInfo() {
    	img = new BufferedImage(250, 250, BufferedImage.TYPE_INT_RGB);
    	setPreferredSize(new Dimension(250, 400));
    }
    
    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Helvetica", Font.BOLD, 18));
        g.drawString(titulo, 10, 275);
        g.setFont(new Font("Helvetica", Font.PLAIN, 14));
        
        if (!txt.contains("\n"))
        	g.drawString(txt, 15, 295);
        else
        	for (int i = 0; i < txt.split("\n").length; i++)
        		g.drawString(txt.split("\n")[i], 15, 295+i*16);
        	
        g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
    }
    
    String titulo="Começe desenhando!";
    String txt="teste";
    public void setText(String txt){
    	this.txt=txt; 
    	repaint();
    }
    
    public void setTitulo(String titulo){
    	this.titulo=titulo;
    	repaint();
    }
    
    public void setImg(String url) throws IOException {
    	try {
    		img = ImageIO.read(new URL(url));
    		if (img==null)
    			img = new BufferedImage(250, 250, BufferedImage.TYPE_INT_RGB);
    	} catch (Exception e){
    		img = new BufferedImage(250, 250, BufferedImage.TYPE_INT_RGB);
    	}
        repaint();
    }

	public void setProcurando() {
		img = (new BufferedImage(250, 250, BufferedImage.TYPE_INT_RGB));
		setTitulo("Procurando...");
		setText("Pode demorar alguns segundos\ndependendo do banco de dados...");
	}
}