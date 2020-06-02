/**
 *
 */
package org.happybot.controllers.pages;

import org.chatbot1905.facades.product.data.rest.CommonRestResponseObject;
import org.springframework.http.HttpStatus;

/**
 * @author Ranjith.s
 *
 */
public class WebUtils {

    public static CommonRestResponseObject createResponseObject(final Object dataObject) {
        final CommonRestResponseObject response = new CommonRestResponseObject();
        response.setResponseObject(dataObject);
        response.setResponseCode(HttpStatus.OK.toString());
        return response;
    }
}
