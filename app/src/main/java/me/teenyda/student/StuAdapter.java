package me.teenyda.student;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * author: teenyda
 * date: 2020/5/17
 * description:
 */
public class StuAdapter extends RecyclerView.Adapter<StuAdapter.ViewHolder> {

    private Context mContext;

    private List<Student> mStudents = new ArrayList<>();

    private IStuManage mStuManage;

    public StuAdapter(Context context) {
        mContext = context;
        Student student = new Student();
        student.setId(9527);
        student.setName("智取棋");
        student.setClassName("16软件兼兽医");
        student.setMajor("软件技术");
        mStudents.add(student);
    }

    public void setStuManage(IStuManage manage) {
        mStuManage = manage;
    }

    interface IStuManage {
        void onUpdateClick(Student stu);
        void onDeleteClick(Student stu);
    }

    public void addStu(Student student) {
        mStudents.add(student);
        notifyDataSetChanged();
    }

    public void updateStu(Student student) {
        for (Student stu : mStudents) {
            if (stu.getId() == student.getId()) {
                stu.setName(student.getName());
                stu.setClassName(student.getClassName());
                stu.setMajor(student.getMajor());
                break;
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_student, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final Student student = mStudents.get(i);
        viewHolder.stu_id.setText(student.getId() + "");
        viewHolder.stu_name.setText(student.getName());
        viewHolder.stu_class.setText(student.getClassName());
        viewHolder.stu_major.setText(student.getMajor());
        viewHolder.stu_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStuManage != null) {
                    mStuManage.onUpdateClick(student);
                }
            }
        });
        viewHolder.stu_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStuManage != null) {
                    mStuManage.onDeleteClick(student);
                    mStudents.remove(i);
                    notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mStudents.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView stu_id;
        TextView stu_name;
        TextView stu_class;
        TextView stu_major;
        TextView stu_update;
        TextView stu_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stu_id = itemView.findViewById(R.id.stu_id);
            stu_name = itemView.findViewById(R.id.stu_name);
            stu_class = itemView.findViewById(R.id.stu_class);
            stu_major = itemView.findViewById(R.id.stu_major);
            stu_update = itemView.findViewById(R.id.stu_update);
            stu_delete = itemView.findViewById(R.id.stu_delete);
        }
    }
}
