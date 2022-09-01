import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class AlexsBinarySearchTree
{
    int[] inputArray;
    String output = "Unsorted array:";
    int s = 0, b = 0,l = 0;
    
    private class AVLNode 
    {      
        int value;           // Value stored in this node.
        AVLNode left, right; // Left and right subtree.
        int height;          // Height of node.

        public AVLNode(int value)
        { 
            // Call the other (sister) constructor.
            this(value, null, null);
        }
        
        public AVLNode(int val, AVLNode left1, AVLNode right1)
        {
            value = val;
            left = left1;
            right = right1;
            height = 0;
        }
        
        /**
           The resetHeight methods recomputes height 
           if the left or right subtrees have changed.
        */
        
        void resetHeight()
        {
          int leftHeight = AlexsBinarySearchTree.getHeight(left);
          int rightHeight = AlexsBinarySearchTree.getHeight(right);      
          height = 1 + Math.max(leftHeight, rightHeight);
        }    
    } 
    
    /** 
      The BTreeDisplay class can graphically 
       display a binary tree.
    */
    
    private class BTreeDisplay extends JPanel
    {
        /**
           Constructor.
           @param tree The root of the binary
			  tree to display.
         */
        
        BTreeDisplay(AVLNode tree)
        {           
           setBorder(BorderFactory.createEtchedBorder());
           setLayout(new BorderLayout());
           if (tree != null) 
           {          
               String value = String.valueOf(tree.value);  
               int pos = SwingConstants.CENTER;
               JLabel rootLabel = new JLabel(value, pos);                         
               add(rootLabel, BorderLayout.NORTH);
               JPanel panel = new JPanel(new GridLayout(1, 2));
               panel.add(new BTreeDisplay(tree.left));
               panel.add(new BTreeDisplay(tree.right));    
               add(panel);
           }       
        }   
    }
    
    /**
       The getHeight method computes the 
       height of an AVL tree.
       @param tree An AVL tree.
		 @return The height of the AVL tree.
    */


    static int getHeight(AVLNode tree)
    {
      if (tree == null) return -1; 
      else return tree.height;       
    }
    
    /**
      The add method adds a value to this AVL tree.
      @param x The value to add.
      @return true. 
    */
    
    public boolean add(int x)
    {
       root = add(root, x);
       ++s;// add 1 to s
       if(s >= b)// if s is equal or more than b do the following
       {
         output = output + "\n" + "InOrder traversal: ";
         inorder();//call inorder method
         output = output + "\n" + "PreOrder traversal: ";
         preorder();//call preorder method
         output = output + "\n" + "PostOrder traversal: ";
         postorder(); //call postorder method
       }
       return true;
    }
    
    /**
       The getView method creates and returns a 
       a graphical view of the binary tree.
       @return A panel that displays a view of the tree.
    */
    
    public JPanel getView()
    {
        return new BTreeDisplay(root);
    }
    
    /**
       The isEmpty method checks if this AVL tree is empty.
       @return true if the tree is empty, false otherwise.
    */
    
    
    public boolean isEmpty() 
    {
        return root == null;
    }
    
    private AVLNode root = null;   // Root of this AVL tree  
	  
    /**
       The add method adds a value to an AVL tree.
       @return The root of the  augmented AVL tree.
    */
    
    
    private AVLNode add(AVLNode bTree, int x)
    {        
        if (bTree == null)
            return new AVLNode(x);        
        if (x < bTree.value)       
            bTree.left = add(bTree.left, x);       
        else       
            bTree.right = add(bTree.right, x);
        
        // Compute heights of the left and right subtrees
		  // and rebalance the tree if needed
        int leftHeight = getHeight(bTree.left);
        int rightHeight = getHeight(bTree.right);       
        if (Math.abs(leftHeight - rightHeight) == 2)
           return balance(bTree);
        else
        {
           bTree.resetHeight();
           return bTree;
        }     
    }
    /**
       The balance method rebalances an AVL tree. 
       @param bTree The AVL tree needing to be balanced.
       @return The balanced AVL tree.      
    */
    
    private AVLNode balance(AVLNode bTree)
    {
        int rHeight = getHeight(bTree.right);
        int lHeight = getHeight(bTree.left);
        
        if (rHeight > lHeight)
        {
            AVLNode rightChild = bTree.right;
            int rrHeight = getHeight(rightChild.right);
            int rlHeight = getHeight(rightChild.left);           
            if (rrHeight > rlHeight)           
                return rrBalance(bTree);            
            else            
                return rlBalance(bTree);            
        }
        else
        {
            AVLNode leftChild = bTree.left;
            int llHeight = getHeight(leftChild.left);
            int lrHeight = getHeight(leftChild.right);           
            if (llHeight > lrHeight)
                return llBalance(bTree);
            else
                return lrBalance(bTree);            
        }        
    }
    
    /** 
       The rrBlance method corrects an RR imbalance.
       @param bTree The AVL tree wih an RR imbalance.
       @return The balanced AVL tree.       
    */

    private AVLNode rrBalance(AVLNode bTree)
    {       
        AVLNode rightChild = bTree.right;
        AVLNode rightLeftChild = rightChild.left;
        rightChild.left = bTree;
        bTree.right = rightLeftChild;
        bTree.resetHeight();
        rightChild.resetHeight();
        return rightChild;
    }
    
    /**
       The rlBalance method corrects an RL imbalance.
       @parame bTree The AVL tree with an RL imbalance.
       @return The balanced AVL tree.
    */
    
    private AVLNode rlBalance(AVLNode bTree)
    {
        AVLNode root = bTree;
        AVLNode rNode = root.right;
        AVLNode rlNode = rNode.left;
        AVLNode rlrTree = rlNode.right;
        AVLNode rllTree = rlNode.left;
        
        // Build the restructured tree
        rNode.left = rlrTree;
        root.right = rllTree;
        rlNode.left = root;
        rlNode.right = rNode;
        
        // Adjust heights
        rNode.resetHeight();
        root.resetHeight();
        rlNode.resetHeight();
        
        return rlNode;        
    }
    
    /**
       The llBalance method corrects an LL imbalance.
       @param bTree The AVL tree with an LL imbalance.
       @return The balanced AVL tree.
    */
    
    private AVLNode llBalance(AVLNode bTree)
    {
        AVLNode leftChild = bTree.left;
        AVLNode lrTree = leftChild.right;
        leftChild.right = bTree;
        bTree.left = lrTree;
        bTree.resetHeight();
        leftChild.resetHeight();
        return leftChild;        
    }

   /**
       The lrBalance method corrects an LR imbalance.
       @param bTree The AVL tree with an LR imbalance.
       @return The balanced AVL tree.
    */
      
    private AVLNode lrBalance(AVLNode bTree)
    {
        AVLNode root = bTree;
        AVLNode lNode = root.left;
        AVLNode lrNode = lNode.right;
        AVLNode lrlTree = lrNode.left;
        AVLNode lrrTree = lrNode.right;
        
        // Build the restructured tree
        lNode.right = lrlTree;
        root.left = lrrTree;
        lrNode.left = lNode;
        lrNode.right = root;
        
        // Adjust heights
        lNode.resetHeight();
        root.resetHeight();
        lrNode.resetHeight();
		  
        return lrNode;        
    }
    
    public void arrayGen(int i)
    {
      Random rand = new Random();
    
      int y = 0;
      b = i;
      
      inputArray = new int[i];// make array to the length of
      //the input i
      
      for(int x = 0; x < i ; ++x)
      {
         //get random number and set it as input array
         inputArray[x] = rand.nextInt(100 - 0);
         
         y = inputArray[x];
         //make new lines at 30 60 and 90 intervals
         if(x == 30 || x == 60 || x == 90)
         {
            output = output + "\n";//new line
         }
         // add to output
         output = output + " " + String.valueOf(y);
      }
      
      quickSort(inputArray);//call quickSort to sort the array
      arrayToTree();//call array to tree
    }
    
    public void arrayToTree()
    {
      int y = 0;
      
      // new line and start of the second part of the output
      output = output + "\n" + "Sorted array";
      
      for(int x = 0; x < inputArray.length; ++x)
      {
         y = inputArray[x];
         
         //make new lines at 30 60 and 90 intervals
         if(x == 30 || x == 60 || x == 90)
         {
            output = output + "\n";//new line
         }
         //add to output
         output = output + " " + String.valueOf(y);
         //call add method
         add(y);
      }
    }
    
    private void inorder(AVLNode btree)
    {     
      
       if (btree != null)
       {
         inorder(btree.left);//call inorder method
         ++l;
         //make new lines at 30 60 and 90 intervals
         if(l == 30 || l == 60 || l == 90)
         {
            output = output + "\n";
         }
         output = output + btree.value + " ";//add root value to output
         inorder(btree.right);//call inorder method
       }
    } 
    
    public void inorder()
    {
        inorder(root);//call inorder method
    }
    
    private void postorder(AVLNode btree)
    {     
      
       if (btree != null)
       {
         postorder(btree.left);//call postorder method
         postorder(btree.right);//call postorder method
         ++l;
         //make new lines at 30 60 and 90 intervals
         if(l == 30 || l == 60 || l == 90)
         {
            output = output + "\n";//new line
         }
         output = output + btree.value + " ";//add root value to output
       }
    } 
    
    public void postorder()
    {
        postorder(root);//call postorder method
    }
    
    private void preorder(AVLNode btree)
    {     
      
       if (btree != null)
       {
         ++l;
         //make new lines at 30 60 and 90 intervals
         if(l == 30 || l == 60 || l == 90)
         {
            output = output + "\n";//new line
         }
         output = output + btree.value + " ";//add root value to output
         preorder(btree.left);//call preorder method
         preorder(btree.right);//call preorder method
       }
    } 
    
    public void preorder()
    {
        preorder(root);//call preorder method
    }
    
    public String textArea()
    {
      return output;// return sorted and unsorted array
    }
    
    public static void quickSort(int array[])
   {
      doQuickSort(array, 0, array.length - 1);
   }
   
   private static void doQuickSort(int array[], int start, int end)
   {
      int pivotPoint;
      
      if(start< end)
      {
         // Get the pivot point.
         pivotPoint = partition(array, start, end);
         
         // Sort the first sublist.
         doQuickSort(array, start, pivotPoint - 1);
         
         // Sort the second sublist.
         doQuickSort(array, pivotPoint + 1, end);
      }// End if
   }// end quickSort
   
   private static int partition(int array[], int start, int end)
   {
      int pivotValue;// To hold the pivot value.
      int endOfLeftList;// Last element in the left sublist.
      int mid;// To hold the mid-point subscript.
      
      // Find the subscript of the middle element.
      // This will be out pivot value.
      mid = (start + end) / 2;
      
      // Swap the middle element with the first element.
      // This moves the pivot value to the start of 
      // the list.
      swap(array, start, mid);
      
      // Save the pivot value for comparisons.
      pivotValue = array[start];
      
      // For now, the end of the left sublist is
      // the first element.
      endOfLeftList = start;
      
      // Scan the entire list and move any values that
      // are less than the pivot value to the left
      // sublist
      for(int scan = start + 1; scan <= end; scan++)
      {
         if(array[scan] < pivotValue)
         {
            endOfLeftList++;
            swap(array, endOfLeftList, scan);
         }// End if
      }// End for
      
      // Move the pivot value to the end of the
      // left sublist.
      swap(array, start, endOfLeftList);
      
      // Return the subscript of the pivot value.
      return endOfLeftList;
   }// End partition 
   
   private static void swap(int[] array, int a, int b)
   {
      int temp;
      
      temp = array[a];
      array[a] = array[b];
      array[b] = temp;
   }// End swap
}