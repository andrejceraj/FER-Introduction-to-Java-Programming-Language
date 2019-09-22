package coloring.demo;

import java.util.Arrays;

import coloring.algorithms.Coloring;
import coloring.algorithms.Pixel;
import coloring.algorithms.SubspaceExploreUtil;
import marcupic.opjj.statespace.coloring.FillAlgorithm;
import marcupic.opjj.statespace.coloring.FillApp;
import marcupic.opjj.statespace.coloring.Picture;

/**
 * Demonstration of {@link FillApp} using custom implemented exploring
 * algorithms.
 * 
 * @author Andrej Ceraj
 *
 */
public class Bojanje2 {
	/**
	 * Method starts when the program is run.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		FillApp.run(FillApp.OWL, Arrays.asList(bfs, dfs, bfsv, dfsv));
	}

	/**
	 * Breadth-First Search (BFS) algorithm that does not keep track of visited
	 * states
	 */
	private static final FillAlgorithm bfs = new FillAlgorithm() {
		@Override
		public String getAlgorithmTitle() {
			return "Moj bfs!";
		}

		@Override
		public void fill(int x, int y, int color, Picture picture) {
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.bfs(col, col, col, col);
		}

	};

	/**
	 * Depth-First Search (DFS) algorithm that does not keep track of visited states
	 */
	private static final FillAlgorithm dfs = new FillAlgorithm() {
		@Override
		public String getAlgorithmTitle() {
			return "Moj dfs!";
		}

		@Override
		public void fill(int x, int y, int color, Picture picture) {
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.dfs(col, col, col, col);
		}

	};

	/**
	 * Breadth-First Search (BFS) algorithm that keeps track of visited states
	 */
	private static final FillAlgorithm bfsv = new FillAlgorithm() {
		@Override
		public String getAlgorithmTitle() {
			return "Moj bfsv!";
		}

		@Override
		public void fill(int x, int y, int color, Picture picture) {
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.bfsv(col, col, col, col);
		}

	};

	/**
	 * Depth-First Search (DFS) algorithm that keeps track of visited states
	 */
	private static final FillAlgorithm dfsv = new FillAlgorithm() {
		@Override
		public String getAlgorithmTitle() {
			return "Moj dfsv!";
		}

		@Override
		public void fill(int x, int y, int color, Picture picture) {
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.dfsv(col, col, col, col);
		}

	};
}
