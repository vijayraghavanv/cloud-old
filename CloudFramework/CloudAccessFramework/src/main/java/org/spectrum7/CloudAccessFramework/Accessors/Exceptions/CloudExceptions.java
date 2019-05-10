package org.spectrum7.CloudAccessFramework.Accessors.Exceptions;

public class CloudExceptions extends Exception {
    private static final long serialVersionUID = 14816178277864093L;
    
    public CloudExceptions(String errorMessage) {
        super(errorMessage);
    }
    
    public CloudExceptions(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    
}
