package com.example.hesproject.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hesproject.R
import com.example.hesproject.remote.UserRepoModel
import kotlinx.android.synthetic.main.card_view_repo.view.*

class RestReposAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var listener: ItemListener

    interface ItemListener {
        fun onItemClicked(repoModel: UserRepoModel, favorited: Boolean)
    }

    fun setListener(listener: ItemListener) {
        this.listener = listener
    }

    private var restRepos: ArrayList<UserRepoModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RestReposAdapter.RepoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_view_repo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RestReposAdapter.RepoViewHolder -> {
                holder.bind(restRepos[position])
                holder.heartIcon.setOnClickListener {
                    if (!restRepos[position].getFavorited()) {
                        holder.heartIcon.setBackgroundResource(R.drawable.ic_heart_filled)
                    } else {
                        holder.heartIcon.setBackgroundResource(R.drawable.ic_heart)
                    }
                    listener.onItemClicked(restRepos[position], restRepos[position].getFavorited())
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return restRepos.size
    }

    fun submitList(repoList: ArrayList<UserRepoModel>) {
        restRepos = repoList
        notifyDataSetChanged()
    }


    class RepoViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        val context = itemView.context
        val repoName = itemView.repo_name_text_view
        val repoDesc = itemView.description_text_view
        val repoLang = itemView.language_name_text_view
        val userAvatar = itemView.avatar_iv
        val heartIcon = itemView.favorite_heart_iv

        fun bind(userRepoModel: UserRepoModel) {
            Log.d("RestRV", "BIND FUNCTION")
            repoName.text = userRepoModel.getName()
            Log.d("RestRV", userRepoModel.getName())
            repoDesc.text = userRepoModel.getDescription()
            repoLang.text = userRepoModel.getLanguage()

            Glide.with(context)
                .load(userRepoModel.getOwner().getAvatarUrl())
                .placeholder(R.drawable.ic_avatar_placeholder)
                .error(R.drawable.ic_error)
                .centerCrop()
                .into(userAvatar)
        }
    }
}