package com.bookcomet.resources;

import com.bookcomet.entities.Book;
import com.bookcomet.entities.BookInventory;
import com.bookcomet.entities.EBook;
import com.bookcomet.services.BookService;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author mx
 */
@Path("/books")
public class BookResource 
{
    private BookService bookService = BookService.getInstance();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> get( @QueryParam("authors") String authors, @QueryParam("publisher") String publisher ) 
    {
        List<Book> books = bookService.getAllBooks();
        
        if ( authors != null )
        {
            books = books.stream().filter( book -> book.getAuthors().equals( authors ) ).collect( Collectors.toList() );
        }
        
        if ( publisher != null )
        {
            books = books.stream().filter( book -> book.getPublisher().equals( publisher ) ).collect( Collectors.toList() );
        }
        
        return books; 
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Book getBookById( @PathParam("id") int id ) 
    {
        return bookService.getBook( id );
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post( Book book, @QueryParam("format") String format )
    {
        try
        {
            if ( ! bookService.isExistingBook( book.getName(), book.getAuthors() ) )
            {
                if ( format != null )
                {
                    EBook ebook = new EBook();
                    ebook.setName( book.getName() );
                    ebook.setAuthors( book.getAuthors() );
                    ebook.setPublicationYear( book.getPublicationYear() );
                    ebook.setPublisher( book.getPublisher() );
                    ebook.setSummary( book.getSummary() );
                    ebook.setFormat( format );
                    
                    bookService.addBook( ebook );
                }
                
                else
                {
                    bookService.addBook( book );
                }
            }

            else
            {
                return Response.status( Response.Status.NOT_ACCEPTABLE ).entity( "Book already exists in database!" ).build();
            }
            
            return Response.status( Response.Status.CREATED ).entity( "" ).build();
        }
        
        catch ( Exception ex )
        {
            return Response.status( Response.Status.INTERNAL_SERVER_ERROR ).entity( ex.getMessage() ).build();
        } 
    }
    
    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put( @PathParam("id") int id, Book newBook )
    {
        Book book = bookService.getBook( id );
        
        if ( book == null )
        {
            return Response.status( Response.Status.NOT_FOUND ).entity( "Book not found!" ).build();
        }
        
        try
        {
            newBook.setId( id );
            
            bookService.updateBook( newBook );
            
            return Response.status( Response.Status.OK ).entity( newBook ).build();
        }
        
        catch ( Exception ex )
        {
            return Response.status( Response.Status.INTERNAL_SERVER_ERROR ).entity( ex.getMessage() ).build();
        } 
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete( @PathParam("id") int id )
    {
        try
        {
            Book book = bookService.getBook( id );
        
            if ( book == null )
            {
                return Response.status( Response.Status.NOT_FOUND ).entity( "Book not found!" ).build();
            }
        
            BookInventory inventory = bookService.getBookInventory( id );
            
            if ( inventory != null )
            {
                if ( inventory.getQuantity() > 0 )
                {
                    return Response.status( Response.Status.NOT_ACCEPTABLE ).entity( "Book has positive inventory!" ).build();
                }
            }
            
            bookService.deleteBook( id );
             
            return Response.status( Response.Status.OK ).entity( "Book succesfully deleted!" ).build();
        }
        
        catch ( Exception ex )
        {
            return Response.status( Response.Status.INTERNAL_SERVER_ERROR ).entity( ex.getMessage() ).build();
        } 
    }
}
