package com.android.graphplay;

import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.graphplay.databinding.ActivityFillGraphBinding;
import com.android.graphplay.widget.FillGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class FillGraphActivity extends AppCompatActivity {

    private ActivityFillGraphBinding binding;


   private int graphColor1 = R.color.graphColor1;
   private int graphColor2 = R.color.graphColor2;


//    private Runnable updateRunnable = new Runnable() {
//        @Override
//        public void run() {
//            Date date = Calendar.getInstance().getTime();
//            binding.graph.appendValue(new FillGraph.GraphValue(date.getTime(), Math.random() * 1000));
//            binding.graph.postDelayed(updateRunnable, (long) (1500 + 1000 * Math.random()));
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fill_graph);

        ArrayList<ArrayList<FillGraph.GraphValue>> dataList= new ArrayList<>();
        ArrayList<FillGraph.GraphValue> nestedList= new ArrayList<>();
        nestedList.add(new FillGraph.GraphValue(100.0f, 150, graphColor1));
        nestedList.add(new FillGraph.GraphValue(200.0f, 350, graphColor1));
        nestedList.add(new FillGraph.GraphValue(300.0f, 550, graphColor1));
        nestedList.add(new FillGraph.GraphValue(400.0f, 450, graphColor1));
        nestedList.add(new FillGraph.GraphValue(500.0f, 350, graphColor1));

        ArrayList<FillGraph.GraphValue> nestedList1= new ArrayList<>();
        nestedList1.add(new FillGraph.GraphValue(140.0f, 250, graphColor2));
        nestedList1.add(new FillGraph.GraphValue(250.0f, 450, graphColor2));
        nestedList1.add(new FillGraph.GraphValue(300.0f, 100, graphColor2));
        nestedList1.add(new FillGraph.GraphValue(440.0f, 150, graphColor2));
        nestedList1.add(new FillGraph.GraphValue(590.0f, 200, graphColor2));
        dataList.add(nestedList);
        dataList.add(nestedList1);

        binding.graph.setGraphPaint1(graphColor1);
        binding.graph.setGraphPaint2(graphColor2);

        binding.graph.setData(dataList);

    }


    @Override
    protected void onResume() {
        super.onResume();
//        binding.graph.postDelayed(updateRunnable, 2000);

    }

    @Override
    protected void onPause() {
        super.onPause();
//        binding.graph.removeCallbacks(updateRunnable);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }


}
