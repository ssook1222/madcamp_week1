package com.example.madcamp_week1

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONTokener

class DiaryFragment : Fragment() {
    lateinit var recyclerView : RecyclerView
    var diaryList = arrayListOf<Diary>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_diary, container, false)
        val diaryAdapter = DiaryAdapter(diaryList)

        if (diaryList.isEmpty()) {
            val diaryJsonString: String = requireActivity().assets.open("diaries.json").bufferedReader().use {
                it.readText()
            }
            val diaryJsonArray = JSONTokener(diaryJsonString).nextValue() as JSONArray

            for (i in 0 until diaryJsonArray.length()) {
                val date = diaryJsonArray.getJSONObject(i).getString("date")
                val name = diaryJsonArray.getJSONObject(i).getString("name")
                // val resId = diaryJsonArray.getJSONObject(i).getInt("resId")
                val text = diaryJsonArray.getJSONObject(i).getString("text")

                diaryList.add(Diary(date, name, R.drawable.sonagi_logo, text))
            }
        }

        recyclerView = rootView.findViewById(R.id.diaryRecyclerView!!)as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = diaryAdapter

        diaryAdapter.notifyDataSetChanged()

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}