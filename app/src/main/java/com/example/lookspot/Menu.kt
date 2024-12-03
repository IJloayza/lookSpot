package com.example.lookspot

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment

/**
 * A simple [Fragment] subclass.
 * Use the [Menu.newInstance] factory method to
 * create an instance of this fragment.
 */
class Menu : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        val helpNav = view.findViewById<LinearLayout>(R.id.helpNav)

        helpNav.setOnClickListener { v: View? ->
            startActivity(
                Intent(
                    context,
                    MainActivity::class.java
                )
            )
        }


        return view
    }

    fun setPageElementActive(page: Page?) {
    }


    enum class Page(val viewId: Int) {
        HELP(0),
        SETTINGS(0),
        HOME(0),
        FAVORITES(0)
    }

    companion object {
        fun newInstance(): Menu {
            return Menu()
        }
    }
}