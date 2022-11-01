package com.pcsoft.calendartest.model;

import android.content.Context;
import android.graphics.Color;

import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pcsoft.calendartest.R;

import java.time.Month;
import java.util.Calendar;

public class SimpleCalendar extends LinearLayout {

    private static final String CUSTOM_GREY = "#a0a0a0";

    private TextView currentDate;
    private TextView currentMonth;
    private TextView currentYear;

    private Button selectedDayButton;
    private Button[] days;

    LinearLayout weekOneLayout;
    LinearLayout weekTwoLayout;
    LinearLayout weekThreeLayout;
    LinearLayout weekFourLayout;
    LinearLayout weekFiveLayout;
    LinearLayout weekSixLayout;
    private LinearLayout[] weeks;
    int[] dateArr = new int[3];

    private int currentDateDay;
    private int currentDateMonth;
    private int currentDateYear;

    private int chosenDateDay;
    private int chosenDateMonth;
    private int chosenDateYear;

    private int pickedDateDay;
    private int pickedDateMonth;
    private int pickedDateYear;

    private DayClickListener mListener;

    private Calendar calendar;
    LinearLayout.LayoutParams layoutButtonParams;
    private LinearLayout.LayoutParams userButtonParams;

    public SimpleCalendar(Context context) {
        super(context);
        init(context);
    }

    public SimpleCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SimpleCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.userButtonParams = userButtonParams;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        View view = LayoutInflater.from(context).inflate(R.layout.simple_calendar, this, true);
        layoutButtonParams = getDaysLayoutParams();
        calendar = Calendar.getInstance();
        currentDateDay   = calendar.get(Calendar.DAY_OF_MONTH);
        currentDateMonth = calendar.get(Calendar.MONTH);
        currentDateYear  = calendar.get(Calendar.YEAR);

