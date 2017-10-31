package br.com.aramizu.themoviedb.data.network;

import br.com.aramizu.themoviedb.data.model.MoviesResponseModel;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface BackendApi {

    @GET("/{api_version}/movie/now_playing?language=en-US")
    Observable<MoviesResponseModel> nowPlayingMovies(
            @Path("api_version") int api_version,
            @Query("api_key") String key,
            @Query("page") int page
    );

    @GET("/{api_version}/search/movie?language=en-US")
    Observable<MoviesResponseModel> getMoviesByTitle(
            @Path("api_version") int api_version,
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );
}
