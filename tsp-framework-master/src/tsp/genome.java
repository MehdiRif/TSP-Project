package tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class genome implements Comparable<genome> {
	private List<Integer> path;
	private Instance graph;
	private long objective;
	
	public List<Integer> NearestNeighbour() throws Exception{
		boolean[] dejavu = new boolean[this.graph.getNbCities()];
		List<Integer> output = new ArrayList<Integer>();
		output.add(0);
		int pos =0;
		while (output.size()<this.graph.getNbCities()) {
			long min = 50000*(long)(this.graph.getMaxX()-this.graph.getMinX()+this.graph.getMaxY()-this.graph.getMinY());
			int nextpos = -1;
			for(int i=1 ; i<this.graph.getNbCities();i++) {
				if (!dejavu[i]) {
					if (this.graph.getDistances(pos,i)<min) {
						min=this.graph.getDistances(pos,i);
						nextpos =i;
						
					}
				}
			}
			output.add(nextpos);
			dejavu[nextpos]=true;
			pos=nextpos;
		}
		return output;
	}
	
	public genome(Instance i) throws Exception {
		this.graph = i;
		this.path= NearestNeighbour();
		this.objective=this.calculpath();
	}
	public genome(Instance i,List<Integer> p) throws Exception {
		this.path=p;
		this.graph=i;
		if (!p.isEmpty()) this.objective=this.calculpath();
	}
	
	public long calculpath() throws Exception {
		int n=this.path.size()-1;
		long sortie =0;
		for (int i=0 ;i<n;i++) {
			sortie += this.graph.getDistances(path.get(i), path.get(i+1));
		}
		return sortie+this.graph.getDistances(path.get(n), path.get(0));
	}

	public void Mutationswap() throws Exception{
		int i = (int) (((this.graph.getNbCities()-1))*Math.random())+1;
		int j=i;
		while (i==j) j=(int) (((this.graph.getNbCities()-1))*Math.random())+1;
		int stock =this.getPath().get(i); 
		this.path.set(i, this.getPath().get(j));
		this.path.set(j, stock);
		
	}
	
	
	public void Mutation(int k) throws Exception {
		
		boolean swaps = Math.random()>0.7;
		for (int i=0 ; i<k ;i++) {
			if (swaps) this.Mutationswap();
			this.singleMutation();
		}
		this.objective=this.calculpath();
	}
	
	
	public void singleMutation() throws Exception{
		int n = path.size();
		int i = (int) (Math.random()*(n-3));
		int j=i;
		while (j<i+2) j= (int) (Math.random()*(n-1));
		int xj = path.get(j);
		int xip1 = path.get(i+1);
		path.set(i+1,xj);
		path.set(j,xip1);
		List<Integer> newpath = new ArrayList<Integer>();
		List<Integer> middle = path.subList(i+2, j);
		Collections.reverse(middle);
			newpath.addAll(path.subList(0, i+2));
			newpath.addAll(middle);
			newpath.addAll(path.subList(j,n));
	}
	

	
	public genome Crossover(genome parent2) throws Exception {
		genome fils1 = new genome(this.graph,new ArrayList<Integer>());
		int i = (int) (((this.graph.getNbCities()-2))*Math.random())+1;
		int j=i;
		while (i>=j) j=(int) (((this.graph.getNbCities()-1))*Math.random())+1;
		boolean[] dejavu = new boolean[this.graph.getNbCities()];
		dejavu[0]=true;
		List<Integer> sequence = new ArrayList<Integer>();
		for (int k=i ; k<j ;k++) {
			sequence.add(this.getPath().get(k));
			dejavu[this.getPath().get(k)]=true;
			
		}
		List<Integer> begin = new ArrayList<Integer>();
		int pos =1;
		while (begin.size()<i-1) {
			if (!dejavu[parent2.getPath().get(pos)]) {
				begin.add(parent2.getPath().get(pos));
				dejavu[(parent2.getPath().get(pos))]=true;
			}
			pos++;
		}
		begin.addAll(sequence);
		List<Integer> ending = new ArrayList<Integer>();
		while (ending.size()<this.graph.getNbCities()-begin.size()-1) {
			if (!dejavu[parent2.getPath().get(pos)]) {
				ending.add(parent2.getPath().get(pos));
				dejavu[(parent2.getPath().get(pos))]=true;
			}
			pos++;
		}
		begin.addAll(ending);
		begin.add(0,0);
		fils1.path=begin;
		fils1.objective=fils1.calculpath();
		return fils1;
	}
	
	public void shuffle() throws Exception {
		List <Integer> newpath = this.path.subList(1, this.path.size());
		Collections.shuffle(newpath);
		newpath.add(0,0);
		this.path=newpath;
		this.objective=this.calculpath();
	}

	public List<Integer> getPath() {
		return path;
	}
	
	public Instance getInstance() {
		return this.graph;
	}
	

	public long getObjective() {
		return objective;
	}

	@Override
	public int compareTo(genome g) {
		// TODO Auto-generated method stub
		if (this.objective==g.getObjective()) return 0;
		else return (this.objective<g.objective) ? -1 : 1;
	}
	
	public void twoOpt() throws Exception{
		int n = path.size();
		boolean amelioration = true;
		while (amelioration ) {
			amelioration=false;
		for (int i=0 ;i<n-2;i++) {
			for (int j=i+2 ;j<n ;j++) {
					int jp1= (j+1) % n;
					if (graph.getDistances(path.get(i), path.get(i+1)) + graph.getDistances(path.get(j), path.get(jp1))
					>graph.getDistances(path.get(i), path.get(j)) + graph.getDistances(path.get(i+1), path.get(jp1)) ) {
						int xj = path.get(j);
						int xip1 = path.get(i+1);
						path.set(i+1,xj);
						path.set(j,xip1);
						List<Integer> newpath = new ArrayList<Integer>();
						List<Integer> middle = path.subList(i+2, j);
						Collections.reverse(middle);
 						newpath.addAll(path.subList(0, i+2));
 						newpath.addAll(middle);
 						newpath.addAll(path.subList(j,n));
 						path=newpath;
							amelioration = true;
						
					}
				}
			}
		}
		
		this.objective=this.calculpath();
	}
	
}
	