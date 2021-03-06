package tsp;

import java.util.ArrayList;
import java.util.List;


public class Ant implements Comparable<Ant>{
	double ALPHA=1;
	double BETA=2;
	double GAMMA=0.000000005;
	private Instance graph;
	private int position;
	private boolean[] already_seen;
	private List<Integer> path;
	private double denominateur;
	private long pathlength;
	private double Q;
	private int nbaccessibles;
	
	public int getPosition() {
		return position;
	}

	public boolean[] getAlready_seen() {
		return already_seen;
	}

	public List<Integer> getPath() {
		return path;
	}

	public Ant(Instance instance, int Depart) {
		this.graph = instance;
		this.position = Depart;
		this.already_seen = new boolean[graph.getNbCities()];
		this.path = new ArrayList<Integer>();
		this.already_seen[Depart]=true;
		this.path.add(Depart);
		this.pathlength=0;
		this.Q=100;
		this.nbaccessibles= this.getGraph().getNbCities()-1;
	}
	
	
	public List<Integer> reachable() throws Exception{
		List<Integer> reachable =new ArrayList<Integer>();
		for (int i=0 ; i<this.graph.getNbCities() ;i++) {
			try { this.graph.getDistances(this.position, i);
			if (!this.already_seen[i]) reachable.add(i);
			
			}
			catch(Exception e) {
			}
			}
		double denomin =0;
		for (int i=0;i<reachable.size();i++) {
			denomin += GAMMA+Math.pow(this.graph.getTraces(this.position, reachable.get(i)),ALPHA)/Math.pow((double) this.graph.getDistances(this.position, reachable.get(i)),BETA);
		}
		this.denominateur=denomin;
		return reachable;
	}
	
	public double ProbaFori(int j) throws Exception {
			return (GAMMA+Math.pow(this.graph.getTraces(this.position, j),ALPHA)/Math.pow((double) this.graph.getDistances(this.position, j),BETA))/this.denominateur;
			
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
			this.pathlength+= this.graph.getDistances(this.position, nextPos);
			this.position=nextPos;
			this.path.add(nextPos);
			this.already_seen[nextPos]=true;
			this.nbaccessibles--;
		}
	}
	
	public void parcour() throws Exception {
		while (this.nextPos()!=-1) {
			updatePos();
		}
		this.pathlength+= this.graph.getDistances(this.position,this.path.get(0));
	}
	
	public boolean fullparcour() {
		return this.path.size()==this.graph.getNbCities();
	}

	public String toString() {
		String sortie ="";
		int n=this.path.size();
		for (int i=0 ;i<n ;i++) {
			sortie+=this.path.get(i);
		}
		return sortie+"test";
	}
	
	public void updateTrace() {
		for (int i=0 ;i<path.size()-1 ;i++) {
			graph.m_traces[path.get(i)][path.get(i+1)]+=Q/this.getPathlength();
		}
	}
	public void updateTraceGlobale() {
		for (int i=0 ;i<path.size()-1 ;i++) {
			graph.m_traces[path.get(i)][path.get(i+1)]+=50*Q/this.getPathlength();
		}
	}
	
	public void updateTraceElite() {
		for (int i=0 ;i<path.size()-1 ;i++) {
			graph.m_traces[path.get(i)][path.get(i+1)]+=1000*Q/this.getPathlength();
		}
	}
	public void updateTraceEliteGlobale() {
		for (int i=0 ;i<path.size()-1 ;i++) {
			graph.m_traces[path.get(i)][path.get(i+1)]+=5000*Q/this.getPathlength();
		}
	}

	@Override
	public int compareTo(Ant a) {
		// TODO Auto-generated method stub
		return (int) Math.signum(this.getPathlength()-a.getPathlength());
	}

	public long getPathlength() {
		return pathlength;
	}

	public void setPath(List<Integer> path) {
		this.path = path;
	}

	
	public long calculpath() throws Exception {
		int n=this.path.size()-1;
		long sortie =0;
		for (int i=0 ;i<n;i++) {
			sortie += this.graph.getDistances(path.get(i), path.get(i+1));
		}
		return sortie+this.graph.getDistances(path.get(n-1), path.get(0));
	}

	public void setPathlength(long pathlength) {
		this.pathlength = pathlength;
	}

	public Instance getGraph() {
		return graph;
	}

	public void setDenominateur(double denominateur) {
		this.denominateur = denominateur;
	}

	public int getNbaccessibles() {
		return nbaccessibles;
	}

	public void setGraph(Instance graph) {
		this.graph = graph;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public void setAlready_seen(boolean[] already_seen) {
		this.already_seen = already_seen;
	}

	public void setNbaccessibles(int nbaccessibles) {
		this.nbaccessibles = nbaccessibles;
	}
	
	



}
