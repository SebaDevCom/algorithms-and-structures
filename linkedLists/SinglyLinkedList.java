package linkedLists;  // Nota: cambiado a minúscula para seguir convención Java

import java.util.NoSuchElementException;
import java.util.Iterator;

/**
 * Singly Linked List Implementation with generic type support.
 * 
 * <p>
 * A singly linked list where each node contains a reference to the next node.
 * Traversal is only possible in one direction (forward).
 * 
 * <p>
 * Time Complexities:
 * <ul>
 *   <li>addFirst: O(1)</li>
 *   <li>addLast: O(1) with tail pointer, O(n) without</li>
 *   <li>removeFirst: O(1)</li>
 *   <li>removeLast: O(n)</li>
 *   <li>add/remove at index: O(n)</li>
 *   <li>get/set at index: O(n)</li>
 *   <li>search: O(n)</li>
 * </ul>
 * 
 * <p>
 * Space Complexity: O(n) where n is the number of elements
 * 
 * @author SebaDevCom
 * @version 1.0
 * @since 2026-03-24
 * @param <T> The type of elements stored in the list
 */
public class SinglyLinkedList<T> implements Iterable<T> {
    
    /**
     * Node class representing an element in the singly linked list.
     * Each node contains data and a reference to the next node.
     */
    private static class Node<T> {
        T data;
        Node<T> next;
        
        Node(T data) {
            this.data = data;
            this.next = null;
        }
        
        Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }
    
    private Node<T> head;  // First node of the list
    private Node<T> tail;  // Last node of the list (optional optimization)
    private int size;      // Number of elements in the list
    
