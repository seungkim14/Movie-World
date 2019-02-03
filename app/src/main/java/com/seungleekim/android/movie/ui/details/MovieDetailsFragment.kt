package com.seungleekim.android.movie.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seungleekim.android.movie.R
import com.seungleekim.android.movie.model.MovieDetails
import com.seungleekim.android.movie.model.TrendingMovie
import com.seungleekim.android.movie.ui.MovieDetailsActivity
import com.seungleekim.android.movie.util.GlideApp
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.view_movie_details_top.*
import javax.inject.Inject

class MovieDetailsFragment @Inject constructor() : DaggerFragment(), MovieDetailsContract.View {

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
        showMovieReleasedate(movieDetails.releaseDate)
    }

    private fun showMovieTitle(title: String) {
        collapsing_toolbar.title = title
        tv_movie_details_title.text = title
    }

    private fun showMovieBackdrop(backdropUrl: String) {
        GlideApp.with(context!!).load(backdropUrl).into(iv_details_movie_backdrop)
    }

    private fun showMovieRating(rating: String) {
        tv_movie_details_rating.text = rating
    }

    private fun showMovieMpaaRating(mpaaRating: String?) {
        tv_movie_details_mpaa_rating.text = mpaaRating
    }

    @SuppressLint("SetTextI18n")
    private fun showMovieDuration(runtime: String) {
        tv_movie_details_runtime.text = runtime
    }

    private fun showMovieGenres(genres: String) {
        tv_movie_details_genres.text = genres
    }

    private fun showMovieReleasedate(releaseDate: String) {
        tv_movie_details_release_date.text = releaseDate
    }

    override fun showMovieTrailer() {
    }

    override fun showGetTickets() {
    }

    override fun showShare() {
    }

    override fun setFavorite(b: Boolean) {
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
