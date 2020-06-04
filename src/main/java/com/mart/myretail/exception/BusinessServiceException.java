package com.mart.myretail.exception;

/**
 * Exception thrown when Business service exception
 */
public class BusinessServiceException extends Exception{

        String message;

        public BusinessServiceException(String message) {

            super(message);
        }

        @Override
        public String getMessage() {
            return this.message;
        }

        @Override
        public String toString() {
            return "BusinessServiceException {" +
                    "message='" + message + '\'' +
                    '}';
        }
}
