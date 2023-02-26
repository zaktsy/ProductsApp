package com.zaktsy.products.utils.mappers

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object{
        fun Date.toSimpleString() : String {
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            return format.format(this)
        }
    }
}