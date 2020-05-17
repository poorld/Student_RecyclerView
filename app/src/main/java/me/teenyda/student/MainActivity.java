package me.teenyda.student;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;

    private StuAdapter mAdapter;

    private Button add_stu;

    private PopStu mPopStu;

    //1添加 2更新
    private static final int flag_add = 1;
    private static final int flag_update = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_stu = findViewById(R.id.add_stu);
        rv = findViewById(R.id.rv);

        mAdapter = new StuAdapter(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);

        mPopStu = new PopStu(this);

        mAdapter.setStuManage(new StuAdapter.IStuManage() {

            @Override
            public void onUpdateClick(Student stu) {
                mPopStu.show(rv, stu, flag_update);
            }

            @Override
            public void onDeleteClick(Student stu) {
                //
            }
        });

        add_stu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopStu.show(rv, null, flag_add);
            }
        });

        mPopStu.setOnStudentChange(new PopStu.IOnStudentChange() {
            @Override
            public void stuChange(Student student, int flage) {
                if (flage == flag_add) {
                    mAdapter.addStu(student);
                } else if (flage == flag_update) {
                    mAdapter.updateStu(student);
                }
                mPopStu.dismiss();
            }
        });
    }
}
