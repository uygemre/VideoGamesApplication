package com.base.core.extensions

fun Any.getClassTag(): String = this.javaClass.simpleName

fun Any.getMethodTag(): String =
    getClassTag() + object : Any() {}.javaClass.enclosingMethod?.name
