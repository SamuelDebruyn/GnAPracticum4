package gna;

import java.util.ArrayList;
import java.util.Comparator;
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
			return Double.compare(this.getWeight(), other.getWeight());
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
	
	public class MSTEdgeComparator implements Comparator<MSTEdge>{

		@Override
		public int compare(MSTEdge arg0, MSTEdge arg1) {
			int result = Double.compare(arg0.getWeight(), arg1.getWeight());
			return result;
		}
		
	}

	private final Point root;
	private final ArrayList<MSTEdge> MST = new ArrayList<MSTEdge>();

	public MinimalSpanningTreeTour(World world) {
		super(world);
		
		//TODO: allerlei shizzle als er maar 1 punt wordt gegeven
		//TODO: fout

		if (this.getWorld().getNbPoints() < 1) {
			this.root = null;
			return;
		}

		HashSet<Point> openPoints = new HashSet<Point>();
		openPoints.addAll(world.getPoints());
		this.root = world.getPoints().get(0);
		
		TreeSet<MSTEdge> openEdges = new TreeSet<MSTEdge>(new MSTEdgeComparator());
		for(Point start: world.getPoints()){
			for(Point end: world.getPoints()){
				if(!start.equals(end)){
					openEdges.add(new MSTEdge(start, end));
				}
			}
		}
		
		Iterator<MSTEdge> itr = openEdges.iterator();
		MSTEdge startEdge = null;
		while(itr.hasNext()){
			startEdge = itr.next();
			if(startEdge.p1.equals(this.getMSTRoot())){				
				break;
			}else if(startEdge.p2.equals(this.getMSTRoot())){
				break;
			}
		}
		while(openEdges.remove(startEdge)){
			//NOP			
		}
		this.getMST().add(startEdge);
		
		HashSet<Point> closedPoints = new HashSet<Point>();
		closedPoints.add(startEdge.p1);
		closedPoints.add(startEdge.p2);
		openPoints.remove(startEdge.p1);
		openPoints.remove(startEdge.p2);
		
		while(!openPoints.isEmpty()){
			
			Iterator<MSTEdge> edgeItr = openEdges.iterator();
			MSTEdge found = null;
			while(edgeItr.hasNext()){
				found = edgeItr.next();
				if(!(closedPoints.contains(found.p1) || closedPoints.contains(found.p2))){
					continue;
				}else if(closedPoints.contains(found.p1) && closedPoints.contains(found.p2)){
					continue;
				}else{
					break;
				}
			}
			this.getMST().add(found);
			closedPoints.add(found.p1);
			closedPoints.add(found.p2);
			openPoints.remove(found.p1);
			openPoints.remove(found.p2);
			while(openEdges.remove(found)){
				//NOP
			}
		}
		
	}

	@Override
	public double getTotalDistance() {
		//TODO
		return 0;
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
		//TODO
		ArrayList<Point> result = new ArrayList<Point>();
		return result;
	}
}
