package com.vanh1200.moviemvvm.data.remote.response

import com.google.gson.annotations.SerializedName

open class BaseListResponse<Item>(
    @SerializedName("page") val page: Int? = null,
    @SerializedName("total_results") val totalResults: Int? = null,
    @SerializedName("total_pages") val totalPages: Int? = null,
    @SerializedName("results") var results: ArrayList<Item>? = null
) : BaseResponse()