    /**
     * Constructs an empty singly linked list.
     */
    public SinglyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }
    
    /**
     * Returns the number of elements in the list.
     * 
     * @return The size of the list
     */
    public int size() {
        return size;
    }
    
    /**
     * Checks if the list is empty.
     * 
     * @return true if the list contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Adds an element to the beginning of the list.
     * Time Complexity: O(1)
     * 
     * @param data The element to add
     */
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data, head);
        head = newNode;
        
        if (size == 0) {  // Was empty
            tail = newNode;
        }
        
        size++;
    }
    
    /**
     * Adds an element to the end of the list.
     * Time Complexity: O(1) with tail pointer
     * 
     * @param data The element to add
     */
    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        
        size++;
    }
    
    /**
     * Adds an element at the specified index.
     * Time Complexity: O(n)
     * 
     * @param index The position where to insert the element (0-based)
     * @param data The element to add
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public void add(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        if (index == 0) {
            addFirst(data);
        } else if (index == size) {
            addLast(data);
        } else {
            Node<T> prev = getNodeAtIndex(index - 1);
            Node<T> newNode = new Node<>(data, prev.next);
            prev.next = newNode;
            size++;
        }
    }
    
    /**
     * Removes and returns the first element of the list.
     * Time Complexity: O(1)
     * 
     * @return The removed first element
     * @throws NoSuchElementException if the list is empty
     */
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove from empty list");
        }
        
        T data = head.data;
        head = head.next;
        size--;
        
        if (isEmpty()) {
            tail = null;
        }
        
        return data;
    }
    
    /**
     * Removes and returns the last element of the list.
     * Time Complexity: O(n) because we need to find the second-to-last node
     * 
     * @return The removed last element
     * @throws NoSuchElementException if the list is empty
     */
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove from empty list");
        }
        
        if (size == 1) {
            return removeFirst();
        }
        
        // Find the second-to-last node
        Node<T> current = head;
        while (current.next != tail) {
            current = current.next;
        }
        
        T data = tail.data;
        current.next = null;
        tail = current;
        size--;
        
        return data;
    }
    
    /**
     * Removes the element at the specified index.
     * Time Complexity: O(n)
     * 
     * @param index The index of the element to remove
     * @return The removed element
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        if (index == 0) {
            return removeFirst();
        }
        
        Node<T> prev = getNodeAtIndex(index - 1);
        T data = prev.next.data;
        prev.next = prev.next.next;
        size--;
        
        // Update tail if we removed the last element
        if (index == size) {
            tail = prev;
        }
        
        return data;
    }
    
    /**
     * Removes the first occurrence of the specified element.
     * Time Complexity: O(n)
     * 
     * @param data The element to remove
     * @return true if the element was found and removed
     */
    public boolean remove(T data) {
        if (isEmpty()) {
            return false;
        }
        
        // Special case: removing the head
        if (head.data.equals(data)) {
            removeFirst();
            return true;
        }
        
        Node<T> current = head;
        while (current.next != null) {
            if (current.next.data.equals(data)) {
                current.next = current.next.next;
                size--;
                
                // Update tail if we removed the last element
                if (current.next == null) {
                    tail = current;
                }
                
                return true;
            }
            current = current.next;
        }
        
        return false;
    }
    
    /**
     * Gets the element at the specified index.
     * Time Complexity: O(n)
     * 
     * @param index The index of the element to retrieve
     * @return The element at the specified index
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        return getNodeAtIndex(index).data;
    }
    
    /**
     * Updates the element at the specified index.
     * Time Complexity: O(n)
     * 
     * @param index The index of the element to update
     * @param data The new value
     * @return The previous value at the index
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public T set(int index, T data) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        Node<T> node = getNodeAtIndex(index);
        T oldData = node.data;
        node.data = data;
        
        return oldData;
    }
    
    /**
     * Returns the index of the first occurrence of the specified element.
     * Time Complexity: O(n)
     * 
     * @param data The element to search for
     * @return The index of the element, or -1 if not found
     */
    public int indexOf(T data) {
        Node<T> current = head;
        int index = 0;
        
        while (current != null) {
            if (current.data.equals(data)) {
                return index;
            }
            current = current.next;
            index++;
        }
        
        return -1;
    }
    
    /**
     * Checks if the list contains the specified element.
     * Time Complexity: O(n)
     * 
     * @param data The element to check
     * @return true if the element is present
     */
    public boolean contains(T data) {
        return indexOf(data) != -1;
    }
    
    /**
     * Returns the first element of the list without removing it.
     * 
     * @return The first element
     * @throws NoSuchElementException if the list is empty
     */
    public T peekFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return head.data;
    }
    
    /**
     * Returns the last element of the list without removing it.
     * 
     * @return The last element
     * @throws NoSuchElementException if the list is empty
     */
    public T peekLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return tail.data;
    }
    
    /**
     * Removes all elements from the list.
     */
    public void clear() {
        head = tail = null;
        size = 0;
    }
    
    /**
     * Returns an array containing all elements in the list.
     * 
     * @return An array of elements in proper sequence
     */
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] array = (T[]) new Object[size];
        Node<T> current = head;
        int i = 0;
        
        while (current != null) {
            array[i++] = current.data;
            current = current.next;
        }
        
        return array;
    }
    
    /**
     * Helper method to get node at a specific index.
     * 
     * @param index The index of the node to retrieve
     * @return The node at the specified index
     */
    private Node<T> getNodeAtIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }
    
    /**
     * Reverses the linked list iteratively.
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public void reverse() {
        if (isEmpty() || size == 1) {
            return;
        }
        
        Node<T> prev = null;
        Node<T> current = head;
        Node<T> next = null;
        
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        
        // Update head and tail
        tail = head;
        head = prev;
    }
    
    /**
     * Finds the middle node of the linked list.
     * Uses Floyd's Tortoise and Hare algorithm.
     * 
     * @return The middle element
     */
    public T findMiddle() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        
        Node<T> slow = head;
        Node<T> fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return slow.data;
    }
    
    /**
     * Checks if the linked list has a cycle.
     * 
     * @return true if a cycle is detected
     */
    public boolean hasCycle() {
        if (isEmpty()) {
            return false;
        }
        
        Node<T> slow = head;
        Node<T> fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if (slow == fast) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Returns a string representation of the list.
     * 
     * @return A string showing the list elements in order
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = head;
        
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(" -> ");
            }
            current = current.next;
        }
        
        sb.append("]");
        return sb.toString();
    }
    
    /**
     * Returns an iterator over the elements in the list.
     * 
     * @return An iterator for forward traversal
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;
            
            @Override
            public boolean hasNext() {
                return current != null;
            }
            
            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }
    
    /**
     * Main method with comprehensive test cases.
     */
    public static void main(String[] args) {
        System.out.println("=== Singly Linked List Test Cases ===\n");
        
        // Test 1: Basic operations
        System.out.println("Test 1: Basic Operations");
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        
        list.addLast("A");
        list.addLast("B");
        list.addLast("C");
        System.out.println("After adding A, B, C: " + list);
        System.out.println("Size: " + list.size());
        System.out.println("First element: " + list.peekFirst());
        System.out.println("Last element: " + list.peekLast());
        
        // Test 2: Add at beginning
        System.out.println("\nTest 2: Add at Beginning");
        list.addFirst("X");
        System.out.println("After addFirst('X'): " + list);
        
        // Test 3: Add at index
        System.out.println("\nTest 3: Add at Index");
        list.add(2, "Z");
        System.out.println("After add(2, 'Z'): " + list);
        
        // Test 4: Remove operations
        System.out.println("\nTest 4: Remove Operations");
        System.out.println("Removed first: " + list.removeFirst());
        System.out.println("After removeFirst: " + list);
        System.out.println("Removed last: " + list.removeLast());
        System.out.println("After removeLast: " + list);
        System.out.println("Removed at index 1: " + list.remove(1));
        System.out.println("After remove(1): " + list);
        
        // Test 5: Search operations
        System.out.println("\nTest 5: Search Operations");
        list.clear();
        list.addLast("Apple");
        list.addLast("Banana");
        list.addLast("Cherry");
        list.addLast("Date");
        System.out.println("List: " + list);
        System.out.println("Index of 'Banana': " + list.indexOf("Banana"));
        System.out.println("Contains 'Cherry'? " + list.contains("Cherry"));
        System.out.println("Contains 'Grape'? " + list.contains("Grape"));
        
        // Test 6: Get and Set
        System.out.println("\nTest 6: Get and Set");
        System.out.println("Element at index 2: " + list.get(2));
        System.out.println("Set index 2 to 'Coconut': " + list.set(2, "Coconut"));
        System.out.println("After set: " + list);
        
        // Test 7: Reverse
        System.out.println("\nTest 7: Reverse List");
        System.out.println("Original: " + list);
        list.reverse();
        System.out.println("Reversed: " + list);
        list.reverse(); // Reverse back
        System.out.println("Back to original: " + list);
        
        // Test 8: Find Middle
        System.out.println("\nTest 8: Find Middle");
        System.out.println("List: " + list);
        System.out.println("Middle element: " + list.findMiddle());
        
        SinglyLinkedList<Integer> evenList = new SinglyLinkedList<>();
        for (int i = 1; i <= 4; i++) {
            evenList.addLast(i);
        }
        System.out.println("Even list: " + evenList);
        System.out.println("Middle element: " + evenList.findMiddle());
        
        // Test 9: Iterator
        System.out.println("\nTest 9: Iterator");
        System.out.print("Iterating: ");
        for (String item : list) {
            System.out.print(item + " ");
        }
        System.out.println();
        
        // Test 10: Remove by value
        System.out.println("\nTest 10: Remove by Value");
        list.clear();
        list.addLast("One");
        list.addLast("Two");
        list.addLast("Three");
        list.addLast("Two");
        list.addLast("Four");
        System.out.println("Before removal: " + list);
        System.out.println("Remove 'Two': " + list.remove("Two"));
        System.out.println("After removal: " + list);
        System.out.println("Remove 'Two' again: " + list.remove("Two"));
        System.out.println("Final list: " + list);
        
        // Test 11: Edge cases
        System.out.println("\nTest 11: Edge Cases");
        SinglyLinkedList<Integer> emptyList = new SinglyLinkedList<>();
        System.out.println("Empty list: " + emptyList);
        System.out.println("Is empty? " + emptyList.isEmpty());
        System.out.println("Size: " + emptyList.size());
        
        // Test 12: Single element
        System.out.println("\nTest 12: Single Element");
        SinglyLinkedList<String> singleList = new SinglyLinkedList<>();
        singleList.addLast("Only");
        System.out.println("List: " + singleList);
        System.out.println("First: " + singleList.peekFirst());
        System.out.println("Last: " + singleList.peekLast());
        System.out.println("Middle: " + singleList.findMiddle());
        singleList.reverse();
        System.out.println("After reverse: " + singleList);
        
        // Test 13: Performance test
        System.out.println("\nTest 13: Performance Test");
        SinglyLinkedList<Integer> perfList = new SinglyLinkedList<>();
        long startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            perfList.addLast(i);
        }
        long endTime = System.nanoTime();
        System.out.println("Added 10,000 elements in: " + (endTime - startTime) / 1_000_000 + " ms");
        System.out.println("Final size: " + perfList.size());
        
        // Test 14: Cycle detection
        System.out.println("\nTest 14: Cycle Detection");
        System.out.println("List has cycle? " + list.hasCycle());
    }
}