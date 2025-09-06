package com.example.personaldiaryapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.personaldiaryapp.room.DiaryEntry
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

class DiaryAdapter(
    val context:Context,
): RecyclerView.Adapter<DiaryAdapter.ViewHolder>() {

    private var listDiaryEntrys = emptyList<DiaryEntry>()
    var viewDetailLambda: ((DiaryEntry) -> Unit)? = null
    var deleteEntryLambda: ((DiaryEntry) -> Unit)? = null
    var editEntryLambda: ((DiaryEntry) -> Unit)? = null

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvTitle = view.findViewById<TextView>(R.id.tvDiaryEntryTitle_layout_card_view)
        val tvContent = view.findViewById<TextView>(R.id.tvDiaryEntryContent_layout_card_view)
        val ivViewEntry = view.findViewById<ImageView>(R.id.ivDiaryEntryViewDetailsLayoutCardView)
        val ivDeleteEntry = view.findViewById<ImageView>(R.id.ivDiaryEntryDeleteEntryLayoutCardView)
        val ivEditEntry = view.findViewById<ImageView>(R.id.ivDiaryEntryEditEntryLayoutCardView)
        val tvDateValue = view.findViewById<TextView>(R.id.tvDateValue_layout_card_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_view,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listDiaryEntrys.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = listDiaryEntrys[position]
        holder.tvTitle.text = entry.title
        var content = entry.content
        if (content.length >= 10) {
            content = content.slice(0..10) + "..."
        }
        holder.tvContent.text = content

        val entryCreationTimeFormatter = SimpleDateFormat("MMM d, yyyy · h:mm a", Locale.getDefault())

        holder.tvDateValue.text = entryCreationTimeFormatter.format(entry.dateCreated)

        holder.ivViewEntry.setOnClickListener{
            viewDetailLambda?.invoke(entry)
        }
        holder.ivDeleteEntry.setOnClickListener {
            deleteEntryLambda?.invoke(entry)
        }
        holder.ivEditEntry.setOnClickListener {
            editEntryLambda?.invoke(entry)
        }

//                                .split(" ")
//                                .slice(1..4)
//                                .joinToString(separator = " ")

    }

    @SuppressWarnings("NotifyDataSetChanged")
    fun setData(data: List<DiaryEntry>) {
        this.listDiaryEntrys = data
        notifyDataSetChanged()
    }
}