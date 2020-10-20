package com.example.customcalender.calender;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.customcalender.DateFormat;
import com.example.customcalender.R;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * View to display a day
 * @author Brownsoo
 *
 */
public class OneDayView extends RelativeLayout {
 
    private static final String TAG = "OneDayView";
    private static final String NAME = "OneDayView";
    private final String CLASS = NAME + "@" + Integer.toHexString(hashCode());
    
    public interface OnDayClickListener {
//        void onClick(int day);
    	void onClick(View view);
    } 
    
    private RelativeLayout oneday_topGroup;
    private RelativeLayout rl_onday_day;
    private LinearLayout ll_onday_day;

    private View onday_item;
    
    /** number text field */
    private TextView tv_day;
    /** Weather icon */
    /** Value object for a day info */
    private OneDayData one;
    
    private OnDayClickListener onDayClickListener;

    private Calendar cal = Calendar.getInstance();

    private int date;

    private int calType;
    /**
     * OneDayView constructor
     * @param context
     */
    public OneDayView(Context context) {
        super(context);
        init(context);
 
    }

    /**
     * OneDayView constructor for xml
     * @param context
     * @param attrs
     */
    public OneDayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
 
    private void init(Context context)
    {

        View v = View.inflate(context, R.layout.include_calendar_onday, this);
        
        oneday_topGroup = (RelativeLayout) v.findViewById(R.id.oneday_topGroup);
        rl_onday_day = (RelativeLayout) v.findViewById(R.id.rl_onday_day);
        ll_onday_day = (LinearLayout) v.findViewById(R.id.ll_onday_day);
        onday_item = (View) v.findViewById(R.id.view_onday_item);
        tv_day = (TextView) v.findViewById(R.id.tv_onday_day);
        one = new OneDayData();

        calType = 0;
    }
    
    /**
     * Set a day
     * @param year 4 digits of a year
     * @param month Calendar.JANUARY ~ Calendar.DECEMBER
     * @param day day of month
     */
    public void setDay(int year, int month, int day) {
        this.one.cal.set(year, month, day);
    }

    /**
     * Set a day
     * @param cal Calendar instance
     */
    public void setDay(Calendar cal) {
        this.one.setDay((Calendar) cal.clone());
    }

    /**
     * Set a day
     * @param one OneDayData instance
     */
    public void setDay(OneDayData one) {
        this.one = one;
    }
    
    /**
     * Get a day info
     * @return OneDayData instance
     */
    public OneDayData getDay() {
        return one;
    }

    /**
     * Set the message to display
     * @param msg message
     */
    public void setMsg(String msg){
        one.setMessage(msg);
    }

    /**
     * Get the message is displaying
     * @return message
     */
    public CharSequence getMsg(){
        return  one.getMessage();
    }

