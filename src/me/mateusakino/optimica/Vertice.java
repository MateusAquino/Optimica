package me.mateusakino.optimica;

import java.util.ArrayList;

public class Vertice {
	private String elemento;
	private int x, y;
	private ArrayList<Vertice> ligacoes;
	private ArrayList<Vertice> duplas;
	private ArrayList<Vertice> triplas;
	
	public Vertice(String elemento, int x, int y) {
		this.elemento = elemento;
		this.x = x;
		this.y = y;
		ligacoes = new ArrayList<Vertice>();
		duplas = new ArrayList<Vertice>();
		triplas = new ArrayList<Vertice>();
	}
	
	public Vertice(int x, int y){
		this("C", x, y);
	}
	
	public void liga(Vertice vertice){
		int idx1 = ligacoes.indexOf(vertice);
		int idx2 = duplas.indexOf(vertice);
		if (idx1 != -1){
			ligacoes.remove(idx1);
			duplas.add(vertice);
		} else if (idx2 != -1){
			duplas.remove(idx2);
			if (triplas.indexOf(vertice)!=-1)
				return;
			triplas.add(vertice);
		}
		ligacoes.add(vertice);
	}
	
	public double distancia(Vertice vertice){
		return Math.sqrt(Math.pow((this.x-vertice.x),2)+Math.pow((this.y-vertice.y),2));
	}
	
	public String getElemento(){
		return elemento;
	}
	
	public int getLigacoes(){
		return ligacoes.size() + duplas.size() + triplas.size();
	}
}