        initializeViewByIds(view);
        setDateTextToView(currentDate, currentMonth, currentYear);
        configsDayWeekRange();
        addDaysinCalendar(layoutButtonParams, context, metrics);
        initCalendarWithDate(currentDateYear, currentDateMonth, currentDateDay, calendar);

    }

    private void initializeViewByIds(View view) {
        weekOneLayout   = view.findViewById(R.id.calendar_week_1);
        weekTwoLayout   = view.findViewById(R.id.calendar_week_2);
        weekThreeLayout = view.findViewById(R.id.calendar_week_3);
        weekFourLayout  = view.findViewById(R.id.calendar_week_4);
        weekFiveLayout  = view.findViewById(R.id.calendar_week_5);
        weekSixLayout   = view.findViewById(R.id.calendar_week_6);
        currentDate     = view.findViewById(R.id.current_date);
        currentMonth    = view.findViewById(R.id.current_month);
        currentYear     = view.findViewById(R.id.current_year);
    }

    private void configsDayWeekRange() {
        weeks = new LinearLayout[6];
        days  = new Button[6 * 7];
        weeks[0] = weekOneLayout;
        weeks[1] = weekTwoLayout;
        weeks[2] = weekThreeLayout;
        weeks[3] = weekFourLayout;
        weeks[4] = weekFiveLayout;
        weeks[5] = weekSixLayout;
    }

    private void initCalendarWithDate(int year, int month, int day, Calendar calendar) {

        calendar.set(year, month, day);
        int daysInCurrentMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        chosenDateYear = year;
        chosenDateMonth = month;
        chosenDateDay = day;
        calendar.set(year, month, 1);

        int firstDayOfCurrentMonth = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.set(year, month, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        int dayNumber = 1;
        int daysLeftInFirstWeek = 0;
        int indexOfDayAfterLastDayOfMonth = 0;

        if (firstDayOfCurrentMonth != 1) {
            daysLeftInFirstWeek = firstDayOfCurrentMonth;
            indexOfDayAfterLastDayOfMonth = daysLeftInFirstWeek + daysInCurrentMonth;

            for (int i = firstDayOfCurrentMonth; i < firstDayOfCurrentMonth + daysInCurrentMonth; ++i) {
                if (dayNumber == day) {
                    days[i].setBackgroundColor(getResources().getColor(R.color.pink));
                    days[i].setTextColor(Color.WHITE);
                } else {
                    days[i].setTextColor(Color.BLACK);
                    days[i].setBackgroundColor(Color.TRANSPARENT);
                }
                dateArr[0] = dayNumber;
                dateArr[1] = month;
                dateArr[2] = year;
                days[i].setTag(dateArr);
                days[i].setText(String.valueOf(dayNumber));
                days[i].setOnClickListener(v -> onDayClick(v));
                ++dayNumber;
            }
        } else {
            daysLeftInFirstWeek = 8;
            indexOfDayAfterLastDayOfMonth = daysLeftInFirstWeek + daysInCurrentMonth;

            for (int i = 8; i < 8 + daysInCurrentMonth; ++i) {

                if (dayNumber == currentDateDay) {
                    days[i].setBackgroundColor(getResources().getColor(R.color.pink));
                    days[i].setTextColor(Color.WHITE);
                } else {
                    days[i].setTextColor(Color.BLACK);
                    days[i].setBackgroundColor(Color.TRANSPARENT);
                }
                dateArr[0] = dayNumber;
                dateArr[1] = month;
                dateArr[2] = year;
                days[i].setTag(dateArr);
                days[i].setText(String.valueOf(dayNumber));
                days[i].setOnClickListener(v -> onDayClick(v));
                ++dayNumber;
            }
        }
        if (month > 0)
            calendar.set(year, month - 1, 1);
        else
            calendar.set(year - 1, 11, 1);
        int daysInPreviousMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = daysLeftInFirstWeek - 1; i >= 0; --i) {

            if (month > 0) {
                if (daysInPreviousMonth == currentDateDay) {
                } else {
                    days[i].setBackgroundColor(Color.TRANSPARENT);
                }
                dateArr[0] = daysInPreviousMonth;
                dateArr[1] = month - 1;
                dateArr[2] = year;

            } else {
                if (daysInPreviousMonth == currentDateDay) {
                } else {
                    days[i].setBackgroundColor(Color.TRANSPARENT);
                }
                dateArr[0] = daysInPreviousMonth;
                dateArr[1] = 11;
                dateArr[2] = year - 1;
            }
            days[i].setTag(dateArr);
            days[i].setText(String.valueOf(daysInPreviousMonth--));
            days[i].setOnClickListener(v -> onDayClick(v));
        }

        int nextMonthDaysCounter = 1;
        for (int i = indexOfDayAfterLastDayOfMonth; i < days.length; ++i) {

            if (month < 11) {
                if (nextMonthDaysCounter == day) {
                    days[i].setBackgroundColor(getResources().getColor(R.color.pink));
                } else {
                    days[i].setBackgroundColor(Color.TRANSPARENT);
                }
                dateArr[0] = nextMonthDaysCounter;
                dateArr[1] = month + 1;
                dateArr[2] = year;

            } else {
                if (nextMonthDaysCounter == currentDateDay) {
                    days[i].setBackgroundColor(getResources().getColor(R.color.pink));
                } else {
                    days[i].setBackgroundColor(Color.TRANSPARENT);
                }
                dateArr[0] = nextMonthDaysCounter;
                dateArr[1] = 0;
                dateArr[2] = year + 1;
            }
            days[i].setTag(dateArr);
            days[i].setTextColor(Color.parseColor(CUSTOM_GREY));
            days[i].setText(String.valueOf(nextMonthDaysCounter++));
            days[i].setOnClickListener(v -> onDayClick(v));
        }

    }

    public void onDayClick(View view) {
        mListener.onDayClick(view);

        if (selectedDayButton != null) {
            if (chosenDateYear == currentDateYear
                    && chosenDateMonth == currentDateMonth
                    && pickedDateDay == currentDateDay) {
                selectedDayButton.setBackgroundColor(getResources().getColor(R.color.pink));
                selectedDayButton.setTextColor(Color.WHITE);
            } else {
                selectedDayButton.setBackgroundColor(Color.TRANSPARENT);
                if (selectedDayButton.getCurrentTextColor() != Color.RED) {
                    selectedDayButton
                            .setTextColor(getResources()
                            .getColor(R.color.calendar_number));
                }
            }
        }

        selectedDayButton = (Button) view;
        if (selectedDayButton.getTag() != null) {
            int[] dateArray = (int[]) selectedDayButton.getTag();
            pickedDateDay = dateArray[0];
            pickedDateMonth = dateArray[1];
            pickedDateYear = dateArray[2];
        }

        if (pickedDateYear == currentDateYear
                && pickedDateMonth == currentDateMonth
                && pickedDateDay == currentDateDay) {
            selectedDayButton.setBackgroundColor(getResources().getColor(R.color.pink));
            selectedDayButton.setTextColor(Color.WHITE);
        } else {
            selectedDayButton.setBackgroundColor(getResources().getColor(R.color.grey));
            if (selectedDayButton.getCurrentTextColor() != Color.RED) {
                selectedDayButton.setTextColor(Color.WHITE);
            }
        }
    }

    private void addDaysinCalendar(LayoutParams buttonParams,
                                   Context context,
                                   DisplayMetrics metrics) {
        int engDaysArrayCounter = 0;

        for (int weekNumber = 0; weekNumber < 6; ++weekNumber) {
            for (int dayInWeek = 0; dayInWeek < 7; ++dayInWeek) {
                final Button day = new Button(context);
                day.setTextColor(Color.parseColor(CUSTOM_GREY));
                day.setBackgroundColor(Color.TRANSPARENT);
                day.setLayoutParams(buttonParams);
                day.setTextSize((int) metrics.density * 8);
                day.setSingleLine();

                days[engDaysArrayCounter] = day;
                weeks[weekNumber].addView(day);

                ++engDaysArrayCounter;
            }
        }
    }

    private LayoutParams getDaysLayoutParams() {
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        buttonParams.weight = 1;
        return buttonParams;
    }

    private void setDateTextToView(TextView currentDate, TextView currentMonth, TextView currentYear) {
        currentDate.setText("" + currentDateDay);
        currentMonth.setText((Month.of(currentDateMonth + 1)).toString());
        currentYear.setText("" + currentDateYear);
    }

    public void setUserDaysLayoutParams(LinearLayout.LayoutParams userButtonParams) {
    }

    
