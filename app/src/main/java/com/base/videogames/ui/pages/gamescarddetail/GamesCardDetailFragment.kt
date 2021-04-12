package com.base.videogames.ui.pages.gamescarddetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.webkit.*
import android.widget.ImageButton
import androidx.lifecycle.Observer
import com.base.component.VideoGamesRecyclerviewAdapter
import com.base.component.ui.gamesdetail.GamesDetailDTO
import com.base.core.extensions.setup
import com.base.core.helpers.LocalPrefManager
import com.base.core.networking.DataFetchResult
import com.base.data.database.model.FavoriteGamesDTO
import com.base.videogames.R
import com.base.videogames.ui.base.fragment.BaseViewModelFragment
import com.base.videogames.ui.pages.gamescarddetail.viewmodel.GamesCardDetailFragmentViewModel
import kotlinx.android.synthetic.main.fragment_games_card.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

// Created by Emre UYGUN on 4/10/21

class GamesCardDetailFragment : BaseViewModelFragment<GamesCardDetailFragmentViewModel>() {

    override val viewModelClass = GamesCardDetailFragmentViewModel::class.java
    override val layoutViewRes = R.layout.fragment_games_card

    @Inject
    lateinit var adapter: VideoGamesRecyclerviewAdapter

    @Inject
    lateinit var localPrefManager: LocalPrefManager

    private var slug: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            it.getString("slug")?.let {
                slug = it
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.getAdapter().itemViewClickListener = { _view, _item, _position ->
            when (_item) {
                is GamesDetailDTO -> {
                    if (_view.id == R.id.img_favorites) {
                        val imgFavorites = _view.findViewById<ImageButton>(R.id.img_favorites)
                        if (localPrefManager.pull(_item.slug ?: "", "") == "") {
                            viewModel.insertFav(
                                FavoriteGamesDTO(
                                    id = 0,
                                    slug = _item.slug,
                                    background_image = _item.background_image,
                                    name = _item.name,
                                    released = _item.released,
                                    rating = _item.rating,
                                    gamesId = _item.gamesId
                                )
                            )
                            localPrefManager.push(_item.slug ?: "", _item.slug)
                            imgFavorites.setBackgroundResource(R.drawable.ic_favorites_selected)
                        } else {
                            viewModel.deleteFavoritesById(_item.gamesId ?: 0)
                            localPrefManager.push(_item.slug ?: "", "")
                            imgFavorites.setBackgroundResource(R.drawable.ic_favorites_unselected)
                        }
                    }
                }
            }
        }

        rv_games_detail.setup(adapter = adapter.getAdapter())
        bindObserver()
        setupView()
    }

    private fun setupView() {
        btn_back.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    @SuppressLint("CheckResult")
    fun bindObserver() {
        viewModel.getGamesDetail(slug)
        viewModel.gamesDetailPublishSubject.subscribe {
            adapter.getAdapter().updateAllItems(it)
        }
        viewModel.gamesDetailDataResult?.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataFetchResult.Progress -> {
                }
                is DataFetchResult.Failure -> {
                }
                is DataFetchResult.Success -> {
                    tv_title.text = it.data.name
                    it.data.description.let { _description ->
                        val jsonData = JSONObject().apply {
                            put("HtmlText", _description)
                            put("ContentId", it.data.id)
                        }

                        val base64Data = Base64.encodeToString(
                            jsonData.toString().toByteArray(),
                            Base64.DEFAULT
                        )
                        setupNewsDetailWebView(base64Data)
                    }
                }
            }
        })
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun setupNewsDetailWebView(htmlData: String) {
        val webView = WebView(requireActivity())
        webView.loadUrl("file:///android_asset/index.html")
        webView.addJavascriptInterface(WebAppInterface(), "AndroidInterface")
        val webSettings = webView.settings
        webSettings.domStorageEnabled = true
        webSettings.javaScriptEnabled = true
        webView.webViewClient = GamesDetailWebViewClient(htmlData)
        webView.webChromeClient = GamesDetailWebChromeClient()
    }

    inner class WebAppInterface {
        @JavascriptInterface
        fun htmlResult(result: String?) {
            activity!!.runOnUiThread {
                Timber.d(result.toString())
                viewModel.parseHtmlData(result)
            }
        }
    }

    private class GamesDetailWebViewClient(var htmlData: String) : WebViewClient() {
        override fun onPageFinished(view: WebView, url: String) {
            view.loadUrl("javascript:alert(htmlParseAndroid('$htmlData'))")
        }
    }

    private class GamesDetailWebChromeClient : WebChromeClient() {
        override fun onJsAlert(
            view: WebView?, url: String?, message: String?, result: JsResult?
        ): Boolean {
            Timber.d(message)
            result?.confirm()
            return true
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle?) =
            GamesCardDetailFragment().apply {
                arguments = bundle
            }
    }
}