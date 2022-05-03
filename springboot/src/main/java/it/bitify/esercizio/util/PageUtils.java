package it.bitify.esercizio.util;

import it.bitify.esercizio.exception.BadRequestException;

/*******************************************************************************************
 * Created by A. Di Raffaele.
 ******************************************************************************************/
public class PageUtils {

	public static void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }
	
}
