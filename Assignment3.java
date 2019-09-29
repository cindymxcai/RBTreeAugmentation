/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3;

/**
 *
 * @author Cindy
 */
public class Assignment3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
    RedBlackTree tree = new RedBlackTree();
     tree.insert(51);
    tree.insert(21);
    tree.insert(19);
    tree.insert(143);
    tree.insert(90);
    tree.insert(80);
    tree.insert(741);
    tree.insert(148);
    tree.insert(214);
    tree.insert(280);
    System.out.println(tree.toString());


    }
}
