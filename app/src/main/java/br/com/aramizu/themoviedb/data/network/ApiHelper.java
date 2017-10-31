package br.com.aramizu.themoviedb.data.network;

import br.com.aramizu.themoviedb.data.model.ErrorResponse;
import br.com.aramizu.themoviedb.data.model.NowPlayingResponseModel;
import io.reactivex.Observable;
import retrofit2.Response;

public interface ApiHelper {
    Observable<NowPlayingResponseModel> getNowPlayingMovies(int page);
    ErrorResponse parseError(Response<?> response);
}
