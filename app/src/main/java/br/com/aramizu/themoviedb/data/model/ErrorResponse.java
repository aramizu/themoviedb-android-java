package br.com.aramizu.themoviedb.data.model;

public class ErrorResponse {

    public String status_message;
    public int status_code;

    public String getStatusMessage() {
        return status_message;
    }

    public int getStatusCode() {
        return status_code;
    }
}
