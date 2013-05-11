package gna;

/**
 * A tour constructed using the nearest neighbor heuristic.
 */
public class NearestNeighborTour extends IncrementallyConstructedTour {

	public NearestNeighborTour(World world) {
		super(world);
	}

	@Override
	public void insert(Point point) {
		throw new RuntimeException("not implemented");
	}

}
