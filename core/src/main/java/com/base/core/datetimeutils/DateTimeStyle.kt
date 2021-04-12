package com.base.core.datetimeutils

enum class DateTimeStyle {
    /**
     * Style full e.g Tuesday, June 13, 2017
     */
    FULL,
    /**
     * Style long e.g June 13, 2017
     */
    LONG,
    /**
     * Style medium e.g Jun 13, 2017
     */
    MEDIUM,
    /**
     * Style short e.g 06/13/17
     */
    SHORT,
    /**
     * Style for ago time e.g 3h ago
     */
    MEDIUM_WITH_POINT,
    /**
     * Style for ago time e.g 3 hours ago
     */
    AGO_FULL_STRING
}