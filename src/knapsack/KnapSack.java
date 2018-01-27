package knapsack;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * A knapSack object that uses data input from user
 * 
 * @author Abdalla Elmedani
 * @author Misikir Mehari
 *
 */
public class KnapSack {

	/* The capacity of the sack */
	private static int sackCapacity;

	/* The items that are available to choose from */
	private static double[][] table;

	/**
	 * Constructs a knapSack object using specified values
	 * 
	 * @param sackCapacity
	 * @param table
	 */
	public KnapSack(int sackCapacity, double[][] table) {

		KnapSack.sackCapacity = sackCapacity;
		KnapSack.table = table;

	}

	/**
	 * Apply the Branch and bound algorithm design which is used for solving
	 * combinatorial optimization problems
	 * 
	 * @return the best node that includes the items with the best possible
	 *         profit to include in the sack
	 */
	public double applyBranchAndBoundAlgorithm() {

		// Display root Node
		System.out.println("\nBegin Exploration of the possibilities tree:\n\nExploring ");
		Node rootNode = new Node(new ArrayList<Integer>(0), new ArrayList<Integer>(0), 0, 0, 0);
		calculateBound(rootNode);
		System.out.print(rootNode);
		rootNode = null;

		// Set the left node by ignoring the item on the current level
		Node leftChild = new Node(new ArrayList<Integer>(0), new ArrayList<Integer>(1), 1, 0, 0);
		leftChild.getItemsIgnored().add(1);

		// Set the right node using the current item on the current level
		Node rightChild = new Node(new ArrayList<Integer>(1), new ArrayList<Integer>(0), 1, table[0][0], table[0][1]);

		// Node to add/remove on a priority queue
		Node currentNodeOnQueue = null;

		// Get the items selected on the first level
		rightChild.getItemsChosen().add(1);

		// Set and start from level 1
		int level = 1;

		// Set a data structure to hold nodes
		PriorityQueue<Node> nodes = new PriorityQueue<>();

		// Go through each node and return the best node / best profit
		while (true) {
			// Checks if a node is pruned
			boolean isPruned = false;

			// Exploring left node
			System.out.print("\n  Left child is ");

			// Check if the current weight of the node is less than
			// the sack capacity and add the node to the priority
			// queue
			if (leftChild.getWeight() < sackCapacity) {

				// Calculate the maximum achievable profit using this node
				calculateBound(leftChild);

				// add node to queue
				nodes.add(leftChild);

				// Stop exploring current node since the weight is equal to the
				// sack capacity
				if (leftChild.getWeight() == sackCapacity) {
					System.out.print(leftChild);
					System.out.print("\n    hit capacity exactly, so don't explore further");
				}

				// Continue exploring current node since the weight is less than
				// the sack capacity

				else {
					System.out.print(leftChild);
					System.out.print("\n    explore Further");
				}

			}
			// Prune current node since the weight is larger than the sack
			// capacity

			else {
				leftChild.setBound(leftChild.getProfit());
				System.out.print(leftChild);
				System.out.print("\n    pruned becuase too heavy");
			}

			// Exploring the right node
			System.out.print("\n  Right child is ");

			// Check if the current weight of the node is less than
			// the sack capacity and add the node to the priority
			// queue

			if (rightChild.getWeight() <= sackCapacity) {

				// Calculate the maximum achievable profit using this node
				calculateBound(rightChild);

				// add node to queue
				nodes.add(rightChild);

				if (rightChild.getWeight() == sackCapacity) {

					// Stop exploring current node since the weight is equal to
					// the
					// sack capacity
					if (nodes.peek() != rightChild) {

						// peek on top of node and confirm that it is the same
						// as the right node
						// if not remove from queue to use
						nodes.remove();
					}

					System.out.print(rightChild);
					System.out.print("\n    hit capacity exactly, so don't explore further");
				}

				// Continue exploring current node since the weight is less than
				// the sack capacity

				else {
					System.out.print(rightChild);
					System.out.print("\n    explore Further");
				}
			}

			// Prune current node since the weight is larger than the sack
			// capacity

			else {
				rightChild.setBound(rightChild.getProfit());
				System.out.print(rightChild);
				System.out.print("\n    pruned because too heavy");
				isPruned = true;

			}

			// Remove current node on queue to explore further
			currentNodeOnQueue = nodes.remove();

			// Current best node
			if (currentNodeOnQueue.getWeight() == sackCapacity || currentNodeOnQueue.getLevel() == table.length) {

				if (!isPruned) {
					System.out.print("\n    note achievable profit of ");
					System.out.print(currentNodeOnQueue.getProfit());
				}
				// Prune nodes that have less profit
				while (!nodes.isEmpty()) {
					Node prune = nodes.remove();
					System.out.print("\n\nExploring ");
					System.out.print(prune);
					System.out.print("\n    pruned, don't explore children because " + "bound " + prune.getBound()
							+ " is smaller than known achievable profit " + currentNodeOnQueue.getProfit());
				}

				// Display best node
				System.out.print("\n\nBest node: ");
				System.out.println(currentNodeOnQueue);

				return currentNodeOnQueue.getProfit();
			}

			else {

				if (!isPruned) {
					System.out.print("\n    note achievable profit of ");
					System.out.print(currentNodeOnQueue.getProfit());
				}

				System.out.print("\n\nExploring ");
				System.out.print(currentNodeOnQueue);
			}

			// increment the current level by 1
			level = (int) currentNodeOnQueue.getLevel() + 1;

			// Set a List to contain the next level items
			ArrayList<Integer> nextLevelItems;

			// Create a left child for the next level
			Node nextLevelLeftChild = new Node(currentNodeOnQueue.getItemsChosen(), null, level,
					currentNodeOnQueue.getProfit(), currentNodeOnQueue.getWeight());

			// Add the next level contents on the list
			nextLevelItems = new ArrayList<>(currentNodeOnQueue.getItemsIgnored());
			
			nextLevelItems.add(level);
			
			nextLevelLeftChild.setItemsIgnored(nextLevelItems);

			// New profit in the next level
			double newProfit = currentNodeOnQueue.getProfit() + table[level - 1][0];
			// New weight in the next level
			double newWeight = currentNodeOnQueue.getWeight() + table[level - 1][1];

			// Create a right child for the next level
			Node nextLevelRightChild = new Node(null, currentNodeOnQueue.getItemsIgnored(), level, newProfit,
					newWeight);
			nextLevelItems = new ArrayList<>(currentNodeOnQueue.getItemsChosen());

			nextLevelItems.add(level);

			nextLevelRightChild.setItemsChosen(nextLevelItems);

			// Set the current next level nodes as right and left child
			rightChild = nextLevelRightChild;
			leftChild = nextLevelLeftChild;
		}
	}

