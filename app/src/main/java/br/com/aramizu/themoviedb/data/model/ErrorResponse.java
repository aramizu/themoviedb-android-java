package br.com.aramizu.themoviedb.data.model;

/**
 * Error Response base on TheMovieDB error json structure
 */
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
