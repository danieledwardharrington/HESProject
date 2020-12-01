package com.example.hesproject.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hesproject.R
import com.example.hesproject.adapter.RestReposAdapter
import com.example.hesproject.persistence.UserRepoEntity
import com.example.hesproject.remote.UserRepoModel
import com.example.hesproject.viewmodels.RepoViewModelFactory
import com.example.hesproject.viewmodels.UserRepoViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment: Fragment(), RestReposAdapter.ItemListener {

    private lateinit var repoViewModel: UserRepoViewModel
    private lateinit var restAdapter: RestReposAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        repoViewModel = ViewModelProvider(this, RepoViewModelFactory(requireActivity().application)).get(UserRepoViewModel::class.java)

        search_edit_text.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable) {
                Log.d("afterTextChanged", s.toString())
                repoViewModel.getRestUsers(s.toString(), restAdapter)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Log.d("onTextChanged", s.toString())
                //repoViewModel.getRestUsers(s.toString(), restAdapter)
            }
        })

        initRV()
    }

    private fun initRV() {
        results_rv.layoutManager = LinearLayoutManager(context)
        results_rv.setHasFixedSize(true)
        restAdapter = RestReposAdapter()
        results_rv.adapter = restAdapter
        restAdapter.setListener(this)
    }

    override fun onItemClicked(repoModel: UserRepoModel, favorited: Boolean) {
        val rEntity: UserRepoEntity
        if (!repoModel.getFavorited()) {
            Log.d("SearchFragment", "FAVORITED")
            repoModel.setFavorited(true)
            rEntity = restToEntity(repoModel)
            Log.d("onItemClicked", rEntity.toString())
            repoViewModel.insert(rEntity)
        } else {
            Log.d("SearchFragment", "UNFAVORITED")
            repoModel.setFavorited(false)
            rEntity = restToEntity(repoModel)
            repoViewModel.delete(rEntity)
        }
    }

    private fun restToEntity(repoModel: UserRepoModel): UserRepoEntity {
        if(repoModel.getName().isNullOrEmpty()) {
            repoModel.setName("None")
        }
        if(repoModel.getDescription().isNullOrEmpty()) {
            repoModel.setDescription("None")
        }
        if(repoModel.getLanguage().isNullOrEmpty()) {
            repoModel.setLanguage("None")
        }

/*        val rEntity: UserRepoEntity = UserRepoEntity()
        rEntity.setName(repoModel.getName())
        rEntity.setDescription(repoModel.getDescription())
        rEntity.setLanguage(repoModel.getLanguage())
        rEntity.setAvatarUrl(repoModel.getOwner().getAvatarUrl())
        rEntity.setFavorited(repoModel.getFavorited())
        return rEntity*/

        return (UserRepoEntity(repoModel.getName(), repoModel.getDescription(), repoModel.getLanguage(), repoModel.getOwner().getAvatarUrl(), repoModel.getFavorited()))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        repoViewModel.clearDisposable()
    }
}