package linkedLists;

import java.util.NoSuchElementException;
import java.util.Iterator;

/**
 * Doubly Linked List Implementation with generic type support.
 * 
 * <p>
 * A doubly linked list where each node contains references to both
 * the next and previous nodes, allowing traversal in both directions.
 * 
 * <p>
 * Time Complexities:
 * <ul>
 *   <li>addFirst/addLast: O(1)</li>
 *   <li>removeFirst/removeLast: O(1)</li>
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
public class DoublyLinkedList<T> implements Iterable<T> {
    
    /**
     * Node class representing an element in the doubly linked list.
     * Each node contains data and references to next and previous nodes.
     */
    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;
        
        Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
        
        Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }
    
    private Node<T> head;  // First node of the list
    private Node<T> tail;  // Last node of the list
    private int size;      // Number of elements in the list
    
    /**
     * Constructs an empty doubly linked list.
     */
    public DoublyLinkedList() {
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
     * 
     * @param data The element to add
     */
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }
    
    /**
     * Adds an element to the end of the list.
     * 
     * @param data The element to add
     */
    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }
    
    /**
     * Adds an element at the specified index.
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
            Node<T> current = getNodeAtIndex(index);
            Node<T> newNode = new Node<>(data, current.prev, current);
            current.prev.next = newNode;
            current.prev = newNode;
            size++;
        }
    }
    
    /**
     * Removes and returns the first element of the list.
     * 
     * @return The removed first element
     * @throws NoSuchElementException if the list is empty
     */
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove from empty list");
        }
        
        T data = head.data;
        
        if (size == 1) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        
        size--;
        return data;
    }
    
    /**
     * Removes and returns the last element of the list.
     * 
     * @return The removed last element
     * @throws NoSuchElementException if the list is empty
     */
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove from empty list");
        }
        
        T data = tail.data;
        
        if (size == 1) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        
        size--;
        return data;
    }
    
    /**
     * Removes the element at the specified index.
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
        
        if (index == size - 1) {
            return removeLast();
        }
        
        Node<T> current = getNodeAtIndex(index);
        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;
        
        return current.data;
    }
    
    /**
     * Removes the first occurrence of the specified element.
     * 
     * @param data The element to remove
     * @return true if the element was found and removed
     */
    public boolean remove(T data) {
        Node<T> current = head;
        
        while (current != null) {
            if (current.data.equals(data)) {
                if (current == head) {
                    removeFirst();
                } else if (current == tail) {
                    removeLast();
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                    size--;
                }
                return true;
            }
            current = current.next;
        }
        
        return false;
    }
    
    /**
     * Gets the element at the specified index.
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
     * 
     * @param data The element to check
     * @return true if the element is present
     */
    public boolean contains(T data) {
        return indexOf(data) != -1;
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
     * Optimizes traversal by starting from the nearest end.
     * 
     * @param index The index of the node to retrieve
     * @return The node at the specified index
     */
    private Node<T> getNodeAtIndex(int index) {
        Node<T> current;
        
        // Optimize: traverse from the closer end
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        
        return current;
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
                sb.append(", ");
            }
            current = current.next;
        }
        
        sb.append("]");
        return sb.toString();
    }
    
    /**
     * Returns a string representation of the list in reverse order.
     * 
     * @return A string showing the list elements in reverse order
     */
    public String toStringReverse() {
        if (isEmpty()) {
            return "[]";
        }
        
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = tail;
        
        while (current != null) {
            sb.append(current.data);
            if (current.prev != null) {
                sb.append(", ");
            }
            current = current.prev;
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
     * Returns an iterator that traverses the list in reverse order.
     * 
     * @return An iterator for backward traversal
     */
    public Iterator<T> reverseIterator() {
        return new Iterator<T>() {
            private Node<T> current = tail;
            
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
                current = current.prev;
                return data;
            }
        };
    }
    
    /**
     * Main method with comprehensive test cases.
     */
    public static void main(String[] args) {
        System.out.println("=== Doubly Linked List Test Cases ===\n");
        
        // Test 1: Basic operations
        System.out.println("Test 1: Basic Operations");
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        
        list.addLast("A");
        list.addLast("B");
        list.addLast("C");
        System.out.println("After adding A, B, C: " + list);
        System.out.println("Size: " + list.size());
        System.out.println("Reverse: " + list.toStringReverse());
        
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
        
        // Test 7: Iterator
        System.out.println("\nTest 7: Forward Iterator");
        System.out.print("Forward traversal: ");
        for (String item : list) {
            System.out.print(item + " ");
        }
        System.out.println();
        
        System.out.print("Reverse traversal: ");
        Iterator<String> revIter = list.reverseIterator();
        while (revIter.hasNext()) {
            System.out.print(revIter.next() + " ");
        }
        System.out.println();
        
        // Test 8: Edge cases
        System.out.println("\nTest 8: Edge Cases");
        DoublyLinkedList<Integer> emptyList = new DoublyLinkedList<>();
        System.out.println("Empty list: " + emptyList);
        System.out.println("Is empty? " + emptyList.isEmpty());
        System.out.println("Size: " + emptyList.size());
        
        // Test with integers
        DoublyLinkedList<Integer> intList = new DoublyLinkedList<>();
        for (int i = 1; i <= 5; i++) {
            intList.addLast(i);
        }
        System.out.println("Integer list: " + intList);
        System.out.println("Sum of all elements: " + intList.stream().mapToInt(Integer::intValue).sum());
        
        // Test 9: Remove by value
        System.out.println("\nTest 9: Remove by Value");
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
        
        // Test 10: Performance test
        System.out.println("\nTest 10: Performance Test");
        DoublyLinkedList<Integer> perfList = new DoublyLinkedList<>();
        long startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            perfList.addLast(i);
        }
        long endTime = System.nanoTime();
        System.out.println("Added 10,000 elements in: " + (endTime - startTime) / 1_000_000 + " ms");
        System.out.println("Final size: " + perfList.size());
    }
}