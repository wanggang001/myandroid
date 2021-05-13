package com.abc.myandroid.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abc.myandroid.R
import com.abc.myandroid.mvp.model.bean.Article
import java.util.ArrayList

class HomeAdapter(private val articleList: MutableList<Article>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private val mContext: Context? = null
    private val mArticleList: MutableList<Article> = ArrayList()
    private var mHeaderView: View? = null
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mArticleAuthor: TextView = view.findViewById(R.id.item_home_author)
        val mArticleContent: TextView = view.findViewById(R.id.item_home_content)
        val mArticleType: TextView = view.findViewById(R.id.item_article_type)
        val mArticleDate: TextView = view.findViewById(R.id.item_home_date)
        val mCollectView: ImageView = view.findViewById(R.id.item_list_collect)
        val mNewView: TextView = view.findViewById(R.id.item_home_new)
        val mQuestionView: TextView = view.findViewById(R.id.item_home_question)
        val mTopView: TextView = view.findViewById(R.id.item_home_top_article)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_list, parent, false)
        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return
        }
        val realPosition = getRealPosition(holder)
        val articleBean = articleList[realPosition]

            holder.mArticleContent.text = articleBean.title

        if (articleBean.author != "") {
            holder.mArticleAuthor.text = String.format(
                mContext!!.resources.getString(R.string.article_author),
                articleBean.author
            )
        } else {
            holder.mArticleAuthor.text = String.format(
                mContext!!.resources.getString(R.string.article_author),
                articleBean.shareUser
            )
        }
        holder.mArticleDate.text = articleBean.niceDate
        val category = String.format(
            mContext.resources.getString(R.string.article_category),
            articleBean.superChapterName, articleBean.chapterName
        )
        holder.mArticleType.text = String.format(category, Html.FROM_HTML_MODE_COMPACT)
        if (articleBean.top == "1") {
            holder.mTopView.visibility = View.VISIBLE
        }
        if (articleBean.fresh) {
            holder.mNewView.visibility = View.VISIBLE
        }
        holder.mQuestionView.visibility = View.VISIBLE
        holder.mQuestionView.text = articleBean.superChapterName

        holder.itemView.setOnClickListener { view: View? ->

        }
        holder.mCollectView.setOnClickListener { view: View? ->

        }
    }
    private fun getRealPosition(articleHolder: ViewHolder): Int {
        val position = articleHolder.layoutPosition
        return if (mHeaderView == null) position else position - 1
    }
    companion object {
        private const val TYPE_HEADER = 0
    }
    override fun getItemCount() = articleList.size
}