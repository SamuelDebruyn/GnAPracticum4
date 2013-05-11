package gna;

import java.util.TreeMap;

/**
 * A tour constructed using the nearest neighbor heuristic.
 */
public class NearestNeighborTour extends IncrementallyConstructedTour {

	public NearestNeighborTour(World world) {
		super(world);
	}

	@Override
	public void insert(Point point) {
		
		TreeMap<Integer, Double> indexesWithDistance = new TreeMap<Integer, Double>();
		//TODO: comparator by value
		
		for(int i = 0; i < this.getVisitSequence().size(); i++){
			indexesWithDistance.put(new Integer(i), new Double(this.getVisitSequence().get(i).distanceTo(point)));
		}
		
		this.addToTour(indexesWithDistance.firstKey().intValue(), point);
		
	}

}
