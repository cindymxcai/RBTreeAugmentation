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
interface Dictionary {
    
    public Object insert(Integer o);

    public void delete(Object handle);

    public Object search(Integer k);
}