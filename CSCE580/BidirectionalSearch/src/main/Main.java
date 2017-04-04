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

		}

		return Integer.MAX_VALUE;
	}

	// Gives the priority of the given node
	private static int priorityOfNodeN(final GraphNode n) {
		// TODO: returns max(f(n),2*g(n))
		return 0;
	}

	// FIXME: This should maybe just return a node for simplicity
	private static int minPriority(final List<GraphNode> nodes) {
		int min = Integer.MIN_VALUE;

		for (final GraphNode n : nodes)
			if (n.cost > min)
				min = n.cost;

		return min;
	}

	private static GraphNode C() {
		return null;
	}

	private static class GraphNode {
		public final List<GraphNode> children = new LinkedList<GraphNode>();
		public final int cost;

		public GraphNode(final int cost) {
			this.cost = cost;
		}

		public GraphNode(final int cost, final GraphNode... children) {
			this.cost = cost;
			for (final GraphNode c : children)
				this.children.add(c);
		}
	}
}
