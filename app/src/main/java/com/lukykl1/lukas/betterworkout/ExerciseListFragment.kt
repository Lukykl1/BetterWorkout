package com.lukykl1.lukas.betterworkout

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukykl1.lukas.betterworkout.database.models.Exercise
import com.lukykl1.lukas.betterworkout.viewmodel.ExerciseListViewModel
import com.lukykl1.lukas.betterworkout.viewmodel.SetListViewModel
import kotlinx.android.synthetic.main.exercise_list_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import java.io.IOException


class ExerciseListFragment : Fragment() {

    companion object {
        fun newInstance() = ExerciseListFragment()
    }

    private val exerciseListViewModel: ExerciseListViewModel by viewModel()
    private val setListViewModel: SetListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.exercise_list_fragment, container, false)
    }


    var mCurrentPhotoPath: String? = null

    @Throws(IOException::class)
    private fun createImageFile(uid: Int): File {
        // Create an image file name
        val storageDir: File = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        storageDir.listFiles().filter { it.nameWithoutExtension.startsWith("WORKOUT_${uid}_") }.forEach { it.delete() }
        return File.createTempFile(
            "WORKOUT_${uid}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = absolutePath
        }
    }

    val REQUEST_TAKE_PHOTO = 1

    private fun dispatchTakePictureIntent(uid: Int) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(activity?.packageManager!!)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile(uid)
                } catch (ex: IOException) {
                    Toast.makeText(context, "Error with cam!", Toast.LENGTH_LONG).show()
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        context!!,
                        "com.lukykl1.lukas.betterworkout.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    var workoutId: Long = 0
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        workoutId = ExerciseListFragmentArgs.fromBundle(arguments).workoutId
        exerciseListViewModel.setWorkoutId(workoutId, WorkoutNameView)


        val recyclerView = recyclerview
        val adapter = ExerciseListAdapter(context!!, setListViewModel, exerciseListViewModel, this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context!!)
        exerciseListViewModel.getExercisesForWorkout(workoutId).observe(this, Observer { exercises ->
            // Update the cached copy of the words in the adapter.
            exercises?.let { adapter.setExercise(it) }
        })
        deleteWorkout.setOnClickListener {
            GlobalScope.launch { exerciseListViewModel.deleteById(workoutId) }
            val action = ExerciseListFragmentDirections.actionExerciseListFragmentToHomeDest()
            findNavController().navigate(action)
        }

        fab.setOnClickListener {
            val exercise = Exercise(
                0,
                "New exercise",
                workoutId.toInt(),
                null
            )
            exerciseListViewModel.insert(exercise)
        }
        galleryButton.setOnClickListener {
            val file = getPhotoFile()
            if (file != null) {
                val photoURI: Uri = FileProvider.getUriForFile(
                    context!!,
                    "com.lukykl1.lukas.betterworkout.fileprovider",
                    file
                )
                val intent = Intent(Intent.ACTION_VIEW, photoURI)
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                startActivity(intent)
            } else {
                Toast.makeText(context, "You have not made a photo", Toast.LENGTH_SHORT).show()
            }
        }

        val file = getPhotoFile()
        galleryButton.isEnabled = file != null
        galleryButton.visibility = if (file != null) VISIBLE else View.INVISIBLE

        takePhotoButton.setOnClickListener {
            dispatchTakePictureIntent(workoutId.toInt())
            galleryButton.isEnabled = true
            galleryButton.visibility = VISIBLE
        }

        WorkoutNameView.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                exerciseListViewModel.workout?.name = WorkoutNameView.text.toString()
                exerciseListViewModel.updateWorkout()
            }
        }

    }

    private fun getPhotoFile(): File? {
        val storageDir: File = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        val file = storageDir.listFiles().singleOrNull { it.nameWithoutExtension.startsWith("WORKOUT_${workoutId}_") }
        return file
    }

}
