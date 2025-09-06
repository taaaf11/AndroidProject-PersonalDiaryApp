package com.example.personaldiaryapp

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
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

    private var searcherEditText: EditText? = null
    private var viewModel: DiaryVM? = null

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvTitle = view.findViewById<TextView>(R.id.tvDiaryEntryTitle_layout_card_view)
        val tvContent = view.findViewById<TextView>(R.id.tvDiaryEntryContent_layout_card_view)
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

        // setting the title textview
        holder.tvTitle.text = entry.title

        // setting the content textview
        var content = entry.content
        if (content.length >= 10) {
            content = content.slice(0..10) + "..."
        }
        holder.tvContent.text = content

        // setting the date value to a pretty format
        val entryCreationTimeFormatter = SimpleDateFormat("MMM d, yyyy · h:mm a", Locale.getDefault())
        holder.tvDateValue.text = entryCreationTimeFormatter.format(entry.dateCreated)

        // setTag is used to embed extra information into
        // the view i.e. the cardview
        // this id (R.id.theEntry) is given in ids.xml
        holder.itemView.setTag(R.id.theEntry, entry)

        // got from chatgpt
        // this code registers the cardview to show the context menu
        // holder.itemView is the card
        // holder.itemView.context is the activity
        (holder.itemView.context as? Activity)?.registerForContextMenu(holder.itemView)


        holder.itemView.setOnClickListener {
            viewDetailLambda?.invoke(entry)
        }
        holder.itemView.setOnLongClickListener {
            popupMenuShowerLambda?.invoke(it)
            true
        }
    }

    @SuppressWarnings("NotifyDataSetChanged")
    fun setData(data: List<DiaryEntry>) {
        this.listDiaryEntrys = data
        notifyDataSetChanged()
    }

    fun setSearchingRequirements(vm: DiaryVM?, et: EditText?) {
        this.viewModel = vm
        this.searcherEditText = et

        searcherEditText?.addTextChangedListener { editable ->
//            Log.i("text changed", "TEXT CHANGE")

            var allDiaryEntrys = viewModel?.readAllDiaryEntrys?.value
            if (allDiaryEntrys == null) {
                Log.i("Null check", "ViewModel passed to DiaryAdapter is yielding null")
            }

            allDiaryEntrys = allDiaryEntrys!!

            val searchQuery = editable.toString()
            val filteredDiaryEntrys = mutableListOf<DiaryEntry>()

            allDiaryEntrys.forEach { diaryEntry ->
                if (searchQuery in diaryEntry.content) {
                    filteredDiaryEntrys.add(diaryEntry)
                }
            }

            this.listDiaryEntrys = filteredDiaryEntrys
            notifyDataSetChanged()
        }
    }
}


class MenuItems(val entry: DiaryEntry, val value: Int) {

    companion object {
        val EDIT = 1
        val DELETE = 2
        val VIEW = 3
    }
//
//    fun getValue(diaryEntry: DiaryEntry, valueName: String): Int {
//        this.diaryEntry = diaryEntry
//        return when (valueName) {
//            "EDIT" -> 1
//            "DELETE" -> 2
//            "VIEW" -> 3
//            else -> -1
//        }
//    }

    fun toInt(): Int {
        return this.value
    }
}