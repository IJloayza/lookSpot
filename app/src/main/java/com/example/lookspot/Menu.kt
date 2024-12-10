package com.example.lookspot

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
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

        val helpNav: LinearLayout = view.findViewById(R.id.helpNav)
        val settingsNav: LinearLayout = view.findViewById(R.id.settingsNav)
        val homeNav: LinearLayout = view.findViewById(R.id.homeNav)
        val favNav: LinearLayout = view.findViewById(R.id.favoriteNav)

        helpNav.setOnClickListener { v: View? ->
            startActivity(
                Intent(
                    context,
                    Welcome::class.java
                )
            )
        }

        settingsNav.setOnClickListener { v: View? ->
            startActivity(
                Intent(
                    context,
                    Settings::class.java
                )
            )
        }

        homeNav.setOnClickListener{ v: View? ->
            startActivity(
                Intent(
                    context,
                    PromptActivity::class.java
                )
            )
        }

        favNav.setOnClickListener{ v: View? ->
            startActivity(
                Intent(
                    context,
                    FavMusicActivity::class.java
                )
            )
        }


        return view
    }

    fun setPageElementAsActive(page: Page) {

        val btn: ImageButton = requireView().findViewById(page.imgId)

        btn.setBackgroundResource(R.drawable.border_radius)


    }


    enum class Page(val imgId: Int) {
        HELP(R.id.helpIcon),
        SETTINGS(R.id.settingsIcon),
        HOME(R.id.homeIcon),
        FAVORITES(R.id.favIcon)
    }

    companion object {
        fun newInstance(): Menu {
            return Menu()
        }
    }
}