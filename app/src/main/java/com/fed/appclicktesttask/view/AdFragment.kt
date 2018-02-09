package com.fed.appclicktesttask.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.fed.appclicktesttask.R
import com.fed.appclicktesttask.presenter.AdFragmentInterface
import com.fed.appclicktesttask.presenter.AdPresenterInterface
import com.fed.appclicktesttask.presenter.Presenter
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_layout.*


class AdFragment : Fragment(), AdFragmentInterface {
    private val TAG = "AdFragment"
    private lateinit var presenter: AdPresenterInterface

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val rxPermissions = RxPermissions(activity)
        presenter = Presenter(rxPermissions)
        presenter.attachView(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_layout, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            webview?.restoreState(savedInstanceState)
            showWebView()
        } else presenter.onFragmentLoaded()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        webview.saveState(outState)
    }

    override fun onDetach() {
        super.onDetach()
        presenter.detachView()
    }

    @SuppressLint("MissingPermission", "HardwareIds")
    override fun getIMSI(): Long {
        val telephoneManager = activity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephoneManager.subscriberId.toLong()
    }

    override fun loadWebView(url: String?) {
        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
                activity.finishAndRemoveTask()
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                showWebView()
            }
        }
        webview.loadUrl(url)
    }

    override fun showServerAnswerAlertDialog(string: String?) {
        val alertDialog = AlertDialog.Builder(activity).create()
        alertDialog.apply {
            setTitle("wrong answer from server")
            setMessage(string)
            setButton(AlertDialog.BUTTON_POSITIVE, "OK", { _, _ ->
                activity.finishAffinity()
            })
        }.show()
    }

    override fun showAlertPermissionDialog() {
        val alertDialog = AlertDialog.Builder(activity).create()
        alertDialog.apply {
            setTitle("App need permission")
            setMessage("app need permission to get IMSI")
            setButton(AlertDialog.BUTTON_POSITIVE, "settings", { _, _ ->
                openSettings()
            })
        }.show()
    }

    private fun showWebView() {
        progressBar?.visibility = View.GONE
        webview.visibility = View.VISIBLE
    }

    private fun openSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", activity.packageName, null)
        intent.data = uri
        startActivity(intent)
        activity.finishAndRemoveTask()
    }
}
