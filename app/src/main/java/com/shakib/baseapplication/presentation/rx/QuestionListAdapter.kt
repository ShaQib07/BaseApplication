package com.shakib.baseapplication.presentation.rx

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shakib.baseapplication.data.model.Question
import com.shakib.baseapplication.databinding.ItemQuestionBinding

class QuestionListAdapter(
    private val questionList: List<Question>,
    private val listener: (Question?) -> Unit
) : RecyclerView.Adapter<QuestionListAdapter.QuestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = QuestionViewHolder(
        ItemQuestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.binding.tvQuestion.text = questionList[position].title
        holder.itemView.setOnClickListener { listener.invoke(questionList[position]) }
    }

    override fun getItemCount() = questionList.size

    class QuestionViewHolder(val binding: ItemQuestionBinding) :
        RecyclerView.ViewHolder(binding.root)
}