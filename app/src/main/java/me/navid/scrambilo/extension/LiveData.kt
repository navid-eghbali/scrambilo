package me.navid.scrambilo.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

fun <X, Y> LiveData<X>.map(body: (X) -> Y): LiveData<Y> = Transformations.map(this, body)

fun <X, Y> LiveData<X>.switchMap(body: (X) -> LiveData<Y>): LiveData<Y> = Transformations.switchMap(this, body)