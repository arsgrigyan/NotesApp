package com.southernsunrise.notesapp.ui.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.southernsunrise.notesapp.R
import com.southernsunrise.notesapp.databinding.ActivityNotesMainBinding
import com.southernsunrise.notesapp.ui.fragments.draw.draw.DrawFragment
import com.southernsunrise.notesapp.ui.fragments.draw.DrawTabFragment
import com.southernsunrise.notesapp.ui.fragments.draw.createDrawing.CreateDrawingFragment
import com.southernsunrise.notesapp.ui.fragments.notes.NotesTabFragment
import com.southernsunrise.notesapp.ui.fragments.notes.createNote.CreateNoteFragment
import com.southernsunrise.notesapp.ui.fragments.notes.notes.NotesFragment
import com.southernsunrise.notesapp.ui.fragments.settings.SettingsFragment
import com.southernsunrise.notesapp.ui.fragments.settings.SettingsTabFragment
import com.southernsunrise.notesapp.ui.fragments.starreds.StarredFragment
import com.southernsunrise.notesapp.ui.fragments.starreds.StarredTabFragment
import com.southernsunrise.notesapp.utils.openFragment
import com.southernsunrise.notesapp.utils.adapters.TabsViewPagerAdapter

class NotesMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesMainBinding
    private lateinit var mainViewPagerAdapter: TabsViewPagerAdapter
    private lateinit var tabFragmentsList: List<Fragment>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestNotificationPermission()

        binding = ActivityNotesMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tabFragmentsList = listOf(
            NotesTabFragment(),
            DrawTabFragment(),
            StarredTabFragment(),
            SettingsTabFragment()
        )

        mainViewPagerAdapter = TabsViewPagerAdapter(this)
        mainViewPagerAdapter.submitFragmentList(tabFragmentsList)

        setupViews()
        setupBottomNavView()
        setOnBackPressedDispatcher()

    }

    private fun setupViews() {
        setupAddButton()
        setupViewPager()
    }

    private fun setupViewPager() {
        binding.notesMainActivityTabsViewPager.apply {
            offscreenPageLimit = 4
            isUserInputEnabled = false
            adapter = mainViewPagerAdapter

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
//                    setOnBackPressedDispatcher()
                }
            })
        }
    }

    fun setOnBackPressedDispatcher() {
        onBackPressedDispatcher.addCallback {
            // when the user presses back button
            // if the currently active viewpager item is NotesTabFragment and the fragment inside NotesTabFragment is NotesFragment,
            // then the app is minimized
            // otherwise NotesTabFragment is selected in both viewpager and bottomNavView
            val currentFragmentPosition =
                binding.notesMainActivityTabsViewPager.currentItem
            val currentlyVisibleTab = tabFragmentsList[currentFragmentPosition]
            val currentlyVisibleTabChildFragment: Fragment? =
                currentlyVisibleTab.childFragmentManager.findFragmentById(R.id.nav_host_fragment)?.childFragmentManager?.fragments?.last()

            when (currentlyVisibleTab) {
                is NotesTabFragment -> { // NotesTab
                    if (currentlyVisibleTabChildFragment is NotesFragment) moveTaskToBack(true)
                    else currentlyVisibleTab.childFragmentManager.popBackStack()
                }

                is DrawTabFragment -> { // DrawTab
                    if (currentlyVisibleTabChildFragment is DrawFragment) goToNotesFragment()
                    else currentlyVisibleTab.childFragmentManager.popBackStack()
                }

                is StarredTabFragment -> { // StarredTab
                    if (currentlyVisibleTabChildFragment is StarredFragment) goToNotesFragment()
                    else currentlyVisibleTab.childFragmentManager.popBackStack()

                }

                is SettingsTabFragment -> { // SettingsTab
                    if (currentlyVisibleTabChildFragment is SettingsFragment) goToNotesFragment()
                    else currentlyVisibleTab.childFragmentManager.popBackStack()
                }
            }
        }

    }

    private fun goToNotesFragment() {
        binding.notesMainActivityTabsViewPager.currentItem = 0
        binding.bottomNavigationView.selectedItemId = R.id.navigation_notes
    }

    private fun setupAddButton() = with(binding) {
        fabAdd.setOnClickListener {
            when (binding.bottomNavigationView.selectedItemId) {
                R.id.navigation_notes -> {
                    openFragment(R.id.notes_main_activity_root, CreateNoteFragment())
                }

                R.id.navigation_draw -> {
                    openFragment(R.id.notes_main_activity_root, CreateDrawingFragment())
                }

                R.id.navigation_starred -> {}
                R.id.navigation_settings -> {}
            }

        }
    }

    private fun setupBottomNavView() = with(binding) {
        bottomNavigationView.setOnItemSelectedListener {
            notesMainActivityTabsViewPager.apply {
                when (it.itemId) {
                    R.id.navigation_notes -> {
                        setCurrentItem(0, false)
                    }

                    R.id.navigation_draw -> {
                        setCurrentItem(1, false)
                    }

                    R.id.navigation_starred -> {
                        setCurrentItem(2, false)
                    }

                    R.id.navigation_settings -> {
                        setCurrentItem(3, false)
                    }
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
            } else {
                // repeat the permission request or open app details
            }
        }
    }


}