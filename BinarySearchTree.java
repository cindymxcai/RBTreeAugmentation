
package assignment3;

public class BinarySearchTree implements Dictionary
{
    protected Node root;

    /** Sentinel, replaces NIL in the textbook's code. */
    protected Node nil;

    public interface Visitor
    {
    
     public Object visit(Object handle);
    }

    protected class Node<T> implements Comparable
    {
    /** The data stored in the node. */
    protected Integer data;

    /** The node's parent. */
    protected Node parent;

    /** The node's left child. */
    protected Node left;

    /** The node's right child. */
    protected Node right;

    public Node(Integer data)
    {
        this.data = data;
        parent = nil;
        left = nil;
        right = nil;
    }

    public int compareTo(Object o)
    {
        return data.compareTo(((Node) o).data);
    }

    public String toString()
    {
        if (this == nil)
        return "nil";
        else
        return data.toString();
    }

    public String toString(int depth)
    {
        String result = "";

        if (left != nil)
        result += left.toString(depth + 1);

        for (int i = 0; i < depth; i++)
        result += "  ";

        result += toString() + "\n";

        if (right != nil)
        result += right.toString(depth + 1);

        return result;
    }
    }

    protected void setNil(Node node)
    {
    nil = node;
    nil.parent = nil;
    nil.left = nil;
    nil.right = nil;
    }

    public BinarySearchTree()
    {
    setNil(new Node(null));
    root = nil;
    }

    public boolean isNil(Object node)
    {
    return node == nil;
    }

    public void inorderWalk(Visitor visitor)
    {
    inorderWalk(root, visitor);
    }

    protected void inorderWalk(Node x, Visitor visitor)
    {
    if (x != nil) {
        inorderWalk(x.left, visitor);
        visitor.visit(x);
        inorderWalk(x.right, visitor);
    }
    }

    public void preorderWalk(Visitor visitor)
    {
    preorderWalk(root, visitor);
    }

    protected void preorderWalk(Node x, Visitor visitor)
    {
    if (x != nil) {
        visitor.visit(x);
        preorderWalk(x.left, visitor);
        preorderWalk(x.right, visitor);
    }
    }

    public void postorderWalk(Visitor visitor)
    {
    postorderWalk(root, visitor);
    }

    
    protected void postorderWalk(Node x, Visitor visitor)
    {
    if (x != nil) {
        postorderWalk(x.left, visitor);
        postorderWalk(x.right, visitor);
        visitor.visit(x);
    }
    }

    
    public String toString()
    {
    return root.toString(0);
    }

    public Object search(Integer k)
    {
    return search(root, k);
    }

    protected Object search(Node x, Integer k)
    {
    int c;

    if (x == nil || (c = k.compareTo(x.data)) == 0)
        return x;

    if (c < 0)
        return search(x.left, k);
    else
        return search(x.right, k);
    }

    public Object iterativeSearch(Integer k)
    {
    Node x = root;
    int c;

    while (x != nil && (c = k.compareTo(x.data)) != 0) {
        if (c < 0)
        x = x.left;
        else
        x = x.right;
    }

    return x;
    }

    public Object minimum()
    {
    return treeMinimum(root);
    }

    
    protected Object treeMinimum(Node x)
    {
    while (x.left != nil)
        x = x.left;

    return x;
    }

    
    public Object maximum()
    {
    return treeMaximum(root);
    }

    protected Object treeMaximum(Node x)
    {
    while (x.right != nil)
        x = x.right;

    return x;
    }

    public Object successor(Object node)
    {
    Node x = (Node) node;
    
    if (x.right != nil)
        return treeMinimum(x.right);

    Node y = x.parent;
    while (y != nil && x == y.right) {
        x = y;
        y = y.parent;
    }

    return y;
    }

  
    public Object predecessor(Object node)
    {
    Node x = (Node) node;

    if (x.left != nil)
        return treeMaximum(x.left);

    Node y = x.parent;
    while (y != nil && x == y.left) {
        x = y;
        y = y.parent;
    }

    return y;
    }

    
    public Object insert(Integer data)
    {
    Node z = new Node(data);
    treeInsert(z);

    return z;
    }

   
    protected void treeInsert(Node z)
    {
    Node y = nil;
    Node x = root;

    while (x != nil) {
        y = x;
        if (z.compareTo(x) <= 0)
        x = x.left;
        else
        x = x.right;
    }

    z.parent = y;
    if (y == nil)
        root = z;       // the tree had been empty
    else {
        if (z.compareTo(y) <= 0)
        y.left = z;
        else
        y.right = z;
    }
    }

    /**
     * Removes a node from the tree.
     *
     * @param node The node to be removed.
     * @throws DeleteSentinelException if there is an attempt to
     * delete the sentinel <code>nil</code>.
     * @throws ClassCastException if <code>node</code> does not
     * reference a <code>Node</code> object.
     */
    public void delete(Object node)
    {
    Node z = (Node) node;

    // Make sure that there is no attempt to delete the sentinel
    // nil.
    if (z == nil)
        throw new DeleteSentinelException();

    Node x;         // Replaces z as the subtree's root
    
    if (z.left == nil)
        x = z.right;
    else 
        if (z.right == nil)
        x = z.left;
        else {      // neither child is nil
        x = (Node) successor(z); // replace with next item
        delete(x);  // Free x from its current position
        
        // Splice out z and put x in its place by fixing links
        // with children.
        x.left = z.left; 
        x.right = z.right;
        x.left.parent = x;
        x.right.parent = x;
        }

    // Fix links between the parent of the subtree and x.
    if (x != nil)
        x.parent = z.parent;
    
    if (root == z)
        root = x;
    else
        if (z == x.parent.left)
        x.parent.left = x;
        else
        x.parent.right = x;
    }

    
}

