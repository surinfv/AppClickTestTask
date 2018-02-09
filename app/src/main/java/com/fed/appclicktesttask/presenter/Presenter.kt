package com.fed.appclicktesttask.presenter

import android.Manifest
import android.util.Log
import com.fed.appclicktesttask.model.responsePOJO
import com.fed.appclicktesttask.network.RetroClient
import com.tbruyelle.rxpermissions2.RxPermissions
import retrofit2.Call
import retrofit2.Callback


class Presenter(private val rxPermissions: RxPermissions) : AdPresenterInterface {
    private val TAG = "Presenter"
    private val SAMPLE_IMSI: Long = 85950205030644900

    private var imsi: Long = SAMPLE_IMSI
    private var fragment: AdFragmentInterface? = null

    override fun onFragmentLoaded() {
        checkPermissions()
    }

    override fun attachView(fragment: AdFragmentInterface) {
        this.fragment = fragment
    }

    override fun detachView() {
        fragment = null
    }

    private fun checkPermissions() {
        rxPermissions.requestEach(Manifest.permission.READ_PHONE_STATE)
                ?.subscribe { permission ->
                    when {
                        permission.granted -> {
                            updateIMSI()
                            doRequest()
                        }
                        permission.shouldShowRequestPermissionRationale -> checkPermissions()
                        else -> fragment?.showAlertPermissionDialog()
                    }
                }
    }

    private fun updateIMSI() {
        val newIMSI = fragment?.getIMSI()
        newIMSI?.let { imsi = newIMSI }
    }

    private fun doRequest() {
        val api = RetroClient.apiService
        val call: Call<responsePOJO> = api.requestForAd(imsi)
        call.enqueue(object : Callback<responsePOJO> {
            override fun onResponse(call: Call<responsePOJO>, response: retrofit2.Response<responsePOJO>) {
                val pojo = response.body()
                if (response.code() == 200) {
                    fragment?.loadWebView(pojo?.url)
                } else fragment?.showServerAnswerAlertDialog(pojo?.message)
            }

            override fun onFailure(call: Call<responsePOJO>, t: Throwable) {
                Log.e(TAG, "Retrofit onFailure() : " + t.toString())
            }
        })
    }
}