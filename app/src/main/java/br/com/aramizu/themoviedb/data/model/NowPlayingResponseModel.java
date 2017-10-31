package br.com.aramizu.themoviedb.data.model;

import java.util.List;

public class NowPlayingResponseModel {

    private int page;
    private List<Movie> results;
    private int total_pages;
    private int total_results;

    public int getPage() {
        return page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }
}