import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

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
     * This test exam the shortestPathPrecompute() method
     * 
     * success if no exception thrown, fail otherwise
     */
    @Test
    public void test_02_shortestPathPrecompute_test_for_exception() {
        graphProcessor.populateGraph("word_list.txt");
        graphProcessor.shortestPathPrecomputation();
    }

    /*
     * This test exam the getShortestPath() method
     * 
     * success if the actual list of String is the same as expected, fail otherwise
     */
    @Test
    public void test_03_getShortestPath() {
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
     * This test exam the getShortestDistance() method with a pair of Strings
     * 
     * success if the # of edges is the same as expected, fail otherwise
     */
    @Test
    public void test_04_getShortestDistance_part_1() {
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
    public void test_04_getShortestDistance_part_2() {
        graphProcessor.populateGraph("word_list.txt");
        graphProcessor.shortestPathPrecomputation();
        
        int actual = graphProcessor.getShortestDistance("CHARGE", "GIMLETS");
        assertEquals(78, actual);
    }
}
