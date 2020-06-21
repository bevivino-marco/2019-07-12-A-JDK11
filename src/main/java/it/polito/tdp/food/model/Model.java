package it.polito.tdp.food.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	// definizione grafi;

	private SimpleWeightedGraph<Food, DefaultWeightedEdge> grafo;
	// idMap e liste;
	private List<Food> cibi;
	private Map<Integer, Food>mappaC;
	private List<Relazioni> rel;
	// dao;
	private FoodDao dao ;
public Model() {
	dao = new FoodDao();
}
public void creaGrafo(int porzioni) {
	// creo il grafo;
	grafo = new SimpleWeightedGraph<Food,DefaultWeightedEdge> (DefaultWeightedEdge.class);
	cibi = new LinkedList<>(dao.listFoods(porzioni));
	//System.out.println(cibi.size());
	mappaC = new HashMap<>();
	for (Food f : cibi) {
		mappaC.put(f.getFood_code(), f);
	}
	// definisco i veritici;
//	grafo.addVertex(i);
	Graphs.addAllVertices(grafo, cibi);
	
	// creo gli archi;
	rel = new LinkedList<>(dao.getRel(mappaC));
	for (Relazioni r : rel) {
		if (mappaC.containsKey(r.getF1().getFood_code()) && mappaC.containsKey(r.getF2().getFood_code())
				&& !grafo.containsEdge(r.getF2(), r.getF1()))
		Graphs.addEdge(grafo , r.getF1(), r.getF2(), r.getCal());
	}

	System.out.println("N. vertici : "+grafo.vertexSet().size());
	System.out.println("N. archi : "+grafo.edgeSet().size());

}
public String getOutPut(Food f) {
	List <Food> vicini = new LinkedList<Food>(Graphs.neighborListOf(grafo, f));
	List <Relazioni> lista = new LinkedList<>();
	for (Food fd : vicini) {
		for (Relazioni r : rel) {
			if ((r.getF1().equals(f)&&r.getF2().equals(fd)) ||
					r.getF2().equals(f)&&r.getF1().equals(fd))
				lista.add(r);
		}
	}
	Collections.sort(lista);
	int cont=0;
	String str = "";
	for (Relazioni re : lista) {
		if (cont<5) {
			if ((re.getF1().equals(f)))
			str+= re.getF2()+" "+re.getCal()+"\n";
			else if ((re.getF2().equals(f)))
					str+= re.getF1()+" "+re.getCal()+"\n";
			}
		cont++;

	}
  return str;
	/*for (Relazioni r : rel) {
		if (r.getF1().getFood_code()==f.getFood_code()|| r.getF2().getFood_code()==f.getFood_code())
			lista.add(r);
	}
	Collections.sort(lista);
	System.out.println(lista.toString());
	int cont=0;
	String str = "";
	for (Relazioni re : lista) {
		if (cont<5) {
		if (re.getF1().getFood_code()==f.getFood_code())
		str+= re.getF2()+" "+re.getCal()+"\n";
		else if (re.getF2().getFood_code()==f.getFood_code())
			str+= re.getF1()+" "+re.getCal()+"\n";}
		cont++;
		
	}
	return str;*/
}
public List<Food> getFood() {
	// TODO Auto-generated method stub
	return this.cibi;
}
public String getVA() {
	// TODO Auto-generated method stub
	return "N. vertici : "+grafo.vertexSet().size()+"\n"+"N. archi : "+grafo.edgeSet().size();
}

}
