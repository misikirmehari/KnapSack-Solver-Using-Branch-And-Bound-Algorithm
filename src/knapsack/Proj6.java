package knapsack;

/**
* Implementation of Branch and Bound Algorithm on a 0-1 KnapSack Problem
* CS 4050 : Project 6
*
* @author Abdalla Elmedani
* @author Misikir Mehari
*
*/

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Proj6 {

	static int sackCapacity = 0;
	static double[][] itemsToChooseFrom = null;

	
	public static void main(String[] args) {

		// Get input from user
		itemsToChooseFrom = getDataFromFile();

		// Create a new KnapSack Object using the Sack Capacity and items
		KnapSack ks = new KnapSack(sackCapacity, itemsToChooseFrom);

		// Display the contents of the table
		printTableDetails();

		// Apply branch and bound algorithm on the knapsack object
		ks.applyBranchAndBoundAlgorithm();
	}

	
	
	/**
	 * Get KnapSack Data file name from user and populate a 2-D Array Table with
	 * the items weight , profit and profit per weight
	 * 
	 * @return Return table with the items' profit , weight and profit/Weight
	 */
	private static double[][] getDataFromFile() {
		double[][] table = null;
		Scanner userInput = new Scanner(System.in);

		System.out.print("Enter the name of the data file: ");
		String fileName = userInput.next();

		try {
			// use the name of the file specified by the user to read from
			Scanner input = new Scanner(new FileInputStream(fileName));

			// Use the first line of the file to set the maximum weight allowed
			// of the sack
			sackCapacity = input.nextInt();

			table = new double[input.nextInt()][3];

			for (int i = 0; i < table.length; i++) {
				double profit = input.nextInt();
				double weight = input.nextInt();
				double pPerWeight = profit / weight;
				table[i][0] = profit;
				table[i][1] = weight;
				table[i][2] = pPerWeight;
			}
			input.close();
		} catch (FileNotFoundException e) {

			System.out.println("File not found ");
			System.exit(0);
		}
		userInput.close();
		return table;
	}

	/**
	 * Displays the current contents of the file
	 */
	private static void printTableDetails() {
		System.out.println("Capacity of knapsack is " + sackCapacity);
		System.out.println("Items are:");

		for (int i = 0; i < itemsToChooseFrom.length; i++) {
			System.out.println(
					" " + (i + 1) + ": " + (int) itemsToChooseFrom[i][0] + " " + (int) itemsToChooseFrom[i][1]);

		}

	}

}
