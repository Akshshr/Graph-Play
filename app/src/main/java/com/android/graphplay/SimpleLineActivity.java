package com.android.graphplay;

import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.graphplay.databinding.ActivitySimpleLineBinding;
import com.android.graphplay.widget.FillGraph;
import com.android.graphplay.widget.SimpleLineGraph;

import java.util.ArrayList;

public class SimpleLineActivity extends AppCompatActivity {

    private ActivitySimpleLineBinding binding;

    private int graphColor1 ;
    private int graphColor2 ;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = DataBindingUtil.setContentView(this, R.layout.activity_simple_line);

        graphColor1= ContextCompat.getColor(this,R.color.graphColor1);
        graphColor1=ContextCompat.getColor(this,R.color.graphColor2);

            ArrayList<ArrayList<SimpleLineGraph.GraphValue>> dataList= new ArrayList<>();
            ArrayList<SimpleLineGraph.GraphValue> nestedList= new ArrayList<>();
            nestedList.add(new SimpleLineGraph.GraphValue(100.0f, 150,graphColor1));
            nestedList.add(new SimpleLineGraph.GraphValue(200.0f, 350, graphColor1));
            nestedList.add(new SimpleLineGraph.GraphValue(300.0f, 550, graphColor1));
            nestedList.add(new SimpleLineGraph.GraphValue(400.0f, 450, graphColor1));
            nestedList.add(new SimpleLineGraph.GraphValue(500.0f, 350, graphColor1));
            nestedList.add(new SimpleLineGraph.GraphValue(620.0f, 150, graphColor1));
            nestedList.add(new SimpleLineGraph.GraphValue(700.0f, 350, graphColor1));

            ArrayList<SimpleLineGraph.GraphValue> nestedList1= new ArrayList<>();
            nestedList1.add(new SimpleLineGraph.GraphValue(140.0f, 250, graphColor2));
            nestedList1.add(new SimpleLineGraph.GraphValue(250.0f, 550, graphColor2));
            nestedList1.add(new SimpleLineGraph.GraphValue(300.0f, 200, graphColor2));
            nestedList1.add(new SimpleLineGraph.GraphValue(440.0f, 450, graphColor2));
            nestedList1.add(new SimpleLineGraph.GraphValue(690.0f, 100, graphColor2));
            dataList.add(nestedList);
            dataList.add(nestedList1);

            binding.graph.showOverLayLable(true);
            binding.graph.setData(dataList);


    }
}
