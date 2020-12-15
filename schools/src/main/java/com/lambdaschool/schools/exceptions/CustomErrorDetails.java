package com.lambdaschool.schools.exceptions;


import com.lambdaschool.schools.handlers.HelperFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CustomErrorDetails extends DefaultErrorAttributes
{

    @Autowired
    private HelperFunctions helper;

    @Override
    public Map<String, Object> getErrorAttributes(
        WebRequest webRequest,
        boolean includeStackTrace)
    {
        Map<String, Object> errorAttributes =
            super.getErrorAttributes(webRequest, includeStackTrace);
        // Linked HasMaps maintain the order the items are inserted.
        // I'm using it here so that the error JSON produced from this class
        // lists the attributes in the same order as other classes.
        Map<String, Object> errorDetails = new LinkedHashMap<>();
        errorDetails.put("title", errorAttributes.get("error"));
        errorDetails.put("status", errorAttributes.get("status"));
        errorDetails.put("detail", errorAttributes.get("message"));
        errorDetails.put("timestamp", errorAttributes.get("timestamp"));
        errorDetails.put("developerMessage", "path: " + errorAttributes.get("path"));

        errorDetails.put("errors", helper.getConstraintViolation(this.getError(webRequest)));

        return errorDetails;
    }
}
