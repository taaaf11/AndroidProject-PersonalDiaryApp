package com.example.personaldiaryapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.personaldiaryapp.room.DiaryEntry
import com.example.personaldiaryapp.room.DiaryVM
import java.text.SimpleDateFormat
import java.util.Locale

class DiaryAdapter(
    val context:Context,
): RecyclerView.Adapter<DiaryAdapter.ViewHolder>() {

    private var listDiaryEntrys = emptyList<DiaryEntry>()
    var viewDetailLambda: ((DiaryEntry) -> Unit)? = null
    var popupMenuShowerLambda: ((View) -> Boolean)? = null

    var viewModel: DiaryVM? = null

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvTitle = view.findViewById<TextView>(R.id.tvDiaryEntryTitle_layout_card_view)
        val tvContent = view.findViewById<TextView>(R.id.tvDiaryEntryContent_layout_card_view)
        val tvDateValue = view.findViewById<TextView>(R.id.tvDateValue_layout_card_view)
        val tvOpenPopupMenu = view.findViewById<TextView>(R.id.tvOpenPopupMenu_layout_card_view)
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

        // setting the title textview
        holder.tvTitle.text = entry.title

        // setting the content textview
        var content = entry.content
        if (content.length >= 10) {
            content = content.slice(0..10) + "..."
        }
        holder.tvContent.text = content

        // setting the date value to a pretty format
        val entryCreationTimeFormatter = SimpleDateFormat(
            "MMM d, yyyy · h:mm a",
            Locale.getDefault()
        )
        holder.tvDateValue.text = entryCreationTimeFormatter.format(entry.dateCreated)

        // setTag is used to embed extra information into
        // the view i.e. the popup menu button
        // this id (R.id.theEntry) is given in ids.xml

        holder.tvOpenPopupMenu.setTag(R.id.theEntry, entry)

        holder.itemView.setOnClickListener {
            viewDetailLambda?.invoke(entry)
        }

        holder.tvOpenPopupMenu.setOnClickListener {
            // entry ki information add karnay ke lie setTag
            // wala function vertical-dots-textView walay
            // component par call kia gia tha

            // ab popMenuShowerLambda wala function aik View argument
            // leta hai, jis par wo getTag call karay ga, entry ki
            // information hasil karnay ke lie
            // is lie ham usay ye vertical-dots-textView ka component
            // pass kar rahay hain
            popupMenuShowerLambda?.invoke(holder.tvOpenPopupMenu)
        }
    }

    @SuppressWarnings("NotifyDataSetChanged")
    fun setData(data: List<DiaryEntry>) {
        this.listDiaryEntrys = data
        notifyDataSetChanged()
    }

    @SuppressWarnings("NotifyDataSetChanged")
    fun setSearchQueries(titleQuery: String, contentQuery: String) {
        val allDiaryEntrys = viewModel?.readAllDiaryEntrys?.value
        require(allDiaryEntrys != null) { "The viewModel is still null" }

        val filteredDiaryEntrys = mutableListOf<DiaryEntry>()

        allDiaryEntrys.forEach { diaryEntry ->
            if (titleQuery in diaryEntry.title &&
                contentQuery in diaryEntry.content
                ) {
                filteredDiaryEntrys.add(diaryEntry)
            }
        }

        this.listDiaryEntrys = filteredDiaryEntrys
        notifyDataSetChanged()
    }
}
