package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;


public class Simulatore {
	
	//dati di accesso
	private int nIng; // numero di ingegneri
	private double probMantenereGene = 0.3 ; 
	private int mese = 36; // 36 mesi totali
	private Genes startg;
	private Graph<Genes, DefaultWeightedEdge> grafo;
	
	
	// dati di uscita
	private List<GeniIng> res;
	
	//modello del mondo

	private List<Genes> geneStudiato;
	
	
	//coda
	private PriorityQueue<Event> queue;
	
	public Simulatore(Genes startg,int nIng,Graph<Genes, DefaultWeightedEdge> grafo) {
		this.startg = startg;
		this.nIng= nIng;
		this.grafo = grafo;
		
		if(this.grafo.degreeOf(startg)==0) {
			throw new IllegalArgumentException("vertice di partenza isolato");
		}
		queue = new PriorityQueue<>();
		
		for(int n =0;n<this.nIng;n++) {
			this.queue.add(new Event(0,n));
		}
		
		//inizializzo modello del mondo
		this.geneStudiato = new ArrayList<>();
		for(int n =0; n<nIng; n++) {
			geneStudiato.add(this.startg);
		}
	}

	public void init() {
		
	}
	
	public void run() {
		
		while(this.queue.isEmpty() ) {//condizione di terminazione
			Event e = this.queue.poll();
			int m = e.getMese();
			Genes g = this.geneStudiato.get(nIng);
			
			if(m<this.mese) {
				if(Math.random()< this.probMantenereGene) {
					//mantieni
					this.queue.add(new Event(m+1, this.nIng));
				}else {
					//cambia
					
					// calcola somma pesi adiacenti
					double s =0.0;
					for(DefaultWeightedEdge edge : this.grafo.edgesOf(g) ) {
						s += this.grafo.getEdgeWeight(edge);
					}
					
					//estrai numero casuale
					double r = Math.random()*s;
					
					//confronta r co nsomme parziali dei pesi
					Genes nuovo = null;
					double somma=0.0;
					for(DefaultWeightedEdge edge : this.grafo.edgesOf(g) ) {
						 somma += this.grafo.getEdgeWeight(edge);
						 if(somma> r) {
							 nuovo = Graphs.getOppositeVertex(grafo, edge, g);
							 break;
						 }
						 
					}
					this.geneStudiato.set(nIng, nuovo);
					this.queue.add(new Event(m+1, this.nIng));
				}
			}
			
			
		}
		
		
	}

	public Map<Genes, Integer> getGeniStud(){
		Map<Genes, Integer> studiati = new HashMap<>();
		
		for(int n =0; n<this.nIng; n++) {
			Genes g= this.geneStudiato.get(n);
			if(studiati.containsKey(g)) {
				studiati.put(g, studiati.get(g)+1);
			}else {
				studiati.put(g, 1);
			}
		}
		return studiati;
	}
	
	
}
