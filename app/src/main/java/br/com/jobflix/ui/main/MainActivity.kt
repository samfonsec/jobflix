package br.com.jobflix.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.jobflix.R
import br.com.jobflix.databinding.ActHomeBinding
import br.com.jobflix.util.extensions.viewBinding
import br.com.jobflix.ui.main.favorite.FavoritesFragment
import br.com.jobflix.ui.main.home.HomeFragment
import br.com.jobflix.ui.main.peopleSearch.PeopleSearchFragment

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActHomeBinding::inflate)

    private lateinit var currentFragment: Fragment

    private val fragmentsList = arrayListOf(
        HomeFragment(),
        FavoritesFragment(),
        PeopleSearchFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupFragments()
        buildUi()
    }

    override fun onBackPressed() {
        if (currentFragment is HomeFragment)
            super.onBackPressed()
        else
            binding.bottomNavigation.selectedItemId = R.id.menu_home
    }

    private fun buildUi() {
        changeFragment(HOME_POSITION)
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> HOME_POSITION
                R.id.menu_favorite -> FAVORITES_POSITION
                R.id.menu_people_search -> PEOPLE_SEARCH_POSITION
                else -> null
            }?.let { position ->
                changeFragment(position)
            }
            true
        }
    }

    private fun setupFragments() {
        currentFragment = fragmentsList.last()
        fragmentsList.forEach {
            supportFragmentManager.beginTransaction().add(R.id.flContainer, it).hide(it).commit()
        }
    }

    private fun changeFragment(position: Int) {
        fragmentsList[position].takeIf { it != currentFragment }?.let {
            supportFragmentManager.beginTransaction().hide(currentFragment).show(it).commit()
            currentFragment = it
            onFragmentChanged(it)
        }

    }

    private fun onFragmentChanged(fragment: Fragment) {
        when (fragment) {
            is FavoritesFragment -> fragment.loadFavorites()
            is PeopleSearchFragment -> fragment.showKeyboard()
        }
    }

    companion object {
        private const val HOME_POSITION = 0
        private const val FAVORITES_POSITION = 1
        private const val PEOPLE_SEARCH_POSITION = 2
    }
}
