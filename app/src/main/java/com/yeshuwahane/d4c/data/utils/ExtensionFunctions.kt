package com.yeshuwahane.d4c.data.utils



fun String.truncateWords(maxLength: Int = 40, ellipsis: String = "..."): String {
    val words = this.split(" ")
    return if (words.size > maxLength) {
        words.take(maxLength).joinToString(" ") + ellipsis
    } else {
        this
    }
}


fun String.truncateByChar(maxLength: Int = 40, ellipsis: String = "..."): String {
    return if (this.length > maxLength) {
        this.take(maxLength - ellipsis.length) + ellipsis
    } else {
        this
    }
}