package org.eclipse.draw2d.internal.graph;

import org.eclipse.draw2d.graph.*;


/**
 * This graph visitor examines all adjacent pairs of nodes and determines if 
 * swapping the two nodes provides improved graph aesthetics.
 * @author Daniel Lee
 */
public class LocalOptimizer extends GraphVisitor {

private boolean shouldSwap(Node current, Node next) {
	int crossCount = 0;
	int invertedCrossCount = 0;
	
	EdgeList currentEdges = current.incoming;
	EdgeList nextEdges = next.incoming;
	int rank = current.rank - 1;
	int iCurrent, iNext;

	for (int i = 0; i < currentEdges.size(); i++) {
		Edge currentEdge = currentEdges.getEdge(i);
		iCurrent = currentEdge.getIndexForRank(rank);
		for (int j = 0; j < nextEdges.size(); j++) {
			iNext = nextEdges.getEdge(j).getIndexForRank(rank);
			if (iNext < iCurrent)
				crossCount++;
			else if (iNext > iCurrent)
				invertedCrossCount++;
			else {
				//edges go to the same location
				int offsetDiff = nextEdges.getEdge(j).getSourceOffset() 
						- currentEdge.getSourceOffset();
				if (offsetDiff < 0)
					crossCount++;
				else if (offsetDiff > 0)
					invertedCrossCount++;
			}
		}
	}
	
	currentEdges = current.outgoing;
	nextEdges = next.outgoing;
	rank = current.rank + 1;
	
	for (int i = 0; i < currentEdges.size(); i++) {
		Edge currentEdge = currentEdges.getEdge(i);
		iCurrent = currentEdge.getIndexForRank(rank);
		for (int j = 0; j < nextEdges.size(); j++) {
			iNext = nextEdges.getEdge(j).getIndexForRank(rank); 
			if (iNext < iCurrent)
				crossCount++;	
			else if (iNext > iCurrent)
				invertedCrossCount++;
			else {
				//edges go to the same location
				int offsetDiff = nextEdges.getEdge(j).getTargetOffset() 
						- currentEdge.getTargetOffset();
				if (offsetDiff < 0)
					crossCount++;
				else if (offsetDiff > 0)
					invertedCrossCount++;
			}
		}
	}
	if (invertedCrossCount < crossCount)		
		return true;
	return false;
}

private void swapNodes(Node current, Node next, Rank rank) {
	int index = rank.indexOf(current);
	rank.set(index + 1, current);
	rank.set(index, next);
	index = current.index;
	current.index = next.index;
	next.index = index;
}

/**
 * @see GraphVisitor#visit(org.eclipse.draw2d.graph.DirectedGraph)
 */
public void visit(DirectedGraph g) {
	boolean flag;
	do {
		flag = false;
		for (int r = 0; r < g.ranks.size(); r++) {
			Rank rank = g.ranks.getRank(r);
			for (int n = 0; n < rank.count() - 1; n++) {
				Node currentNode = rank.getNode(n);
				Node nextNode = rank.getNode(n + 1);
				if (shouldSwap(currentNode, nextNode)) {
					swapNodes(currentNode, nextNode, rank);
					flag = true;
					n = Math.max(0, n - 2);
				}
			}
		}
	} while (flag);
}

}
