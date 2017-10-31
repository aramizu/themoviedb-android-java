package br.com.aramizu.themoviedb.data.network;

import br.com.aramizu.themoviedb.data.model.ErrorResponse;
import br.com.aramizu.themoviedb.data.model.MoviesResponseModel;
import io.reactivex.Observable;
import retrofit2.Response;

public interface ApiHelper {
    Observable<MoviesResponseModel> getNowPlayingMovies(int page);
    Observable<MoviesResponseModel> getMoviesByTitle(String query, int page);
    ErrorResponse parseError(Response<?> response);
}
