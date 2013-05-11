package gna;

/**
 * A tour constructed using the smallest increase heuristic.
 */
public class SmallestIncreaseTour extends IncrementallyConstructedTour {

	public SmallestIncreaseTour(World world) {
		super(world);
	}

	@Override
	public void insert(Point point) {
		throw new RuntimeException("not implemented");
	}
}
