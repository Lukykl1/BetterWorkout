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
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.lukykl1.lukas.betterworkout.viewmodel.WorkoutJsonViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    companion object {
        const val newExerciseActivityRequestCode = 1
    }

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
    override fun onSupportNavigateUp(): Boolean {
        // Allows NavigationUI to support proper up navigation or the drawer layout
        // drawer menu, depending on the situation
        return findNavController(R.id.my_nav_host_fragment).navigateUp()
    }
}

