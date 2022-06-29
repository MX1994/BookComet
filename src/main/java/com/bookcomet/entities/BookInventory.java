package com.bookcomet.entities;

/**
 *
 * @author mx
 */
public class BookInventory 
{
    private int id;
    
    private Book book;
    
    private int quantity;

    public int getId() 
    {
        return id;
    }

    public void setId( int id ) 
    {
        this.id = id;
    }

    public Book getBook() 
    {
        return book;
    }

    public void setBook( Book book ) 
    {
        this.book = book;
    }

    public int getQuantity() 
    {
        return quantity;
    }

    public void setQuantity( int quantity ) 
    {
        this.quantity = quantity;
    }
    
}
