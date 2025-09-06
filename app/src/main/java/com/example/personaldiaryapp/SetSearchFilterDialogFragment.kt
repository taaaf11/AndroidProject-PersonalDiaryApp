package com.example.personaldiaryapp

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class SetSearchFilterDialogFragment : DialogFragment() {
    var titleQuery: String? = null
    var contentQuery: String? = null
    var valueGetterLambda: ((String?, String?) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction.
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            // from chatgpt, this line sets the dialogView variable to the
            // inflated (built) layout, so we can get the inner components
            // using this variable
            val dialogView = inflater.inflate(R.layout.search_filter_dialog,null)
            builder.setView(dialogView)
            builder.setMessage("Start game")
                .setPositiveButton("Apply") { dialog, id ->
                    val etTitleQuery = dialogView?.findViewById<EditText>(R.id.etTitleValue_layout_search_filter_dialog)
                    val etContentQuery = dialogView?.findViewById<EditText>(R.id.etContentValue_layout_search_filter_dialog)
                    // START THE GAME!
                    valueGetterLambda?.invoke(
                        etTitleQuery?.text.toString(),
                        etContentQuery?.text.toString()
                    )
                }
                .setNeutralButton("Cancel") { dialog, id ->
                    // User cancelled the dialog.
                }
            // Create the AlertDialog object and return it.
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


}
//
//class OldXmlActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_old_xml)
//
//        StartGameDialogFragment().show(supportFragmentManager, "GAME_DIALOG")
//    }
//}