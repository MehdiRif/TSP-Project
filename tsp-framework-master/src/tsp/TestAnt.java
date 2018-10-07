package tsp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestAnt {
	public static void main(String[] args) throws Exception {
		Instance graph=new Instance("C:/Users/Mehdi/Desktop/TD info/TSP Project/tsp-framework-master/instances/eil10.tsp",2);
		
		List<Ant> bestiteration = new ArrayList<Ant>();
		for (int j=0 ;j<500;j++) {
		
		List<Ant> fourmis = new ArrayList<Ant>();
		for (int i=0 ;i<200;i++) {
			fourmis.add(new Ant(graph,0));
			fourmis.get(i).parcour();
		}
		graph.evaporate();
		for (int i=0 ;i<200;i++) {
			fourmis.get(i).updateTrace();
		}
		Collections.sort(fourmis);
		for (int i=0;i<10;i++) {
			fourmis.get(i).updateTrace();
			fourmis.get(i).updateTrace();
			fourmis.get(i).updateTrace();
			fourmis.get(i).updateTrace();
			fourmis.get(i).updateTrace();
			fourmis.get(i).updateTrace();
		}
		Ant ant = fourmis.get(0);
		bestiteration.add(ant);
		Collections.sort(bestiteration);
		
		}
		Ant ant= bestiteration.get(0);
		System.out.println(ant.getPath().toString());
		System.out.println(ant.pathLength());
		System.out.println(ant.fullparcour());
		/*Test des probas*/
		 
		 /*double sum=0;
		ant.updatePos();
		List<Integer> reachable = ant.reachable();
		System.out.println(reachable.toString());
		for (int i=0 ; i<reachable.size();i++) {
			System.out.println(reachable.get(i)+" : "+ant.ProbaFori(reachable.get(i)));
			sum+=ant.ProbaFori(reachable.get(i));
			
		}
		System.out.println("sum :"+sum); */
	
		
	}
}
