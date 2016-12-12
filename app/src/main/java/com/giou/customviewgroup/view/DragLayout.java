package com.giou.customviewgroup.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2016/12/12
 * Time:下午8:21
 */

public class DragLayout extends FrameLayout {

    private ViewDragHelper mViewDragHelper;

    /**
     * 代码
     * @param context
     */
    public DragLayout(Context context) {
        this(context,null);
    }


    /**
     * xml
     * @param context
     * @param attrs
     */
    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }


    /**
     * xml
     * @param context
     * @param attrs
     * @param defStyleAttr style的id
     */
    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //被拖拽控件父布局,敏感度越大越敏感,
        //创建ViewDragHelper对象
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, mCallback);
    }


    /**
     * 提供信息,接受事件
     */
    ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {


        /**
         * 重写监听回调,返回值决定被拖拽的控件是否可以移动,若是false,就无法移动
         * @param child
         * @param pointerId
         * @return
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        /**
         * 返回值决定水平方向移动位置
         * @param child
         * @param left
         * @param dx
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }
    };

    /**
     *
     注意：
     1.
     在ViewGroup中onInterceptTouchEvent方法若反回false,那么触屏事件会继续向下传递，
     但如果没有子View去处理这个事件，即子view的onTouchEvent没有返回True，
     则最后还是由ViewGroup去处理这个事件，也就又执行了自己的onTouchEvent。
     2.
     onTouch调用前会自动调用onInterceptTouchEvent 如果onInterceptTouchEvent返回的false,
     则不会调用onTouchEvent，若重写onInterceptTouchEvent让它在需要调用onTouchEvent时返回true
     */

    /**
     * 转交拦截判断,处理触摸事件
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;//消费事件
    }
}
