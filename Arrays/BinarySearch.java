package Arrays;

/**
 * Binary Search implementation on a sorted array.
 * 
 * <p>Time Complexity: O(log n)
 * <p>Space Complexity: O(1)
 * 
 * <p>Binary search is an efficient algorithm for finding an element
 * in a sorted array by repeatedly dividing the search interval in half.
 * 
 * @SebaDevCom
 * @version 1.0
 * @since 2026-03-21
 */
public class BinarySearch {
    
    /**
     * Performs binary search on a sorted integer array.
     * 
     * @param arr The sorted array to search in (must be sorted in ascending order)
     * @param target The value to search for
     * @return The index of the target if found, otherwise -1
     * 
     * @throws IllegalArgumentException if the array is null
     */
    public static int search(int[] arr, int target) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        
        int left = 0;
        int right = arr.length - 1;
        
        while (left <= right) {
            // Avoid overflow by using this formula instead of (left + right) / 2
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == target) {
                return mid;  // Target found
            }
            
            if (arr[mid] < target) {
                left = mid + 1;  // Search in the right half
            } else {
                right = mid - 1;  // Search in the left half
            }
        }
        
        return -1;  // Target not found
    }
    
    /**
     * Recursive implementation of binary search.
     * 
     * @param arr The sorted array to search in
     * @param target The value to search for
     * @param left The left boundary index
     * @param right The right boundary index
     * @return The index of the target if found, otherwise -1
     */
    public static int searchRecursive(int[] arr, int target, int left, int right) {
        if (left > right) {
            return -1;
        }
        
        int mid = left + (right - left) / 2;
        
        if (arr[mid] == target) {
            return mid;
        }
        
        if (arr[mid] < target) {
            return searchRecursive(arr, target, mid + 1, right);
        } else {
            return searchRecursive(arr, target, left, mid - 1);
        }
    }
    
    /**
     * Main method with test cases.
     */
    public static void main(String[] args) {
        // Test case 1: Normal array
        int[] numbers = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19};
        
        System.out.println("=== Binary Search Test Cases ===\n");
        
        // Test iterative version
        System.out.println("Iterative Binary Search:");
        System.out.println("Searching for 7: index = " + search(numbers, 7));     // Expected: 3
        System.out.println("Searching for 1: index = " + search(numbers, 1));     // Expected: 0
        System.out.println("Searching for 19: index = " + search(numbers, 19));   // Expected: 9
        System.out.println("Searching for 20: index = " + search(numbers, 20));   // Expected: -1
        
        // Test recursive version
        System.out.println("\nRecursive Binary Search:");
        System.out.println("Searching for 7: index = " + 
            searchRecursive(numbers, 7, 0, numbers.length - 1));                  // Expected: 3
        System.out.println("Searching for 20: index = " + 
            searchRecursive(numbers, 20, 0, numbers.length - 1));                 // Expected: -1
        
        // Test edge case: single element array
        int[] singleElement = {42};
        System.out.println("\nEdge Cases:");
        System.out.println("Single element [42], search 42: index = " + search(singleElement, 42));  // Expected: 0
        System.out.println("Single element [42], search 10: index = " + search(singleElement, 10));  // Expected: -1
        
        // Test edge case: empty array
        int[] empty = {};
        System.out.println("Empty array, search any: index = " + search(empty, 5));                  // Expected: -1
    }
}