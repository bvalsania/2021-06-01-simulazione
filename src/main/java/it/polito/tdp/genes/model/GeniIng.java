package it.polito.tdp.genes.model;

public class GeniIng {
	
	private int nGeni;
	private int nIng;
	
	public int getnGeni() {
		return nGeni;
	}
	public void setnGeni(int nGeni) {
		this.nGeni = nGeni;
	}
	public int getnIng() {
		return nIng;
	}
	public void setnIng(int nIng) {
		this.nIng = nIng;
	}
	public GeniIng(int nGeni, int nIng) {
		super();
		this.nGeni = nGeni;
		this.nIng = nIng;
	}

}
