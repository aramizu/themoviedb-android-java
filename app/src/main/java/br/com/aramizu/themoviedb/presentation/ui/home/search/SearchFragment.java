package br.com.aramizu.themoviedb.presentation.ui.home.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import org.w3c.dom.Text;

import javax.inject.Inject;

import br.com.aramizu.themoviedb.R;
import br.com.aramizu.themoviedb.data.model.MoviesResponseModel;
import br.com.aramizu.themoviedb.presentation.ui.base.BaseActivity;
import br.com.aramizu.themoviedb.presentation.ui.base.BaseFragment;
import br.com.aramizu.themoviedb.presentation.ui.custom.MoviesAdapter;
import br.com.aramizu.themoviedb.presentation.ui.custom.OnLoadMoreListenerInterface;
import br.com.aramizu.themoviedb.presentation.ui.custom.dialogs.GenericDialogOkCancel;
import br.com.aramizu.themoviedb.presentation.ui.home.HomeActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchFragment extends BaseFragment implements SearchMvpView {

    @BindView(R.id.edt_search)
    EditText edtSearch;

    @BindView(R.id.lst_movies)
    RecyclerView lstMovies;

    @Inject
    SearchMvpPresenter<SearchMvpView> presenter;

    private MoviesAdapter moviesAdapter;
    private HomeActivity mParentActivity;

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mParentActivity = (HomeActivity) getBaseActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivityComponent().inject(this);
        presenter.onAttach(this);

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, view);

        setUp();

        return view;
    }

    @Override
    protected void setUp() {
        setToolbarTitle(R.string.search_title);
        setToolbarStyle(BaseActivity.SEARCH_STYLE);

        moviesAdapter = new MoviesAdapter(mParentActivity);

        moviesAdapter.setOnLoadMoreInterfaceListener(new OnLoadMoreListenerInterface() {
            @Override
            public void onLoadMore() {
                presenter.getMoviesByTitle(edtSearch.getText().toString(), moviesAdapter.getCurrentPage());
            }
        });

        lstMovies.setAdapter(moviesAdapter);
        lstMovies.setItemAnimator(new DefaultItemAnimator());
        lstMovies.setLayoutManager(new LinearLayoutManager(mParentActivity));
    }

    @Override
    public void showNowPlayingMovies(MoviesResponseModel nowPlayingMovies) {
        if (nowPlayingMovies.getResults().size() > 0) {
            hideKeyboard();
            moviesAdapter.addMovies(nowPlayingMovies.getResults());
            moviesAdapter.setCurrentPage(nowPlayingMovies.getPage());
            moviesAdapter.setTotalPages(nowPlayingMovies.getTotal_pages());
        } else {
            new GenericDialogOkCancel(
                    getContext(),
                    mParentActivity.getString(R.string.dialog_title_error),
                    mParentActivity.getString(R.string.dialog_title_no_results_message),
                    mParentActivity.getString(R.string.dialog_ok_label),
                    null
            ).showDialog();
        }
    }

    @OnClick(R.id.search)
    public void onSearchClick() {
        if (verifyFields()) {
            moviesAdapter.clearMovies();
            presenter.getMoviesByTitle(edtSearch.getText().toString(), moviesAdapter.getCurrentPage());
        }
    }

    private boolean verifyFields() {
        if (edtSearch.getText().toString().trim().isEmpty()) {
            new GenericDialogOkCancel(
                    getContext(),
                    mParentActivity.getString(R.string.dialog_title_error),
                    mParentActivity.getString(R.string.dialog_title_mandatory_search_filed_message),
                    mParentActivity.getString(R.string.dialog_ok_label),
                    null
            ).showDialog();
            return false;
        }
        return true;
    }
}
