package main;

import java.util.HashSet;
import java.util.Set;

public class AStar {
	public int nodesExpanded = 0;

	public AStar() {}

	public int run(final AStarNode start, final AStarNode goal) {
		final Set<AStarNode> Open = new HashSet<AStarNode>();
		final Set<AStarNode> Closed = new HashSet<AStarNode>();
		Open.add(start);

		while (!Open.isEmpty()) {
			final AStarNode current = minfNode(Open);

			if (current.equals(goal))
				return getEndPathCost(goal);

			Open.remove(current);
			Closed.add(current);

			nodesExpanded++;

			for (final AStarNode neighbor : current.getChildren()) {
				if (Closed.contains(neighbor))
					continue;

				int tentativeGValue = current.gValue + current.getCostTo(neighbor);

				if (!Open.contains(neighbor))
					Open.add(neighbor);
				else if (tentativeGValue >= neighbor.gValue)
					continue;

				neighbor.successor = current;
				neighbor.gValue = tentativeGValue;
			}
		}

		return -1;
	}

	// Assumes the forward vars are used
	private int f(final AStarNode n) {
		return n.hueristic + n.gValue;
	}

	private AStarNode minfNode(final Set<AStarNode> open) {
		AStarNode min = null;

		for(final AStarNode n : open)
			if (min == null)
				min = n;
			else if (f(n) < f(min))
				min = n;

		return min;
	}

	// Follows the successors backward to get the final cost
	private int getEndPathCost(final AStarNode goal) {
		int cost = 0;
		AStarNode iter = goal;

		while (iter != null) {
			cost += iter.getCostTo(iter.successor);
			iter = iter.successor;
		}

		return cost;
	}

	public static class AStarNode {
		public final Set<AStarEdge> outgoing = new HashSet<AStarEdge>();
		public int hueristic;
		public int gValue;
		public AStarNode successor;

		public AStarNode() {}

		public AStarNode(final int h) {
			hueristic = h;
		}

		public void addOutgoingEdges(final AStarEdge... outgoing) {
			for (final AStarEdge c : outgoing)
				this.outgoing.add(c);
		}

		public void addOutgoingEdges(final Set<AStarEdge> outgoing) {
			addOutgoingEdges(outgoing.toArray(new AStarEdge[outgoing.size()]));
		}

		public Set<AStarNode> getChildren() {
			final Set<AStarNode> children = new HashSet<AStarNode>();

			for (final AStarEdge e : outgoing)
				children.add(e.destination);

			return children;
		}

		public int getCostTo(final AStarNode destination) {
			for (final AStarEdge e : outgoing)
				if (e.destination.equals(destination))
					return e.cost;

			return -1;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof AStarNode))
				return false;

			final AStarNode comp = (AStarNode)obj;

			if (this.hueristic != comp.hueristic)
				return false;
			if (this.gValue != comp.gValue)
				return false;
			if (!this.outgoing.equals(comp.outgoing))
				return false;

			return true;
		}
	}

	// A directed edge with an associated cost
	public static class AStarEdge {
		public int cost;
		public AStarNode destination;

		public AStarEdge() {}

		public AStarEdge(final int cost, final AStarNode destination) {
			this.cost = cost;
			this.destination = destination;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof AStarEdge))
				return false;

			final AStarEdge comp = (AStarEdge)obj;

			if (this.cost != comp.cost)
				return false;
			if (!this.destination.equals(comp.destination))
				return false;

			return true;
		}
	}
}
