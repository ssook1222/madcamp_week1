package com.example.madcamp_week1.diaries

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week1.R
import org.json.JSONArray
import org.json.JSONTokener
import java.io.File

class DiaryFragment : Fragment() {
    lateinit var recyclerView : RecyclerView
    var diaryList = arrayListOf<Diary>()
    lateinit var diaryFragment: DiaryFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_diary, container, false)
        diaryFragment = this

        val addDiary = rootView?.findViewById<Button>(R.id.add_diary)
        addDiary?.setOnClickListener {
            val intent = Intent(activity, DiaryAddActivity::class.java)
            startActivity(intent)
        }

        val diaryAdapter = DiaryAdapter(requireContext(), diaryList)
        diaryAdapter.setOnItemClickListener(object:
            DiaryAdapter.OnItemClickListener {
                override fun onTextClick(view: View, diary: Diary, pos: Int) {
                    val intent = Intent(activity, DiaryShowActivity::class.java)
                    intent.putExtra("diaryData", arrayOf(diary.date, diary.name, diary.uri.toString(), diary.title))
                    activity?.supportFragmentManager
                        ?.beginTransaction()
                        ?.remove(diaryFragment)
                        ?.commit()
                    startActivity(intent)
                }
            }
        )

        val diaryJsonFile = File(context?.filesDir, "diaries.json")

        if (diaryJsonFile.exists()) {
            val diaryJsonString = diaryJsonFile.readText()

            if (diaryList.size == 0 && diaryJsonString.isNotEmpty()) {
                val diaryJsonArray = JSONTokener(diaryJsonString).nextValue() as JSONArray

                for (i in 0 until diaryJsonArray.length()) {
                    val date = diaryJsonArray.getJSONObject(i).getString("date")
                    val name = diaryJsonArray.getJSONObject(i).getString("name")
                    val rawUri = diaryJsonArray.getJSONObject(i).getString("uri")
                    val title = diaryJsonArray.getJSONObject(i).getString("title")

                    diaryList.add(Diary(date, name, rawUri.toUri(), title))
                }
            }
        }

        recyclerView = rootView.findViewById(R.id.diaryRecyclerView)as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = diaryAdapter

        diaryAdapter.notifyDataSetChanged()

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}