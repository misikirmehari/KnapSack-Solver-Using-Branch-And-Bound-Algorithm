package knapsack;

/**
 * A Node object that holds each items profit , weight , best profit 
 * - Implements the Comparable interface 
 * @author Abdalla Elmedani
 * @author Misikir Mehari
 *  
 */
import java.lang.Comparable;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Node implements Comparable<Node> {
	protected static int nodeCount = 1;
	private ArrayList<Integer> itemsChosen;
	private ArrayList<Integer> itemsIgnored;
	private int nodeId;
	private int level;
	private double profit;
	private double weight;
	private double bound;
	DecimalFormat format = new DecimalFormat("#.00");

	/**
	 * Constructs a node object using specified values
	 * 
	 * @param itemsChosen
	 *            - items that are chosen to be included in the sack
	 * @param itemsIgnored
	 *            - items that are ignored from the sack
	 * @param level
	 *            - current level of the tree
	 * @param profit
	 *            - current profit
	 * @param weight
	 *            - current weight
	 */

	public Node(ArrayList<Integer> itemsChosen, ArrayList<Integer> itemsIgnored, int level, double profit,
			double weight) {
		this.itemsChosen = itemsChosen;
		this.itemsIgnored = itemsIgnored;
		nodeId = nodeCount;
		nodeCount++;
		this.level = level;
		this.profit = profit;
		this.weight = weight;
	}

	/**
	 * Constructs a node object without specific values
	 */
	public Node() {

	}

	/**
	 * @return - the profit of an item
	 */
	public double getProfit() {
		return profit;
	}

	/**
	 * 
	 * @return - the weight of an item
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * 
	 * @return - the best achievable profit
	 */
	public double getBound() {
		return bound;
	}

	/**
	 * Sets the bound
	 * 
	 * @param bound
	 */
	public void setBound(double bound) {
		this.bound = bound;
	}

	/**
	 * @return - the items that are chosen to be included in the sack
	 */
	public ArrayList<Integer> getItemsChosen() {
		return itemsChosen;
	}

	/**
	 * @return - the items that are chosen to be ignored from the sack
	 */
	public ArrayList<Integer> getItemsIgnored() {
		return itemsIgnored;
	}

	/**
	 * @return - the current level of the tree
	 */
	public double getLevel() {
		return level;
	}

	/**
	 * @return - the id of a node
	 */
	public double getNodeId() {
		return nodeId;
	}

	/**
	 * Sets the List with the items chosen to be included in the sack
	 * 
	 * @param itemsChosen
	 */
	public void setItemsChosen(ArrayList<Integer> itemsChosen) {
		this.itemsChosen = itemsChosen;
	}

	/**
	 * Sets the List with the items ignored from the sack
	 * 
	 * @param itemsIgnored
	 */
	public void setItemsIgnored(ArrayList<Integer> itemsIgnored) {
		this.itemsIgnored = itemsIgnored;
	}

	/**
	 * Sets the level of the tree
	 * 
	 * @param level
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * Sets the id of a node
	 * 
	 * @param nodeid
	 */
	public void setNodeId(int nodeid) {
		this.nodeId = nodeid;
	}

	/**
	 * Compares the bounds of two nodes
	 */
	@Override
	public int compareTo(Node nextNode) {
		// A Flag to indicate that the bound of the next node is greater than
		// the current node
		final int biggerBound = 1;
		// A Flag to indicate that the bound of the next node is smaller than
		// the current node
		final int smallerBound = -1;
		// A Flag to indicate that the bound of the next node is equal to the
		// current node
		final int sameBound = 0;

		double nextBound = nextNode.getBound();
		if (bound < nextBound) {
			return biggerBound;
		} else if (bound == nextBound) {
			return sameBound;
		}
		return smallerBound;
	}

	/**
	 * Renders object in the format <Node items: level: profit: weight: bound:>
	 * 
	 * @return A String representation of the Node Object
	 */
	@Override
	public String toString() {
		
		return "<Node " + nodeId + ": items: " + itemsChosen + " level: " + level + " profit: " + profit + " weight: "
				+ weight + " bound: " + format.format(bound) + ">";
	}
}
