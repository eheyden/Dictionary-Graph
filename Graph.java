import java.util.LinkedList;

/**
 * Undirected and unweighted graph implementation
 * 
 * @param <E> type of a vertex
 * 
 * 
 * 
 */
public class Graph<E> implements GraphADT<E> {
    
	private LinkedList<GraphNode<E>> nodes;
	
	class Graphnode<E>{
		private E data;
		public LinkedList<Graphnode<E>> neighbors;
		public Graphnode(E data) {
		public LinkedList<GraphNode<E>> neighbors;
		public GraphNode(E data) {
			neighbors = new LinkedList<GraphNode<E>>();
			this.data = data;
		}
		
	}
	
	public Graph() {
		nodes = new LinkedList<GraphNode<E>>;
		
	}
    /**
     * Instance variables and constructors
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public E addVertex(E vertex) {
    	if(vertex == null || nodes.contains(vertex)) return null;
        GraphNode<E> node = new GraphNode<E>(vertex);
        nodes.add(node);
        return vertex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E removeVertex(E vertex) {
    	if(vertex == null || nodes.contains(vertex)) return null;
    	for(GraphNode<E> neighbor | vertex.neighbors) {
    		removeEdge(neighbor, vertex);
    	}
    	nodes.remove(vertex);
    	return vertex;
    	
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEdge(E vertex1, E vertex2) {
        if(vertex1 == null || vertex2 == null) return false;
        if(vertex1.equals(vertex2)) return false;
        if(!nodes.contains(vertex1) || !nodes.contains(vertex2)) return false;
        if(vertex1.neighbors.contains(vertex2)) return false;
        vertex2.neighbors.add(vertex1);
        vertex1.neighbors.add(vertex2);
        return true;
    }    

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeEdge(E vertex1, E vertex2) {
        if(vertex1 == null || vertex2 == null) return false;
        if(vertex1.equals(vertex2) return false;
        if(!nodes.contains(vertex1) || !nodes.contains(vertex2)) return false;
        if(!vertex2.neighbors.contains(vertex1)) return false;
        vertex2.neighbors.remove(vertex1);
        vertex1.neighbors.remove(vertex2);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAdjacent(E vertex1, E vertex2) {
    	if(vertex1 == null || vertex2 == null) return false;
        if(vertex1.equals(vertex2) return false;
        if(!nodes.contains(vertex1) || !nodes.contains(vertex2)) return false;
        if(vertex1.neighbors.contains(vertex2)) return true;
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getNeighbors(E vertex) {
        if(vertex == null) return null;
        if(!nodes.contains(vertex)) return null;
        return vertex.neighbors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getAllVertices() {
        return nodes;
    }

}
