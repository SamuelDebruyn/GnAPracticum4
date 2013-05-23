package gna;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 * A tour constructed using the minimal spanning tree heuristic.
 */
public class MinimalSpanningTreeTour extends Tour {

	public class MSTEdge implements Comparable<MSTEdge> {

		public final Point p1, p2;
		private final double weight;

		public MSTEdge(Point p1, Point p2) {
			this.p1 = p1;
			this.p2 = p2;
			this.weight = p1.distanceTo(p2);
		}

		public double getWeight() {
			return weight;
		}

		@Override
		public int compareTo(MSTEdge other) {
			int result = Double.compare(this.getWeight(), other.getWeight());
			if(result == 0)
				result = Double.compare(this.p1.getX(), other.p1.getX());
			if(result == 0)
				result = Double.compare(this.p1.getY(), other.p1.getY());
			if(result == 0)
				result = Double.compare(this.p2.getX(), other.p2.getX());
			if(result == 0)
				result = Double.compare(this.p2.getY(), other.p2.getY());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			
			if(this == obj)
				return true;

			if(obj == null)
				return false;

			if(this.getClass() != obj.getClass())
				return false;

			MSTEdge toCompare = (MSTEdge) obj;
			
			if(this.getWeight() != toCompare.getWeight())
				return false;

			if(this.p1 == toCompare.p1 && this.p2 == toCompare.p2)
				return true;
			
			if(this.p1 == toCompare.p2 && this.p2 == toCompare.p1)
				return true;
			
			return false;
		}

		@Override
		public String toString() {
			return "[" + String.valueOf(this.p1) + " - " + String.valueOf(this.p2) +"] - " + String.valueOf(this.getWeight());
		}
	}

	private final Point root;
	private final ArrayList<MSTEdge> MST = new ArrayList<MSTEdge>();
	private final ArrayList<Point> MSTTour;

	public MinimalSpanningTreeTour(World world) {
		super(world);

		if (this.getWorld().getNbPoints() < 1) {
			this.root = null;
			this.MSTTour = new ArrayList<Point>();
			return;
		}
		
		
		/*
		 * Oud algoritme: back-up
		TreeSet<MSTEdge> openEdges = new TreeSet<MSTEdge>();
		for(Point start: world.getPoints()){
			for(Point end: world.getPoints()){
				if(!start.equals(end)){
					openEdges.add(new MSTEdge(start, end));
				}
			}
		}
		
		this.root = world.getPoints().get(0);

		HashSet<Point> openPoints = new HashSet<Point>();
		openPoints.addAll(world.getPoints());
		openPoints.remove(this.getMSTRoot());
		
		HashSet<Point> closedPoints = new HashSet<Point>();
		closedPoints.add(this.getMSTRoot());
		
		while(!openPoints.isEmpty()){
			
			Iterator<MSTEdge> edgeItr = openEdges.iterator();
			MSTEdge found = null;
			while(edgeItr.hasNext()){
				found = edgeItr.next();
				if(closedPoints.contains(found.p1) && (!closedPoints.contains(found.p2))){
					break;
				}
			}
			this.getMST().add(found);
			closedPoints.add(found.p2);
			openPoints.remove(found.p2);
			while(openEdges.remove(found)){
				//NOP
			}
		}
		*/
		
		this.MSTTour = this.constructSequence(this.getMSTRoot(), new ArrayList<Point>());
		
	}

	/**
	 * Return the root of the MST used to construct the visit sequence.
	 * 
	 * This method returns null if and only if
	 * <code>getWorld().getPoints()</code> is empty.
	 */
	public Point getMSTRoot() {
		return this.root;
	}

	/**
	 * Return the edges on the MST used to construct the visit sequence.
	 * 
	 * The result of this method is never null.
	 */
	public List<MSTEdge> getMST() {
		return this.MST;
	}

	private ArrayList<Point> constructSequence(Point insert, ArrayList<Point> previous){
		
		previous.add(insert);
		
		for(MSTEdge edge: this.getMST()){
			
			if(edge.p1.equals(insert) && (!previous.contains(edge.p2))){
				previous = constructSequence(edge.p2, previous);
			}
			
		}
		
		return previous;
		
	}
	
	@Override
	/**
	 * The visit sequence is a PRE-ORDER traversal of the MST
	 * starting at a root (e.g. the first point of the world).
	 *
	 * Traverse the children of each node in increasing order of distance.
	 * 
	 * Return the empty list if world is empty.
	 */

	public List<Point> getVisitSequence() {
		
		return this.MSTTour;
		
	}
}
