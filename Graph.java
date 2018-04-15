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
    
    class GraphNode<E>{
        private E data;
        public LinkedList<GraphNode<E>> neighbors;
        public GraphNode(E data) {
            neighbors = new LinkedList<GraphNode<E>>();
            this.data = data;
        }
        public E getData() {return data;}
        
    }
    
    public Graph() {
        nodes = new LinkedList<GraphNode<E>>();
        
    }
    /**
     * Instance variables and constructors
     */

    private GraphNode<E> getNode(E data){
        for(GraphNode<E> node : nodes) {
            if(node.getData().equals(data)) return node;
        }
        return null;
    }
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
        GraphNode<E> node = getNode(vertex);
        if(vertex == null || node == null) return null;
        for(GraphNode<E> neighbor : node.neighbors) {
            removeEdge(neighbor.getData(), vertex);
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
        
        GraphNode<E> node1 = getNode(vertex1);
        GraphNode<E> node2 = getNode(vertex2);
        if(node1 == null || node2 == null) return false;
        if(node1.neighbors.contains(node2)) return false;
        node2.neighbors.add(node1);
        node1.neighbors.add(node2);
        return true;
    }    

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeEdge(E vertex1, E vertex2) {
        if(vertex1 == null || vertex2 == null) return false;
        if(vertex1.equals(vertex2)) return false;
        
        GraphNode<E> node1 = getNode(vertex1);
        GraphNode<E> node2 = getNode(vertex2);
        if(node1 == null || node2 == null) return false;
        if(!node2.neighbors.contains(node1)) return false;
        node2.neighbors.remove(node1);
        node1.neighbors.remove(node2);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAdjacent(E vertex1, E vertex2) {
        if(vertex1 == null || vertex2 == null) return false;
        if(vertex1.equals(vertex2)) return false;
        
        GraphNode<E> node1 = getNode(vertex1);
        GraphNode<E> node2 = getNode(vertex2);
        if(node1 == null || node2 == null) return false;
        if(node1.neighbors.contains(node2)) return true;
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getNeighbors(E vertex) {
        if(vertex == null) return null;
        
        GraphNode<E> node = getNode(vertex);
        if(node == null) return null;
        LinkedList<E> list = new LinkedList<E>();
        for(GraphNode<E> neighbor : node.neighbors) list.add(neighbor.getData());
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getAllVertices() {
        LinkedList<E> vertices = new LinkedList<E>();
        for(GraphNode<E> node : nodes) vertices.add(node.getData());
        return vertices;
    }

}
