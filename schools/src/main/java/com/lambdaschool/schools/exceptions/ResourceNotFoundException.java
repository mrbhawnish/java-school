package com.lambdaschool.schools.exceptions;


/**
 * Create our own exception that we can throw
 */

public class ResourceNotFoundException extends RuntimeException
{
    public ResourceNotFoundException(String message)
    {
        super("Found an error with School " + message);
    }

}
