package com.lebron.carrot.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lebron.carrot.R;
import com.lebron.carrot.bean.Temperature;
import com.lebron.carrot.imple.ModelVolley;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ColumnFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ColumnFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ColumnFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Activity activity_main;
    private ModelVolley modelVolley;
    //json解析得来的数据
    private List<Temperature> listData;
    //柱状图View
    private ColumnChartView columnChartView;
    //包含所有柱状图图数据的data
    private ColumnChartData data;
    public ColumnFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ColumnFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ColumnFragment newInstance(String param1, String param2) {
        ColumnFragment fragment = new ColumnFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private void generateDefaultData(){
        int numSubcolumns = 1;
        int numColumns = 8;
        List<Column> columnList = new ArrayList<>();
        List<SubcolumnValue> subcolumnList;
        for (int i = 0; i < numColumns; i++) {
            subcolumnList = new ArrayList<>();
            for (int j = 0; j < numSubcolumns; j++) {
                subcolumnList.add(new SubcolumnValue((float)Math.random() * 50f + 5, ChartUtils.pickColor()));
            }
            Column column = new Column(subcolumnList);
            column.setHasLabels(true);
            column.setHasLabelsOnlyForSelected(true);
            columnList.add(column);
        }
        data = new ColumnChartData(columnList);
        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true);
        axisX.setName("Axis X");
        axisY.setName("Axis Y");
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        columnChartView.setColumnChartData(data);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_column, container, false);
        columnChartView = (ColumnChartView)view.findViewById(R.id.column_chart);
        generateDefaultData();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
}
