package com.lebron.carrot.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lebron.carrot.R;
import com.lebron.carrot.bean.Temperature;
import com.lebron.carrot.common.Constant;
import com.lebron.carrot.imple.ModelVolley;
import com.lebron.carrot.interfaces.GetDataListener;
import com.lebron.carrot.view.DatePickDialogView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LineFragment extends Fragment implements GetDataListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Activity activity_main;
    private LineChartView lineChartView;
    //折线图的List,里面可以包含多条线型图
    private List<Line> lineList;
    //包含所有线型图数据的data
    private LineChartData data;
    private ModelVolley modelVolley;
    //json解析得来的数据
    private List<Temperature> listData;
    private View rootView;
    private int currentPage = 1;
    private TextView textView_date;
    private ListView line_listView;
    private Button buttonPre;
    private Button buttonNext;
    private ProgressDialog proDialog;

    public LineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LineFragment newInstance(String param1, String param2) {
        LineFragment fragment = new LineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
        activity_main = getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        modelVolley = new ModelVolley();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null){
            // Inflate the layout for this fragment
            rootView = inflater.inflate(R.layout.fragment_line, container, false);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null){
            parent.removeView(rootView);
        }
        initLineChartView(rootView);
        initButtonPre(rootView);
        initButtonNext(rootView);
        initDateTextView(rootView);
        return rootView;
    }

    /**
     * 前向查询按钮
     * @param view
     */
    private void initButtonPre(View view){
        buttonPre = (Button) view.findViewById(R.id.button_pre);
        buttonPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDataDate(reduceDay(textView_date.getText().toString()));
            }
        });
    }

    private void initButtonNext(View view){
        buttonNext = (Button) view.findViewById(R.id.button_next);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDataDate(addDay(textView_date.getText().toString()));
            }
        });
    }

    private void showProgressDialog(){
        proDialog = ProgressDialog.show(activity_main, "网络请求", "加载中...");
    }

    private void dismissProgressDialog(){
        proDialog.dismiss();
    }

    private void initDateTextView(View view){
        textView_date = (TextView) view.findViewById(R.id.textView_date);
        textView_date.setText("");
        final DatePickDialogView datePickDialogView = new DatePickDialogView(activity_main);
        textView_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datePickDialogView.dateTimePicKDialog(textView_date, textView_date.getText().toString());
            }
        });
        datePickDialogView.setOnDatePickerChoicedListener(new DatePickDialogView.OnDatePickerChoicedListener() {
            @Override
            public void onDatePickerChoiced(String date) {
                loadDataDate(date);
            }
        });
    }

//    private void initListView(View com.lebron.view){
//        line_listView = (ListView) com.lebron.view.findViewById(R.id.line_listView);
//        String[] names = activity_main.getResources().getStringArray(R.array.username);
//        List<String> list = Arrays.asList(names);
//        ListViewAdapter adapter = new ListViewAdapter(list, activity_main);
//        line_listView.setAdapter(adapter);
//    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (listData == null){
            loadDataPage(currentPage);
        }
        initLineGraph();
    }

    /**
     * 根据页数查找
     * @param page
     */
    private void loadDataPage(int page){
        showProgressDialog();
        modelVolley.getDataFromServer(String.format(Constant.urlPageApi, page), this);
    }

    /**
     * 根据日期来查找
     * @param date "2016-03-16" 这种格式的
     */
    private void loadDataDate(String date){
        showProgressDialog();
        modelVolley.getDataFromServer(String.format(Constant.urlDateApi, date), this);
    }

    private void initLineChartView(View view){
        lineChartView = (LineChartView) view.findViewById(R.id.line_chart);
        lineChartView.setInteractive(true);
        lineChartView.setZoomEnabled(true);
        lineChartView.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
        lineChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
    }

    /**
     * 初始化x轴坐标的数字
     * @return
     */
