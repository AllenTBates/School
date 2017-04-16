package main;

import main.AStar.AStarEdge;
import main.AStar.AStarNode;
import main.MM.MMEdge;
import main.MM.MMNode;

public class Main {

	public static void main(String[] args) {
		classGraphMM();
		classGraphAStar();
	}

	private static void classGraphAStar() {
		AStarNode a = new AStarNode(11);
		AStarNode b = new AStarNode(13);
		AStarNode c = new AStarNode(8);
		AStarNode d = new AStarNode(5);
		AStarNode e = new AStarNode(17);
		AStarNode f = new AStarNode(17);
		AStarNode g = new AStarNode(0);

		AStarEdge atob = new AStarEdge(2,b);
		AStarEdge atoc = new AStarEdge(6,c);
		AStarEdge atod = new AStarEdge(10,d);
		a.addOutgoingEdges(atob, atoc, atod);

		AStarEdge btoa = new AStarEdge(2,a);
		AStarEdge btoe = new AStarEdge(4,e);
		AStarEdge btof = new AStarEdge(4,f);
		b.addOutgoingEdges(btoa, btoe, btof);

		AStarEdge ctoa = new AStarEdge(6,a);
		AStarEdge ctod = new AStarEdge(5,d);
		c.addOutgoingEdges(ctoa, ctod);

		AStarEdge dtoa = new AStarEdge(10,a);
		AStarEdge dtoc = new AStarEdge(3,c);
		AStarEdge dtog = new AStarEdge(5,g);
		d.addOutgoingEdges(dtoa, dtoc, dtog);

		AStarEdge etob = new AStarEdge(4,b);
		AStarEdge etof = new AStarEdge(3,f);
		e.addOutgoingEdges(etob, etof);

		AStarEdge ftob = new AStarEdge(4,b);
		AStarEdge ftoe = new AStarEdge(3,e);
		f.addOutgoingEdges(ftob, ftoe);

		AStarEdge gtod = new AStarEdge(5,d);
		g.addOutgoingEdges(gtod);

		final AStar as = new AStar();

		int minCost = as.run(a, g);
		int nodesExpanded = as.nodesExpanded;

		System.out.println("MinCost = " + minCost + "\nNodes Expanded = " + nodesExpanded + "\n");
	}

	// Start is a, goal is g
	private static void classGraphMM() {
		MMNode a = new MMNode(11,0, "a");
		MMNode b = new MMNode(13,2, "b");
		MMNode c = new MMNode(8,5, "c");
		MMNode d = new MMNode(5,8, "d");
		MMNode e = new MMNode(17,6, "e");
		MMNode f = new MMNode(17,6, "f");
		MMNode g = new MMNode(0,11, "g");

		MMEdge atob = new MMEdge(2,b);
		MMEdge atoc = new MMEdge(6,c);
		MMEdge atod = new MMEdge(10,d);
		a.addOutgoingEdges(atob, atoc, atod);

		MMEdge btoa = new MMEdge(2,a);
		MMEdge btoe = new MMEdge(4,e);
		MMEdge btof = new MMEdge(4,f);
		b.addOutgoingEdges(btoa, btoe, btof);

		MMEdge ctoa = new MMEdge(6,a);
		MMEdge ctod = new MMEdge(5,d);
		c.addOutgoingEdges(ctoa, ctod);

		MMEdge dtoa = new MMEdge(10,a);
		MMEdge dtoc = new MMEdge(3,c);
		MMEdge dtog = new MMEdge(5,g);
		d.addOutgoingEdges(dtoa, dtoc, dtog);

		MMEdge etob = new MMEdge(4,b);
		MMEdge etof = new MMEdge(3,f);
		e.addOutgoingEdges(etob, etof);

		MMEdge ftob = new MMEdge(4,b);
		MMEdge ftoe = new MMEdge(3,e);
		f.addOutgoingEdges(ftob, ftoe);

		MMEdge gtod = new MMEdge(5,d);
		g.addOutgoingEdges(gtod);

		final MM mm = new MM();

		int minCost = mm.run(a, g);
		int nodesExpanded = mm.nodesExpanded;

		System.out.println("MinCost = " + minCost + "\nNodes Expanded = " + nodesExpanded);
	}
}
