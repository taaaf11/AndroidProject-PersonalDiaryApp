package com.example.personaldiaryapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.personaldiaryapp.databinding.FragmentDetailsBinding
import com.example.personaldiaryapp.room.DiaryVM
import org.commonmark.node.Node
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer


class DetailsFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDetailsBinding? = null
    public val binding get() = _binding
    var viewModel: DiaryVM? = null
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(DiaryVM::class)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        registerClicks()
    }

    private fun initialize() {
        binding?.tvTitleValueFragmentDetails?.text = args.entryInstance.title

//        val markdown = "# My Diary Entry\nThis is **bold text**"
//        Markdown
//        val html = Markdown.toHtml(markdown) // need a parser here
//        textView.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)

        val parser: Parser = Parser.builder().build()
//        val document: Node = parser.parse("This is *Markdown*")
        val document: Node = parser.parse(args.entryInstance.content)
        val renderer = HtmlRenderer.builder().build()
        binding?.tvContentValueFragmentDetails?.text  = HtmlCompat.fromHtml(renderer.render(document), HtmlCompat.FROM_HTML_MODE_LEGACY)
//        binding?.tvContentValueFragmentDetails?.text = args.entryInstance.content
    }

    private fun registerClicks() {
        binding?.ivBackButtonFragmentDetails?.setOnClickListener(this)
        binding?.ivEditButtonFragmentDetails?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivBackButtonFragmentDetails -> {
                findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToEntrysFragment())
            }
            R.id.ivEditButton_fragment_details -> {
                findNavController().navigate(
                    DetailsFragmentDirections.actionDetailsFragmentToEditFragment(
                        args.entryInstance, 1
                    )
                )
            }
        }
    }
}