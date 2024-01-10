package com.example.httpjson

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.widget.AppCompatTextView

class ListAdapte (val context: Context, val list: ArrayList<Function>) : BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, countertView: View?, parent: ViewGroup?): View {

        val view: View = LayoutInflater.from(context).inflate(R.layout.row_layout,parent,false)
        val funcId = view.findViewById(R.id.func_id) as AppCompatTextView
        val funcName = view.findViewById(R.id.func) as AppCompatTextView
        val funcDesc = view.findViewById(R.id.desc) as AppCompatTextView

        funcId.text = list[position].id.toString()
        funcName.text = list[position].func
        funcDesc.text = list[position].desc
        return view
    }

}