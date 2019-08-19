package me.mateusakino.optimica;

import java.io.Serializable;
import java.util.ArrayList;

public class Vertice implements Serializable {
	private static final long serialVersionUID = -7057887514490143866L;
	private String elemento, ligTipo="";
	private int x, y;
	private ArrayList<Vertice> simples;
	private ArrayList<Vertice> duplas;
	private ArrayList<Vertice> triplas;
	static int uuidn = 0;
	int uuid = 0;
	
	public Vertice(String elemento, int x, int y) {
		this.elemento = elemento;
		this.x = x;
		this.y = y;
		simples = new ArrayList<Vertice>();
		duplas = new ArrayList<Vertice>();
		triplas = new ArrayList<Vertice>();
		uuidn++;
		uuid = uuidn;
	}
	
	@Override
	public boolean equals(Object obj) {
		return ((Vertice) obj).uuid == this.uuid;
	}
	
	public Vertice(int x, int y){
		this("C", x, y);
	}
	
	public void liga(Vertice vertice){
		if (vertice == this)
			return;
		int idx1 = simples.indexOf(vertice);
		int idx2 = duplas.indexOf(vertice);
		if (idx1 != -1){
			simples.remove(idx1);
			duplas.add(vertice);
		} else if (idx2 != -1){
			duplas.remove(idx2);
			if (triplas.indexOf(vertice)!=-1)
				return;
			triplas.add(vertice);
		} else
			simples.add(vertice);
	}
	
	int verif = -1;
	public int verif(){
		return verif++; 
	}
	
	private int id = 0;
	public void ciclo(int id){
		this.id = id;
	}
	
	public int getCicloId(){
		return id;
	}
	
	public double distancia(Vertice vertice){
		return Math.sqrt(Math.pow((vertice.x-this.x),2)+Math.pow((vertice.y-this.y),2));
	}
	
	public String getElemento(){
		return elemento;
	}
	
	public String getLigTipo(){
		return ligTipo;
	}
	
	public void setLigTipo(String tipo){
		ligTipo = tipo;
	}
	
	public int getLigacoes(){
		return simples.size() + duplas.size() + triplas.size();
	}
	
	public ArrayList<Vertice> simples() {
		return simples;
	}
	
	public ArrayList<Vertice> duplas() {
		return duplas;
	}
	
	public ArrayList<Vertice> triplas() {
		return triplas;
	}
}