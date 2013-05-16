package gna;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Tours that are constructed incrementally by inserting the elements in the
 * given world one by one.
 */
public abstract class IncrementallyConstructedTour extends Tour {
	
	private final ArrayList<Point> currentTour = new ArrayList<Point>();

	public IncrementallyConstructedTour(World world) {
		super(world);
		/**
		 * Note: we expect the elements to be inserted in the same order they
		 * are added to the world.
		 */
		for (Point point : world.getPoints()) {
			insert(point);
		}
	}

	protected abstract void insert(Point point);
	
	protected void addToTour(int index, Point p){
		this.getVisitSequence().add(index, p);				
	}
	
	protected void addToTour(Point p){
		this.getVisitSequence().add(p);
	}
	
	@Override
	public double getTotalDistance() {

		double totalDistance = 0;
		
		if(this.getWorld().getNbPoints() <= 1)
			return totalDistance;

		if (this.getVisitSequence().isEmpty())
			return totalDistance;

		Iterator<Point> itr = this.getVisitSequence().iterator();

		Point previous = itr.next();

		while (itr.hasNext()) {

			Point current = itr.next();
			totalDistance += previous.distanceTo(current);
			previous = current;

		}

		if (this.getVisitSequence().size() > 1)
			totalDistance += this.getVisitSequence().get(0).distanceTo(this.getVisitSequence().get(this.getVisitSequence().size() - 1));

		return totalDistance;
	}
	
	@Override
	public List<Point> getVisitSequence() {
		return currentTour;
	}

}
