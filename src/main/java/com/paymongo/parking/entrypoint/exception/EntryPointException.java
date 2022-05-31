
package com.paymongo.parking.entrypoint.exception;

public class EntryPointException extends Exception {

	public static final String ENTRY_POINT_NOT_FOUND_EXCEPTION = "Entry Point not found";

	/**
	 * 
	 */
	private static final long serialVersionUID = 3049298802502316077L;

	public EntryPointException(String message) {
		super(message);
	}
}
