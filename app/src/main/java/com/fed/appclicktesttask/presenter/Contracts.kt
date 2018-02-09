package com.fed.appclicktesttask.presenter


interface AdFragmentInterface {
    fun showAlertPermissionDialog()
    fun showServerAnswerAlertDialog(string: String?)
    fun loadWebView(url: String?)
    fun getIMSI(): Long
}

interface AdPresenterInterface {
    fun onFragmentLoaded()
    fun attachView(fragment: AdFragmentInterface)
    fun detachView()
}