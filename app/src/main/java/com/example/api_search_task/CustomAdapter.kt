package com.example.api_search_task


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.api_search_task.API.UserResponseItem


class CustomAdapter(var list: ArrayList<UserResponseItem>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id: TextView = itemView.findViewById(R.id.id)
        val name: TextView = itemView.findViewById(R.id.name)
        val username: TextView = itemView.findViewById(R.id.username)
        val email: TextView = itemView.findViewById(R.id.email)
        val address: TextView = itemView.findViewById(R.id.address)
        val code: TextView = itemView.findViewById(R.id.code)
        val phone: TextView = itemView.findViewById(R.id.phone)
        val website: TextView = itemView.findViewById(R.id.website)
        val company: TextView = itemView.findViewById(R.id.company)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.shape, parent, false)
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        holder.id.text = "ID : " + list[position].id.toString()
        holder.name.text = "Name : " + list[position].name
        holder.username.text = "User Name : " + list[position].username
        holder.email.text = "Email : " + list[position].email
        holder.address.text = "Address : " + list[position].address.street +" , "+list[position].address.suite+" , "+list[position].address.city
        holder.code.text = "ZipCode : " + list[position].address.zipcode
        holder.phone.text = "Phone : " + list[position].phone
        holder.website.text = "Website : " + list[position].website
        holder.company.text = "Company : " + list[position].company.name

    }

    override fun getItemCount(): Int {
        return list.size
    }
}
