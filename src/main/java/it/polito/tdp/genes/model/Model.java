package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	private GenesDao dao;
	private Graph<Genes, DefaultWeightedEdge> grafo;
	private Map<String, Genes> idMap;
	
	public Model() {
		dao = new GenesDao();
		idMap = new HashMap<>();
		this.dao.getAllGenes(idMap);
	}
	
	public String creaGrafo() {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, this.dao.getVertici(idMap));
		
		for(Coppia c : this.dao.getArchi(idMap)) {
			if(grafo.containsVertex(c.getG1()) && grafo.containsVertex(c.getG2()))
				Graphs.addEdge(this.grafo, c.getG1(), c.getG2(), c.getPeso());
		}
		
		return "Il grafo ha "+this.grafo.vertexSet().size()+ "  verici e "+this.grafo.edgeSet().size()+"  archi";
	}
	
	
	public List<Genes> getVert(){
		return new ArrayList<>(grafo.vertexSet());
	}
	
	public List<Adiacenza> getAdiacenza(Genes g){
		List<Adiacenza> res = new ArrayList<>();
		
		
		for(Genes v : Graphs.neighborListOf(grafo, g)) {
			double peso = grafo.getEdgeWeight(this.grafo.getEdge(v, g));
			res.add(new Adiacenza(v,peso));
		}
		
		Collections.sort(res, new Comparator<Adiacenza> () {

			@Override
			public int compare(Adiacenza o1, Adiacenza o2) {
				// TODO Auto-generated method stub
				return o2.getPeso().compareTo(o1.getPeso());
			}
			});
		return res;
	}
	
	
	
	public Map<Genes, Integer> simulaIng(Genes start, int n){
		try {
		Simulatore sim = new Simulatore(start, n, grafo);
		sim.run();
		return sim.getGeniStud();
		}catch(IllegalArgumentException e) {
			return null;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*
	private GenesDao dao ;
	private Graph<Genes, DefaultWeightedEdge> grafo;
	private Map<String, Genes> idMap;
	
	public Model() {
		dao = new GenesDao();
		idMap = new HashMap<>();
		this.dao.getAllGenes(idMap);
	}
	
	
	public String creagrafo() {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, this.dao.getVertici(idMap));
		
		for(Coppia c: this.dao.getArchi(idMap)) {
			Graphs.addEdge(this.grafo, c.getG1(), c.getG2(), c.getPeso());
		}
		
		return "Grafo creato con "+this.grafo.vertexSet().size()+" vertici e "+this.grafo.edgeSet().size()+" archi!";
	}
	
	
	public List<Genes> getgeni(){
		return new ArrayList<>(this.grafo.vertexSet());
	}
	
	public List<Adiacenza> getAdiacenza(Genes g){
		List<Adiacenza> result = new ArrayList<>();
		
		List<Genes> vicini = Graphs.neighborListOf(this.grafo, g);
		
		for(Genes  i: vicini) {
			result.add(new Adiacenza (i, grafo.getEdgeWeight(grafo.getEdge(i, g))));
		}
		
		Collections.sort(result, new Comparator<Adiacenza>() {

			@Override
			public int compare(Adiacenza o1, Adiacenza o2) {
				// TODO Auto-generated method stub
				return o2.getPeso().compareTo(o1.getPeso());
			}
		}
		);
		
		return result;
	}
	
*/
}
