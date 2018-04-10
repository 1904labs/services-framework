package io.tsdb.services.framework.base.service.responses;

/**
 * @author jcreasy
 */

class ErrorMessage {
    
    private int code;
    private String message;
    
    /**
     * @return the code
     */
    int getCode() {
        return code;
    }
    
    /**
     * @param providedCode the code to set
     */
    void setCode(final int providedCode) {
        this.code = providedCode;
    }
    
    /**
     * @return the message
     */
    String getMessage() {
        return message;
    }
    
    /**
     * @param providedMessage the message to set
     */
    void setMessage(final String providedMessage) {
        this.message = providedMessage;
    }
    
}

