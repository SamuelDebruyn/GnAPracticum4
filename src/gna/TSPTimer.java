package gna;

import java.util.ArrayList;
import java.util.List;

/**
 * Time the three heuristics by generating random instances of size N.
 */
public class TSPTimer {

	public static void main(String[] args) {
		int SIZE = 600;
		int N = Integer.parseInt(args[0]);
		long start, stop;
		double elapsed;

		// generate data
		List<Point> points = new ArrayList<Point>(N);
		for (int i = 0; i < N; i++) {
			double x = Math.random() * SIZE;
			double y = Math.random() * SIZE;
			points.add(new Point(x, y));
		}
		World world = new World(SIZE, SIZE, points);

		String out;

		// run nearest insertion heuristic
		start = System.currentTimeMillis();
		Tour tour1 = new NearestNeighborTour(world);
		stop = System.currentTimeMillis();
		out = "Tour distance = " + tour1.getTotalDistance();
		System.out.println(out.replace(".", ","));
		elapsed = (stop - start) / 1000.0;
		out = "Nearest insertion (s):  " + elapsed;
		System.out.println(out.replace(".", ","));

		// run smallest insertion heuristic
		start = System.currentTimeMillis();
		Tour tour2 = new SmallestIncreaseTour(world);
		stop = System.currentTimeMillis();
		out = "Tour distance = " + tour2.getTotalDistance();
		System.out.println(out.replace(".", ","));
		elapsed = (stop - start) / 1000.0;
		out = "Smallest increase (s):  " + elapsed;
		System.out.println(out.replace(".", ","));

		// run MST insertion heuristic
		start = System.currentTimeMillis();
		Tour tour3 = new MinimalSpanningTreeTour(world);
		stop = System.currentTimeMillis();
		out = "Tour distance = " + tour3.getTotalDistance();
		System.out.println(out.replace(".", ","));
		elapsed = (stop - start) / 1000.0;
		out = "MST insertion (s):  " + elapsed;
		System.out.println(out.replace(".", ","));
	}
}
