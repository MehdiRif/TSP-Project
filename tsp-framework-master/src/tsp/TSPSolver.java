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
		genome secondbest = new genome(this.m_instance);
		genome bestgen = new genome(this.m_instance);
		bestgen.twoOpt();
		long startTime = System.currentTimeMillis();
		long spentTime = 0;
		genome secondbestmute = new genome(this.m_instance,new ArrayList<Integer>(secondbest.getPath()));
		genome bestgenmute = new genome(this.m_instance,new ArrayList<Integer>(bestgen.getPath()));
		
		do
		{
			if (Math.random()>0.7) secondbestmute.shuffle();
			if (Math.random()>0.9) bestgenmute.shuffle();
			generation nextgen = new generation(bestgenmute,secondbestmute);
			generation nextgen2 = new generation(secondbestmute,bestgenmute);
			nextgen.getGen().addAll(nextgen2.getGen());
			Collections.sort(nextgen.getGen());
			nextgen.getGen().get(0).twoOpt();
			nextgen.getGen().get(1).twoOpt();
			bestgen =(bestgen.getObjective()<nextgen.getGen().get(0).getObjective()) ? bestgen : nextgen.getGen().get(0);
			bestgenmute = new genome(this.m_instance,new ArrayList<Integer>(bestgen.getPath()));
			secondbest=(secondbest.getObjective()<nextgen.getGen().get(1).getObjective()) ? secondbest : nextgen.getGen().get(1);
			secondbestmute = new genome(this.m_instance,new ArrayList<Integer>(secondbest.getPath()));
			System.out.println("new longueur" +bestgen.calculpath()) ;
			// TODO
			// Code a loop base on time here
			spentTime = System.currentTimeMillis() - startTime;
			System.out.println("fin d'une gen");
		}while(spentTime < (m_timeLimit * 1000 - 100) ); 
			
		List<Integer> finalpath = bestgen.getPath();
		//finalpath = twoOpt(this.m_instance,finalpath);
		for (int k=0 ; k< finalpath.size();k++) {
			this.m_solution.setCityPosition(finalpath.get(k),k);
		}
		this.m_solution.setCityPosition(0, finalpath.size());
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
