package gna;

/**
 * A tour constructed using the smallest increase heuristic.
 */
public class SmallestIncreaseTour extends IncrementallyConstructedTour {

	public SmallestIncreaseTour(World world) {
		super(world);
	}
	
	private double newPossibleTotalDistance(int index, Point point, double cachedTotalDistance){
		int previousIndex = this.previousIndex(index);
		
		if(index >= this.getVisitSequence().size())
			index = 0;
		
		double distanceToPrevious = this.getVisitSequence().get(previousIndex).distanceTo(point);
		double distanceToNext = this.getVisitSequence().get(index).distanceTo(point);
		double distanceRemoved = this.getVisitSequence().get(previousIndex).distanceTo(this.getVisitSequence().get(index));
		
		return cachedTotalDistance + distanceToPrevious + distanceToNext - distanceRemoved;
	}
	
	private int previousIndex(int index) {
		if (index == 0 || index > this.getVisitSequence().size())
			return this.getVisitSequence().size() - 1;
		return index - 1;
	}

	@Override
	public void insert(Point point) {
		
		if(this.getVisitSequence().size() <= 1){
			this.addToTour(point);
			return;
		}

		double currentTotalDistance = this.getTotalDistance();
		
		int bestIndex = 0;
		double bestTotalDistance = this.newPossibleTotalDistance(0, point, currentTotalDistance);
		
		for(int i = 0; i <= this.getVisitSequence().size(); i++){
			
			double currentPossibleTotalDistance = this.newPossibleTotalDistance(i, point, currentTotalDistance);
			
			if(currentPossibleTotalDistance < bestTotalDistance){
				bestIndex = i;
				bestTotalDistance = currentPossibleTotalDistance;
			}
		}
		
		this.addToTour(bestIndex, point);
		
	}
	
}
