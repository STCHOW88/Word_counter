/*Yingjie Ni
 * UFID: 41267952
 * Date: Nov.15 2018
 */
import java.util.*;


public class FibonacciHeap
{

    private Node maxNode;
    private int i;// i for counting nodes number

    public void insert(Node node)
    {
        if (maxNode != null) {

            node.left = maxNode;
            node.right = maxNode.right;
            maxNode.right = node;
            
        	if (node.key > maxNode.key) {
                maxNode = node;
            }// compare key of node to maxnode firstly
            
            if ( node.right==null)
            {
                node.right = maxNode;
                maxNode.left = node;
            }
            
            if ( node.right!=null) {                               
                node.right.left = node;
            }
             
        } else {
            maxNode = node;// if there is no node

        }

        i++;
    }

    public void increaseKey(Node node, int i)
    {
    	node.key = node.key + i;

        Node parent = node.parent;

        if ((parent != null) && (node.key > parent.key)) {
            remove(node);
            cascadingCut(parent);
        }

        if (node.key > maxNode.key) {
            maxNode = node;
        }
    }// increase key then perform cascading cut if necessary
    
    public void cascadingCut(Node node)
    {
        Node parent = node.parent;

        if (parent != null) {
        	
            if (!node.cascadingCut) {
                node.cascadingCut = true;
            } else {
            	
                remove(node);

                cascadingCut(parent);
            }
        }
    }// cascading cut

    public void remove(Node node)
    {
    	Node parent = node.parent;
    	node.left.right = node.right;
    	node.right.left = node.left;
    	parent.degree--;

    	if (parent.degree == 0) {
        	parent.child = null;
        }// check if parent's degree is 0

        if (parent.child == node) {
        	parent.child = node.right;
        }
        
        node.left = maxNode;
        node.right = maxNode.right;
        maxNode.right = node;
        node.right.left = node;

        node.parent = null;

        node.cascadingCut = false;// set cascading cut mark
    }
      //Increase the value of key for the given node in heap


    //Removes the maximum from the heap
    public Node removeMax()
    {
        Node m = maxNode;
        if (m != null) {
            int numberofChildren = m.degree;
            Node child = m.child;
            Node x;
            
            while (numberofChildren > 0) {
                x = child.right;

                child.left.right = child.right;
                child.right.left = child.left;
               
                child.left = maxNode;
                child.right = maxNode.right;
                maxNode.right = child;
                child.right.left = child; // add child to root list of heap

                child.parent = null;
                child = x;

                numberofChildren--;

            }

            m.left.right = m.right;
            m.right.left = m.left;// remove max node

            if (m == m.right) {
                maxNode = null;

            } else {
               maxNode = m.right;
               merge();
           }
           i--;// decrease the number of nodes
           return m;
       }
        return null;
    }
    

    //performs degree wise merge(if 2 degrees are same then it merges it)
    public void merge()
    {
        int sizeofDegreeTable = 50;// size of degree table


        List<Node> degreeTable =
        new ArrayList<Node>(sizeofDegreeTable);
     
        for (int i = 0; i < sizeofDegreeTable; i++) {
            degreeTable.add(null);// Initialization
        }
                       
        int numOfRoots = 0;
        Node x = maxNode;

        if (x != null) {
            numOfRoots++;
            x = x.right;                     

            while (x != maxNode) {
                numOfRoots++;
                x = x.right;
            }
        }

        while (numOfRoots > 0) {

            int d = x.degree;
            Node next = x.right;

            for (;;) {
                Node y = degreeTable.get(d);
                if (y == null) {
                    break;
                }// check if there is a same degree

                if (x.key < y.key) {
                    Node temp = y;
                    y = x;
                    x = temp;
                }

                makeChild(y, x);// pairwise combine

                degreeTable.set(d, null);
                d++;
            }

            degreeTable.set(d, x);// update degree table

            x = next;
            numOfRoots--;
        }

        maxNode = null;

        for (int i = 0; i < sizeofDegreeTable; i++) {
            Node y = degreeTable.get(i);
            if (y == null) {
                continue;
            }

            //till max node is not null
            if (maxNode != null) {

                y.left.right = y.right;
                y.right.left = y.left;// First remove node from root list.

                y.left = maxNode;
                y.right = maxNode.right;
                maxNode.right = y;
                y.right.left = y;
           
                if (y.key > maxNode.key) {
                    maxNode = y;
                } // compare y's key with max node's
            } else {
                maxNode = y;
            }
        }
    }
    

    //Makes y the child of node x
    public void makeChild(Node y, Node x)
    {
    	
        y.left.right = y.right;
        y.right.left = y.left;

        y.parent = x;// let x become parent of y

        if (x.child == null) {
            x.child = y;
            y.right = y;
            y.left = y;
        } else {
            y.left = x.child;
            y.right = x.child.right;
            x.child.right = y;
            y.right.left = y;
        }

        x.degree++;

        y.cascadingCut = false;// set cascading cut to false
    }

}