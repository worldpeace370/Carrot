package com.lebron.carrot.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lebron.carrot.R;

/**
 * 日期时间选择控件 使用方法： private TextView inputDate;//需要设置的日期时间文本编辑框 private String
 * initDateTime="2012年9月3日 14:44",//初始日期时间值 在点击事件中使用：
 * inputDate.setOnClickListener(new OnClickListener() {
 *
 * @Override public void onClick(View v) { DateTimePickDialogUtil
 *           dateTimePicKDialog=new
 *           DateTimePickDialogUtil(Activity.this,initDateTime);
 *           dateTimePicKDialog.dateTimePicKDialog(inputDate);
 *
 *           } });
 *
 * @author
 */
public class DatePickDialogView implements OnDateChangedListener{
	private DatePicker datePicker;
	private AlertDialog ad;
	private String dateTime;
	private String initDateTime;
	private Activity activity;
	private OnDatePickerChoicedListener listener;

	public void setOnDatePickerChoicedListener(OnDatePickerChoicedListener listener){
		this.listener = listener;
	}
	/**
	 * 日期时间弹出选择框构造函数
	 *
	 * @param activity
	 *            ：调用的父activity
	 *
	 */
	public DatePickDialogView(Activity activity) {
		this.activity = activity;
	}

	public void init(DatePicker datePicker) {
		Calendar calendar = Calendar.getInstance();
		if (!(null == initDateTime || "".equals(initDateTime))) {
			calendar = this.getCalendarByInitData(initDateTime);
		} else {
			initDateTime = calendar.get(Calendar.YEAR) + "-"
					+ calendar.get(Calendar.MONTH) + "-"
					+ calendar.get(Calendar.DAY_OF_MONTH);
		}

		datePicker.init(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), this);
	}

	/**
	 * 弹出日期时间选择框方法
	 *
	 * @param textView
	 *            :为需要设置的日期时间文本编辑框
	 * @return
	 */
	public AlertDialog dateTimePicKDialog(final TextView textView, String textViewDateTime) {
		this.initDateTime = textViewDateTime;
		LinearLayout dateTimeLayout = (LinearLayout) activity
				.getLayoutInflater().inflate(R.layout.item_datepicker, null);
		datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datePicker);
		init(datePicker);

		ad = new AlertDialog.Builder(activity)
				.setTitle(initDateTime)
				.setView(dateTimeLayout)
				.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						textView.setText(dateTime);
						listener.onDatePickerChoiced(dateTime);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						textView.setText(initDateTime);
					}
				}).show();

		onDateChanged(null, 0, 0, 0);
		return ad;
	}


	/**
	 * 实现将初始日期时间2012年07月02日 16:45 拆分成年 月 日 时 分 秒,并赋值给calendar
	 *
	 * @param initDateTime
	 *            初始日期时间值 字符串型
	 * @return Calendar
	 */
	private Calendar getCalendarByInitData(String initDateTime) {
		Calendar calendar = Calendar.getInstance();
		String year = initDateTime.substring(0, 4);
		String month = initDateTime.substring(5, 7);
		String day = initDateTime.substring(8,10);
		Log.i("getCalendarByInitData", "getCalendarByInitData: "+ year + ":" + month + ":" + day);
		int currentYear = Integer.valueOf(year.trim()).intValue();
		int currentMonth = Integer.valueOf(month.trim()).intValue()-1;
		int currentDay = Integer.valueOf(day.trim()).intValue();

		calendar.set(currentYear, currentMonth, currentDay);
		return calendar;
	}

	/**
	 * 实现的接口方法
	 * @param view
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 */
	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		// 获得日历实例
		Calendar calendar = Calendar.getInstance();

		calendar.set(datePicker.getYear(), datePicker.getMonth(),
				datePicker.getDayOfMonth());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		dateTime = sdf.format(calendar.getTime());
		ad.setTitle(dateTime);
	}

	public interface OnDatePickerChoicedListener{
		void onDatePickerChoiced(String date);
	}
}
