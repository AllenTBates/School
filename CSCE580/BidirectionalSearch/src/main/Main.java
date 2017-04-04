package main;

import java.util.LinkedList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		// TODO: Generate Graph
		// TODO: call MM
	}

	private static int MM (final GraphNode start, final GraphNode goal) {
		List<GraphNode> openF = new LinkedList<GraphNode>();
		List<GraphNode> openB = new LinkedList<GraphNode>();
		int U = Integer.MAX_VALUE;

		openF.add(start);
		openB.add(goal);

		while (!openF.isEmpty() && !openB.isEmpty()) {
			int prminF = prmin(openF);
			int prminB = prmin(openB);
			int C = getC(prminF, prminB);
			
			if (U <= C)
				return U;
			
			if (C == prminF) {
				// Expand in the forward direction
			} else /* C == prminB */ {
				// Expand in the backward direction
			}
		}

		return Integer.MAX_VALUE;
	}

	// Gives the priority of the given node
	// pr(n) = max(f(n),2*g(n))
	private static int priority(final GraphNode n) {
		int f = f(n);
		int gtimes2 = n.gValue * 2;
		return (f > gtimes2) ? f : gtimes2;
	}
	
	// Gives the fvalue of the given node
	// f(n) = h(n) + g(n)
	private static int f(final GraphNode n) {
		return n.hueristic + n.gValue;
	}
	
	// Gives the minimum priority within the given open list
	private static int prmin(final List<GraphNode> openList) {
		int min = Integer.MAX_VALUE;

		for (final GraphNode n : openList) {
			int currentPriority = priority(n);
			if (currentPriority < min)
				min = currentPriority;
		}

		return min;
	}

	// Description: Gives the C value of the current iteration
	// C = min(prminF,prminB)
	private static int getC(final int prminF, final int prminB) {
		return (prminF < prminB) ? prminF : prminB;
	}

	private static class GraphNode {
		public final List<GraphNode> children = new LinkedList<GraphNode>();
		public int cost;
		public int hueristic;
		public int gValue;
		
		public void addChildren(final GraphNode... children) {
			for (final GraphNode c : children)
				this.children.add(c);
		}
		
		public void addChildren(final List<GraphNode> children) {
			addChildren(children.toArray(new GraphNode[children.size()]));
		}
	}
}
