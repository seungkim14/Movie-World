package com.seungleekim.android.movie.network
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.seungleekim.android.movie.model.Cast
import com.seungleekim.android.movie.model.Crew
import com.seungleekim.android.movie.model.MovieDetails
import com.seungleekim.android.movie.model.Review
import com.seungleekim.android.movie.model.Trailer
import java.lang.reflect.Type

class MovieDetailsDeserializer : JsonDeserializer<MovieDetails> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): MovieDetails? {
        val rootJsonObject = json?.asJsonObject ?: return null

        val id = getId(rootJsonObject)
        val backdropPath = getBackdropPath(rootJsonObject)
        val title = getTitle(rootJsonObject)
        val rating = getRating(rootJsonObject)
        val mpaaRating = getMpaaRating(rootJsonObject)
        val duration = getRuntime(rootJsonObject)
        val genreIds = getGenres(rootJsonObject)
        val releaseDate = getReleaseDate(rootJsonObject)
        val trailers = getTrailers(rootJsonObject)
        val overview = getOverview(rootJsonObject)
        val casts = getCasts(rootJsonObject)
        val crews = getCrews(rootJsonObject)
        val reviews = getReviews(rootJsonObject)

        return MovieDetails(
            id = id,
            backdropPath = backdropPath,
            title = title,
            rating = rating,
            mpaaRating = mpaaRating,
            runtime = duration,
            genreIds = genreIds,
            releaseDate = releaseDate,
            trailers = trailers,
            overview = overview,
            crews = crews,
            casts = casts,
            reviews = reviews
        )
    }

    private fun getId(rootJsonObject: JsonObject): Int {
        return rootJsonObject.get("id").asInt
    }

    private fun getTitle(rootJsonObject: JsonObject): String {
        return rootJsonObject.get("title").asString
    }

    private fun getBackdropPath(rootJsonObject: JsonObject): String {
        return rootJsonObject.get("backdrop_path").asString
    }

    private fun getRating(rootJsonObject: JsonObject): Double {
        return rootJsonObject.get("vote_average").asDouble
    }

    private fun getMpaaRating(rootJsonObject: JsonObject): String? {
        val releaseDatesJsonArray = getJsonArrayFromJsonObject(rootJsonObject, "release_dates")
        for (releaseDateJsonElement in releaseDatesJsonArray) {
            val releaseDateJsonObject = releaseDateJsonElement.asJsonObject
            val iso = releaseDateJsonObject.get("iso_3166_1").asString
            if (iso == "US") {
                return releaseDateJsonObject.get("release_dates").asJsonArray.get(0)
                    .asJsonObject.get("certification").asString
            }
        }
        return null
    }

    private fun getRuntime(rootJsonObject: JsonObject): Int {
        return rootJsonObject.get("runtime").asInt
    }

    private fun getGenres(rootJsonObject: JsonObject): MutableList<Int> {
        val genreIds = mutableListOf<Int>()
        val jsonGenreIdsArray = rootJsonObject.get("genres").asJsonArray
        for (genreIdJsonElement in jsonGenreIdsArray) {
            val genreIdJsonObject = genreIdJsonElement.asJsonObject
            genreIds.add(genreIdJsonObject.get("id").asInt)
        }
        return genreIds
    }

    private fun getReleaseDate(rootJsonObject: JsonObject): String {
        return rootJsonObject.get("release_date").asString
    }

    private fun getTrailers(rootJsonObject: JsonObject): List<Trailer> {
        val trailers = mutableListOf<Trailer>()
        val videosJsonArray = getJsonArrayFromJsonObject(rootJsonObject, "videos")
        for (videosJsonElement in videosJsonArray) {
            val videoJsonObject = videosJsonElement.asJsonObject
            val trailer = Trailer(
                id = videoJsonObject.get("id").asString,
                name = videoJsonObject.get("name").asString,
                site = videoJsonObject.get("site").asString,
                key = videoJsonObject.get("key").asString
            )
            trailers.add(trailer)
        }
        return trailers
    }

    private fun getOverview(rootJsonObject: JsonObject): String {
        return rootJsonObject.get("overview").asString
    }

    private fun getCrews(rootJsonObject: JsonObject): List<Crew> {
        val crews = mutableListOf<Crew>()
        val creditsJsonObject = rootJsonObject.get("credits").asJsonObject
        val crewsJsonArray = creditsJsonObject.get("crew").asJsonArray
        for (crewJsonElement in crewsJsonArray) {
            val crewJsonObject = crewJsonElement.asJsonObject
            val department = crewJsonObject.get("department").asString
            if (department == "Directing" || department == "Writing") {
                val crew = Crew(
                    id = crewJsonObject.get("id").asInt,
                    name = crewJsonObject.get("name").asString,
                    department = department,
                    job = crewJsonObject.get("job").asString
                )
                crews.add(crew)
            }
        }
        return crews
    }

    private fun getCasts(rootJsonObject: JsonObject): List<Cast> {
        val casts = mutableListOf<Cast>()
        val creditsJsonObject = rootJsonObject.get("credits").asJsonObject
        val castsJsonArray = creditsJsonObject.get("cast").asJsonArray
        for (castJsonElement in castsJsonArray) {
            val castJsonObject = castJsonElement.asJsonObject
            val order = castJsonObject.get("order").asInt
            if (order > 4) break
            val cast = Cast(
                id = castJsonObject.get("id").asInt,
                order = order,
                name = castJsonObject.get("name").asString,
                character = castJsonObject.get("character").asString
            )
            casts.add(cast)
        }
        return casts
    }

    private fun getReviews(rootJsonObject: JsonObject): MutableList<Review> {
        val reviews = mutableListOf<Review>()
        val reviewsJsonArray = getJsonArrayFromJsonObject(rootJsonObject, "reviews")
        for (reviewJsonElement in reviewsJsonArray) {
            val reviewJsonObject = reviewJsonElement.asJsonObject
            val review = Review(
                id = reviewJsonObject.get("id").asString,
                author = reviewJsonObject.get("author").asString,
                content = reviewJsonObject.get("content").asString
            )
            reviews.add(review)
        }
        return reviews
    }

    private fun getJsonArrayFromJsonObject(jsonObject: JsonObject, keyword: String): JsonArray {
        return jsonObject.get(keyword).asJsonObject.get("results").asJsonArray
    }
}
