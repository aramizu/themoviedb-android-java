package br.com.aramizu.themoviedb.presentation.ui.home.search;

import javax.inject.Inject;

import br.com.aramizu.themoviedb.data.DataManager;
import br.com.aramizu.themoviedb.data.model.MoviesResponseModel;
import br.com.aramizu.themoviedb.data.network.APIConstants;
import br.com.aramizu.themoviedb.presentation.ui.base.BasePresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter<V extends SearchMvpView> extends BasePresenter<V>
        implements SearchMvpPresenter<V> {

    @Inject
    SearchPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getMoviesByTitle(String title, int page) {
        if (page == APIConstants.INITIAL_PAGINATION_INDEX)
            getMvpView().showLoading();

        title = title.replace(" ", "+");

        getCompositeDisposable().add(getDataManager().getMoviesByTitle(title, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<MoviesResponseModel>() {
                            @Override
                            public void accept(MoviesResponseModel nowPlayingMovies) throws Exception {
                                V view = getMvpView();
                                view.hideLoading();

                                getMvpView().showNowPlayingMovies(nowPlayingMovies);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                V view = getMvpView();
                                view.hideLoading();

                                handlerThrowableError(view, throwable);
                            }
                        }
                )
        );
    }
}
