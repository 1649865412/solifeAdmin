/*
 * create 2006-8-28
 *
 * 
 */
package com.cartmatic.estore.sales.model;

/**
 * @author Ryan
 *
 * 
 */
public class EmailModel
{
    private String firstName;
    private String lastName;
    private String email;
    
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public String getFirstName()
    {
        return firstName;
    }
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    public String getLastName()
    {
        return lastName;
    }
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
}
