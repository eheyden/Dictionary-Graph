import java.util.HashMap;
/**
 * Undirected and unweighted graph implementation
 * 
 * @param <E> type of a vertex
 * 
 * 
 * 
 */
public class Graph<E> implements GraphADT<E> {
    
    private HashMap<E, GraphNode<E>> nodes;
    
    class GraphNode<E>{
        public HashMap<E, GraphNode<E>> neighbors;
        public GraphNode(E data) {
            neighbors = new HashMap<E, GraphNode<E>>();
        }
        
    }
    
    public Graph() {
        nodes = new HashMap<E, GraphNode<E>>();
        
    }

    
    /**
     * {@inheritDoc}
     */
    @Override
    public E addVertex(E vertex) {
        if(vertex == null || nodes.containsKey(vertex)) return null;
        GraphNode<E> node = new GraphNode<E>(vertex);
        nodes.put(vertex, node);
        return vertex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E removeVertex(E vertex) {
        GraphNode<E> node = nodes.get(vertex);
        if(node == null) return null;
        for(E key : node.neighbors.keySet()) {
            removeEdge(key, vertex);
        }
        nodes.remove(node);
        return vertex;
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEdge(E vertex1, E vertex2) {
        if(vertex1 == null || vertex2 == null) return false;
        if(vertex1.equals(vertex2)) return false;
        
        GraphNode<E> node1 = nodes.get(vertex1);
        GraphNode<E> node2 = nodes.get(vertex2);
        if(node1 == null || node2 == null) return false;
        if(node1.neighbors.containsKey(vertex2)) return false;
        node2.neighbors.put(vertex1, node1);
        node1.neighbors.put(vertex2, node2);
        return true;
    }    

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeEdge(E vertex1, E vertex2) {
        if(vertex1 == null || vertex2 == null) return false;
        if(vertex1.equals(vertex2)) return false;
        
        GraphNode<E> node1 = nodes.get(vertex1);
        GraphNode<E> node2 = nodes.get(vertex2);
        if(node1 == null || node2 == null) return false;
        if(!node2.neighbors.containsKey(vertex1)) return false;
        node2.neighbors.remove(vertex1);
        node1.neighbors.remove(vertex2);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAdjacent(E vertex1, E vertex2) {
        if(vertex1 == null || vertex2 == null) return false;
        if(vertex1.equals(vertex2)) return false;
        
        GraphNode<E> node1 = nodes.get(vertex1);
        if(node1 == null) return false;
        if(node1.neighbors.containsKey(vertex2)) return true;
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getNeighbors(E vertex) {
        GraphNode<E> node = nodes.get(vertex);
        if(node == null) return null;
        return node.neighbors.keySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getAllVertices() {
        return nodes.keySet();
    }

}
