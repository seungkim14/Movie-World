package com.seungleekim.android.movie.ui.details

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.seungleekim.android.movie.R
import com.seungleekim.android.movie.di.ActivityScoped
import com.seungleekim.android.movie.model.*
import com.seungleekim.android.movie.ui.MovieDetailsActivity
import com.seungleekim.android.movie.util.GlideApp
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.view_movie_details_top.*
import kotlinx.android.synthetic.main.view_movie_details_trailers.*
import kotlinx.android.synthetic.main.view_movie_details_summary_reviews.*
import javax.inject.Inject

@ActivityScoped
class MovieDetailsFragment @Inject constructor() : DaggerFragment(), MovieDetailsContract.View,
    MovieTrailersAdapter.OnClickListener {

    @Inject
    lateinit var mPresenter: MovieDetailsContract.Presenter

    private var mTrendingMovie: TrendingMovie? = null
    private var mMovieDetails: MovieDetails? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTrendingMovie = arguments?.getParcelable(ARG_MOVIE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.takeView(this)
        mPresenter.getMovieDetails(mTrendingMovie!!)
        mPresenter.getFavorite(mTrendingMovie!!)
        setupToolbar()
    }

    private fun setupToolbar() {
        (activity as MovieDetailsActivity).apply {
            setSupportActionBar(toolbar_details)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.dropView()
    }

    override fun showMovieDetails(movieDetails: MovieDetails) {
        mMovieDetails = movieDetails
        showMovieTitle(movieDetails.getTitleWithYear())
        showMovieBackdrop(movieDetails.getBackdropUrl())
        showMovieRating(movieDetails.getRatingText())
        showMovieMpaaRating(movieDetails.mpaaRating)
        showMovieDuration(movieDetails.getRuntimeString())
        showMovieGenres(movieDetails.getFirstGenre())
        showMovieReleaseDate(movieDetails.releaseDate)
        showMovieTrailers(movieDetails.trailers)
        showMovieOverview(movieDetails.overview)
        showMovieCasts(movieDetails.getCastsString())
        showMovieCrews(movieDetails.getCrewsString())
        showMovieReviews(movieDetails.getReviewsString())
    }

    override fun showMovieTitle(title: String) {
        collapsing_toolbar.title = title
        tv_movie_details_title.text = title
    }

    override fun showMovieBackdrop(backdropUrl: String) {
        GlideApp.with(context!!).load(backdropUrl).into(iv_details_movie_backdrop)
    }

    override fun showMovieRating(rating: String) {
        tv_movie_details_rating.text = rating
    }

    override fun showMovieMpaaRating(mpaaRating: String?) {
        tv_movie_details_mpaa_rating.text = mpaaRating
    }

    @SuppressLint("SetTextI18n")
    override fun showMovieDuration(runtime: String) {
        tv_movie_details_runtime.text = runtime
    }

    override fun showMovieGenres(genres: String) {
        tv_movie_details_genres.text = genres
    }

    override fun showMovieReleaseDate(releaseDate: String) {
        tv_movie_details_release_date.text = releaseDate
    }

    override fun showFavorite(b: Boolean) {
        if (b) {

        } else {

        }
    }

    override fun showMovieTrailers(trailers: List<Trailer>) {
        if (trailers.isEmpty()) {
            return
        }
        showView(container_view_movie_details_trailers)
        rv_movie_details_trailers.setHasFixedSize(true)
        rv_movie_details_trailers.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_movie_details_trailers.adapter = MovieTrailersAdapter(this)
        (rv_movie_details_trailers.adapter as MovieTrailersAdapter).submitList(trailers)
    }

    override fun onTrailerClick(trailer: Trailer) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(trailer.getYoutubeUrl()))
        startActivity(intent)
    }

    override fun showMovieOverview(overview: String?) {
        if (overview == null) {
            return
        }
        showView(container_movie_details_overview)
        tv_movie_details_overview_content.text = overview
    }

    override fun showMovieCasts(casts: String?) {
        if (casts == null) {
            return
        }
        showView(container_movie_details_casts)
        tv_movie_details_casts_content.text = casts

    }
    override fun showMovieCrews(crews: String?) {
        if (crews == null) {
            return
        }
        showView(container_movie_details_crews)
        tv_movie_details_featured_crews_content.text = crews
    }

    override fun showMovieReviews(reviews: String?) {
        if (reviews == null) {
            return
        }
        showView(container_movie_details_reviews)
        tv_movie_details_featured_reviews_content.text = reviews
    }

    private fun showView(v: View?) {
        v?.visibility = View.VISIBLE
    }

    private fun hideView(v: View?) {
        v?.visibility = View.GONE
    }

    companion object {
        private const val ARG_MOVIE = "mTrendingMovie"

        fun newInstance(trendingMovie: TrendingMovie): MovieDetailsFragment {
            val fragment = MovieDetailsFragment()
            val args = Bundle()
            args.putParcelable(ARG_MOVIE, trendingMovie)
            fragment.arguments = args
            return fragment
        }
    }
}
