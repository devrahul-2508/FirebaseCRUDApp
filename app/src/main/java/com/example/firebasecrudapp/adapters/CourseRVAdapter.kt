package com.example.firebasecrudapp.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firebasecrudapp.MainActivity
import com.example.firebasecrudapp.databinding.CourseRvItemBinding
import com.example.firebasecrudapp.modals.Course

class CourseRVAdapter(private val activity:Activity,private val courseList:List<Course>):RecyclerView.Adapter<CourseRVAdapter.ViewHolder>() {

    class ViewHolder(itemView: CourseRvItemBinding):RecyclerView.ViewHolder(itemView.root){
        val courseIV=itemView.idIVCourse
        val courseTV=itemView.idTVCourseName
        val coursePriceTV=itemView.idTVCoursePrice
    }
    var lastPos=-1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=CourseRvItemBinding.inflate(LayoutInflater.from(activity),parent,false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=courseList[position]
        holder.courseTV.text=item.courseName
        holder.coursePriceTV.text = "$"+item.coursePrice
        Glide.with(activity).load(item.imageLink).into(holder.courseIV)
        setAnimation(holder.itemView,position)
        holder.itemView.setOnClickListener {
            if (activity is MainActivity){
                activity.displayBottomSheetDialog(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return courseList.size
    }
    private fun setAnimation(itemView: View, position: Int) {
        if (position> lastPos){
            val animation:Animation=AnimationUtils.loadAnimation(activity,android.R.anim.slide_in_left)
            itemView.animation=animation
            lastPos=position
        }
    }
}