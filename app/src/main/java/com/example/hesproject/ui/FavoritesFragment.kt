package com.example.hesproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hesproject.R
import com.example.hesproject.adapter.FavReposAdapter
import com.example.hesproject.persistence.UserRepoEntity
import com.example.hesproject.viewmodels.RepoViewModelFactory
import com.example.hesproject.viewmodels.UserRepoViewModel
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment: Fragment(), FavReposAdapter.HeartListener {

    private lateinit var repoViewModel: UserRepoViewModel
    private var favAdapter: FavReposAdapter = FavReposAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRV()
    }

    private fun initVM() {
        //initRV()
        repoViewModel = ViewModelProvider(this, RepoViewModelFactory(requireActivity().application)).get(UserRepoViewModel::class.java)
        repoViewModel.getAllDBRepos()
        repoViewModel.favRepos.observe(viewLifecycleOwner, Observer {
            favAdapter.submitList(ArrayList(it))
        })
    }

    private fun initRV() {
        initVM()
        favorites_rv.layoutManager = LinearLayoutManager(context)
        favorites_rv.setHasFixedSize(true)
        favorites_rv.adapter = favAdapter
        favAdapter.setListener(this)
    }

    override fun onItemClicked(repoEntity: UserRepoEntity) {
        repoViewModel.delete(repoEntity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        repoViewModel.clearDisposable()
    }
}