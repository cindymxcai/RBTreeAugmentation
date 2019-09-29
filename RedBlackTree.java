package assignment3;

import java.awt.Color;

public class RedBlackTree extends BinarySearchTree
{
    protected static final Color RED = Color.red;
    protected static final Color BLACK = Color.black;
    public static final String ANSI_RED = "\u001B[31m";
    Node leftst;
    Integer leftGap;
    Node rightst;
    Integer rightGap;
    int newMin = 0;

                
    public static class BlackHeightException extends RuntimeException
    {
    }
//////////////////////////////////////////////////////////////////////////////////////
    protected class Node extends BinarySearchTree.Node
    {
    protected Color color;
    protected int min;
    protected int max;
    int minGap; 
  
    public Node(Integer data)
    {
        super(data);
        this.color = RED;
        this.min = Integer.MIN_VALUE;
        this.max = Integer.MAX_VALUE;
        this.minGap = Integer.MAX_VALUE;
    }

    @Override
    public String toString()
    {
            if (color == RED) {
                if (minGap == Integer.MAX_VALUE) {
                    return ANSI_RED + super.toString() + " minGap: no min gap";
                } else {
                    return ANSI_RED + super.toString()  + " minGap: " + minGap;
                }
            } 
            
            else {
                if (minGap == Integer.MAX_VALUE) {
                    return super.toString() + " minGap: no min gap";
                } else {
                    return super.toString() + " minGap: " + minGap;
                }
            }
        }
    }
////////////////////////////////////////////////////////////////////
    protected void setNil(Node node)
    {
    node.color = BLACK;
    super.setNil(node);
    }

    
    public RedBlackTree()
    {
    setNil(new Node(null));
    root = nil;
    }

    protected void leftRotate(Node x)
    {
    Node y = (Node) x.right;

    // Swap the in-between subtree from y to x.
    x.right = y.left;
    if (y.left != nil)
        y.left.parent = x;

    // Make y the root of the subtree for which x was the root.
    y.parent = x.parent;
    
    // If x is the root of the entire tree, make y the root.
    // Otherwise, make y the correct child of the subtree's
    // parent.
    if (x.parent == nil)
        root = y;
    else 
        if (x == x.parent.left)
        x.parent.left = y;
        else
        x.parent.right = y;

    // Relink x and y.
    y.left = x;
    x.parent = y;
    }

    protected void rightRotate(Node x)
    {
    Node y = (Node) x.left;

    x.left = y.right;
    if (x.left != null)
        y.right.parent = x;

    y.parent = x.parent;

    y.right = x;
    x.parent = y;

    if (root == x)
        root = y;
    else
        if (y.parent.left == x)
        y.parent.left = y;
        else
        y.parent.right = y;
    }

    public Object insert(Integer data)
    {
    Node z = new Node(data);
    treeInsert(z);
    return z;
    }

    protected void treeInsert(Node z)
    {
    super.treeInsert(z);
    insertFixup(z);
    }

    
    protected void insertFixup(Node z)
    {
    Node y = null;
    Node inserted = z;

    while (((Node) z.parent).color == RED) {
        if (z.parent == z.parent.parent.left) {
        y = (Node) z.parent.parent.right;
        if (y.color == RED) {
            ((Node) z.parent).color = BLACK;
            y.color = BLACK;
            ((Node) z.parent.parent).color = RED;
            z = (Node) z.parent.parent;
        }
        
        else {
            if (z ==  z.parent.right) {
            z = (Node) z.parent;
            leftRotate(z);
            calcGap(z);
            }
            
            ((Node) z.parent).color = BLACK;
            ((Node) z.parent.parent).color = RED;
            rightRotate((Node) z.parent.parent);
            calcGap((Node) z.parent.right);
        }
        }
        //rotate left
        else {
            System.out.println(" rotated left");
        y = (Node) z.parent.parent.left;
        if (y.color == RED) {
            ((Node) z.parent).color = BLACK;
            y.color = BLACK;
            ((Node) z.parent.parent).color = RED;
            z = (Node) z.parent.parent;
        }
        
       
        else {
                System.out.println(" rotated right");
            if (z ==  z.parent.left) {
            z = (Node) z.parent;
            rightRotate(z);
            calcGap(z);
            }
            
            ((Node) z.parent).color = BLACK;
            ((Node) z.parent.parent).color = RED;
            leftRotate((Node) z.parent.parent);
            calcGap((Node) z.parent.left);
        }
        }
    }
    calcGap(inserted);
    ((Node) root).color = BLACK;
    }
    
    