//    private List<AxisValue> getAxisValueList(){
//        List<AxisValue> axisValueList = new ArrayList<>();
//        for (int i = 0; i < 7; i++) {
//            axisValueList.add(new AxisValue(i).setLabel(i+""));
//        }
//        return axisValueList;
//    }
      private List<AxisValue> getAxisValueList(){
        List<AxisValue> axisValueList = new ArrayList<>();
        if (listData != null){
            for (int i = 0; i < listData.size(); i++) {
                axisValueList.add(new AxisValue(i).setLabel(listData.get(i).getCreate_time().substring(10)));
            }
            return axisValueList;
        }else {
            for (int i = 0; i < 7; i++) {
                axisValueList.add(new AxisValue(i).setLabel(""));
            }
            return axisValueList;
        }
      }

    /**
     * 初始化折线图上的每个点
     * @return
     */
    private List<PointValue> getPointValueList(int count){
        List<PointValue> pointValueList = new ArrayList<>();
        PointValue pointValue;
        for (int i = 0; i < count; i++) {
            if (listData != null){
                if (i < listData.size()){
                    pointValue = new PointValue(i, listData.get(i).getValue());
                }else {
                    pointValue = new PointValue(i, 0);
                }
            }else {
                pointValue = new PointValue(i, 0);
            }
            pointValueList.add(pointValue);
        }
        return pointValueList;
    }

    /**
     * 初始化折线图的Line的外形,x轴,y轴
     */
    private void initLineGraph(){
        //线型图集合赋初值
        lineList = new ArrayList<>();

        //In most cased you can call data model methods in builder-pattern-like manner.
        Line line = new Line(getPointValueList(7)).setColor(Color.parseColor("#AFEEEE")).setCubic(false);//setCubic(false)表示折线,true表示曲线
        //line.setShape()设置图形上面点的形状,圆形,方形
        line.setShape(ValueShape.CIRCLE);
        //让图形和X轴围城的阴影部分呈现
        line.setFilled(true);
        //line.setHasLabels(true)设置true在折线图上标注出y轴值的大小,设置false不显示值
        line.setHasLabels(true);
        //line.setHasLabelsOnlyForSelected(false)设置只有当被选中的时候才显示y轴值得大小
        line.setHasLabelsOnlyForSelected(false);
        //line.setHasLines()是否有线,设置false后只有一些数据的点
        line.setHasLines(true);
        //line.setHasPoints(true);设置是否有数据点点,true有点,false后只是一些折线,没有点点
        line.setHasPoints(true);
        //将设置好的线型图加入到lineList中去
        lineList.add(line);

        data = new LineChartData();
        data.setLines(lineList);

        //坐标轴
        Axis axisX = new Axis().setHasLines(true);    //x轴
        //axisX.setHasTiltedLabels(true);让x轴lable变成斜体,false为正常
        axisX.setHasTiltedLabels(false);
        axisX.setTextColor(Color.parseColor("#98FB98"));
        axisX.setName("采集时间/s");
        axisX.setAutoGenerated(true);
        axisX.setMaxLabelChars(0);
        axisX.setValues(getAxisValueList());

        data.setAxisXBottom(axisX);

        Axis axisY = new Axis();    //y轴
        //axisY.setHasLines(true)设置y轴有水平的横线和x轴垂直,这样图形看起来更清晰
        axisY.setHasLines(true);
        axisY.setName("采集值℃");   //y轴坐标名
        //设置y轴到y轴lable的距离
        //axisY.setMaxLabelChars(3);
        axisY.setTextColor(Color.parseColor("#B0C4DE"));
        axisY.setAutoGenerated(true);
        data.setAxisYLeft(axisY);
        //将设置好含有x,y坐标和数据的data放入到LineChartView中去
        lineChartView.setLineChartData(data);
        //设置图形中的值得点击事件
        lineChartView.setOnValueTouchListener(new LineChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
                Toast.makeText(activity_main, "Selected:" + value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValueDeselected() {

            }
        });
        resetViewport();
    }

    /**
     * 对坐标轴进行复位
     */
    private void resetViewport(){
        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.bottom = 0;
//        viewport.top = 10;
        viewport.left = 0;
        viewport.right = 6;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSuccess(String string) {
        dismissProgressDialog();
        if ("[]".equals(string)){
            listData = null;
            Toast.makeText(activity_main, "没有此天的数据", Toast.LENGTH_SHORT).show();
            return;
        }
        listData = modelVolley.getListDataFromJSon(string);
        initLineGraph();
        textView_date.setText(listData.get(0).getCreate_time().substring(0, 10));
    }

    @Override
    public void onError() {
        dismissProgressDialog();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**
     * 在原有字符串日期上增加一天，
     * @param currentDay "2016-01-01"
     * @return "2016-01-02"
     */
    private String addDay(String currentDay){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = new Date(format.parse(currentDay).getTime() + 86400000);
            String afterAddDate = format.format(date);
            return afterAddDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return "2016-01-01";
        }
    }

    /**
     * 在原有字符串日期上减少一天
     * @param currentDay "2016-01-02"
     * @return "2016-01-01"
     */
    private String reduceDay(String currentDay){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = new Date(format.parse(currentDay).getTime() - 86400000);
            String afterAddDate = format.format(date);
            return afterAddDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return "2016-01-01";
        }
    }

}
