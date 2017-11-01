package br.com.aramizu.themoviedb.presentation.ui.details;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import br.com.aramizu.themoviedb.R;
import br.com.aramizu.themoviedb.data.model.Movie;
import br.com.aramizu.themoviedb.data.network.APIConstants;
import br.com.aramizu.themoviedb.presentation.ui.base.BaseActivity;
import butterknife.BindView;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieDetailsActivity extends BaseActivity {

    @BindView(R.id.title_header)
    TextView titleHeader;
    @BindView(R.id.grade)
    TextView grade;
    @BindView(R.id.cover)
    ImageView cover;
    @BindView(R.id.cover_loading)
    ProgressBar coverLoading;
    @BindView(R.id.wallpaper)
    ImageView wallpaper;
    @BindView(R.id.wallpaper_loading)
    ProgressBar wallpaperLoading;
    @BindView(R.id.image_not_available)
    TextView imgNotAvailable;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.release_date)
    TextView releaseDate;
    @BindView(R.id.overview)
    TextView overview;

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movie = (Movie) getIntent().getExtras().getSerializable("MOVIE");

        setUp();
    }

    @Override
    protected void setUp() {
        setToolbarTitle(R.string.details_title);
        setToolbarStyle(BaseActivity.DETAILS_STYLE);

        titleHeader.setText(movie.getTitle());
        grade.setText(String.valueOf(String.format("%.1f", movie.getVote_average())));
        title.setText(movie.getOriginal_title());
        releaseDate.setText(movie.getRelease_date());
        overview.setText(movie.getOverview());

        Glide.with(this).load(String.format(APIConstants.POSTER_URL, movie.getBackdrop_path()))
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        imgNotAvailable.setVisibility(View.VISIBLE);
                        wallpaperLoading.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        wallpaperLoading.setVisibility(View.GONE);
                        return false;
                    }
                })
                .bitmapTransform(new RoundedCornersTransformation(this, 4, 0, RoundedCornersTransformation.CornerType.ALL))
                .into(wallpaper);

        Glide.with(this).load(String.format(APIConstants.POSTER_URL, movie.getPoster_path()))
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        coverLoading.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        coverLoading.setVisibility(View.GONE);
                        return false;
                    }
                })
                .bitmapTransform(new RoundedCornersTransformation(this, 4, 0, RoundedCornersTransformation.CornerType.ALL))
                .into(cover);
    }
}
