package me.teenyda.student;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;


/**
 * author: teenyda
 * date: 2020/5/17
 * description:
 */
public class PopStu {
    private Context mContext;
    private PopupWindow mPopupWindow;
    private View mView;

    private EditText et_id;
    private EditText et_name;
    private EditText et_class;
    private EditText et_major;

    private Button btnOk;

    private IOnStudentChange mStudentChange;

    //1添加 2更新
    private int mFlag = 1;

    public PopStu(Context context) {
        mContext = context;

        initPopView();
    }

    interface IOnStudentChange{
        void stuChange(Student student, int flage);
    }

    public void setOnStudentChange(IOnStudentChange onStudentChange) {
        mStudentChange = onStudentChange;
    }

    private void initPopView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.pop_stu, null, false);
        mPopupWindow = new PopupWindow(mView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });

        et_id = mView.findViewById(R.id.et_id);
        et_name = mView.findViewById(R.id.et_name);
        et_class = mView.findViewById(R.id.et_class);
        et_major = mView.findViewById(R.id.et_major);
        btnOk = mView.findViewById(R.id.ok);

        et_id.setFocusable(false);
        et_id.setFocusableInTouchMode(false);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStudentChange != null) {
                    Student student = new Student();
                    student.setId(Long.parseLong(et_id.getText().toString()));
                    student.setName(et_name.getText().toString());
                    student.setClassName(et_class.getText().toString());
                    student.setMajor(et_major.getText().toString());

                    et_id.setText("");
                    et_name.setText("");
                    et_class.setText("");
                    et_major.setText("");
                    mStudentChange.stuChange(student, mFlag);
                }
            }
        });
    }


    public void show(View v, Student stu, int flag) {
        mFlag = flag;
        // setFocusable 默认是false
        // setFocusable(true)外部和内部都会响应，点击外部就会取消，setOutsideTouchable(false)失效
        mPopupWindow.setFocusable(true);
        // 设置PopupWindow是否能响应点击事件
        mPopupWindow.setTouchable(true);
        // 设置PopupWindow内容区域外的区域是否响应点击事件（true：响应；false：不响应）
        mPopupWindow.setOutsideTouchable(false);

        mPopupWindow.showAtLocation(v, Gravity.CENTER, 0 , 0);
        backgroundAlpha(0.5f);


        if (flag == 1){
            String id = String.valueOf(System.currentTimeMillis());
            et_id.setText(id);
        }else {
            et_id.setText(stu.getId() + "");
            et_name.setText(stu.getName());
            et_class.setText(stu.getClassName());
            et_major.setText(stu.getMajor());
        }

    }

    public void dismiss() {
        mPopupWindow.dismiss();
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }
}
