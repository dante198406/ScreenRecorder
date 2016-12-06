package com.erobbing.view;

import com.erobbing.screenrecorder.R;
import com.erobbing.screenrecorder.R.drawable;
import com.erobbing.screenrecorder.R.id;
import com.erobbing.screenrecorder.R.layout;
import com.erobbing.screenrecorder.R.styleable;

import android.R.color;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * Created by zhangzhaolei on 2016/11/24.
 */

public class DropEditText extends FrameLayout implements View.OnClickListener, OnItemClickListener {
    private EditText mEditText;  // input field
    private ImageView mDropImage; // imageButton by the right side of input field
    private PopupWindow mPopup; // show popupwindow when click imageButton
    private WrapListView mPopView; // layout of popupwindow

    private int mDrawableLeft;
    private int mDropMode; // flow_parent or wrap_content
    private String mHit;

    public DropEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.edit_layout, this);
        mPopView = (WrapListView) LayoutInflater.from(context).inflate(R.layout.pop_view, null);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DropEditText, defStyle, 0);
        mDrawableLeft = ta.getResourceId(R.styleable.DropEditText_drawableRight, R.mipmap.ic_launcher);
        mDropMode = ta.getInt(R.styleable.DropEditText_dropMode, 0);
        mHit = ta.getString(R.styleable.DropEditText_hint);
        ta.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mEditText = (EditText) findViewById(R.id.dropview_edit);
        mDropImage = (ImageView) findViewById(R.id.dropview_image);

        mEditText.setSelectAllOnFocus(true);
        //mEditText.setText();
        mDropImage.setImageResource(mDrawableLeft);

        if (!TextUtils.isEmpty(mHit)) {
            mEditText.setHint(mHit);
        }

        mDropImage.setOnClickListener(this);
        mPopView.setOnItemClickListener(this);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // layout changed
        // and dropMode is flower_parent
        // width of ListView will be changed
        if (changed && 0 == mDropMode) {
            mPopView.setListWidth(getMeasuredWidth());
        }
    }

    /**
     * Adapter
     *
     * @param adapter ListView的Adapter
     */
    public void setAdapter(BaseAdapter adapter) {
        mPopView.setAdapter(adapter);

        mPopup = new PopupWindow(mPopView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopup.setBackgroundDrawable(new ColorDrawable(color.transparent));
        mPopup.setFocusable(true); // popwin obtain focus
    }

    /**
     * obtain text of input field
     *
     * @return String content
     */
    public String getText() {
        return mEditText.getText().toString();
    }

    public void setText(String string) {
        mEditText.setText(string);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dropview_image) {
            if (mPopup.isShowing()) {
                mPopup.dismiss();
                return;
            }

            mPopup.showAsDropDown(this, 0, 5);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        mEditText.setText(mPopView.getAdapter().getItem(position).toString());
        mPopup.dismiss();
    }
}