	/**
	 * Calculate the bound or maximum achievable profit using the current node
	 * 
	 * @param node
	 */
	private static void calculateBound(Node node) {
		if (node != null) {
			ArrayList<Integer> chosen = node.getItemsChosen();
			ArrayList<Integer> ignored = node.getItemsIgnored();

			// Bound contains both items chosen and ignored 
			if (chosen.size() + ignored.size() == table.length) {
				node.setBound(node.getProfit());
				return;
			}

			// Set flag to check if items have been skipped
			boolean skipItem = false;
			
			// Current bound is the profit of the current node 
			double bound = node.getProfit();
			
			double totalWeight = node.getWeight();
			
			for (int i = 0; i < table.length; i++) {
				double profit = table[i][0];
				double weight = table[i][1];
				double pPerW = table[i][2];

				// Ignore items from bound 
				if (!chosen.isEmpty() || !ignored.isEmpty()) {
					if (chosen.contains(i + 1) || ignored.contains(i + 1)) {
						skipItem = true;
					}
				}

				// Add items 
				if (!skipItem) {
					if ((weight + totalWeight) <= sackCapacity) {
						totalWeight += weight;
						bound += profit;
					} else {
						
						bound += (sackCapacity - totalWeight) * pPerW;
						
						break;
					}
				}
				skipItem = false;
			}

			node.setBound(bound);
		}

	}

}
