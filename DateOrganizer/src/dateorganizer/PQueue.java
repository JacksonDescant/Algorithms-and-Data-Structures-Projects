package dateorganizer;

import java.util.*;

/**
 * This class describes a priority min-queue that uses an array-list-based min binary heap 
 * that implements the PQueueAPI interface. The array holds objects that implement the 
 * parameterized Comparable interface.
 * @author Duncan
 * @param <E> the priority queue element type. 
 * @since 09-24-2023
 */
public class PQueue<E extends Comparable<E>> implements PQueueAPI<E>
{    
   /**
    * A complete tree stored in an array list representing the 
    * binary heap
    */
   private ArrayList<E> tree;
   /**
    * A comparator lambda function that compares two elements of this
    * heap when rebuilding it; cmp.compare(x,y) gives 1. negative when x less than y
    * 2. positive when x greater than y 3. 0 when x equal y
    */   
   private Comparator<? super E> cmp;
   
   /**
    * Constructs an empty PQueue using the compareTo method of its data type as the 
	* comparator
    */
   public PQueue()
   {
	   tree = new ArrayList<E>();
	   cmp = (E obj1, E obj2) -> obj2.compareTo(obj1);
   }
   
   /**
    * A parameterized constructor that uses an externally defined comparator    
    * @param fn - a trichotomous integer value comparator function   
    */
   public PQueue(Comparator<? super E> fn)
   {
	  tree = new ArrayList<E>();
	  cmp = fn;
   }


public boolean isEmpty()
   {
      if (tree.size() < 1) {
    	  return true;
      }
      else return false;
   }

   public void insert(E obj)
   {
      tree.add(obj);
      int newItem = tree.size()-1;
      
      while(newItem > 0) {
    	  int parent = (newItem - 1) / 2;
    	  if (cmp.compare(tree.get(newItem), tree.get(parent)) > 0) {
    		  swap(newItem, parent);
    		  newItem = parent;
    	  }
    	  else break;
      }
   }

   public E remove() throws PQueueException
   {
      if (tree.isEmpty()) {
    	  return null;
      }
      else {
    	  E root = tree.get(0);
    	  int lastIndex = tree.size() - 1;
    	  E lastElement = tree.remove(lastIndex);
    	  
    	  if(tree.isEmpty()) {
    		  return root;
    	  }
    	  
    	  tree.set(0, lastElement);
    	  rebuild(0);
    	  return root;
      }
   }
 
   public E peek() throws PQueueException
   {
      if (tree.isEmpty()) {
    	  return null;
      }
      else return tree.get(0);
   }

   public int size()
   {
      return tree.size();
   }
   
   /**
    * Swaps a parent and child elements of this heap at the specified indices
    * @param place an index of the child element on this heap
    * @param parent an index of the parent element on this heap
    */
   private void swap(int place, int parent)
   {
     E x = tree.get(place);
     E y = tree.get(parent);
     tree.set(place, y);
     tree.set(parent, x);
   }

   /**
    * Rebuilds the heap to ensure that the heap property of the tree is preserved.
    * @param root the root index of the subtree to be rebuilt
    */
   private void rebuild(int root) {
	    int left = 2 * root + 1;
	    int right = 2 * root + 2;
	    int largest = root;

	    if (left < tree.size() && cmp.compare(tree.get(left), tree.get(largest)) > 0) {
	        largest = left;
	    }

	    if (right < tree.size() && cmp.compare(tree.get(right), tree.get(largest)) > 0) {
	        largest = right;
	    }

	    if (largest != root) {
	        swap(root, largest);
	        rebuild(largest);
	    }
	}

	   
            
}
