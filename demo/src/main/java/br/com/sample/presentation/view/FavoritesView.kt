package br.com.sample.presentation.view


import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.sample.R
import br.com.sample.databinding.FavoritesViewBinding
import br.com.sample.presentation.presenter.FavoritesPresenter
import dagger.android.support.AndroidSupportInjection.inject
import javax.inject.Inject

/**
 * Created by edgar on 6/7/15.
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
