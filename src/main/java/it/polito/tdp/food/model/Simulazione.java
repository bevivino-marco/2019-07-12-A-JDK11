package it.polito.tdp.food.model;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;


public class Simulazione {
	//Modello -> Stato del sistema ad ogni passo
		private Graph<Food, DefaultEdge> grafo;

		//Tipi di evento/coda prioritaria
		// 1 solo evento
		private PriorityQueue<Event> queue;
		
		//Parametri della simulazione
		private Food partenza;
		private int K;
		private	List<Food> cibi;
		private Model m;
		//Valori in output
		
		private Map<Integer, Boolean> stazioni;
		
		public void init(Model m ,Food partenza, Graph<Food,DefaultEdge> grafo, int K) {
			//ricevo i parametri
			this.partenza = partenza;
			this.grafo = grafo;
			this.m = m;
			//impostazione dello stato iniziale
			this.K = K;
			stazioni = new HashMap<Integer, Boolean>();
			for(int i=1; i<=K;i++) {
				stazioni.put(i, false);
			}
			queue = new PriorityQueue<Event>();
			//inserisco il primo evento
			cibi.addAll(m.getLista());
			this.queue.add(new Event(1, partenza, getCalSucc(partenza,getSucc(partenza))));
			for (int i =1; i<=stazioni.size();i++) {
				stazioni.put(i, true);
			this.queue.add(new Event(i+1, cibi.get(i),getCalSucc()));
			System.out.println("la stazione "+e.getStazione()+" viene occupata dal cibo ", e.getCibo().getDisplay_name());
			}
		}
		
		public void run() {
			//Estraggo un evento per volta dalla coda e lo eseguo,
			//finchÃ¨ la coda non si svuota
			Event e;

			while((e = queue.poll()) != null){
				Food successivo = getSucc(e.getCibo());
				if (successivo ==null) {
					stazioni.put(e.getStazione(),false);
					System.out.println("la stazione "+e.getStazione()+" viene liberata");
				}else {
				double tempo = e.getTempo+getCalsucc(successivo, getSucc(successivo));
				this.queue.add(new Event(e.getStazione(),successivo,tempo));}
				System.out.println("la stazione "+e.getStazione()+" viene occupata dal cibo ", e.getCibo().getDisplay_name());
			}	
		}

		
	}

// creare il metodo getSucc in modo da non prendere i cibi gia fatti

