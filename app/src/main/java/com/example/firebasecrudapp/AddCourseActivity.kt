package com.example.firebasecrudapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasecrudapp.databinding.ActivityAddCourseBinding
import com.example.firebasecrudapp.modals.Course
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class AddCourseActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddCourseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var courseID:String

        val firebaseDatabase= FirebaseDatabase.getInstance()
        val databaseRef=firebaseDatabase.getReference("Courses")

        binding.idBtnAddCourse.setOnClickListener {

            val courseName=binding.idEdtCourseName.text.toString()
            val coursePrice=binding.idEdtCoursePrice.text.toString()
            val courseSuitedFor=binding.idEdtSuitedFor.text.toString()
            val courseImageLink=binding.idEdtCourseImageLink.text.toString()
            val courseLink=binding.idEdtCourseLink.text.toString()
            val courseDescription=binding.idEdtCourseDescription.text.toString()
            courseID=databaseRef.push().key.toString()

            when{
                TextUtils.isEmpty(courseName)->{
                    Toast.makeText(this,"Enter Course Name",Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(coursePrice)->{
                    Toast.makeText(this,"Enter Course Price",Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(courseSuitedFor)->{
                    Toast.makeText(this,"Enter Course Suited For",Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(courseImageLink)->{
                    Toast.makeText(this,"Enter Course Image Link",Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(courseLink)->{
                    Toast.makeText(this,"Enter Course Link",Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(courseDescription)->{
                    Toast.makeText(this,"Enter Course Description",Toast.LENGTH_SHORT).show()
                }
                else->{
                    val course=Course(
                        courseName,
                        coursePrice,
                        courseSuitedFor,
                        courseImageLink,
                        courseLink,
                        courseDescription,
                        courseID
                        )
                    binding.idPBLoading.visibility= View.VISIBLE

                    databaseRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            binding.idPBLoading.visibility= View.GONE
                            databaseRef.child(courseID).setValue(course)
                            Toast.makeText(this@AddCourseActivity, "Course Added..", Toast.LENGTH_SHORT).show()

                            finish()
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(this@AddCourseActivity, "Fail to add Course..", Toast.LENGTH_SHORT).show();
                        }

                    })
                }
            }
        }
    }
}