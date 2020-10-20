package com.example.customcalender.calender;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.Calendar;

public class CalendarAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {
    public static int CAL_TYPE_DEFAULT = 0;
    public static int CAL_TYPE_NOT_PAST = 1;
    public static int CAL_TYPE_NOT_FUTURE = 2;

    private Context mContext;

    private OneMonthView[] monthViews;
    /** 위치계산을 위한 기준 년 */
    final static int BASE_YEAR = 2015;
    /** 위치계산을 위한 기준 월 */
    final static int BASE_MONTH = Calendar.JANUARY;
    /** 뷰페이저에서 재사용할 페이지 갯수 */
    final static int PAGES = 5;
    /** 루프수를 1000 이상 설정할 수 있겠지만, 이정도면 무한 스크롤이라고 생각하자 */
    final static int LOOPS = 1000;
    /** 기준 위치, 기준 날짜에 해당하는 위치 */
    final static int BASE_POSITION = PAGES * LOOPS / 2;
    /** 기준 날짜를 기반한 Calendar */
    final Calendar BASE_CAL;
    /** 이전 위치 */
    private int previousPosition;

    private int mCurrentPosition;

    private int mCalType;

    public interface OnMonthChangeListener {
        /**
         * 날짜가 바뀔 때
         * @param year 년
         * @param month 월
         */
        void onChange(int year, int month, OneMonthView monthViews);
    }

    private CalendarAdapter.OnMonthChangeListener dummyListener = new CalendarAdapter.OnMonthChangeListener() {
        @Override
        public void onChange(int year, int month, OneMonthView monthViews) {}
    };

    private CalendarAdapter.OnMonthChangeListener listener = dummyListener;


    public CalendarAdapter(Context context, OneDayView.OnDayClickListener onDayClickListener) {
        this.mContext = context;
        //기준 Calendar 지정
        Calendar base = Calendar.getInstance();
        base.set(BASE_YEAR, BASE_MONTH, 1);
        BASE_CAL = base;

        mCalType = CalendarAdapter.CAL_TYPE_DEFAULT;

        monthViews = new OneMonthView[PAGES];
        for(int i = 0; i < PAGES; i++) {
            monthViews[i] = new OneMonthView(context);
            monthViews[i].setCalType(mCalType);
            monthViews[i].setOnDayClickListener(onDayClickListener);
        }
    }

    public void setCalType(int calType) {
        mCalType = calType;

        if(monthViews != null) {
            for(int i = 0; i < monthViews.length; i++) {
                monthViews[i].setCalType(mCalType);
            }
        }

    }

    /**
     * 년월이 구하기
     * @param position 페이지 위치
     * @return position 위치에 해당하는 년월이
     */
    public YearMonth getYearMonth(int position) {
        Calendar cal = (Calendar)BASE_CAL.clone();
        cal.add(Calendar.MONTH, position - BASE_POSITION);

        return new YearMonth(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
    }

    /**
     * 페이지 위치 구하기
     * @param year 년
     * @param month 월
     * @return 페이지 위치
     */
    public int getPosition(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        return BASE_POSITION + howFarFromBase(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
    }

    public void setOnMonthChangeListener(CalendarAdapter.OnMonthChangeListener onMonthChangeListener) {
        listener = onMonthChangeListener;
    }

    /**
     * 기준 날짜를 기준으로 몇달이 떨어져 있는지 확인
     * @param year 비교할 년
     * @param month 비교할 월
     * @return 달 수, count of months
     */
    private int howFarFromBase(int year, int month) {

        int disY = (year - BASE_YEAR) * 12;
        int disM = month - BASE_MONTH;

        return disY + disM;
    }

    public void test(int index) {
        for(int i = 0; i < PAGES; i++) {
            monthViews[i].selectDay(index);
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

//            HLog.d(TAG, CLASS, "instantiateItem " + position);

        int howFarFromBase = position - BASE_POSITION;
        Calendar cal = (Calendar) BASE_CAL.clone();
        cal.add(Calendar.MONTH, howFarFromBase);


//        ComLog.d("container: " + container);

        position = position % PAGES;


//        if(prevPosition != -1) {
//            container.removeView((View) monthViews[prevPosition]);
//        }

//        if (container != null) {
//            container.removeView(monthViews[position]);
//        }

        container.addView(monthViews[position]);

        monthViews[position].make(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));

//            Calendar cal = Calendar.getInstance();
//			cal.set(year, month, 1);

//			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);//1일의 요일
//			int maxOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);//마지막 일수
//
//			Log.i("test", "maxOfMonth: " + maxOfMonth);

        OneMonthView[] copyMonthViews = null;

        if(monthViews != null) {
            copyMonthViews = new OneMonthView[monthViews.length];
            for(int i=0; i<monthViews.length; i++) {
                copyMonthViews[i] = monthViews[i];
            }
        }

//        return copyMonthViews[position];
        return monthViews[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//            HLog.d(TAG, CLASS, "destroyItem " + position);
//        ComLog.d("destroyItem " + position);
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return PAGES * LOOPS;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch(state) {
            case ViewPager.SCROLL_STATE_IDLE:
//            	Log.i(TAG, "SCROLL_STATE_IDLE");
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
//            	Log.i(TAG, "SCROLL_STATE_DRAGGING");
                previousPosition = mCurrentPosition;
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
//                Log.i(TAG, "SCROLL_STATE_SETTLING");
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        //HLog.d(TAG, CLASS, position + "-  " + positionOffset);
        if(previousPosition != position) {
            previousPosition = position;

            YearMonth ym = getYearMonth(position);

            int howFarFromBase = position % PAGES;

//            for(int i = 0; i < PAGES; i++) {
//                if(howFarFromBase != i) {
//                    monthViews[i].clearSelect();
//                }
//
//            }



            listener.onChange(ym.year, ym.month, monthViews[howFarFromBase]);

//                HLog.d(TAG, CLASS, position + " onPageScrolled-  " + ym.year + "." + ym.month);
        }
    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPosition = position;
    }
}
