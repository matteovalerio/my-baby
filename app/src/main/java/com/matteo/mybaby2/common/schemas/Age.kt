package com.matteo.mybaby2.common.schemas

data class Age(val years: Int, val months: Int, val days: Int) {
    override fun toString(): String {
        var res = ""
        if(years != 0) {
            res += "$years anni"
        }
        if(months != 0) {
            res += if(res != "") {
                ", $months mesi"
            } else {
                "$months mesi"
            }
        }
        if(days != 0) {
            res += if(res != "") {
                ", $days giorni"
            } else {
                "$days giorni"
            }
        }
        return res
    }
}
