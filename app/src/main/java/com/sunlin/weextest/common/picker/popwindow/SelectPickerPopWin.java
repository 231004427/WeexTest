package com.sunlin.weextest.common.picker.popwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.PopupWindow;

import com.sunlin.weextest.R;
import com.sunlin.weextest.common.picker.LoopScrollListener;
import com.sunlin.weextest.common.picker.LoopView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SelectPickerPopWin extends PopupWindow implements View.OnClickListener {
    private static final int DEFAULT_SELECT = 0;
    public Button cancelBtn;
    public Button confirmBtn;
    public LoopView yearLoopView;
    public View pickerContainerV;
    public View contentView;//root view

    private int itemPos = 0;
    private Context mContext;
    private String textCancel;
    private String textConfirm;
    private int colorCancel;
    private int colorConfirm;
    private int btnTextsize;//text btnTextsize of cancel and confirm button
    private int viewTextSize;

    List<String> itemList = new ArrayList();
    public static class Builder{

        //Required
        private Context context;
        private SelectPickerPopWin.OnSelectPickedListener listener;
        public Builder(Context context, SelectPickerPopWin.OnSelectPickedListener listener){
            this.context = context;
            this.listener = listener;
        }

        //Option
        private int selectIndex = DEFAULT_SELECT;
        private String valueChose;
        private String textCancel = "取消";
        private String textConfirm = "确认";
        private int colorCancel = Color.parseColor("#999999");
        private int colorConfirm = Color.parseColor("#303F9F");
        private int btnTextSize = 16;//text btnTextsize of cancel and confirm button
        private int viewTextSize = 25;

        public SelectPickerPopWin.Builder textCancel(String textCancel){
            this.textCancel = textCancel;
            return this;
        }

        public SelectPickerPopWin.Builder textConfirm(String textConfirm){
            this.textConfirm = textConfirm;
            return this;
        }

        public SelectPickerPopWin.Builder valueChose(String valueChose){
            this.valueChose = valueChose;
            return this;
        }

        public SelectPickerPopWin.Builder colorCancel(int colorCancel){
            this.colorCancel = colorCancel;
            return this;
        }

        public SelectPickerPopWin.Builder colorConfirm(int colorConfirm){
            this.colorConfirm = colorConfirm;
            return this;
        }

        /**
         * set btn text btnTextSize
         * @param textSize dp
         */
        public SelectPickerPopWin.Builder btnTextSize(int textSize){
            this.btnTextSize = textSize;
            return this;
        }

        public SelectPickerPopWin.Builder viewTextSize(int textSize){
            this.viewTextSize = textSize;
            return this;
        }
        public SelectPickerPopWin build(){
            return new SelectPickerPopWin(this);
        }
    }

    public SelectPickerPopWin(SelectPickerPopWin.Builder builder){

        this.textCancel = builder.textCancel;
        this.textConfirm = builder.textConfirm;
        this.mContext = builder.context;
        this.mListener = builder.listener;
        this.colorCancel = builder.colorCancel;
        this.colorConfirm = builder.colorConfirm;
        this.btnTextsize = builder.btnTextSize;
        this.viewTextSize = builder.viewTextSize;
        setSelectedValue(builder.valueChose);
        initView();
    }

    private SelectPickerPopWin.OnSelectPickedListener mListener;

    private void initView() {

        contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_select_picker, null);
        cancelBtn = (Button) contentView.findViewById(R.id.btn_cancel);
        cancelBtn.setTextColor(colorCancel);
        cancelBtn.setTextSize(btnTextsize);
        confirmBtn = (Button) contentView.findViewById(R.id.btn_confirm);
        confirmBtn.setTextColor(colorConfirm);
        confirmBtn.setTextSize(btnTextsize);
        yearLoopView = (LoopView) contentView.findViewById(R.id.picker_select);
        pickerContainerV = contentView.findViewById(R.id.container_picker);

        yearLoopView.setLoopListener(new LoopScrollListener() {
            @Override
            public void onItemSelect(int item) {
                itemPos = item;
                initSelectPickerView();
            }
        });

        initPickerViews(); // init

        cancelBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
        contentView.setOnClickListener(this);

        if(!TextUtils.isEmpty(textConfirm)){
            confirmBtn.setText(textConfirm);
        }

        if(!TextUtils.isEmpty(textCancel)){
            cancelBtn.setText(textCancel);
        }

        setTouchable(true);
        setFocusable(true);
        // setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setAnimationStyle(R.style.FadeInPopWin);
        setContentView(contentView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
    }

    /**
     * Init year and month loop view,
     * Let the day loop view be handled separately
     */
    private void initPickerViews() {

        initSelectPickerView();
        yearLoopView.setDataList((ArrayList) itemList);
        yearLoopView.setInitPosition(itemPos);
    }

    /**
     * Init item
     */
    private void initSelectPickerView() {

        itemList = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            itemList.add("TEST:" + i);
        }

    }

    /**
     * set selected date position value when initView.
     *
     * @param valueStr
     */
    public void setSelectedValue(String valueStr) {

        if (!TextUtils.isEmpty(valueStr)) {
            this.itemPos=0;
        }
    }

    /**
     * Show date picker popWindow
     *
     * @param activity
     */
    public void showPopWin(Activity activity) {

        if (null != activity) {

            TranslateAnimation trans = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
                    0, Animation.RELATIVE_TO_SELF, 1,
                    Animation.RELATIVE_TO_SELF, 0);

            showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM,
                    0, 0);
            trans.setDuration(400);
            trans.setInterpolator(new AccelerateDecelerateInterpolator());

            pickerContainerV.startAnimation(trans);
        }
    }

    /**
     * Dismiss date picker popWindow
     */
    public void dismissPopWin() {

        TranslateAnimation trans = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);

        trans.setDuration(400);
        trans.setInterpolator(new AccelerateInterpolator());
        trans.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                dismiss();
            }
        });

        pickerContainerV.startAnimation(trans);
    }

    @Override
    public void onClick(View v) {

        if (v == contentView || v == cancelBtn) {

            dismissPopWin();
        } else if (v == confirmBtn) {

            if (null != mListener) {

                mListener.onSelectPickCompleted(itemPos,getStrValue());
            }

            dismissPopWin();
        }
    }

    public String getStrValue() {
        return this.itemList.get(itemPos);
    }

    /**
     * Transform int to String with prefix "0" if less than 10
     * @param num
     * @return
     */
    public static String format2LenStr(int num) {

        return (num < 10) ? "0" + num : String.valueOf(num);
    }

    public static int spToPx(Context context, int spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    public interface OnSelectPickedListener {

        /**
         * Listener when date has been checked
         *
         * @param index
         */
        void onSelectPickCompleted(int index,String valueDesc);
    }
}
