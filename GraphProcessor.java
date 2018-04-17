
//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Dictionary-Graph
// Files: GraphProcessorTest.java; GraphProcessor.java; Graph.java; 
// GraphADT.java; GraphTest.java; WordProcessor.java
// Course: CS400, Spring 2018
//
// Author: Zhichun Huang
// Email: zhuang294@wisc.edu
// Lecturer's Name: Debra Deppeler
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: , Evan Heyden, Ribhav Hora, Harry Huang, Xinyang Hu
// Partner Email: N/A
// Lecturer's Name: Debra Deppeler
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// _X__ Write-up states that pair programming is allowed for this assignment.
// _X__ We have both read and understand the course Pair Programming Policy.
// _X__ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates
// strangers, etc do. If you received no outside help from either type of
// source, then please explicitly indicate NONE.
//
// Persons: (identify each person and describe their help in detail)
// Online Sources: (identify each URL and describe their assistance in detail)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * This class adds additional functionality to the graph as a whole.
 * 
 * Contains an instance variable, {@link #graph}, which stores information for
 * all the vertices and edges.
 * 
 * @see #populateGraph(String) - loads a dictionary of words as vertices in the
 *      graph. - finds possible edges between all pairs of vertices and adds
 *      these edges in the graph. - returns number of vertices added as Integer.
 *      - every call to this method will add to the existing graph. - this
 *      method needs to be invoked first for other methods on shortest path
 *      computation to work.
 * @see #shortestPathPrecomputation() - applies a shortest path algorithm to
 *      precompute data structures (that store shortest path data) - the
 *      shortest path data structures are used later to to quickly find the
 *      shortest path and distance between two vertices. - this method is called
 *      after any call to populateGraph. - It is not called again unless new
 *      graph information is added via populateGraph().
 * @see #getShortestPath(String, String) - returns a list of vertices that
 *      constitute the shortest path between two given vertices, computed using
 *      the precomputed data structures computed as part of
 *      {@link #shortestPathPrecomputation()}. -
 *      {@link #shortestPathPrecomputation()} must have been invoked once before
 *      invoking this method.
 * @see #getShortestDistance(String, String) - returns distance (number of
 *      edges) as an Integer for the shortest path between two given vertices -
 *      this is computed using the precomputed data structures computed as part
 *      of {@link #shortestPathPrecomputation()}. -
 *      {@link #shortestPathPrecomputation()} must have been invoked once before
 *      invoking this method.
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * 
 */
public class GraphProcessor {

	/**
	 * Graph which stores the dictionary words and their associated connections
	 */
	private GraphADT<String> graph;

	/**
	 * HashMap which stores the following path information between any two vertices
	 * Key: A word in graph (origin) Value: A HashMap containing path information
	 * from the key to all other words in the graph with: Key: a word in graph other
	 * than origin (destination) Value: A PathNode containing information of paths
	 * from origin to destination
	 */
	private HashMap<String, HashMap<String, PathNode>> pathMaps;

	/**
	 * Constructor for this class. Initializes instances variables to set the
	 * starting state of the object
	 */
	public GraphProcessor() {
		this.graph = new Graph<>();
	}

	/**
	 * Builds a graph from the words in a file. Populate an internal graph, by
	 * adding words from the dictionary as vertices and finding and adding the
	 * corresponding connections (edges) between existing words.
	 * 
	 * Reads a word from the file and adds it as a vertex to a graph. Repeat for all
	 * words.
	 * 
	 * For all possible pairs of vertices, finds if the pair of vertices is adjacent
	 * {@link WordProcessor#isAdjacent(String, String)} If a pair is adjacent, adds
	 * an undirected and unweighted edge between the pair of vertices in the graph.
	 *
	 * Log any issues encountered (print the issue details)
	 * 
	 * @param filepath
	 *            file path to the dictionary
	 * @return Integer the number of vertices (words) added; return -1 if file not
	 *         found or if encountering other exceptions
	 */
	public Integer populateGraph(String filepath) {
		Stream<String> wordStream;
		try {
			wordStream = WordProcessor.getWordStream(filepath);
		} catch (IOException ioe) {
			System.out.print(ioe.getMessage());
			return -1;
		}

		final AtomicInteger count = new AtomicInteger();
		try {
			wordStream.forEach(word -> {
				addWordToGraph(graph, word);
				count.incrementAndGet();
			});
		} catch (NullPointerException npe) {
			System.out.print("Error in GraphProcessor.populateGraph(): Null element in Stream");
			return -1;
		}

		shortestPathPrecomputation();

		return count.intValue();
	}

	/**
	 * Private helper method to add word to the graph
	 *
	 * If word is adjacent to any other nodes in
	 *
	 * @param graph
	 *            - a GraphADT
	 * @param word
	 *            - the word to add
	 */
	private void addWordToGraph(GraphADT<String> graph, String word) {
		graph.addVertex(word);
		for (String s : graph.getAllVertices()) {
			if (WordProcessor.isAdjacent(s, word))
				graph.addEdge(s, word);
		}
	}

	/**
	 * Gets the list of words that create the shortest path between word1 and word2
	 * 
	 * Example: Given a dictionary, cat rat hat neat wheat kit shortest path between
	 * cat and wheat is the following list of words: [cat, hat, heat, wheat]
	 *
	 * If word1 = word2, List will be empty. Both the arguments will always be
	 * present in the graph.
	 * 
	 * @param word1
	 *            first word
	 * @param word2
	 *            second word
	 * @return List<String> list of the words
	 */
	public List<String> getShortestPath(String word1, String word2) {
		List<String> list = new ArrayList<>();

		if (pathMaps == null) {
			System.out.print("Error in GraphProcessor.getShortestPath: "
					+ "shorestPathPrecomputation() must be called before any calls to getShortestPath()");
			return list;
		}

		HashMap<String, PathNode> pathMap = pathMaps.get(word1);
		if (word1.equals(word2) || pathMap == null || !pathMap.containsKey(word2))
			return list;

		// Backtrace the path using parents of pathNodes
		Stack<String> pathStack = new Stack<>();
		PathNode backtraceNode = pathMap.get(word2);
		while (backtraceNode != null) {
			pathStack.push(backtraceNode.word);
			backtraceNode = pathMap.get(backtraceNode.parent);
		}
		pathStack.push(word1);

		while (!pathStack.isEmpty())
			list.add(pathStack.pop());
		return list;
	}

	/**
	 * Gets the distance of the shortest path between word1 and word2
	 * 
	 * Example: Given a dictionary, cat rat hat neat wheat kit distance of the
	 * shortest path between cat and wheat, [cat, hat, heat, wheat] = 3 (the number
	 * of edges in the shortest path)
	 *
	 * Distance = -1 if no path found between words (true also for word1=word2) Both
	 * the arguments will always be present in the graph.
	 * 
	 * @param word1
	 *            first word
	 * @param word2
	 *            second word
	 * @return Integer distance
	 */
	public Integer getShortestDistance(String word1, String word2) {
		if (pathMaps == null) {
			System.out.print("Error in GraphProcessor.getShortestPath: "
					+ "shorestPathPrecomputation() must be called before any calls to getShortestDistance()");
			return -1;
		}

		HashMap<String, PathNode> pathMap = pathMaps.get(word1);
		if (word1.equals(word2) || pathMap == null || !pathMap.containsKey(word2))
			return -1;
		return pathMaps.get(word1).get(word2).length;
	}

	/**
	 * Computes shortest paths and distances between all possible pairs of vertices.
	 * This method is called after every set of updates in the graph to recompute
	 * the path information. Any shortest path algorithm can be used (Djikstra's or
	 * Floyd-Warshall recommended).
	 *
	 * Implemented using Uniform-cost Search (Djikstra's Algorithm)
	 */
	public void shortestPathPrecomputation() {
		pathMaps = new HashMap<>();
		Iterable<String> vertices = graph.getAllVertices();
		StreamSupport.stream(vertices.spliterator(), true).forEach(this::ucs_all_paths);

		// for (Map.Entry<String, PathNode> dest: pathMaps.get("JOLLIES").entrySet()) {
		// System.out.println(dest.getValue());
		// }
	}

	private void ucs_all_paths(String s) {
		HashMap<String, PathNode> pathNodes = new HashMap<>(); // All paths from the current word
		pathMaps.put(s, pathNodes);

		// UCS shortest path algorithm
		PriorityQueue<PathNode> frontier = new PriorityQueue<>((p1, p2) -> -Integer.compare(p1.length, p2.length)); // pQ
																													// search
																													// frontier
		ArrayList<String> explored = new ArrayList<>(); // explored words
		frontier.add(new PathNode(s, null, 0));

		while (!frontier.isEmpty()) {
			PathNode searchNode = frontier.remove(); // remove the pathNode with highest priority (least length)
			if (!explored.contains(searchNode.word)) {
				for (String neighbor : graph.getNeighbors(searchNode.word)) {
					// Throw away current path if existing path has smaller length
					if (explored.contains(neighbor) || (pathNodes.containsKey(neighbor)
							&& pathNodes.get(neighbor).length < searchNode.length + 1))
						continue;

					// Add current path to pathNodes
					PathNode newPathNode = new PathNode(neighbor, searchNode.word, searchNode.length + 1);
					pathNodes.put(neighbor, newPathNode);
					if (!explored.contains(neighbor) && !frontier.contains(newPathNode)) {
						frontier.add(newPathNode);
					}
				}
				explored.add(searchNode.word);
			}
		}
	}

	/**
	 * Private class representing nodes on a path
	 */
	private class PathNode {
		private String parent; // word in the previous PathNode
		private String word; // word in the current PathNode
		private int length; // length so far from origin

		private PathNode(String word, String parent, int length) {
			this.word = word;
			this.parent = parent;
			this.length = length;
		}

		@Override
		public boolean equals(Object o) {
			try {
				PathNode p = (PathNode) o;
				return p.word.equals(word);
			} catch (Exception e) {
				return false;
			}
		}

		public String toString() {
			return String.format("%s %s %d", word, parent, length);
		}
	}
}