package tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class generation {
	private List<genome> gen;
	
	/**
	 * Creates a population of new genomes descending from two parents p1 and p2
	 * @param p1
	 * @param p2
	 * @throws Exception
	 */
	public generation(genome p1 , genome p2) throws Exception {
		List<genome> newgen = new ArrayList<genome>();
		for (int i=0 ;i<50;i++) {
			newgen.add(new genome(p1.getInstance(),new ArrayList<Integer>(p1.Crossover(p2).getPath())));
			if (Math.random()<0.7) newgen.get(i).Mutation(1+(int) (4*Math.random()));

		}
		
		this.gen=newgen;
		}

	public List<genome> getGen() {
		return gen;
	}
	
	
}
