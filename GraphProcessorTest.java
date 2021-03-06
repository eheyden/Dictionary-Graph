
//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Dictionary-Graph
// Files: GraphProcessorTest.java; GraphProcessor.java; Graph.java; 
// GraphADT.java; GraphTest.java; WordProcessor.java
// Course: CS400, Spring 2018
//
// Author: Xinyang Hu
// Email: xhu257@wisc.edu
// Lecturer's Name: Debra Deppeler
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: Eric Huang, Evan Heyden, Ribhav Hora, Harry Huang
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

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Assignment: Exercise4, Due: 04/16/18
 * 
 * GraphProcessorTest class provides 10 tests to exam the functionality of the
 * GraphProcessor class
 *
 * @author Xinyang Hu(xhu257@wisc.edu)
 */

public class GraphProcessorTest {
	GraphProcessor graphProcessor;

	/*
	 * The setup method initializes a new graphProcessor
	 */
	@Before
	public void setup() {
		graphProcessor = new GraphProcessor();
	}

	/*
	 * This test lets the graphProcessor read a txt file
	 * 
	 * success if the actual # of vertices is the same as expected, fail otherwise
	 */
	@Test
	public void test_01_populateGraph() {
		int actual = graphProcessor.populateGraph("word_list.txt");
		assertEquals(441, actual);
	}

	/*
	 * This test passes a wrong file name to populateGraph()
	 * 
	 * success if return -1, fail otherwise
	 */
	@Test
	public void test_02_populateGraph_file_not_found() {
		int actual = graphProcessor.populateGraph("random.txt");
		assertEquals(-1, actual);
	}

	/*
	 * This test exam the shortestPathPrecompute() method
	 * 
	 * success if no exception thrown, fail otherwise
	 */
	@Test
	public void test_03_shortestPathPrecompute_test_for_exception() {
		graphProcessor.populateGraph("word_list.txt");
		graphProcessor.shortestPathPrecomputation();
	}

	/*
	 * This test exam the getShortestPath() method
	 * 
	 * success if the actual list of String is the same as expected, fail otherwise
	 */
	@Test
	public void test_04_getShortestPath() {
		graphProcessor.populateGraph("word_list.txt");
		graphProcessor.shortestPathPrecomputation();

		List<String> expecteds = new ArrayList<String>();
		expecteds.add("BELLIES");
		expecteds.add("JELLIES");
		expecteds.add("JOLLIES");
		List<String> actuals = graphProcessor.getShortestPath("BELLIES", "JOLLIES");
		assertEquals(expecteds, actuals);
	}

	/*
	 * This test exam the getShortestPath() with 2 identical words
	 * 
	 * success if return a empty List of String, fail otherwise
	 */
	@Test
	public void test_05_getShortestPath_between_two_identical_words() {
		graphProcessor.populateGraph("word_list.txt");
		graphProcessor.shortestPathPrecomputation();

		List<String> expecteds = new ArrayList<String>();
		List<String> actuals = graphProcessor.getShortestPath("BELLIES", "BELLIES");
		assertEquals(expecteds, actuals);
	}

	/*
	 * This test exam the getShortestPath() with 2 words where connection doesn't
	 * exist
	 * 
	 * success if return a empty List of String, fail otherwise
	 */
	@Test
	public void test_06_getShortestPath_if_path_does_not_exist() {
		graphProcessor.populateGraph("no_connection.txt");
		graphProcessor.shortestPathPrecomputation();

		List<String> expecteds = new ArrayList<String>();
		List<String> actuals = graphProcessor.getShortestPath("cat", "dog");
		assertEquals(expecteds, actuals);
	}

	/*
	 * This test exam the getShortestDistance() method with a pair of Strings
	 * 
	 * success if the # of edges is the same as expected, fail otherwise
	 */
	@Test
	public void test_07_getShortestDistance_part_1() {
		graphProcessor.populateGraph("word_list.txt");
		graphProcessor.shortestPathPrecomputation();

		int actual = graphProcessor.getShortestDistance("COMEDO", "CHARGE");
		assertEquals(49, actual);
	}

	/*
	 * This test exam the getShortestDistance() method with another pair of Strings
	 * 
	 * success if the # of edges is the same as expected, fail otherwise
	 */
	@Test
	public void test_08_getShortestDistance_part_2() {
		graphProcessor.populateGraph("word_list.txt");
		graphProcessor.shortestPathPrecomputation();

		int actual = graphProcessor.getShortestDistance("CHARGE", "GIMLETS");
		assertEquals(78, actual);
	}

	/*
	 * This test exam the getShortestDistance() with 2 identical words
	 * 
	 * success if return -1, fail otherwise
	 */
	@Test
	public void test_09_getShortestDistance_between_two_identical_words() {
		graphProcessor.populateGraph("word_list.txt");
		graphProcessor.shortestPathPrecomputation();

		int actual = graphProcessor.getShortestDistance("BELLIES", "BELLIES");
		assertEquals(-1, actual);
	}

	/*
	 * This test exam the getShortestDistance() with 2 words where connection
	 * doesn't exist
	 * 
	 * success if return -1, fail otherwise
	 */
	@Test
	public void test_10_getShortestDistance_if_path_does_not_exist() {
		graphProcessor.populateGraph("no_connection.txt");
		graphProcessor.shortestPathPrecomputation();

		int actual = graphProcessor.getShortestDistance("cat", "dog");
		assertEquals(-1, actual);
	}
}