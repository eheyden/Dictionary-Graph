import java.util.HashMap;
/*
 * Project: Dictionary Graph
 * Due Date: April 16 2018
 * 
 */
 
 /**
 *
 * Undirected and unweighted graph implementation
 * @param <E> type of a vertex
 * 
 * @author Evan Heyden (eheyden@wisc.edu), Xinyang Hu (xhu257@wisc.edu)
 */
public class Graph<E> implements GraphADT<E> {
    
    private HashMap<E, GraphNode<E>> nodes; // All entries in the graph
    
    /**
    * Node class for each entry
    */
    class GraphNode<E>{
        public HashMap<E, GraphNode<E>> neighbors; // Set of adjacent nodes
        
        /**
        * Constructor for graphnodes
        */
        public GraphNode() {
            neighbors = new HashMap<E, GraphNode<E>>();
        }
        
    }
    
    public Graph() {
        nodes = new HashMap<E, GraphNode<E>>();
    }

    
    /**
     * Add new vertex to the graph
     * 
     * Valid argument conditions:
     * 1. vertex should be non-null
     * 2. vertex should not already exist in the graph 
     * 
     * @param vertex the vertex to be added
     * @return vertex if vertex added, else return null if vertex can not be added (also if valid conditions are violated)
     */
    @Override
    public E addVertex(E vertex) {
        if(vertex == null || nodes.containsKey(vertex)) return null;
        GraphNode<E> node = new GraphNode<E>();
        nodes.put(vertex, node);
        return vertex;
    }

    /**
     * Remove the vertex and associated edge associations from the graph
     * 
     * Valid argument conditions:
     * 1. vertex should be non-null
     * 2. vertex should exist in the graph 
     *  
     * @param vertex the vertex to be removed
     * @return vertex if vertex removed, else return null if vertex and associated edges can not be removed (also if valid conditions are violated)
     */
    @Override
    public E removeVertex(E vertex) {
        GraphNode<E> node = nodes.get(vertex);
        if(node == null) return null;
        
        //Removes node's edges
        for(E key : node.neighbors.keySet()) {
            removeEdge(key, vertex);
        }
        nodes.remove(vertex);
        return vertex;
        
    }

    /**
     * Add an edge between two vertices (edge is undirected and unweighted)
     * 
     * Valid argument conditions:
     * 1. both the vertices should exist in the graph
     * 2. vertex1 should not equal vertex2
     *  
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if edge added, else return false if edge can not be added (also if valid conditions are violated)
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
     * Remove the edge between two vertices (edge is undirected and unweighted)
     * 
     * Valid argument conditions:
     * 1. both the vertices should exist in the graph
     * 2. vertex1 should not equal vertex2
     *  
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if edge removed, else return false if edge can not be removed (also if valid conditions are violated)
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
     * Check whether the two vertices are adjacent
     * 
     * Valid argument conditions:
     * 1. both the vertices should exist in the graph
     * 2. vertex1 should not equal vertex2
     *  
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if both the vertices have an edge with each other, else return false if vertex1 and vertex2 are not connected (also if valid conditions are violated)
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
     * Get all the neighbor vertices of a vertex
     * 
     * Valid argument conditions:
     * 1. vertex is not null
     * 2. vertex exists
     * 
     * @param vertex the vertex
     * @return an iterable for all the immediate connected neighbor vertices
     */
    @Override
    public Iterable<E> getNeighbors(E vertex) {
        GraphNode<E> node = nodes.get(vertex);
        if(node == null) return null;
        
        return node.neighbors.keySet(); // Returns iterable set of the neighbors' keys
    }

    /**
     * Get all the vertices in the graph
     * 
     * @return an iterable for all the vertices
     */
    @Override
    public Iterable<E> getAllVertices() {
        return nodes.keySet(); // Returns iterable set of all the keys
    }

}
