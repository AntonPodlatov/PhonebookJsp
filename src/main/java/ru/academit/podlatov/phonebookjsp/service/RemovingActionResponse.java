package ru.academit.podlatov.phonebookjsp.service;

public class RemovingActionResponse {
    private boolean isError;
    private String message;

    public RemovingActionResponse() {

    }

    public RemovingActionResponse(boolean isError, String message) {
        this.message = message;
        this.isError = isError;
    }

    public boolean isError() {
        return isError;
    }

    public String getMessage() {
        return message;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}