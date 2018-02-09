package com.fed.appclicktesttask.presenter

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AlertDialog
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.fed.appclicktesttask.R.id.progressBar
import com.fed.appclicktesttask.R.id.webview
import com.fed.appclicktesttask.model.POJO
import com.fed.appclicktesttask.network.RetroClient
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_layout.*
import retrofit2.Call
import retrofit2.Callback


class Presenter (private val rxPermissions: RxPermissions) {
//    private val TAG = "Presenter"
//    private val SAMPLE_ID: Long = 85950205030644900
//
//    fun onFragmentLoaded() {
//        checkPermissions()
//    }
//
//    private fun checkPermissions() {
//        rxPermissions.requestEach(Manifest.permission.READ_PHONE_STATE)
//                ?.subscribe { permission ->
//                    when {
//                        permission.granted -> {
//                            setIMSI()
//                            doRequest()
//                        }
//                        permission.shouldShowRequestPermissionRationale -> checkPermissions()
//                        else -> showAlertPermissionDialog()
//                    }
//                }
//    }
//
//    @SuppressLint("MissingPermission", "HardwareIds")
//    private fun setIMSI() {
//        val telephoneManager = activity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//        imsi = telephoneManager.subscriberId.toLong()
//        Log.i(TAG, imsi.toString())
//    }
//
//    private fun doRequest() {
//        val api = RetroClient.apiService
//        val call: Call<POJO> = api.requestForAd(SAMPLE_ID)
//        call.enqueue(object : Callback<POJO> {
//            override fun onResponse(call: Call<POJO>, response: retrofit2.Response<POJO>) {
//                val pojo = response.body()
//                if (response.code() == 200) {
//                    loadWebView(pojo?.url)
//                } else showServerAnswerAlertDialog(pojo?.message)
//            }
//
//            override fun onFailure(call: Call<POJO>, t: Throwable) {
//                Log.e(TAG, "Retrofit onFailure() : " + t.toString())
//            }
//        })
//    }
//
//    private fun loadWebView(url: String?) {
//        webview.webViewClient = object : WebViewClient() {
//            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
//                val intent = Intent(Intent.ACTION_VIEW)
//                intent.data = Uri.parse(url)
//                activity.finishAffinity()
//                startActivity(intent)
//                return true
//            }
//
//            override fun onPageFinished(view: WebView?, url: String?) {
//                showWebView()
//            }
//        }
//        webview.loadUrl(url)
//    }
//
//    private fun showWebView() {
//
//    }
//
//    private fun showServerAnswerAlertDialog(string: String?) {
//        val alertDialog = AlertDialog.Builder(activity).create()
//        alertDialog.apply {
//            setTitle("wrong answer from server")
//            setMessage(string)
//            setButton(AlertDialog.BUTTON_POSITIVE, "OK", { _, _ ->
//                activity.finishAffinity()
//            })
//        }.show()
//    }
//
//    private fun showAlertPermissionDialog() {
//        val alertDialog = AlertDialog.Builder(activity).create()
//        alertDialog.apply {
//            setTitle("App need permission")
//            setMessage("app need permission to get IMSI")
//            setButton(AlertDialog.BUTTON_POSITIVE, "settings", { _, _ ->
//                openSettings()
//            })
//        }.show()
//    }
//
//    private fun openSettings() {
//        val intent = Intent()
//        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//        val uri = Uri.fromParts("package", activity.packageName, null)
//        intent.data = uri
////        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
////        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
////        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
//        activity.finishAffinity()
//        startActivity(intent)
//    }
}