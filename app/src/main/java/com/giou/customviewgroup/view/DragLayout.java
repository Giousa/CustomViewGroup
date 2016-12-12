package com.giou.customviewgroup.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2016/12/12
 * Time:下午8:21
 */

public class DragLayout extends FrameLayout {

    private final String TAG = DragLayout.class.getSimpleName();
    private ViewDragHelper mViewDragHelper;
    private ViewGroup mLeftMenu;//左侧单
    private ViewGroup mMainContent;//主内容

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
         * @param child 被拖拽的子控件
         * @param pointerId 多点触摸的手指id
         * @return true可以滑动,false禁止滑动,我们可以获取所有子控件的View,然后判断当前是否可以滑动。
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            Log.d(TAG,"child="+child+"    pointerId="+pointerId);
            return child == mMainContent;//当子View是主界面的时候,不让它滑动
        }

        /**
         * 返回值决定水平方向移动位置
         * @param child 被拖拽的子控件
         * @param left 移动到的位置
         * @param dx 偏移量
         * @return
         *
         * 备注:默认是0,说明无法移动
         *      若是tryCaptureView方法中返回false,那么这里也不会被执行(禁止滑动,自然没有x偏移)
         *
         *      oldLeft = left - dx;
         *      此时的left,即为下一次的oldLeft
         *      xxxxxxxx
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            int oldLeft = child.getLeft();
            Log.d(TAG,"child="+child+"    left="+left+"    dx="+dx+"   newLeft="+oldLeft);
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

    /**
     *
     * 查找所有控件
     * 当View中所有的子控件均被映射成xml后触发
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG,"onFinishInflate"+getChildCount());

        //容错性检查,健壮性检查,子View至少2个
        if(getChildCount() < 2){
            throw new IllegalStateException("子View少于2个!!");
        }

        //判断是否是ViewGroup的实现类

        for (int i = 0; i < getChildCount(); i++) {
            if(!(getChildAt(i) instanceof ViewGroup)){
                throw  new IllegalStateException("第"+i+"个子View不是ViewGroup的实现类!");
            }
        }

        mLeftMenu = (ViewGroup) getChildAt(0);
        mMainContent = (ViewGroup) getChildAt(1);
        Log.d(TAG,"here mLeftMenu="+mLeftMenu);
        Log.d(TAG,"here mMainContent="+mMainContent);
    }
}
