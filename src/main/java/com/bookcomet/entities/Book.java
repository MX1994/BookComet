package com.bookcomet.entities;

/**
 *
 * @author mx
 */
public class Book 
{
    private int id;

    private String name;
    private String authors;
    private String publisher;
    private String publicationYear;
    private String summary;

    public int getId() 
    {
        return id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    public String getName() 
    {
        return name;
    }

    public void setName( String name ) 
    {
        this.name = name;
    }

    public String getAuthors() 
    {
        return authors;
    }

    public void setAuthors( String authors ) 
    {
        this.authors = authors;
    }

    public String getPublisher() 
    {
        return publisher;
    }

    public void setPublisher( String publisher ) 
    {
        this.publisher = publisher;
    }

    public String getPublicationYear() 
    {
        return publicationYear;
    }

    public void setPublicationYear( String publicationYear ) 
    {
        this.publicationYear = publicationYear;
    }

    public String getSummary() 
    {
        return summary;
    }

    public void setSummary( String summary ) 
    {
        this.summary = summary;
    }
}
