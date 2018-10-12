package tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * This class is the place where you should enter your code and from which you can create your own objects.
 * 
 * The method you must implement is solve(). This method is called by the programmer after loading the data.
 * 
 * The TSPSolver object is created by the Main class.
 * The other objects that are created in Main can be accessed through the following TSPSolver attributes: 
 * 	- #m_instance :  the Instance object which contains the problem data
 * 	- #m_solution : the Solution object to modify. This object will store the result of the program.
 * 	- #m_timeLimit : the maximum time limit (in seconds) given to the program.
 *  
 * @author Damien Prot, Fabien Lehuede, Axel Grimault
 * @version 2017
 * 
 */
public class TSPSolver {

	// -----------------------------
	// ----- ATTRIBUTS -------------
	// -----------------------------

	/**
	 * The Solution that will be returned by the program.
	 */
	private Solution m_solution;

	/** The Instance of the problem. */
	private Instance m_instance;

	/** Time given to solve the problem. */
	private long m_timeLimit;

	// -----------------------------
	// ----- CONSTRUCTOR -----------
	// -----------------------------

	/**
	 * Creates an object of the class Solution for the problem data loaded in Instance
	 * @param instance the instance of the problem
	 * @param timeLimit the time limit in seconds
	 */
	public TSPSolver(Instance instance, long timeLimit) {
		m_instance = instance;
		m_solution = new Solution(m_instance);
		m_timeLimit = timeLimit;
		
	}

	// -----------------------------
	// ----- METHODS ---------------
	// -----------------------------

	/**
	 * **TODO** Modify this method to solve the problem.
	 * 
	 * Do not print text on the standard output (eg. using `System.out.print()` or `System.out.println()`).
	 * This output is dedicated to the result analyzer that will be used to evaluate your code on multiple instances.
	 * 
	 * You can print using the error output (`System.err.print()` or `System.err.println()`).
	 * 
	 * When your algorithm terminates, make sure the attribute #m_solution in this class points to the solution you want to return.
	 * 
	 * You have to make sure that your algorithm does not take more time than the time limit #m_timeLimit.
	 * 
	 * @throws Exception may return some error, in particular if some vertices index are wrong.
	 */
	public void solve() throws Exception
	{
		m_solution.print(System.err);
		int nbite=0;
		// Example of a time loop
		long startTime = System.currentTimeMillis();
		long spentTime = 0;
		Instance graph=this.m_instance;
		List<Ant> bestiteration = new ArrayList<Ant>();
		do
		{
			
			Ant bestfourmis = new Ant(graph,0);
			bestfourmis.parcour();
			nbite++;
			List<Ant> fourmis = new ArrayList<Ant>();
			for (int i=0 ;i<400;i++) {
				fourmis.add(new Ant(graph,(int) (Math.random()*graph.getNbCities())));
				fourmis.get(i).parcour();
				if (bestfourmis.getPathlength() > fourmis.get(i).getPathlength()) bestfourmis = fourmis.get(i);
			}
			graph.evaporate();
			for (int i=0 ;i<400;i++) {
				fourmis.get(i).updateTrace();
			}
			bestfourmis.setPath(twoOpt(graph, bestfourmis.getPath()));
			if (bestiteration.isEmpty()) bestiteration.add(bestfourmis);
			bestiteration.add(0,bestfourmis);
			for (int eli =0 ; eli<10 ;eli++) {
			if (eli<bestiteration.size()) {
				bestiteration.get(eli).setPath(twoOpt(graph,bestiteration.get(eli).getPath()));
				bestiteration.get(eli).updateTraceElite();
				
			}
			}
			System.out.println(bestfourmis.getPathlength());
			spentTime = System.currentTimeMillis() - startTime;
			System.out.println("temps ité :"+spentTime/1000 +"s");
			
			 
			
			
		
			
			// TODO
			// Code a loop base on time here
			spentTime = System.currentTimeMillis() - startTime;
			
		}while(spentTime < (m_timeLimit * 1000 - 100) );
			Collections.sort(bestiteration);
			Ant ant= bestiteration.get(0);
		System.out.println(ant.getPath().toString());
		System.out.println(ant.getPathlength());
		System.out.println(twoOpt(graph,ant.getPath()));
		System.out.println(ant.fullparcour());
		List<Integer> finalpath = twoOpt(graph,ant.getPath());
		for (int k=0 ; k< ant.getPath().size() ; k++ ) {
			m_solution.setCityPosition(finalpath.get(k), k);
		}
		m_solution.setCityPosition(finalpath.get(0), (int) ant.getPath().size());
	}

	public List<Integer> twoOpt(Instance graph,List<Integer> path) throws Exception{
		int n = path.size();
		boolean amelioration = true;
		while (amelioration) {
			amelioration=false;
		for (int i=0 ;i<n-1;i++) {
			for (int j=0 ;j<n-1 ;j++) {
				if (i!=j) {
					int min = Math.min(i, j);
					int max = Math.max(i, j);
					if (graph.getDistances(path.get(min), path.get(min+1)) + graph.getDistances(path.get(max), path.get(max+1))
					>graph.getDistances(path.get(min), path.get(max)) + graph.getDistances(path.get(min+1), path.get(max+1)) ) {
						int xj = path.get(max);
						int xip1 = path.get(min+1);
						path.set(min+1,xj);
						path.set(max,xip1);
						for (int p = min+2 ; p<(max-min-2)/2 ; p++) {
							int stockage = path.get(p);
							path.set(p, path.get(max-(p-min-1)));
							path.set(max-(p-min-1), stockage);
							amelioration = true;
						}
					}
				}
			}
		}
		}
		return path;
	}
	// -----------------------------
	// ----- GETTERS / SETTERS -----
	// -----------------------------

	/** @return the problem Solution */
	public Solution getSolution() {
		return m_solution;
	}

	/** @return problem data */
	public Instance getInstance() {
		return m_instance;
	}

	/** @return Time given to solve the problem */
	public long getTimeLimit() {
		return m_timeLimit;
	}

	/**
	 * Initializes the problem solution with a new Solution object (the old one will be deleted).
	 * @param solution : new solution
	 */
	public void setSolution(Solution solution) {
		this.m_solution = solution;
	}

	/**
	 * Sets the problem data
	 * @param instance the Instance object which contains the data.
	 */
	public void setInstance(Instance instance) {
		this.m_instance = instance;
	}

	/**
	 * Sets the time limit (in seconds).
	 * @param time time given to solve the problem
	 */
	public void setTimeLimit(long time) {
		this.m_timeLimit = time;
	}

}
