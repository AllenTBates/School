package main;

import java.util.HashSet;
import java.util.Set;

public class MM {
	
	int nodesExpanded = 0;
	
	public MM() {}
	
	public int run(final MMNode start, final MMNode goal) {
		final Set<MMNode> OpenF = new HashSet<MMNode>();
		final Set<MMNode> OpenB = new HashSet<MMNode>();
		final Set<MMNode> ClosedF = new HashSet<MMNode>();
		final Set<MMNode> ClosedB = new HashSet<MMNode>();
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
				final MMNode n = moveN_F(prminF, OpenF, ClosedF);
				
				nodesExpanded++;
				
				for (final MMNode c : n.getChildren()) {
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
				final MMNode n = moveN_B(prminB, OpenB, ClosedB);
				
				nodesExpanded++;
				
				for (final MMNode c : n.getChildren()) {
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
			
			System.out.println("U = " + U);
		}

		return U;
	}

	// Gives the priority of the given node in the forward direction
	// pr(n) = max(f(n),2*g(n))
	private int prF(final MMNode n) {
		int f = fF(n);
		int gtimes2 = n.gValueF * 2;
		return (f > gtimes2) ? f : gtimes2;
	}
	
	// Gives the priority of the given node in the backward direction
	// pr(n) = max(f(n),2*g(n))
	private int prB(final MMNode n) {
		int f = fB(n);
		int gtimes2 = n.gValueB * 2;
		return (f > gtimes2) ? f : gtimes2;
	}
	
	// Gives the fvalue of the given node in the forward direction
	// f(n) = h(n) + g(n)
	private int fF(final MMNode n) {
		return n.hueristicF + n.gValueF;
	}
	
	// Gives the fvalue of the given node in the backward direction
	// f(n) = h(n) + g(n)
	private int fB(final MMNode n) {
		return n.hueristicB + n.gValueB;
	}
	
	// Gives the minimum priority within the given open Set
	private int prminF(final Set<MMNode> openSet) {
		int min = Integer.MAX_VALUE;

		for (final MMNode n : openSet) {
			int currentPriority = prF(n);
			if (currentPriority < min)
				min = currentPriority;
		}

		return min;
	}
	
	// Gives the minimum priority within the given open Set
	private int prminB(final Set<MMNode> openSet) {
		int min = Integer.MAX_VALUE;

		for (final MMNode n : openSet) {
			int currentPriority = prB(n);
			if (currentPriority < min)
				min = currentPriority;
		}

		return min;
	}

	// Description: Gives the C value of the current iteration
	// C = min(prminF,prminB)
	private int getC(final int prminF, final int prminB) {
		return (prminF < prminB) ? prminF : prminB;
	}
	
	private int getU(final int prevU, final int sum) {
		return (prevU < sum) ? prevU : sum;
	}
	
	// Gives the node n such that:
	//	- n is in OpenF
	//	- priority(n) == prminF
	private MMNode moveN_F(final int prmin, final Set<MMNode> openSet, final Set<MMNode> closeSet) {
		for (final MMNode o : openSet)
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
	private MMNode moveN_B(final int prmin, final Set<MMNode> openSet, final Set<MMNode> closeSet) {
		for (final MMNode o : openSet)
			if (prB(o) == prmin) {
				openSet.remove(o);
				closeSet.add(o);
				return o;
			}
		
		return null;
	}
	
	private Set<MMNode> union(final Set<MMNode> Set1, final Set<MMNode> Set2) {
		final Set<MMNode> union = new HashSet<MMNode>();
		
		for (final MMNode n : Set1)
			union.add(n);
			
		for (final MMNode n : Set2)
			if (!union.contains(n))
				union.add(n);
		
		return union;
	}
	
	public static class MMNode {
		public final Set<MMEdge> outgoing = new HashSet<MMEdge>();
		public int hueristicF;
		public int hueristicB;
		public int gValueF;
		public int gValueB;
		
		public MMNode() {}
		
		public MMNode(final int hF, final int hB) {
			hueristicF = hF;
			hueristicB = hB;
		}
		
		public void addOutgoingEdges(final MMEdge... outgoing) {
			for (final MMEdge c : outgoing)
				this.outgoing.add(c);
		}
		
		public void addOutgoingEdges(final Set<MMEdge> outgoing) {
			addOutgoingEdges(outgoing.toArray(new MMEdge[outgoing.size()]));
		}
		
		public Set<MMNode> getChildren() {
			final Set<MMNode> children = new HashSet<MMNode>();
			
			for (final MMEdge e : outgoing)
				children.add(e.destination);
			
			return children;
		}
		
		public int getCostTo(final MMNode destination) {
			for (final MMEdge e : outgoing)
				if (e.destination.equals(destination))
					return e.cost;
			
			return -1;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof MMNode))
				return false;
			
			final MMNode comp = (MMNode)obj;
		
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
	
	// A directed edge with an associated cost
	public static class MMEdge {
		public int cost;
		public MMNode destination;
		
		public MMEdge() {}
		
		public MMEdge(final int cost, final MMNode destination) {
			this.cost = cost;
			this.destination = destination;
		}
		
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof MMEdge))
				return false;
			
			final MMEdge comp = (MMEdge)obj;
			
			if (this.cost != comp.cost)
				return false;
			if (!this.destination.equals(comp.destination))
				return false;
			
			return true;
		}
	}
}
