package br.com.aramizu.themoviedb.data.network;

/**
 * Some constants used by network context
 */
public class APIConstants {

    /** API KEY */
    public static final String API_KEY = "d272326e467344029e68e3c4ff0b4059";

    /** Server endpoint. */
    public static final String IMAGE_SERVER_URL = "https://image.tmdb.org/t/p/";

    /** Poster size. */
    public static final String POSTER_SIZE = "w500";

    /** Movies services. */
    public static final String POSTER_URL = IMAGE_SERVER_URL + POSTER_SIZE + "%1$s";

    /** Initial pagination index for response movies on a list. */
    public static final int INITIAL_PAGINATION_INDEX = 1;
}
