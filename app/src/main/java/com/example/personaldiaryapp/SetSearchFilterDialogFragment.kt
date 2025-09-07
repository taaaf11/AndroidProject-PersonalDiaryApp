package com.example.personaldiaryapp

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Typeface
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment


// source: https://developer.android.com/develop/ui/views/components/dialogs
class SetSearchFilterDialogFragment : DialogFragment() {
    var titleQuery: String? = null
    var contentQuery: String? = null

    // this is the function that will be called when the
    // "Apply" button in this dialog will be pressed
    var valueGetterLambda: ((String?, String?) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction.

            // Source for theming: https://stackoverflow.com/a/78558947/19619895
            val builder = AlertDialog.Builder(it, R.style.ApplyFiltersDialogStyle)
            val inflater = requireActivity().layoutInflater

            // from chatgpt, this line sets the dialogView variable to the
            // inflated (built) layout, so we can get the inner components
            // using this variable
            val dialogView = inflater.inflate(R.layout.search_filter_dialog,null)
            builder.setView(dialogView)

            // mujhe styles.xml men alertdialog title ko customize karna nai a raha tha
            // to mene stackoverflow ka ye answer use kia
            // source: https://stackoverflow.com/a/51380246/19619895
            val textView = TextView(context)
            textView.text = "Apply filters"
            textView.setPadding(40, 30, 20, 10)
            textView.textSize = 16f
            textView.setTextColor(resources.getColor(R.color.primary))

            // ye line chatgpt se hai, kionke mene title ko bold bhi
            // karna tha. mene official reference parha, jis men
            // textStyle wali xml property ke sath ye function given tha
            // mene is function ko use karnay ke lie chatgpt se pucha,
            // phir us ne ye code dia
            textView.setTypeface(null, Typeface.BOLD)     // Bold

//          builder.setMessage("Apply filters")

            // jo textView abhi create kia tha, us
            builder.setCustomTitle(textView)
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

    // source: https://stackoverflow.com/a/27913325/19619895
    override fun onStart() {
        super.onStart()
        (dialog as AlertDialog?)!!.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
            resources.getColor(R.color.primary)
        )
        (dialog as AlertDialog?)!!.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(
            resources.getColor(R.color.primary)
        )
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