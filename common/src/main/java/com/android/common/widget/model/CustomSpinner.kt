package com.android.common.widget.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CustomSpinner(
    var id : String,
    var text : String,
    var optional_text : String = "",
    var optional_id : Int = -1,
)