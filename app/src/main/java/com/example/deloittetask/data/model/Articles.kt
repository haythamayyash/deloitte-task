package com.example.deloittetask.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class Articles(
    @SerializedName("copyright")
    var copyright: String?,
    @SerializedName("num_results")
    var numResults: Int?,
    @SerializedName("results")
    var articles: List<Article?>?,
    @SerializedName("status")
    var status: String?
) {
    @Entity
    data class Article(
        @SerializedName("abstract")
        var `abstract`: String?,
        @SerializedName("adx_keywords")
        var adxKeywords: String?,
        @SerializedName("asset_id")
        var assetId: Long?,
        @SerializedName("byline")
        var byline: String?,
        @SerializedName("des_facet")
        var desFacet: List<String?>?,
        @SerializedName("eta_id")
        var etaId: Int?,
        @SerializedName("geo_facet")
        var geoFacet: List<String?>?,
        @SerializedName("id")
        @PrimaryKey
        var id: Long?,
        @SerializedName("media")
        var media: List<Media?>?,
        @SerializedName("nytdsection")
        var nytdsection: String?,
        @SerializedName("org_facet")
        var orgFacet: List<String?>?,
        @SerializedName("per_facet")
        var perFacet: List<String?>?,
        @SerializedName("published_date")
        var publishedDate: String?,
        @SerializedName("section")
        var section: String?,
        @SerializedName("source")
        var source: String?,
        @SerializedName("subsection")
        var subsection: String?,
        @SerializedName("title")
        var title: String?,
        @SerializedName("type")
        var type: String?,
        @SerializedName("updated")
        var updated: String?,
        @SerializedName("uri")
        var uri: String?,
        @SerializedName("url")
        var url: String?
    ) {
        constructor() : this(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )

        data class Media(
            @SerializedName("approved_for_syndication")
            var approvedForSyndication: Int?,
            @SerializedName("caption")
            var caption: String?,
            @SerializedName("copyright")
            var copyright: String?,
            @SerializedName("media-metadata")
            var mediaMetadata: List<MediaMetadata?>?,
            @SerializedName("subtype")
            var subtype: String?,
            @SerializedName("type")
            var type: String?
        ) {
            data class MediaMetadata(
                @SerializedName("format")
                var format: String?,
                @SerializedName("height")
                var height: Int?,
                @SerializedName("url")
                var url: String?,
                @SerializedName("width")
                var width: Int?
            )
        }
    }
}