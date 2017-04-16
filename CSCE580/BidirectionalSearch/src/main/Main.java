package main;

import main.MM.MMNode;
import main.MM.MMEdge;

public class Main {

	public static void main(String[] args) {
		classGraphMM();
	}
	
	// Start is a, goal is g
	private static void classGraphMM() {
		MMNode a = new MMNode(11,0);
		MMNode b = new MMNode(13,2);
		MMNode c = new MMNode(8,5);
		MMNode d = new MMNode(5,8);
		MMNode e = new MMNode(17,6);
		MMNode f = new MMNode(17,6);
		MMNode g = new MMNode(0,11);
		
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
		c.addOutgoingEdges(dtoa, dtoc, dtog);
		
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
