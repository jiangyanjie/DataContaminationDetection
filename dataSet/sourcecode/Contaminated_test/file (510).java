///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  DisplayEditor.java
// File:             DblListnode.java
// Semester:         CS302 Spring 2014
//
// Author:           Alejandro Puente
// Email:            apuente@wisc.edu
// CS Login:         alejandr
// Lecturer's Name:  Jim Skrentny
// Lab Section:      Lecture 1
//////////////////////////// 80 columns wide //////////////////////////////////
/**
 * The DblListnode class that represents a single node in the MessageLoop.
 *
 * <p>Bugs: None
 *
 * @author Alejandro Puente
 */
public class DblListnode<E> 
{
	//Pointer to the previous node in the list
    private DblListnode<E> prev;
    //The data inside the node
    private E data;
    //Pointer to the next node in the list
    private DblListnode<E> next;
 
    /**
    * 1st DblListnode constructor that sets all private values to null
    */
    public DblListnode() 
    {
        this(null, null, null);
    }
 
    /**
    * 2nd DblListnode constructor that sets the data
    * 
    * @param d - the data in the DblListnode
    */
    public DblListnode(E d) 
    {
        this(null, d, null);
    }
 
    /**
    * 3rd DblListnode constructor that sets all private values to the
    * given values
    * 
    * @param p - pointer to the previous node of the DblListnode
    * @param d - the data contained in the DblListnode
    * @param n - pointer to the next node of the DblListnode
    */
    public DblListnode(DblListnode<E> p, E d, DblListnode<E> n) 
    {
        prev = p;
        data = d;
        next = n;
    }
    
    /**
    * Get the data that is contained in the DblListnode
    * 
    * @return the data that is contained in the DblListnode
    * 
    */
    public E getData() 
    {
        return data;
    }
 
    /**
    * Get the DblListnode that comes after this one
    * 
    * @return the next DblListnode
    * 
    */
    public DblListnode<E> getNext() 
    {
        return next;
    }
 
    /**
    * Get the DblListnode that comes before this one
    * 
    * @return the previous DblListnode
    * 
    */
    public DblListnode<E> getPrev() 
    {
        return prev;
    }
 
    /**
    * Set the data of the DblListnode to the given data
    * 
    * @param d - the given data
    * 
    */
    public void setData(E d) 
    {
        data = d;
    }
 
    /**
    * Set the next DblListnode of the current DblListnode to the given 
    * DblListnode
    * 
    * @param n - the given next DblListnode
    * 
    */
    public void setNext(DblListnode<E> n) 
    {
        next = n;
    }
 
    /**
    * Set the previous DblListnode of the current DblListnode to the given
    * DblListnode
    * 
    * @param p - the given previous DblListnode
    * 
    */
    public void setPrev(DblListnode<E> p) 
    {
        prev = p;
    }
}