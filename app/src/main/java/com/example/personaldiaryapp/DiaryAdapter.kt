package com.example.personaldiaryapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
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
    var deleteEntryLambda: ((DiaryEntry) -> Unit)? = null
    var editEntryLambda: ((DiaryEntry) -> Unit)? = null

    private var searcherEditText: EditText? = null
    private var viewModel: DiaryVM? = null

//    init {
//        searcherEditText?.addTextChangedListener { editable ->
////            Log.i("text changed", "TEXT CHANGE")
//
//            var allDiaryEntrys = viewModel?.readAllDiaryEntrys?.value
//            if (allDiaryEntrys == null) {
//                Log.i("Null check", "ViewModel passed to DiarySearchAdapter is yielding null")
//            }
//
//            allDiaryEntrys = allDiaryEntrys!!
//
//            val searchQuery = editable.toString()
//            val filteredDiaryEntrys = mutableListOf<DiaryEntry>()
//
//            allDiaryEntrys.forEach { diaryEntry ->
//                if (searchQuery in diaryEntry.content) {
//                    filteredDiaryEntrys.add(diaryEntry)
//                }
//            }
//
//            this.listDiaryEntrys = filteredDiaryEntrys
//            notifyDataSetChanged()
//        }
//    }

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

    fun setViewModel(vm: DiaryVM?) {
        this.viewModel = vm
    }

    fun setSearcherEditText(et: EditText?) {
        this.searcherEditText = et
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