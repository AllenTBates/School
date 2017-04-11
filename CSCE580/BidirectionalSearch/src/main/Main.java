package main;

import java.util.HashSet;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
		// TODO: Generate Graph
		// TODO: call MM
	}

	private static int MM (final Node start, final Node goal) {
		Set<Node> OpenF = new HashSet<Node>();
		Set<Node> OpenB = new HashSet<Node>();
		Set<Node> ClosedF = new HashSet<Node>();
		Set<Node> ClosedB = new HashSet<Node>();
		int U = Integer.MAX_VALUE;

		OpenF.add(start);
		OpenB.add(goal);

		while (!OpenF.isEmpty() && !OpenB.isEmpty()) {
			int prminF = prminF(OpenF);
			int prminB = prminB(OpenB);
			int C = getC(prminF, prminB);
			
			if (U <= C)
				return U;
			
			if (C == prminF) {
				// Expand in the forward direction
				final Node n = moveN_F(prminF, OpenF, ClosedF);
				
				for (final Node c : n.getChildren()) {
					if (union(OpenF, ClosedF).contains(c) &&
							c.gValueF <= (n.gValueF + n.getCostTo(c)))
						continue;
					
					if (union(OpenF, ClosedF).contains(c)) {
						OpenF.remove(c);
						ClosedF.remove(c);
					}
					
					c.gValueF = n.gValueF + n.getCostTo(c);
					OpenF.add(c);
					
					if (OpenB.contains(c))
						U = getU(U, c.gValueF + c.gValueB);
				}
			} else if (C == prminB) {
				// Expand in the backward direction
				final Node n = moveN_B(prminB, OpenB, ClosedB);
				
				for (final Node c : n.getChildren()) {
					if (union(OpenB, ClosedB).contains(c) &&
							c.gValueB <= (n.gValueB + n.getCostTo(c)))
						continue;
					
					if (union(OpenB, ClosedB).contains(c)) {
						OpenB.remove(c);
						ClosedB.remove(c);
					}
					
					c.gValueB = n.gValueB + n.getCostTo(c);
					OpenB.add(c);
					
					if (OpenF.contains(c))
						U = getU(U, c.gValueB + c.gValueF);
				}
			} else {
				System.err.println("Something has gone horribly wrong");
				System.exit(-1);
			}
		}

		return Integer.MAX_VALUE;
	}

	// Gives the priority of the given node in the forward direction
	// pr(n) = max(f(n),2*g(n))
	private static int prF(final Node n) {
		int f = fF(n);
		int gtimes2 = n.gValueF * 2;
		return (f > gtimes2) ? f : gtimes2;
	}
	
	// Gives the priority of the given node in the backward direction
	// pr(n) = max(f(n),2*g(n))
	private static int prB(final Node n) {
		int f = fB(n);
		int gtimes2 = n.gValueB * 2;
		return (f > gtimes2) ? f : gtimes2;
	}
	
	// Gives the fvalue of the given node in the forward direction
	// f(n) = h(n) + g(n)
	private static int fF(final Node n) {
		return n.hueristicF + n.gValueF;
	}
	
	// Gives the fvalue of the given node in the backward direction
	// f(n) = h(n) + g(n)
	private static int fB(final Node n) {
		return n.hueristicB + n.gValueB;
	}
	
	// Gives the minimum priority within the given open Set
	private static int prminF(final Set<Node> openSet) {
		int min = Integer.MAX_VALUE;

		for (final Node n : openSet) {
			int currentPriority = prF(n);
			if (currentPriority < min)
				min = currentPriority;
		}

		return min;
	}
	
	// Gives the minimum priority within the given open Set
	private static int prminB(final Set<Node> openSet) {
		int min = Integer.MAX_VALUE;

		for (final Node n : openSet) {
			int currentPriority = prB(n);
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
	
	private static int getU(final int prevU, final int sum) {
		return (prevU < sum) ? prevU : sum;
	}
	
	// Gives the node n such that:
	//	- n is in OpenF
	//	- priority(n) == prminF
	private static Node moveN_F(final int prmin, final Set<Node> openSet, final Set<Node> closeSet) {
		for (final Node o : openSet)
			if (prF(o) == prmin) {
				openSet.remove(o);
				closeSet.add(o);
				return o;
			}
		
		return null;
	}
	
	// Gives the node n such that:
	//	- n is in OpenF
	//	- priority(n) == prminF
	private static Node moveN_B(final int prmin, final Set<Node> openSet, final Set<Node> closeSet) {
		for (final Node o : openSet)
			if (prB(o) == prmin) {
				openSet.remove(o);
				closeSet.add(o);
				return o;
			}
		
		return null;
	}
	
	private static Set<Node> union(final Set<Node> Set1, final Set<Node> Set2) {
		final Set<Node> union = new HashSet<Node>();
		
		for (final Node n : Set1)
			union.add(n);
			
		for (final Node n : Set2)
			if (!union.contains(n))
				union.add(n);
		
		return union;
	}

	private static class Node {
		public final Set<Edge> outgoing = new HashSet<Edge>();
		public int hueristicF;
		public int hueristicB;
		public int gValueF;
		public int gValueB;
		
		public void addOutgoingEdges(final Edge... outgoing) {
			for (final Edge c : outgoing)
				this.outgoing.add(c);
		}
		
		public void addOutgoingEdges(final Set<Edge> outgoing) {
			addOutgoingEdges(outgoing.toArray(new Edge[outgoing.size()]));
		}
		
		public Set<Node> getChildren() {
			final Set<Node> children = new HashSet<Node>();
			
			for (final Edge e : outgoing)
				children.add(e.destination);
			
			return children;
		}
		
		public int getCostTo(final Node destination) {
			for (final Edge e : outgoing)
				if (e.destination.equals(destination))
					return e.cost;
			
			return -1;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof Node))
				return false;
			
			final Node comp = (Node)obj;
		
			if (this.hueristicF != comp.hueristicF)
				return false;
			if (this.gValueF != comp.gValueF)
				return false;
			if (this.hueristicB != comp.hueristicB)
				return false;
			if (this.gValueB != comp.gValueB)
				return false;
			if (!this.outgoing.equals(comp.outgoing))
				return false;
			
			return true;
		}
	}
	
	private static class Edge {
		public int cost;
		public Node destination;
		
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof Edge))
				return false;
			
			final Edge comp = (Edge)obj;
			
			if (this.cost != comp.cost)
				return false;
			if (!this.destination.equals(comp.destination))
				return false;
			
			return true;
		}
	}
}
