package com.example.customcalender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.customcalender.calender.CalendarAdapter;
import com.example.customcalender.calender.OneDayView;
import com.example.customcalender.calender.OneMonthView;

public class MainActivity extends AppCompatActivity {
    private LinearLayout ll_date_time_date_prev;
    private LinearLayout ll_date_time_date_next;

    private ViewPager vp_date_item_calendar;

    private TextView tv_date_time_date;

    private CalendarAdapter adapter;
    private OneMonthView mOneMonthView;

    private String mCurrentYear;
    private String mCurrentMonth;
    private String mCurrentDate;

    private int mMinH = 0;
    private int mMaxH = 23;
    private int mMinM = 0;
    private int mMaxM = 59;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setView();
    }

    public void initView() {
        ll_date_time_date_prev = findViewById(R.id.ll_date_time_date_prev);
        ll_date_time_date_next = findViewById(R.id.ll_date_time_date_next);
        vp_date_item_calendar = findViewById(R.id.vp_date_item_calendar);
        tv_date_time_date = findViewById(R.id.tv_date_time_date);

        mCurrentYear = DateFormat.getCurrentDate("yyyy");
        mCurrentMonth = DateFormat.getCurrentDate("MM");

        mCurrentDate = DateFormat.getCurrentDate("yyyyMMdd");
    }

    public void setView() {
        ll_date_time_date_prev.setOnClickListener(onClickListener);
        ll_date_time_date_next.setOnClickListener(onClickListener);
        tv_date_time_date.setText(mCurrentMonth + "월 " + mCurrentYear);
        adapter = new CalendarAdapter(MainActivity.this, onDayClickListener);
        adapter.setOnMonthChangeListener(onMonthChangeListener);
        adapter.setCalType(0);
        vp_date_item_calendar.setAdapter(adapter);
        vp_date_item_calendar.setOnPageChangeListener(adapter);
        vp_date_item_calendar.setCurrentItem(adapter.getPosition(Integer.parseInt(mCurrentYear), Integer.parseInt(mCurrentMonth) - 1));
        vp_date_item_calendar.setOffscreenPageLimit(1);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        private String date = "";
        private String prevDate = "";
        private int year = 0;
        private int month = 0;

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_date_time_date_prev:

                    date = mCurrentYear + mCurrentMonth;
                    prevDate = DateFormat.getMDate(date, "yyyyMM", -1);

                    year = Integer.parseInt(DateFormat.newFormat(prevDate, "yyyyMM", "yyyy"));
                    month = Integer.parseInt(DateFormat.newFormat(prevDate, "yyyyMM", "MM"));

                    vp_date_item_calendar.setCurrentItem(adapter.getPosition(year, month - 1));
                    break;
                case R.id.ll_date_time_date_next:
                    date = mCurrentYear + mCurrentMonth;
                    prevDate = DateFormat.getMDate(date, "yyyyMM", 1);

                    year = Integer.parseInt(DateFormat.newFormat(prevDate, "yyyyMM", "yyyy"));
                    month = Integer.parseInt(DateFormat.newFormat(prevDate, "yyyyMM", "MM"));

                    vp_date_item_calendar.setCurrentItem(adapter.getPosition(year, month - 1));
                    break;
            }
        }
    };
    private CalendarAdapter.OnMonthChangeListener onMonthChangeListener = new CalendarAdapter.OnMonthChangeListener() {
        @Override
        public void onChange(int year, int month, OneMonthView monthViews) {
            mOneMonthView = monthViews;
            Log.e("month ? ",month+"");
            Log.e("currentmonth ? ",DateFormat.getCurrentDate("MM")+"");
            /*if(Integer.parseInt(DateFormat.getCurrentDate("MM")) == month){
                ll_date_time_date_next.setBackgroundResource(R.drawable.ic_chevron_right_gray_24dp);
            }else{
                ll_date_time_date_next.setBackgroundResource(R.drawable.ic_chevron_right_black_24dp);
            }*/
            mCurrentYear = String.valueOf(year);
            mCurrentMonth = String.valueOf((month + 1));

            tv_date_time_date.setText(year + "년 " + (month + 1) + "월");

            adapter.test(Integer.parseInt(mCurrentDate));

        }
    };

    private OneDayView.OnDayClickListener onDayClickListener = new OneDayView.OnDayClickListener() {

        @Override
        public void onClick(View view) {
          //  ComLog.d("date: " + view.getId());

            mCurrentDate = String.valueOf(view.getId());


            adapter.test(Integer.parseInt(mCurrentDate));

            if(mOneMonthView != null) {

                mOneMonthView.selectDay(view.getId());
            }

        }
    };
}
