package algorithms;

import java.util.List;

import graph.Edge;
import graph.EdgeListObject;
import graph.Graph;
import graph.Vertex;
import graph.Edge.EdgeType;
import graph.Vertex.VertexType;

public class DAGDistance {

	/**
	 * This method calculates and stores the shortest distance of every vertex
	 * from start vertex. It also stores edges leading to minimum distance so
	 * that shortes path can be printed
	 * 
	 * @param g
	 *            : Directed Acyclic Graph
	 * @param start
	 *            : Source vertex
	 */
	public void dagDistances(Graph g, Vertex start) {
		List<Vertex> vertexList = g.vertices();
		for (Vertex v : vertexList)
			if (v.equals(start))
				v.setDistance(0);
			else
				v.setDistance(Integer.MAX_VALUE);

		Vertex[] sortedVertices = topologicalDFS(g);

		for (Vertex u : sortedVertices) {
			List<EdgeListObject> outEdges = u.getOutEdges();
			if (outEdges != null) {
				for (EdgeListObject elo : outEdges) {
					Vertex z = g.opposite(u, elo.getEdge());
					int r = u.getDistance() + elo.getEdge().getWeight();
					if (r < z.getDistance()) {
						z.setDistance(r);
						z.setParent(elo.getEdge());// To Track Shortest Path
					}
				}
			}
		}
	}

	/**
	 * This method sorts the vertices of a DAG in topological order and returns
	 * an array of vertices in order
	 * 
	 * @param g
	 *            Directed Acyclic Graph
	 * @return Array of vertices in topologically sorted order
	 */
	private Vertex[] topologicalDFS(Graph g) {

		int n = g.getNoOfVertices();

		List<Vertex> vertices = g.vertices();
		for (Vertex v : vertices)
			v.setVertexType(VertexType.UNEXPLORED);

		for (Edge e : g.edges())
			e.setEdgeType(EdgeType.UNEXPLORED);

		for (Vertex v : vertices)
			if (v.getVertexType() == VertexType.UNEXPLORED)
				n = topologicalDFS(g, v, n);

		Vertex[] sortedVertices = new Vertex[g.getNoOfVertices()];
		for (Vertex v : vertices)
			sortedVertices[v.getTopologicalNumber() - 1] = v;
		return sortedVertices;
	}

	private int topologicalDFS(Graph g, Vertex v, int n) {

		v.setVertexType(VertexType.VISITED);
		List<Edge> incidentEdges = g.incidentEdges(v);
		if (incidentEdges != null) {
			for (Edge edge : incidentEdges) {
				if (edge.getEdgeType() == EdgeType.UNEXPLORED) {
					Vertex w = g.opposite(v, edge);
					if (w.getVertexType() == VertexType.UNEXPLORED) {
						edge.setEdgeType(EdgeType.DISCOVERY);
						n = topologicalDFS(g, w, n);
					} else
						edge.setEdgeType(EdgeType.CROSS);

				}
			}
		}
		v.setTopologicalNumber(n);
		n = n - 1;
		return n;
	}
}
