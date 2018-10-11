package tsp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestAnt {
	public static void main(String[] args) throws Exception {
		Instance graph=new Instance("C:/Users/Mehdi/Desktop/TD info/TSP Project/tsp-framework-master/instances/kroA200.tsp",2);
		long startTime = System.currentTimeMillis();
		List<Ant> bestiteration = new ArrayList<Ant>();
		Ant bestfourmis = new Ant(graph,0);
		bestfourmis.parcour();
		for (int j=0 ;j<50;j++) {
		
		List<Ant> fourmis = new ArrayList<Ant>();
		for (int i=0 ;i<200;i++) {
			fourmis.add(new Ant(graph,(int) (Math.random()*graph.getNbCities())));
			fourmis.get(i).parcour();
			if (bestfourmis.getPathlength() > fourmis.get(i).getPathlength()) bestfourmis = fourmis.get(i);
		}
		graph.evaporate();
		for (int i=0 ;i<200;i++) {
			fourmis.get(i).updateTrace();
		}
		bestfourmis.updateTraceElite();
		System.out.println(bestfourmis.getPathlength());
		} 
		bestiteration.add(bestfourmis); 
		
	
		Collections.sort(bestiteration);
		Ant ant= bestiteration.get(0);
		System.out.println(ant.getPath().toString());
		System.out.println(ant.getPathlength());
		System.out.println(ant.fullparcour());
		 long stopTime = System.currentTimeMillis();
		  long elapsedTime = stopTime - startTime;
		  System.out.println("temps d'exe :"+elapsedTime/1000);
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
