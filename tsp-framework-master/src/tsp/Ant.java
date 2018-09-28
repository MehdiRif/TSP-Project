package tsp;

import java.util.ArrayList;
import java.util.List;

public class Ant {
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
	
	













}
