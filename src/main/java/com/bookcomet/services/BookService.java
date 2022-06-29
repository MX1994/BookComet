package com.bookcomet.services;

import com.bookcomet.entities.Book;
import com.bookcomet.entities.BookInventory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author mx
 */
public class BookService 
{
    private static BookService instance;

    private BookService() 
    {}

    public static BookService getInstance()
    {
        if ( instance == null )
        {
            instance = new BookService();
        }
        
        return instance;
    }
    
    private final static HashMap<Integer, Book> books = new HashMap<>();
    
    private final static HashMap<Integer, BookInventory> inventoryMap = new HashMap<>();

    public List<Book> getAllBooks()
    {
        return new ArrayList<Book>( books.values() );
    }

    public Book getBook( final int id ) 
    {
        return books.get( id );
    }
    
    public boolean isExistingBook( final String name, final String author )
    {
        List<Book> allBooks = new ArrayList<Book>( books.values() );
        
        allBooks = allBooks.stream().filter( book -> book.getName().equals( name ) && book.getAuthors().equals( author ) ).collect( Collectors.toList() );
        
        return allBooks.size() > 0;
    }

    public void addBook( final Book book ) 
    {
        book.setId( generateId( books.size() + 1, false ) );
        books.put( book.getId(), book );
    }

    public void updateBook( final Book book ) 
    {
        books.remove( book.getId() );
        books.put( book.getId(), book );
    }

    public void deleteBook( final int id ) 
    {
        books.remove( id );
        inventoryMap.remove( id );
    }
    
    public List<BookInventory> getIventory()
    {
        return new ArrayList<BookInventory>( inventoryMap.values() );
    }

    public BookInventory getBookInventory( final int bookId )
    {
        return inventoryMap.get( bookId );
    }
    
    public void addToInventory( final BookInventory inventory )
    {
        if ( inventory.getId() == 0 )
        {
            inventory.setId( generateId( inventoryMap.size() + 1, true ) );
            inventory.setQuantity( 1 );
            
            inventoryMap.put( inventory.getBook().getId(), inventory );
        }
        
        else
        {
            inventory.setQuantity( inventory.getQuantity() + 1 );
        }
    }
    
    public void removeFromInventory( final int id )
    {
        BookInventory inventory = inventoryMap.get( id );
        
        if ( inventory.getQuantity() > 0 )
        {
            inventory.setQuantity( inventory.getQuantity() - 1 );
        }
    }
    
    private int generateId( final int nextId, boolean isInventory )
    {
        if ( isInventory )
        {
            if( inventoryMap.containsKey( nextId ) )
            {
                return generateId( nextId + 1, true );
            }
        }
        
        else
        {
            if( books.containsKey( nextId ) )
            {
                return generateId( nextId + 1, false );
            }
        }
        
        return nextId;
    }
}
