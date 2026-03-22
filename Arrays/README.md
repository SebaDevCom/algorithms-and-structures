# 📊 Arrays

This folder contains implementations of common algorithms and solutions related to arrays.

## 📁 Files

| File | Description | Complexity |
|------|-------------|------------|
| `BinarySearch.java` | Binary search algorithm for sorted arrays | Time: O(log n), Space: O(1) |
| `TwoSum.java` | Find two numbers that sum to a target | Time: O(n), Space: O(n) |

---

## 🔍 Binary Search

Binary search is an efficient algorithm for finding an element in a **sorted array**. It works by repeatedly dividing the search interval in half.

### Key Concepts
- Requires the array to be **sorted**
- Much faster than linear search for large arrays
- Can be implemented iteratively or recursively

### Visual Example

/**
* Array: [1, 3, 5, 7, 9, 11, 13]
* Target: 7
*
* Step 1: [1, 3, 5, 7, 9, 11, 13] → mid = 7, found!
* ↑ ↑ ↑
* left mid right

/**


### Usage
```java
int[] numbers = {1, 3, 5, 7, 9};
int index = BinarySearch.search(numbers, 7);
// index = 3

# Compile
javac arrays/BinarySearch.java
javac arrays/TwoSum.java

# Run
java arrays.BinarySearch
java arrays.TwoSum