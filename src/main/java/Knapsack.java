/**
 * The Knapsack class solves the 0/1 Knapsack problem using dynamic programming.
 * Given a set of items with weights and values, and a knapsack with a weight limit,
 * it determines the maximum value of items that can be included in the knapsack.
 * Each item can only be fully included or excluded.
 @Author Rylin Combs
 Date: 4/26/2024
 */
public class Knapsack {

    /**
     * Returns the maximum of two integers.
     *
     * @param a First integer
     * @param b Second integer
     * @return Maximum of two integers
     */
    static int max(int a, int b) {
        return (a > b) ? a : b;
    }

    /**
     * Returns maximum value that can be put in a knapsack of capacity W.
     *
     * @param W   Maximum weight capacity of knapsack
     * @param wt  Array of item weights
     * @param val Array of item values
     * @param n   Number of items
     * @return Maximum value that can be put in the knapsack
     */
    static int knapsack(int W, int[] wt, int[] val, int n) {
        int[][] K = new int[n + 1][W + 1]; // 2D array to store intermediate results

        // Build table K[][] in bottom up manner
        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                if (i == 0 || w == 0)
                    K[i][w] = 0; // Base case: no items or knapsack capacity is 0
                else if (wt[i - 1] <= w)
                    // If the current item's weight is less than or equal to the knapsack capacity,
                    // choose the maximum of including and excluding the current item
                    K[i][w] = max(val[i - 1] + K[i - 1][w - wt[i - 1]], K[i - 1][w]);
                else
                    // If the current item's weight is more than the knapsack capacity, exclude it
                    K[i][w] = K[i - 1][w];
            }
        }
        return K[n][W]; // Return the maximum value that can be put in the knapsack
    }

    /**
     * Main method to test the Knapsack algorithm with sample inputs.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        int[] val = {60, 100, 120, 160, 50, 110, 150, 200}; // Values of items
        int[] wt = {10, 20, 30, 40, 10, 25, 35, 45}; // Weights of items
        int W = 120; // Maximum weight capacity of knapsack
        int n = val.length; // Number of items

        // Print the maximum value that can be put in a knapsack of capacity W
        System.out.println("Maximum value that can be put in a knapsack of capacity W = " +
                W + " is " + knapsack(W, wt, val, n));
    }
}
