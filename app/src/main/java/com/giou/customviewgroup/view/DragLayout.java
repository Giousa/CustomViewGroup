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

     1.LLayout 中 onInterceptTouchEvent 默认返回值为false，onTouchEvent 默认返回值为false，所以只调用了ACTION_DOWN事件；
     LView中 onTouchEvent 默认返回值为true；调用了ACTION_DOWN，ACTION_UP 两个事件；

     2.LLayout中onInterceptTouchEvent返回了true，对触摸事件进行了拦截，所以没有将事件传递给View，
     而直接执行了LLayout中的onTouchEvent事件；

     3.把LLayout中onInterceptTouchEvent返回值改为false，再把LView中的onTouchEvent改为返回false:
     只执行了ACTION_DOWN，然后就到LLayout中执行onTouchEvent事件了；

     结论:
     ViewGroup里的onInterceptTouchEvent默认值是false这样才能把事件传给View里的onTouchEvent.
     ViewGroup里的onTouchEvent默认值是false,只执行一次touch事件。
     View里的onTouchEvent返回默认值是true.这样才能执行多次touch事件。

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
