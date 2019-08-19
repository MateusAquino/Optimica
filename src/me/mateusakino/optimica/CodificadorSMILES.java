package me.mateusakino.optimica;

import java.util.ArrayList;

public class CodificadorSMILES {
	public String codificar(ArrayList<Vertice> substancia){
		Vertice inicio = substancia.get(0);
		build(inicio, null);
		return "";
	}
	
	ArrayList<Vertice> temp = new ArrayList<Vertice>();
	ArrayList<Integer> ids = new ArrayList<Integer>();
	
	int id = 0;
	String format = "";
	private int buildPiece(Vertice vertice, Vertice v, Vertice old, int l, String prefix){
		if (v != old) {
			v.setLigTipo(prefix);
			if (vertice.getLigacoes()>l)
				format+="(";
			build (v, vertice);
			if (vertice.getLigacoes()>l)
				format+=")";
			if (format.endsWith("()"))
				format = format.substring(0, format.length()-2);
			++l;
		}
		return l;
	}
	
	private void build(Vertice vertice, Vertice old){
		if (!ids.contains(vertice.uuid)) {
			format+="%s";
			temp.add(vertice);
			ids.add(vertice.uuid);
			int l = 2;
			for (Vertice v : vertice.simples())
				l = buildPiece(vertice, v, old, l, "");
			for (Vertice v : vertice.duplas())
				l = buildPiece(vertice, v, old, l, "=");
			for (Vertice v : vertice.triplas())
				l = buildPiece(vertice, v, old, l, "#");
		} else {
			if (vertice.getCicloId()==0){
				vertice.ciclo(++id);
				old.ciclo(id);
			}
		}
	}
	
	int ramificacoes = 0;
	StringBuilder SMILES = new StringBuilder();
	int tempidx;
	public String get(){
		Object[] elementos = new String[temp.size()];
		tempidx = 0;
		temp.forEach(e->{
			elementos[tempidx++] =
			   e.getLigTipo()
			+  e.getElemento()
			+ (e.getCicloId()>0?e.getCicloId():"");});
		return String.format(format, elementos);
	}
}