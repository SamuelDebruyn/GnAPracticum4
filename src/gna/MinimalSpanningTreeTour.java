package gna;

import java.util.ArrayList;
import java.util.List;

/**
 * A tour constructed using the minimal spanning tree heuristic.
 */
public class MinimalSpanningTreeTour extends Tour {

	public class MSTEdge {

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
	private ArrayList<Point> MSTTour = null;

	public MinimalSpanningTreeTour(World world) {
		super(world);

		if (this.getWorld().getNbPoints() < 1) {
			this.root = null;
			return;
		}
		
		ArrayList<Point> openPoints = new ArrayList<Point>();
		ArrayList<Point> closedPoints = new ArrayList<Point>();
		
		openPoints.addAll(this.getWorld().getPoints());
		
		this.root = openPoints.remove(0);
		closedPoints.add(this.getMSTRoot());
		
		while(!openPoints.isEmpty()){
			
			double weight = Double.MAX_VALUE;
			MSTEdge edge = null;
			
			for(Point start: closedPoints){
				
				for(Point end: openPoints){
					
					double cWeight = start.distanceTo(end);
					
					if(cWeight < weight){
						weight = cWeight;
						edge = new MSTEdge(start, end);
					}
					
				}
			}
			
			openPoints.remove(edge.p2);
			closedPoints.add(edge.p2);
			this.getMST().add(edge);
			
		}
		
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
	
	private void setMSTTour(ArrayList<Point> MSTTour) {
		this.MSTTour = MSTTour;
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
		
		if(this.MSTTour == null)
			this.setMSTTour(this.constructSequence(this.getMSTRoot(), new ArrayList<Point>()));
		
		return this.MSTTour;
		
	}
}
