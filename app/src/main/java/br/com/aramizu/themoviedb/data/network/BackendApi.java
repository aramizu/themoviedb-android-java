package br.com.aramizu.themoviedb.data.network;

import br.com.aramizu.themoviedb.data.model.NowPlayingResponseModel;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface BackendApi {

    @GET("/{id}/movie/now_playing?language=en-US")
    Observable<NowPlayingResponseModel> nowPlayingMovies(@Path("id") int id, @Query("api_key") String key, @Query("page") int page);
}
