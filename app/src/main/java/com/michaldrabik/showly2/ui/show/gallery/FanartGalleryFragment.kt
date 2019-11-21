package com.michaldrabik.showly2.ui.show.gallery

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.michaldrabik.showly2.R
import com.michaldrabik.showly2.appComponent
import com.michaldrabik.showly2.model.IdTrakt
import com.michaldrabik.showly2.ui.common.base.BaseFragment
import com.michaldrabik.showly2.ui.show.gallery.recycler.FanartGalleryAdapter
import com.michaldrabik.showly2.utilities.extensions.onClick
import kotlinx.android.synthetic.main.fragment_fanart_gallery.*

@SuppressLint("SetTextI18n", "DefaultLocale")
class FanartGalleryFragment : BaseFragment<FanartGalleryViewModel>() {

  companion object {
    const val ARG_SHOW_ID = "ARG_SHOW_ID"
  }

  override val layoutResId = R.layout.fragment_fanart_gallery

  private val showId by lazy { IdTrakt(arguments?.getLong(ARG_SHOW_ID, -1) ?: -1) }
  private val galleryAdapter by lazy { FanartGalleryAdapter() }

  override fun onCreate(savedInstanceState: Bundle?) {
    appComponent().inject(this)
    super.onCreate(savedInstanceState)
  }

  override fun createViewModel(provider: ViewModelProvider) =
    provider.get(FanartGalleryViewModel::class.java)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    requireActivity().requestedOrientation = SCREEN_ORIENTATION_LANDSCAPE
    setupView()
    viewModel.run {
      uiStream.observe(viewLifecycleOwner, Observer { render(it!!) })
      loadImage(showId)
    }
  }

  override fun onResume() {
    super.onResume()
    handleBackPressed()
  }

  private fun setupView() {
    fanartGalleryBackArrow.onClick { requireActivity().onBackPressed() }
    fanartGalleryPager.run {
      adapter = galleryAdapter
      offscreenPageLimit = 2
      fanartGalleryPagerIndicator.setViewPager(this)
      adapter?.registerAdapterDataObserver(fanartGalleryPagerIndicator.adapterDataObserver)
    }
  }

  private fun render(uiModel: FanartGalleryUiModel) {
    uiModel.run {
      images?.let {
        galleryAdapter.setItems(it)
      }
    }
  }

  private fun handleBackPressed() {
    val dispatcher = requireActivity().onBackPressedDispatcher
    dispatcher.addCallback(viewLifecycleOwner) {
      remove()
      findNavController().popBackStack()
    }
  }
}
