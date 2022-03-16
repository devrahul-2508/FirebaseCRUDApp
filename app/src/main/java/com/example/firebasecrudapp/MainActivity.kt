package com.example.firebasecrudapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.firebasecrudapp.adapters.CourseRVAdapter
import com.example.firebasecrudapp.databinding.ActivityMainBinding
import com.example.firebasecrudapp.databinding.BottomSheetLayoutBinding
import com.example.firebasecrudapp.modals.Course
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    lateinit var courseList:ArrayList<Course>
    lateinit var firebaseDatabase:FirebaseDatabase
    lateinit var dataBaseRef:DatabaseReference
    lateinit var courseRVAdapter: CourseRVAdapter
    private lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idFABAddCourse.setOnClickListener {
            startActivity(Intent(this,AddCourseActivity::class.java))
        }
        courseList= ArrayList()
        firebaseDatabase= FirebaseDatabase.getInstance()
        dataBaseRef=firebaseDatabase.getReference("Courses")

        getCourses()

        courseRVAdapter=CourseRVAdapter(this@MainActivity,courseList)
        binding.idRVCourses.layoutManager=LinearLayoutManager(this@MainActivity)
        binding.idRVCourses.adapter=courseRVAdapter




    }

    private fun getCourses() {
        courseList.clear()
        dataBaseRef.addChildEventListener(object: ChildEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                binding.idPBLoading.visibility= View.GONE

                snapshot.getValue(Course::class.java)?.let {
                    courseList.add(it)

                }

                courseRVAdapter.notifyDataSetChanged()



            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                binding.idPBLoading.visibility= View.GONE
                courseRVAdapter.notifyDataSetChanged()

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onChildRemoved(snapshot: DataSnapshot) {
                binding.idPBLoading.visibility= View.GONE
                courseRVAdapter.notifyDataSetChanged()
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                binding.idPBLoading.visibility= View.GONE
                courseRVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                binding.idPBLoading.visibility=View.GONE
            }

        })
    }
    @SuppressLint("SetTextI18n")
    fun displayBottomSheetDialog(course: Course) {
        val bottomSheetDialog=BottomSheetDialog(this)
        val binding=BottomSheetLayoutBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(binding.root)
        binding.idTVCourseName.text=course.courseName
        binding.idTVCourseDesc.text=course.courseDescription
        binding.idTVSuitedFor.text=course.bestSuitedFor
        binding.idTVCoursePrice.text="$"+course.coursePrice
        Glide.with(this).load(course.imageLink).into(binding.idIVCourse)

       binding.idBtnEditCourse.setOnClickListener {
           val intent=Intent(this,EditCourseActivity::class.java)
           intent.putExtra("course",course)
           startActivity(intent)
       }


        binding.idBtnVIewDetails.setOnClickListener {
            val uri: Uri = Uri.parse(course.courseLink) // missing 'http://' will cause crashed

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }


        bottomSheetDialog.show()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater:MenuInflater=menuInflater
        inflater.inflate(R.menu.menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.logout -> {
                logOut()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logOut() {
        mAuth= FirebaseAuth.getInstance()
        mAuth.signOut()
        startActivity(Intent(this,LoginActivity::class.java))
    }
}