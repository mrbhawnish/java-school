package com.lambdaschool.schools.handlers;

import com.lambdaschool.schools.models.ValidationError;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

/**
 * We will create here a method that will populate the validation constraint errors
 * We will use such a function in a few places throughout the application.
 */
@Component
public class HelperFunctions
{
    public List<ValidationError> getConstraintViolation(Throwable cause)
    {
        while ((cause != null) && !(cause instanceof ConstraintViolationException  || cause instanceof MethodArgumentNotValidException))
        {
            System.out.println(cause.getClass().toString());
            cause = cause.getCause();
        }

        List<ValidationError> listVE = new ArrayList<>();

        //We know that cause either null  or an instanceof ConstraintViolationException or MethodArgumentNotValidException
        if(cause != null)
        {
            if (cause instanceof  ConstraintViolationException)
            {
                //this would be an exception from Hibernate
                ConstraintViolationException ex = (ConstraintViolationException) cause;
                ValidationError newVE = new ValidationError();
                newVE.setCode(ex.getMessage());
                newVE.setMessage(ex.getConstraintName());
                listVE.add(newVE);
            } else {
                if(cause instanceof MethodArgumentNotValidException)
                {
                    // this would be an exception from the @Valid exception
                    MethodArgumentNotValidException ex = (MethodArgumentNotValidException) cause;
                    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
                    for(FieldError err : fieldErrors)
                    {
                        ValidationError newVE = new ValidationError();
                        newVE.setCode(err.getField());
                        newVE.setMessage(err.getDefaultMessage());
                        listVE.add(newVE);
                    }
                }
                else
                {
                    System.out.println("Error in producing constraint violations exceptions. " +
                        "If we see this in the console a major logic error has occured in the " +
                        "helperfunction,getConstraintViolation method that we should investigate." +
                        "Note the application will kep running as this only affects exception reporting!");
                }
            }

        }
        return listVE;
    }
}
