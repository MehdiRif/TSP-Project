package tsp;

import java.util.ArrayList;
import java.util.List;


public class Ant {
	double Q =1;
	double ALPHA=1;
	double BETA=1;
	double GAMMA=0;
	private Instance graph;
	private int position;
	private boolean[] already_seen;
	private List<Integer> path;

	public Ant(Instance instance) {
		this.graph = instance;
		this.position = 0;
		this.already_seen = new boolean[graph.getNbCities()];
		this.path = new ArrayList<Integer>();
	}
	
	public long pathLength() {
		long length = 0;
		for (int i=0; i<this.path.size()-1; i++) {
			try {
				length += graph.getDistances(i, i+1);
			}
			catch (Exception e) {
				return -1;
			}
		}
		return length;
	}
	
	public List<Integer> reachable(){
		List<Integer> reachable =new ArrayList<Integer>();
		for (int i=0 ; i<this.graph.getNbCities() ;i++) {
			try { this.graph.getDistances(this.position, i);
			if (!this.already_seen[i]) reachable.add(i);
			
			}
			catch(Exception e) {
			}
			}
		return reachable;
	}
	
	public double ProbaFori(int j) {
		List<Integer> reachable = this.reachable();
		if (reachable.contains(j)){
			double denominateur =0;
			try {
				
			for (int i=0;i<reachable.size();i++) {
				denominateur += GAMMA+Math.pow(this.graph.getTraces(this.position, i),ALPHA)/Math.pow(this.graph.getDistances(this.position, i),BETA);
			}
			return (GAMMA+Math.pow(this.graph.getTraces(this.position, j),ALPHA)/Math.pow(this.graph.getDistances(this.position, j),BETA)/denominateur);
			
			}
			catch(Exception e) {
				return 0;
			}
			}
		else return 0;
	}
	
	public int nextPos() {
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

	public void updatePos() {
		int nextPos=this.nextPos();
		if (nextPos>=0) {
			this.position=nextPos;
			this.path.add(nextPos);
			this.already_seen[nextPos]=true;
		}
	}










}
