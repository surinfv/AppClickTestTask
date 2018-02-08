package com.fed.appclicktesttask

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.fed.appclicktesttask.model.POJO
import com.fed.appclicktesttask.network.RetroClient
import com.tbruyelle.rxpermissions2.RxPermissions
import retrofit2.Call
import retrofit2.Callback


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val SAMPLE_ID: Long = 85950205030644900
    private var rxPermissions: RxPermissions? = null
    private var webView: WebView? = null
    private var progressBar: ProgressBar? = null
    private var imsi: Long = SAMPLE_ID

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView = findViewById(R.id.webview)
        progressBar = findViewById(R.id.progressBar)
        rxPermissions = RxPermissions(this)

        checkPermissions()
        Log.i(TAG, imsi.toString())
    }

    private fun checkPermissions() {
        rxPermissions?.requestEach(Manifest.permission.READ_PHONE_STATE)
                ?.subscribe { permission ->
                    when {
                        permission.granted -> {
                            setIMSI()
                            doRequest()
                        }
                        permission.shouldShowRequestPermissionRationale -> checkPermissions()
                        else -> showAlertPermissionDialog()
                    }
                }
    }

    @SuppressLint("MissingPermission", "HardwareIds")
    private fun setIMSI() {
        val telephoneManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        imsi = telephoneManager.subscriberId.toLong()
    }

    private fun doRequest() {
        val api = RetroClient.apiService
        val call: Call<POJO> = api.requestForAd(SAMPLE_ID)
        call.enqueue(object : Callback<POJO> {
            override fun onResponse(call: Call<POJO>, response: retrofit2.Response<POJO>) {
                val pojo = response.body()
                if (response.code() == 200) {
                    loadWebView(pojo?.url)
                } else showServerAnswerAlertDialog(pojo?.message)
            }

            override fun onFailure(call: Call<POJO>, t: Throwable) {
                Log.e(TAG, "Retrofit onFailure() : " + t.toString())
            }
        })
    }

    private fun loadWebView(url: String?) {
        webView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                finishAffinity()
                startActivity(intent)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                showWebView()
            }
        }
        webView!!.loadUrl(url)
    }

    private fun showWebView() {
        progressBar?.visibility = View.GONE
        webView?.visibility = View.VISIBLE
    }

    private fun showServerAnswerAlertDialog(string: String?) {
        val alertDialog = AlertDialog.Builder(this@MainActivity).create()
        alertDialog.apply {
            setTitle("wrong answer from server")
            setMessage(string)
            setButton(AlertDialog.BUTTON_POSITIVE, "OK", { _, _ ->
                finishAffinity()
            })
        }.show()
    }

    private fun showAlertPermissionDialog() {
        val alertDialog = AlertDialog.Builder(this@MainActivity).create()
        alertDialog.apply {
            setTitle("App need permission")
            setMessage("app need permission to get IMSI")
            setButton(AlertDialog.BUTTON_POSITIVE, "settings", { _, _ ->
                openSettings()
            })
        }.show()
    }

    private fun openSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
//        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        finishAffinity()
        startActivity(intent)
    }
}
