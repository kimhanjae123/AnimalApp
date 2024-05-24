package com.sds.animalapp.exception;

public class MovieException extends RuntimeException{
	
	public MovieException(String msg) {
		super(msg);
	}
	public MovieException(String msg, Throwable e) {
		super(msg, e);
	}
	public MovieException(Throwable e) {
		super(e);
	}
	
}
