package com.miw.gildedrose.Exception;

/**
 * The Class UserDefinedSystemException.
 *
 */
public class UserDefinedSystemException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7870532688628083973L;

	public UserDefinedSystemException(String str)  
    {  
        // Calling constructor of parent Exception  
        super(str);  
    } 
}
