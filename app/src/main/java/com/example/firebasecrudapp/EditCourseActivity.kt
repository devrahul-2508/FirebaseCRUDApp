package com.example.firebasecrudapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasecrudapp.databinding.ActivityEditCourseBinding
import com.example.firebasecrudapp.modals.Course
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class EditCourseActivity : AppCompatActivity() {
    lateinit var binding:ActivityEditCourseBinding
    private var courseDetails:Course?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEditCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val firebaseDatabase= FirebaseDatabase.getInstance()

        courseDetails=intent.getParcelableExtra("course")
        courseDetails?.let {
            binding.idEdtCourseName.setText(it.courseName)
            binding.idEdtCoursePrice.setText(it.coursePrice)
            binding.idEdtSuitedFor.setText(it.bestSuitedFor)
            binding.idEdtCourseImageLink.setText(it.imageLink)
            binding.idEdtCourseLink.setText(it.courseLink)
            binding.idEdtCourseDescription.setText(it.courseDescription)
        }

        val databaseRef= courseDetails!!.courseID?.let {
            firebaseDatabase.getReference("Courses").child(
                it
            )
        }

        binding.idBtnUpdateCourse.setOnClickListener {
            binding.idPBLoading.visibility= View.VISIBLE
            val courseName=binding.idEdtCourseName.text.toString()
            val coursePrice=binding.idEdtCoursePrice.text.toString()
            val courseSuitedFor=binding.idEdtSuitedFor.text.toString()
            val courseImageLink=binding.idEdtCourseImageLink.text.toString()
            val courseLink=binding.idEdtCourseLink.text.toString()
            val courseDescription=binding.idEdtCourseDescription.text.toString()
            val courseID=courseDetails!!.courseID.toString()

            val map: MutableMap<String, Any> = HashMap()
            map["courseName"] = courseName
            map["courseDescription"] = courseDescription
            map["coursePrice"] = coursePrice
            map["bestSuitedFor"] = courseSuitedFor
            map["imageLink"] = courseImageLink
            map["courseLink"] = courseLink
            map["courseID"] = courseID

            databaseRef?.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    binding.idPBLoading.visibility=View.GONE
                    databaseRef.updateChildren(map)
                    Toast.makeText(this@EditCourseActivity, "Course Updated..", Toast.LENGTH_SHORT)
                        .show()

                    val intent=Intent(this@EditCourseActivity,MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)

                }

                override fun onCancelled(error: DatabaseError) {
                    binding.idPBLoading.visibility=View.GONE
                    Toast.makeText(this@EditCourseActivity, "Error Updating Course..", Toast.LENGTH_SHORT).show()


                }

            })

        }

        binding.idBtnDeleteCourse.setOnClickListener {
            databaseRef?.removeValue()
            Toast.makeText(this@EditCourseActivity, "Course Deleted..", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@EditCourseActivity, MainActivity::class.java))


        }
    }
}