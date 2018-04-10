package com.sunlin.weextest.common.dialog;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunlin.weextest.R;
import com.sunlin.weextest.common.ScreenUtil;

public class ConfirmDialog extends DialogFragment implements View.OnClickListener {
    private ConfirmDialog.OnClickListener listener;
    private View line1;
    private TextView title;
    private TextView messText;
    private TextView submit;
    private TextView cancel;
    private String titleStr="提示";
    private String submitStr="确认";
    private String cancelStr="";
    private String messStr="";
    public void setStr(String _titleStr,String _submitStr,String _cancelStr,String _messStr){
        if(_titleStr != null)
        titleStr=_titleStr;
        if(_submitStr != null)
        submitStr=_submitStr;
        if(_cancelStr != null)
        cancelStr=_cancelStr;
        if(_messStr != null)
        messStr=_messStr;
    }
    @Override
    public void onResume() {
        super.onResume();
        Window dialogWindow = getDialog().getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width=(int)(ScreenUtil.getScreenWidthPixels(getActivity())*0.75);
        dialogWindow.setAttributes(lp);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int theme = 0;
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE,theme);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_confirm, container);

        line1=(View)view.findViewById(R.id.line1);
        title=(TextView)view.findViewById(R.id.title);
        messText=(TextView)view.findViewById(R.id.messText);
        submit=(TextView)view.findViewById(R.id.submit);
        cancel=(TextView)view.findViewById(R.id.cancel);

        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);
        if(!titleStr.equals("null") && !titleStr.equals("")) {
            title.setText(titleStr);
        }else{
            title.setVisibility(View.GONE);
        }
        if(!submitStr.equals("")) {
            submit.setText(submitStr);
        }
        if(!cancelStr.equals("null") && !cancelStr.equals("")) {
            cancel.setText(cancelStr);
        }else{
            cancel.setVisibility(View.GONE);
            line1.setVisibility(View.GONE);
        }
        if(!messStr.equals("")) {
            messText.setText(messStr);
        }



        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                if(listener != null){
                    listener.onClick(1);
                }
                this.dismiss();
                break;
            case R.id.cancel:
                if(listener != null){
                    listener.onClick(2);
                }
                this.dismiss();
                break;
        }
    }
    public void setOnClickListener(ConfirmDialog.OnClickListener listener)
    {
        this.listener=listener;
    }
    public interface OnClickListener{
        void onClick(int type);
    }
}