    public void delete(Object handle)
    {
    Node z = (Node) handle;
    Node y = z;
    Node x = (Node) nil;

    // Do not allow the sentinel to be deleted.
    if (z == nil)
        throw new DeleteSentinelException();
        
    if (z.left != nil && z.right != nil)
        y = (Node) successor(z);

    if (z.left != nil)
        x = (Node) y.left;
    else
        x = (Node) y.right;

    x.parent = y.parent;

    if (y.parent == nil)
        root = x;
    else
        if (y == y.parent.left)
        y.parent.left = x;
        else
        y.parent.right = x;

    if (y != z) {
        y.left = z.left;
        y.left.parent = y;
        y.right = z.right;
        y.right.parent = y;
        y.parent = z.parent;
        if (z == root)
        root = y;
        else
        if (z == z.parent.left)
            z.parent.left = y;
        else
            z.parent.right = y;
    }

    if (y.color == BLACK)
        deleteFixup(x);
    }
    
    protected void deleteFixup(Node x)
    {
    while (x != root && x.color == BLACK) {
        if (x.parent.left == x) {
        Node w = (Node) x.parent.right;

        if (w.color == RED) {
            w.color = BLACK;
            ((Node) x.parent).color = RED;
            leftRotate((Node) x.parent);
            w = (Node) x.parent.right;
        }

        if (((Node) w.left).color == BLACK 
            && ((Node) w.right).color == BLACK) {
            w.color = RED;
            x = (Node) x.parent;
        }
        else {
            if (((Node) w.right).color == BLACK) {
            ((Node) w.left).color = BLACK;
            w.color = RED;
            rightRotate(w);
            w = (Node) x.parent.right;
            }

            w.color = ((Node) x.parent).color;
            ((Node) x.parent).color = BLACK;
            ((Node) w.right).color = BLACK;
            leftRotate((Node) x.parent);
            x = (Node) root;
        }
        }
        else {
        Node w = (Node) x.parent.left;

        if (w.color == RED) {
            w.color = BLACK;
            ((Node) x.parent).color = RED;
            rightRotate((Node) x.parent);
            w = (Node) x.parent.left;
        }

        if (((Node) w.right).color == BLACK 
            && ((Node) w.left).color == BLACK) {
            w.color = RED;
            x = (Node) x.parent;
        }
        else {
            if (((Node) w.left).color == BLACK) {
            ((Node) w.right).color = BLACK;
            w.color = RED;
            leftRotate(w);
            w = (Node) x.parent.left;
            }

            w.color = ((Node) x.parent).color;
            ((Node) x.parent).color = BLACK;
            ((Node) w.left).color = BLACK;
            rightRotate((Node) x.parent);
            x = (Node) root;
        }       
        }
    }
    x.color = BLACK;
    }

    public int blackHeight(Node z)
    {
    if (z == nil)
        return 0;

    int left = blackHeight((Node) z.left);
    int right = blackHeight((Node) z.right);
    if (left == right)
        if (z.color == BLACK)
        return left + 1;
        else
        return left;
    else
        throw new BlackHeightException();
    }
    
    public int blackHeight()
    {
    return blackHeight((Node) root);
    }
    
///////////////////////////////////////////////////////////////////////////
    public void calcGap (Node z){
        
        checkRotation(z);
        
        while (z != nil ){
             //check child
            if(z.right == nil && z.left == nil){
                z.min = z.max = z.data;
                z.minGap = Integer.MAX_VALUE;
            }
            else {
                
                leftst = (Node)z.left;
                rightst = (Node)z.right;
                leftGap = rightGap = null;

            //parents right
            if(rightst != nil){
                
                rightGap = Math.min(Math.abs(z.data - rightst.data), Math.abs(z.data - rightst.min));

                if(rightst.min  > z.min){
                    z.max = rightst.max;
                }
                else{
                    z.min = rightst.min;

                }
            }
            
            //parents left 
            if(leftst != nil)
            {
                leftGap = Math.min(Math.abs(z.data - leftst.data), Math.abs(z.data - leftst.max));

                if(leftst.min > z.min){
                    z.max = leftst.max;
                }
                else
                {
                    z.min = leftst.min;                        
                }
            }
            
            compareMin(z);
            
            z.minGap = newMin;
        }
        
        z = (Node)z.parent;
               
        }
    }
    
    public int checkRotation (Node z){
        if(z.left != nil){
            Node leftChild = (Node)z.left;
            z.min = leftChild.min;
        }
        else {
            z.min = z.data;
        }
        if (z.right != nil){
            Node rightChild = (Node)z.right;
            z.min = rightChild.min;
        }
        else {
            z.max = z.data;
        }
        return z.min + z.max;
    }
    
    
    public int compareMin(Node z){
        if(leftGap != null && rightGap != null)
            {
                newMin = Math.min(z.minGap, Math.min(Math.min(leftGap, leftst.minGap), Math.min(rightGap, rightst.minGap)));
            }
            else if(leftGap != null)
            {
                newMin = Math.min(Math.min(leftGap, leftst.minGap), z.minGap);
            }
            else if(rightGap != null)
            { 
                newMin = Math.min(Math.min(rightGap, rightst.minGap), z.minGap);
            }
        return newMin;
    }
   
}
