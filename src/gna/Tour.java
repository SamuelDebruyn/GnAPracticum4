package gna;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class for representing tours.
 * 
 * Note: You can extend this class with additional fields/methods as you see
 * fit. Do not modify/overload the signatures of the existing methods.
 */
public abstract class Tour {
	
	private final ArrayList<Point> currentTour = new ArrayList<Point>();
	private final World world;

	public Tour(World world) {
		this.world = world;
	}

	public World getWorld() {
		return world;
	}

	/**
	 * Return the total distance of this tour. The total distance includes the
	 * distance between the last and first point in this tour (i.e. the distance
	 * required to return to the starting position).
	 * 
	 * If <code>getWorld().getNbPoints()</code> is less than or equal to one,
	 * then this method returns 0.
	 */
	public double getTotalDistance() {
		// TODO: vragen of dit toegelaten is

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

	/**
	 * Return the list of points in the order they should be visited.
	 * 
	 * The result of this method is never <code>null</code>. Each point in
	 * <code>getWorld().getPoints()</code> appears exactly once in result.
	 */
	public List<Point> getVisitSequence() {
		// TODO: vragen of dit toegelaten is
		return currentTour;
	}

	@Override
	public String toString() {
		List<Point> sequence = getVisitSequence();
		if (sequence.isEmpty()) {
			return "empty tour";
		} else {
			StringBuilder builder = new StringBuilder();
			for (Point p : sequence) {
				builder.append(p);
				builder.append('\n');
			}
			return builder.toString();
		}
	}

	protected int previousIndex(int index) {
		if (index == 0 || index > this.getVisitSequence().size())
			return this.getVisitSequence().size() - 1;
		return index - 1;
	}

}
