package tsp;

import java.util.ArrayList;
import java.util.List;

public class Kopt {
	private Instance graph;
	private int position;
	private List<Integer> path;
	

	
	public int getpos(int i) {
		return path.get(i);
	}

	
	public List<Integer> getPath() {
		return path;
	}
	
	// Get size of the tour
	public Kopt(Instance instance, int Depart) {
		this.graph = instance;
		this.position = Depart;
		this.path = new ArrayList<Integer>();
		this.path.add(Depart);
		
	}
	
	
	
	
	public void add(int i) {
		this.path.add(i);
	}
	
	public void setpos(int indexCity, int position) {
		this.path.set(indexCity, position);
		
	}
	
	public int getlength() throws Exception {
		int length=0;
		for(int i=0;i<this.graph.getNbCities();i++) {
			length= (int) (length+this.graph.getDistances(path.get(i), path.get(i+1)));
		}
		return length;
	}
	

	public String toString() {
		String sortie ="";
		int n=this.path.size();
		for (int i=0 ;i<n ;i++) {
			sortie+=this.path.get(i);
		}
		return sortie+"test";
	}
	
	void TwoOptSwap( int i, int k, Instance graph ,Kopt _newTour) 
	{
		int size=graph.getNbCities();
	    // 1. take route[0] to route[i-1] and add them in order to new_route
	    for ( int c = 0; c <= i - 1; ++c )
	    {
	        _newTour.setpos( c, path.get(c) );
	    }
	         
	    // 2. take route[i] to route[k] and add them in reverse order to new_route
	    int dec = 0;
	    for ( int c = i; c <= k; ++c )
	    {
	        _newTour.setpos( c, path.get( k - dec ) );
	        dec++;
	    }
	 
	    // 3. take route[k+1] to end and add them in order to new_route
	    for ( int c = k + 1; c < size; ++c )
	    {
	        _newTour.setpos( c, path.get( c ) );
	    }
	}

	
	
	
}
