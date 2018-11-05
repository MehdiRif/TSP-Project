package tsp;

import java.util.ArrayList;
import java.util.List;

public class AntCapacitive extends Ant {

	

	private int capacity;
	
	public AntCapacitive(Instance instance, int Depart) {
		super(instance, Depart);
		this.capacity=50;
		// TODO Auto-generated constructor stub
	}
	
	
	public List<Integer> reachable() throws Exception{
		List<Integer> reachable =new ArrayList<Integer>();
		int nb = Math.min(this.getGraph().getNbCities(), this.capacity);
		int i=0;
		while (this.getNbaccessibles()>0 && i<nb) {
			int pivot = (int) (Math.random()*this.getGraph().getNbCities());
			try { this.getGraph().getDistances(this.getPosition(), pivot);
			if (!this.getAlready_seen()[pivot]) {
				reachable.add(pivot);
				i++;
			}
			
			}
			catch(Exception e) {
			}
			}
		double denomin =0;
		for (int j=0;j<reachable.size();j++) {
			denomin += GAMMA+Math.pow(this.getGraph().getTraces(this.getPosition(), reachable.get(j)),ALPHA)/Math.pow((double) this.getGraph().getDistances(this.getPosition(), reachable.get(j)),BETA);
		}
		this.setDenominateur(denomin);
		return reachable;
	}
	
	public int nextPos() throws Exception {
		double p = Math.random();
		int i=0;
		double sum=0;
		List<Integer> reachable = this.reachable();
		if (reachable.isEmpty()) return -1;
		while (sum<p) {
			sum+=ProbaFori(reachable.get(i));
			i++;
		}
		return reachable.get(i-1); 
	}
	
	
	
	public void updatePos() throws Exception {
		int nextPos=this.nextPos();
		if (nextPos>=0) {
			this.setPathlength(this.getPathlength()+ this.getGraph().getDistances(this.getPosition(), nextPos));
			this.setPosition(nextPos);
			this.getPath().add(nextPos);
			this.getAlready_seen()[nextPos]=true;
			this.setNbaccessibles(this.getNbaccessibles()-1);
			this.capacity--;
		}
	}
	public void parcour() throws Exception {
		while (this.nextPos()!=-1) {
			updatePos();
		}
	}
}