    /**
     * Returns the value of the given field after computing the field values by
     * calling {@code complete()} first.
     * 
     * @param field Calendar.YEAR or Calendar.MONTH or Calendar.DAY_OF_MONTH
     *
     * @throws IllegalArgumentException
     *                if the fields are not set, the time is not set, and the
     *                time cannot be computed from the current field values.
     * @throws ArrayIndexOutOfBoundsException
     *                if the field is not inside the range of possible fields.
     *                The range is starting at 0 up to {@code FIELD_COUNT}.
     */
    public int get(int field) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        return one.get(field);
    }


    public void setCalType(int calType) {
        this.calType = calType;
    }

    public int getDate() {
        return date;
    }

    public void setSelect(boolean isSelect) {
        if(isSelect) {
            rl_onday_day.setBackgroundResource(R.drawable.round_bg007caa_5dp);
            tv_day.setTextColor(0xFFFFFFFF);
        } else {
            rl_onday_day.setBackgroundResource(0);
            setDayColor();
        }
//        onday_dayRl.setBackgroundColor(color);
    }

    public void setItem(boolean isVisible) {
        if(isVisible) {
            onday_item.setVisibility(VISIBLE);
        } else {
            onday_item.setVisibility(GONE);
        }
    }

    public void setDayText(String text) {
        tv_day.setText(text);
    }
    
    public void setDayClickListener(OnDayClickListener onDayClickListener) {
    	this.onDayClickListener = onDayClickListener;
    }

    private void setDayColor() {
        if(one.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            switch (one.getDayType()) {
                case OneDayData.DAY_TYPE_PREVIOUS :
                    tv_day.setTextColor(0x4D3273AC);
                    break;
                case OneDayData.DAY_TYPE_NONE :
                    tv_day.setTextColor(0xFF3273AC);
                    break;
                case OneDayData.DAY_TYPE_NEXT :
                    tv_day.setTextColor(0x4D3273AC);
                    break;
            }


        } else if(one.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {

            switch (one.getDayType()) {
                case OneDayData.DAY_TYPE_PREVIOUS:
                    tv_day.setTextColor(0x4DDE5757);
                    break;
                case OneDayData.DAY_TYPE_NONE:
                    tv_day.setTextColor(0xFFDE5757);
                    break;
                case OneDayData.DAY_TYPE_NEXT:
                    tv_day.setTextColor(0x4DDE5757);
                    break;
            }
        } else {
            switch (one.getDayType()) {
                case OneDayData.DAY_TYPE_PREVIOUS:
                    tv_day.setTextColor(0x4D575656);
                    break;
                case OneDayData.DAY_TYPE_NONE:
                    tv_day.setTextColor(0xFF575656);
                    break;
                case OneDayData.DAY_TYPE_NEXT:
                    tv_day.setTextColor(0x4D575656);
                    break;
            }
        }
    }
    
    /**
     * Updates UI upon the value object.
     */
    public void refresh() {
//    	Log.i(TAG, "refresh()");

        rl_onday_day.setBackgroundResource(0);
        setDayColor();
    	
    	/**
    	 * 항목의 id값을 해당 날짜로 설정
    	 * 항목을 클릭 하였을 때 이벤트 발생
    	 */

        date = (one.get(Calendar.YEAR) * 10000)
                + ((one.get(Calendar.MONTH) + 1) * 100) + (one.get(Calendar.DAY_OF_MONTH));
        final int current = Integer.parseInt(DateFormat.getCurrentDate("yyyyMMdd"));

    	oneday_topGroup.setId(date);
    	
    	oneday_topGroup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Log.i("test", "id: " + v.getId());
				if(onDayClickListener != null)
//
//                    ComLog.d("date: " + date);
//                ComLog.d("calType: " + calType);
//                ComLog.d("current: " + current);

                    switch (calType) {
                        case 0 :
                            //default
                            onDayClickListener.onClick(v);
                            break;
                        case 1 :
                            //not past
                            if(current >  date) {
                                Toast.makeText(getContext(), "과거일자는 선택할 수 없습니다.", Toast.LENGTH_SHORT).show();
                            } else if(current == date) {
                                Toast.makeText(getContext(), "오늘은 선택할 수 없습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                onDayClickListener.onClick(v);
                            }
                            break;
                        case 2:
                            //not future
                            // 20191204 <= 20191203
                            if(current <  date) {
                                Toast.makeText(getContext(), "미래일자는 선택할 수 없습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                onDayClickListener.onClick(v);
                            }
                            break;
                    }

//					onDayClickListener.onClick(v.getId());
			}
		});
		
    	
        tv_day.setText(String.valueOf(one.get(Calendar.DAY_OF_MONTH)));
        setDayColor();



        if(one.getIsFocus()) {
            tv_day.setText("djal");
        }
        
        LinearLayout linear = null;
        ArrayList<LinearLayout> linears = new ArrayList<LinearLayout>();
        if(ll_onday_day != null)
        	ll_onday_day.removeAllViews();


        for(int i=linears.size()-1; i>-1; i--)
        	ll_onday_day.addView(linears.get(i));
        
    }
    
}