//    public static class OnSwipeTouchListener implements View.OnTouchListener {
//
//        private final GestureDetector gestureDetector;
//
//        public OnSwipeTouchListener (Context ctx){
//            gestureDetector = new GestureDetector(ctx, new GestureListener());
//        }
//
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//            return gestureDetector.onTouchEvent(event);
//        }
//
//        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
//
//            private static final int SWIPE_THRESHOLD = 100;
//            private static final int SWIPE_VELOCITY_THRESHOLD = 100;
//
//            @Override
//            public boolean onDown(MotionEvent e) {
//                return true;
//            }
//
//            @Override
//            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//                boolean result = false;
//                try {
//                    float diffY = e2.getY() - e1.getY();
//                    float diffX = e2.getX() - e1.getX();
//                    if (Math.abs(diffX) > Math.abs(diffY)) {
//                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
//                            if (diffX > 0) {
//
//                            } else {
//
//                            }
//                            result = true;
//                        }
//                    }
//                    else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
//                        if (diffY > 0) {
//
//                        } else {
//
//                        }
//                        result = true;
//                    }
//                } catch (Exception exception) {
//                    exception.printStackTrace();
//                }
//                return result;
//            }
//        }
//    }
//
//    public void onSwipeRight() {
//        this.month = month + 1;
//    }
//
//    public void onSwipeLeft() {
//        this.month = month - 1;
//    }
//
//    public void onSwipeTop() {
//        this.year = year - 1;
//    }
//
//    public void onSwipeBottom() {
//        this.year = year + 1;
//    }

    public interface DayClickListener {
        void onDayClick(View view);
    }

    public void setCallBack(DayClickListener mListener) {
        this.mListener = mListener;
    }

}
