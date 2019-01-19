package me.navid.scrambilo.repository

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.navid.scrambilo.MyApplication
import me.navid.scrambilo.model.Language
import me.navid.scrambilo.model.Result
import me.navid.scrambilo.model.Trie
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@SuppressLint("CheckResult")
class DictionaryRepository @Inject constructor(private val app: MyApplication) {
    //Reads a file and creates it's dictionary
    fun createDictionary(lang: Language): LiveData<Result<Trie>> {
        val result = MutableLiveData<Result<Trie>>()

        Single.fromCallable {
            val trie = Trie()
            val stream = app.resources.assets.open(lang.file)
            val reader = InputStreamReader(stream)
            reader.forEachLine { trie.insert(it) }
            trie
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result.value = Result.Success(it) }, {
                it.printStackTrace()
                result.value = Result.Error(Exception(it))
            })

        return result
    }
}