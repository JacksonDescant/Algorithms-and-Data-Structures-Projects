package dendrologist;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * A testbed for an augmented implementation of an AVL tree
 * @author William Duncan, Jackson Descant
 * @see AVLTreeAPI, AVLTree
 * <pre>
 * Date: 10-18-2023
 * Course: csc 3102 
 * Programming Project # 2
 * Instructor: Dr. Duncan 
 * </pre>
 */
public class Dendrologist
{
    public static void main(String[] args) throws FileNotFoundException, AVLTreeException 
    {
        String usage = "Dendrologist <order-code> <command-file>\n";
        usage += "  <order-code>:\n";
        usage += "  0 ordered by increasing string length, primary key, and reverse lexicographical order, secondary key\n";
        usage += "  -1 for reverse lexicographical order\n";
        usage += "  1 for lexicographical order\n";
        usage += "  -2 ordered by decreasing string length\n";
        usage += "  2 ordered by increasing string length\n";
        usage += "  -3 ordered by decreasing string length, primary key, and reverse lexicographical order, secondary key\n";
        usage += "  3 ordered by increasing string length, primary key, and lexicographical order, secondary key\n";      
        if (args.length != 2)
        {
            System.out.println(usage);
            throw new IllegalArgumentException("There should be 2 command line arguments.");
        }
       
        int orderCode = Integer.parseInt(args[0]);
        String fileName = args[1];
        
        AVLTree<String> strings = new AVLTree<String>();
        
        switch (orderCode) {
        
        	case 0:
		        Comparator<String> zero = (x, y) -> 
		        {
		        	
		        	if(x.length()>y.length())
		        	{
						return 1;
					}
					else if(x.length()<y.length()) 
					{
						return -1;
					}
					else 
					{
						return y.compareTo(x);
					}
		        	
		        };
		        usage += "  0 ordered by increasing string length, primary key, and reverse lexicographical order, secondary key\n";
		        strings = new AVLTree<String>(zero);
		        break;
        	
        	case -1:
        		Comparator<String> negOne = (x, y) ->
        		{
        			return y.compareTo(x);        		
        		};
        		usage += "  -1 for reverse lexicographical order\n";
        		strings = new AVLTree<String>(negOne);
		        break;
		        
        	case 1:
        		Comparator<String> one = (x, y) ->
        		{
        			return x.compareTo(y);        		
        		};
                usage += "  1 for lexicographical order\n";
        		strings = new AVLTree<String>(one);
		        break;
		        
        	case -2:
        		Comparator<String> negTwo = (x, y) ->
        		{
        			if(x.length()<y.length())
        			{
                        return 1;
                    }
                    else if(x.length()>y.length()) 
                    {
                        return -1;
                    }
                    else 
                    {
                        return -1;
                    }        		
        		};
                usage += "  -2 ordered by decreasing string length\n";
        		strings = new AVLTree<String>(negTwo);
		        break;
		        
        	case 2:
        		Comparator<String> two = (x, y) ->
        		{
        			if(y.length()<x.length()) 
        			{
                        return 1;
                    }
                    else if(y.length()<x.length()) 
                    {
                        return -1;
                    }
                    else 
                    {
                        return -1;
                    }
        		};
                usage += "  2 ordered by increasing string length\n";
        		strings = new AVLTree<String>(two);
		        break;
		        
        	case -3:
        		Comparator<String> negThree = (x, y) -> 
        		{
        			if(x.length()<y.length()) 
        			{
    					return 1;
        		    }
    				else if(x.length()>y.length())
    				{
    					return -1;
    				}
    				else 
    				{
    					return y.compareTo(x);
    				}
    				
        		};
                usage += "  -3 ordered by decreasing string length, primary key, and reverse lexicographical order, secondary key\n";
        		strings = new AVLTree<String>(negThree);
		        break;
		        
        	case 3:
        		Comparator<String> three = (x, y) ->
        		{
        			if(x.length()>y.length()) 
        			{
    					return 1;
        		    }
    				else if(x.length()<y.length())
    				{
    					return -1;
    				}
    				else 
    				{
    					return y.compareTo(x);
    				}      		
        		};
                usage += "  3 ordered by increasing string length, primary key, and lexicographical order, secondary key\n";      
        		strings = new AVLTree<String>(three);
		        break;
        
        }
        
        	FileReader reader = new FileReader(fileName);
        	Scanner fileScan = new Scanner(reader);
        	
        	while (fileScan.hasNextLine()) {
        		String fullString = fileScan.nextLine();
        		String[] splitter = fullString.split(" ");
        		
        		String command = splitter[0];
        		
        		switch(command) {
        		
        		case "props":
        		
        			System.out.println("Properties :");
        			System.out.println("size = " + strings.size() + ", height = " + strings.height() + ", diameter = " + strings.diameter());
        			System.out.println("fibonacci? = " + strings.isFibonacci() + ", complete? = " + strings.isComplete());
        			break;
        		
        		case "insert":
        			System.out.println("Inserted : " + splitter[1]);
        			strings.insert(splitter[1]);
        			break;
        		
        		case "gen":
        			
        			String check = splitter[1];
        			if (strings.inTree(check)) {
        
        			System.out.println("Geneology: " + splitter[1]);
        			
        			ArrayList<String> childNode = strings.getChildren(splitter[1]);
        			String leftChild = null;
        			String rightChild = null;
        			if (childNode.size() == 2) {
        				leftChild = childNode.get(0);
        				rightChild = childNode.get(1);
        			}
        			else if (childNode.size() == 1) {
        				leftChild = childNode.get(0);
        				rightChild = "NONE";
        			}
        			else if (childNode.size() == 0) {
        				rightChild = "NONE";
        				leftChild = "NONE";
        			}
        			
        		
        			
        			System.out.println("parent = " + strings.getParent(splitter[1]) + ", left-child = " + leftChild + ", right-child = " + rightChild);
        			System.out.println("#ancestors = " + strings.ancestors(splitter[1]) + ", #descendants = " + strings.descendants(splitter[1]));
        			}
        			else System.out.println("Geneology : " + splitter[1] + " UNDEFINED");
        			break;
        		case "traverse":		
        			
        			System.out.println("pre-Order Traversal :");
        			strings.preorderTraverse(x -> {System.out.println(x); return null;});
        			System.out.println("In-Order Traversal :");
        			strings.traverse(x -> {System.out.println(x); return null;});
        			System.out.println("post-Order Traversal :");
        			strings.postorderTraverse(x -> {System.out.println(x); return null;});

        			
        			break;
        		case "delete":
        			System.out.println("Deleted : " + splitter[1]);
        			strings.remove(splitter[1]);
        			break;
        		}
        		
        	}
        	fileScan.close();
        	
        }
        
}   


