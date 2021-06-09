package com.shakib.baseapplication.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shakib.baseapplication.common.base.BaseFragment
import com.shakib.baseapplication.common.extensions.printDebugLog
import com.shakib.baseapplication.databinding.FragmentSecondaryBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class SecondaryFragment : BaseFragment<FragmentSecondaryBinding>() {

    private val compositeDisposable = CompositeDisposable()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSecondaryBinding.inflate(inflater, container, false)

    override fun configureViews(savedInstanceState: Bundle?) {
        super.configureViews(savedInstanceState)
        binding.tv.setTextColor(Color.BLUE)

        rxPlayground()
    }

    private fun rxPlayground() {
        val animalObservable = Observable.just("Ant", "Bee", "Cat", "Dog", "Fox")
        val animalObserver = object : DisposableObserver<String>() {
            override fun onNext(s: String) {
                //printDebugLog("Name: $s")
            }

            override fun onError(e: Throwable) {
                //printDebugLog("onError: " + e.message)
            }

            override fun onComplete() {
                //printDebugLog("All items are emitted!")
            }
        }
        val animalObserverFilterMap = object : DisposableObserver<String>() {
            override fun onNext(s: String) {
                //printDebugLog("Name: $s")
            }

            override fun onError(e: Throwable) {
                //rintDebugLog("onError: " + e.message)
            }

            override fun onComplete() {
                //printDebugLog("All items are emitted!")
            }
        }

        compositeDisposable.add(
            animalObservable
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribeWith(animalObserver)
        )

        compositeDisposable.add(
            animalObservable
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .filter { it.lowercase().startsWith("b") }
                .map { it.uppercase() }
                .subscribeWith(animalObserverFilterMap)
        )
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}