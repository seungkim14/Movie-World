package com.seungleekim.android.movie.network

import com.google.gson.*
import com.seungleekim.android.movie.model.*
import java.lang.reflect.Type

class MovieDetailsDeserializer : JsonDeserializer<MovieDetails> {

    override fun deserialize(json: JsonElement?,
                             typeOfT: Type?,
                             context: JsonDeserializationContext?): MovieDetails? {
        val rootJsonObject : JsonObject = json?.asJsonObject ?: return null

        val id = getId(rootJsonObject)
        val backdropPath = getBackdropPath(rootJsonObject)
        val genreIds = getGenres(rootJsonObject)
        val overview = getOverview(rootJsonObject)
        val releaseDate = getReleaseDate(rootJsonObject)
        val duration = getDuration(rootJsonObject)
        val rating = getRating(rootJsonObject)
        val trailer = getTrailer(rootJsonObject)
        val reviews = getReviews(rootJsonObject)
        val mpaaRating = getMpaaRating(rootJsonObject)
        val credits = getCredits(rootJsonObject)

        return MovieDetails(
            id,
            backdropPath,
            genreIds,
            overview,
            releaseDate,
            duration,
            rating,
            trailer,
            reviews,
            mpaaRating,
            credits
        )
    }

    private fun getId(rootJsonObject: JsonObject): Int {
        return rootJsonObject.get("id").asInt
    }

    private fun getBackdropPath(rootJsonObject: JsonObject): String {
        return rootJsonObject.get("backdrop_path").asString
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

    private fun getOverview(rootJsonObject: JsonObject): String {
        return rootJsonObject.get("overview").asString
    }

    private fun getReleaseDate(rootJsonObject: JsonObject): String {
        return rootJsonObject.get("release_date").asString
    }

    private fun getDuration(rootJsonObject: JsonObject): Int {
        return rootJsonObject.get("runtime").asInt
    }

    private fun getRating(rootJsonObject: JsonObject): Double {
        return rootJsonObject.get("vote_average").asDouble
    }

    private fun getTrailer(rootJsonObject: JsonObject): Trailer {
        val videosJsonArray = getJsonArrayFromJsonObject(rootJsonObject, "videos")
        val videoJsonObject = videosJsonArray.get(0).asJsonObject
        return Trailer(
            id = videoJsonObject.get("id").asString,
            site = videoJsonObject.get("site").asString,
            key = videoJsonObject.get("key").asString
        )
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

    private fun getJsonArrayFromJsonObject(jsonObject: JsonObject, keyword: String): JsonArray {
        return jsonObject.get(keyword).asJsonObject.get("results").asJsonArray
    }

    private fun getCredits(rootJsonObject: JsonObject) : Credits {
        val creditsJsonObject = rootJsonObject.get("credits").asJsonObject
        val casts = getCasts(creditsJsonObject)
        val crews = getCrews(creditsJsonObject)
        return Credits(casts, crews)
    }

    private fun getCasts(creditsJsonObject: JsonObject): MutableList<Cast> {
        val casts = mutableListOf<Cast>()
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

    private fun getCrews(creditsJsonObject: JsonObject): MutableList<Crew> {
        val crews = mutableListOf<Crew>()
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
}
