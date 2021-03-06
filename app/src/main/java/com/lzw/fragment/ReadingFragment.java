package com.lzw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lzw.activity.activity_in_fragment1.reading.ReadingExamActivity;
import com.lzw.englishExamSystem.R;
import com.lzw.utils.testPaperUtils.TestPaperFactory;
import com.lzw.utils.testPaperUtils.TestPaperFromWord;

import java.util.ArrayList;
import java.util.List;

/**
 *  这个类为  ReadingActivity 提供Fragment，里面包含一个ListView
 */
public class ReadingFragment extends Fragment {

    // 用来标识是哪个页面：依次为  选词填空、快速阅读、仔细阅读
    private int index;

    private ListView listView;  //试卷信息
    private TextView tv_test_paper_name;    // 试卷名称

    private List<TestPaperFromWord.TestPaperInfo> allTestPaperInfo = new ArrayList<>();   // 存储所有试卷-信息


    public ReadingFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(int position) {
        ReadingFragment fragment = new ReadingFragment();
        fragment.index = position;
        return fragment;
    }

    /**
     * 获取需要的所有数据
     */
    private void initData(){
        TestPaperFactory testPaperFactory = TestPaperFactory.getInstance();
        allTestPaperInfo = testPaperFactory.getAllTestPaperInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initData();

        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.lv_question);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return allTestPaperInfo.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup viewGroup) {
                View view;
                if(convertView == null ) {
                    LayoutInflater inflater = ReadingFragment.this.getLayoutInflater();
                    view = inflater.inflate(R.layout.examination_paper_information_item,null);
                }
                else {
                    view = convertView;
                }

                final TestPaperFromWord.TestPaperInfo testPaperInfo = allTestPaperInfo.get(position);

                tv_test_paper_name = view.findViewById(R.id.tv_item_test_paper_name);
                tv_test_paper_name.setText(testPaperInfo.getYear()+"年"+testPaperInfo.getMonth()+"月"+"-第"+testPaperInfo.getIndex()+"套");

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), ReadingExamActivity.class);
                        intent.putExtra(ReadingExamActivity.TEST_PAPER_INDEX,position);//告诉下一个页面是第几套试卷
                        intent.putExtra(ReadingExamActivity.QUESTION_TYPE,index);//是什么题型

                        startActivity(intent);
                    }
                });

                return view;
            }
        });
    }

}
