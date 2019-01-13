package la.hitomi.sm

import com.google.gson.annotations.SerializedName

/**
 * Created by jeong-woochang on 2018. 7. 20..
 */

class PeopleRepo {
    @SerializedName("id")
    internal var id: String? = null
    @SerializedName("writer")
    internal var writer: String? = null
    @SerializedName("content")
    internal var content: String? = null
    @SerializedName("date")
    internal var date: String? = null
}