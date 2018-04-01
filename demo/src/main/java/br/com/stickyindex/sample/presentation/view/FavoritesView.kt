package br.com.stickyindex.sample.presentation.view


import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.stickyindex.sample.R
import br.com.stickyindex.sample.databinding.FavoritesViewBinding
import br.com.stickyindex.sample.presentation.presenter.FavoritesPresenter
import dagger.android.support.AndroidSupportInjection.inject
import javax.inject.Inject

/**
 * Displays user favorite's contacts
 */
class FavoritesView : Fragment() {

    @Inject
    lateinit var presenter: FavoritesPresenter

    /**
     * {@inheritDoc}
     */
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FavoritesViewBinding>(
                inflater,
                R.layout.favorites_view,
                container,
                false
        )
        binding.presenter = presenter
        return binding.root
    }

    /**
     * {@inheritDoc}
     */
    override fun onAttach(context: Context?) {
        inject(this)
        super.onAttach(context)
    }
}
