package Arrays;

import java.util.HashMap;
import java.util.Map;

/**
 * Two Sum Problem - Find two numbers in an array that sum to a target.
 * 
 * <p>
 * Given an array of integers nums and an integer target, return indices
 * of the two numbers that add up to target.
 * 
 * <p>
 * Time Complexity: O(n) using HashMap approach
 * <p>
 * Space Complexity: O(n) for the HashMap
 * 
 * <p>
 * Problem from LeetCode #1: https://leetcode.com/problems/two-sum/
 * 
 * @SebaDevCom
 * @version 1.0
 * @since 2026-03-21
 */
public class TwoSum {

    /**
     * Finds two numbers that sum to the target using a HashMap.
     * This is the optimal solution with O(n) time complexity.
     * 
     * @param nums   The input array of integers
     * @param target The target sum
     * @return An array containing the indices of the two numbers,
     *         or an empty array if no solution exists
     * 
     * @throws IllegalArgumentException if the array is null
     */
    public static int[] findTwoSum(int[] nums, int target) {
        if (nums == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }

        // Map to store value -> index
        Map<Integer, Integer> seen = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            // Check if the complement exists in the map
            if (seen.containsKey(complement)) {
                return new int[] { seen.get(complement), i };
            }

            // Store the current value with its index
            seen.put(nums[i], i);
        }

        // No solution found
        return new int[0];
    }

    /**
     * Alternative solution: Brute force approach with O(n²) time complexity.
     * Good for understanding but not optimal for large inputs.
     * 
     * @param nums   The input array of integers
     * @param target The target sum
     * @return An array containing the indices of the two numbers,
     *         or an empty array if no solution exists
     */
    public static int[] findTwoSumBruteForce(int[] nums, int target) {
        if (nums == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[] { i, j };
                }
            }
        }

        return new int[0];
    }

    /**
     * Helper method to print results.
     * 
     * @param nums   The original array
     * @param target The target sum
     * @param result The result indices
     */
    private static void printResult(int[] nums, int target, int[] result) {
        System.out.print("Array: ");
        for (int num : nums) {
            System.out.print(num + " ");
        }
        System.out.println();
        System.out.println("Target: " + target);

        if (result.length == 2) {
            System.out.println("Result: indices [" + result[0] + ", " + result[1] + "]");
            System.out.println("Values: " + nums[result[0]] + " + " + nums[result[1]] + " = " + target);
        } else {
            System.out.println("Result: No solution found");
        }
        System.out.println();
    }

    /**
     * Main method with comprehensive test cases.
     */
    public static void main(String[] args) {
        System.out.println("=== Two Sum Problem Test Cases ===\n");

        // Test case 1: Basic case
        int[] nums1 = { 2, 7, 11, 15 };
        int target1 = 9;
        int[] result1 = findTwoSum(nums1, target1);
        printResult(nums1, target1, result1);

        // Test case 2: Numbers with negative values
        int[] nums2 = { 3, 5, -4, 8, 11, 1, -1, 6 };
        int target2 = 10;
        int[] result2 = findTwoSum(nums2, target2);
        printResult(nums2, target2, result2);

        // Test case 3: Duplicate numbers
        int[] nums3 = { 3, 3, 4, 5 };
        int target3 = 6;
        int[] result3 = findTwoSum(nums3, target3);
        printResult(nums3, target3, result3);

        // Test case 4: No solution
        int[] nums4 = { 1, 2, 3, 4 };
        int target4 = 10;
        int[] result4 = findTwoSum(nums4, target4);
        printResult(nums4, target4, result4);

        // Test case 5: Single element array (no solution)
        int[] nums5 = { 5 };
        int target5 = 5;
        int[] result5 = findTwoSum(nums5, target5);
        printResult(nums5, target5, result5);
    }
}