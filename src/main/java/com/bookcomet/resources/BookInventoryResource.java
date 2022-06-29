package com.bookcomet.resources;

import com.bookcomet.entities.Book;
import com.bookcomet.entities.BookInventory;
import com.bookcomet.services.BookService;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author mx
 */
@Path("/inventory")
public class BookInventoryResource 
{
    private BookService bookService = BookService.getInstance();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BookInventory> get() 
    {
        return bookService.getIventory();
    }
    
    @POST
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response post( @PathParam("id") int id )
    {
        Book book = bookService.getBook( id );
        
        if ( book == null )
        {
            return Response.status( Response.Status.NOT_FOUND ).entity( "Book not found!" ).build();
        }
        
        BookInventory inventory = bookService.getBookInventory( id );
        
        if ( inventory == null )
        {
            inventory = new BookInventory();
            inventory.setBook( book );
        }
        
        try
        {
            bookService.addToInventory( inventory );
            
            return Response.status( Response.Status.CREATED ).entity( inventory ).build();
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

            if ( inventory == null )
            {
                return Response.status( Response.Status.NOT_FOUND ).entity( "Inventory not found!" ).build();
            }
        
            bookService.removeFromInventory( id );
             
            return Response.status( Response.Status.OK ).entity( "Book unit succesfully removed from inventory!" ).build();
        }
        
        catch ( Exception ex )
        {
            return Response.status( Response.Status.INTERNAL_SERVER_ERROR ).entity( ex.getMessage() ).build();
        } 
    }
}
