package br.com.aramizu.themoviedb.data.network;

import java.io.IOException;
import java.lang.annotation.Annotation;

import javax.inject.Inject;

import br.com.aramizu.themoviedb.data.model.ErrorResponse;
import br.com.aramizu.themoviedb.data.model.MoviesResponseModel;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AppApiHelper implements ApiHelper {

    private BackendApi backendApi;
    private Retrofit retrofit;

    @Inject
    AppApiHelper(Retrofit retrofit) {
        this.backendApi = retrofit.create(BackendApi.class);
        this.retrofit = retrofit;
    }

    @Override
    public Observable<MoviesResponseModel> getNowPlayingMovies(int page) {
        return backendApi.nowPlayingMovies(3, APIConstants.API_KEY, page);
    }

    @Override
    public Observable<MoviesResponseModel> getMoviesByTitle(String query, int page) {
        return backendApi.getMoviesByTitle(3, APIConstants.API_KEY, query, page);
    }

    @Override
    public ErrorResponse parseError(Response<?> response) {
        Converter<ResponseBody, ErrorResponse> converter =
                retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
        try {
            return converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ErrorResponse();
        }
    }
}
