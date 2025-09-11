package com.example.personaldiaryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.example.personaldiaryapp.room.DiaryEntry
import org.commonmark.node.Node
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        handleIntent(intent)
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // Update the activity's intent (important!)
//        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        Log.i("handleIntent", "called")
//        Log.i("handleIntent", "Value: ")
        Log.i("handleIntent", "Value2: ${intent?.extras}\nValue: ${intent?.getParcelableExtra("entry") as DiaryEntry?}")

        val entry = intent?.getParcelableExtra("entry") as DiaryEntry?
        if (entry != null) {
            findViewById<TextView>(R.id.tvTitleValueActivity2).text = entry.title

            val parser: Parser = Parser.builder().build()
            val document: Node = parser.parse(entry.content)
            val renderer = HtmlRenderer.builder().build()
            findViewById<TextView>(R.id.tvContentValueActivity2).text = HtmlCompat.fromHtml(renderer.render(document), HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }
}