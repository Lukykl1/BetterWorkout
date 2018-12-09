package com.lukykl1.lukas.betterworkout

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.lukykl1.lukas.betterworkout.viewmodel.WorkoutJsonViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val workoutJsonViewModel: WorkoutJsonViewModel by viewModel()

    private var drawerLayout: DrawerLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

        // Set up Action Bar
        val navController = host.navController
        setupActionBar(navController)
        setupNavigationMenu(navController)
        when {
            intent?.action == Intent.ACTION_SEND -> {
                if ("application/workout" == intent.type) {
                    workoutJsonViewModel.insertNewWorkout(intent.getStringExtra(Intent.EXTRA_TEXT))
                }
            }
            intent?.action == Intent.ACTION_VIEW -> {
                val uriToFile = intent.data!!
                val stream = contentResolver.openInputStream(uriToFile)
                val contents = java.util.Scanner(stream).useDelimiter("\\A").next()
                stream?.close()
                workoutJsonViewModel.insertNewWorkout(contents)

            }
        }
    }

    private fun setupNavigationMenu(navController: NavController) {
        val sideNavView = findViewById<NavigationView>(R.id.bottom_nav_view)
        sideNavView?.setupWithNavController(navController)
    }


    private fun setupActionBar(navController: NavController) {
        drawerLayout = findViewById(R.id.drawer_layout)
        setupActionBarWithNavController(navController, drawerLayout)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.quit_dest -> {
                this.finish()
                System.exit(0)
                return true
            }
        }
        return item.onNavDestinationSelected(findNavController(R.id.my_nav_host_fragment))
                || super.onOptionsItemSelected(item)
    }

    fun quitClicked(item: MenuItem) {
        this.finish()
        System.exit(0)
    }

    override fun onSupportNavigateUp(): Boolean {
        // Allows NavigationUI to support proper up navigation or the drawer layout
        // drawer menu, depending on the situation
        return findNavController(R.id.my_nav_host_fragment).navigateUp(drawerLayout)
    }
}

