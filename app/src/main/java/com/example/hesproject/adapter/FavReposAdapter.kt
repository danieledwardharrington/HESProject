package com.example.hesproject.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hesproject.R
import com.example.hesproject.persistence.UserRepoEntity
import kotlinx.android.synthetic.main.card_view_repo.view.*

class FavReposAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var listener: HeartListener

    interface HeartListener {
        fun onItemClicked(repoEntity: UserRepoEntity)
    }

    fun setListener(listener: HeartListener) {
        this.listener = listener
    }

    private var favRepos: ArrayList<UserRepoEntity> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("FavReposAdapter", "onCreateViewHolder")
        return FavReposAdapter.RepoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_view_repo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        Log.d("FavReposAdapter", "onBindViewHolder")
        when (holder) {
            is FavReposAdapter.RepoViewHolder -> {
                holder.bind(favRepos[position])
                holder.heartIcon.setOnClickListener {
                    holder.heartIcon.setBackgroundResource(R.drawable.ic_heart)
                    listener.onItemClicked(favRepos[position])
                    Log.d("FavReposAdapter HeartClick", favRepos[position].getId().toString())
                    removeAt(holder.adapterPosition)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return favRepos.size
    }

    fun submitList(repoList: ArrayList<UserRepoEntity>) {
        favRepos = repoList
        notifyDataSetChanged()
    }

    class RepoViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.context
        private val repoName = itemView.repo_name_text_view
        private val repoDesc = itemView.description_text_view
        private val repoLang = itemView.language_name_text_view
        private val userAvatar = itemView.avatar_iv
        val heartIcon = itemView.favorite_heart_iv

        fun bind(userRepoEntity: UserRepoEntity) {
            Log.d("FavReposAdapter", "Bind function")
            repoName.text = userRepoEntity.getName()
            repoDesc.text = userRepoEntity.getDescription()
            repoLang.text = userRepoEntity.getLanguage()
            heartIcon.setBackgroundResource(R.drawable.ic_heart_filled)

            Glide.with(context)
                .load(userRepoEntity.getAvatarUrl())
                .placeholder(R.drawable.ic_avatar_placeholder)
                .error(R.drawable.ic_error)
                .centerCrop()
                .into(userAvatar)
        }

    }

    private fun removeAt(position: Int) {
        favRepos.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, favRepos.size)
    }

    fun add(repo: UserRepoEntity) {
        favRepos.add(repo)
        notifyDataSetChanged()
    }

}
