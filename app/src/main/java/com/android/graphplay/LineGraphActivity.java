package com.android.graphplay;

import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.graphplay.databinding.ActivityLineGraphBinding;
import com.android.graphplay.databinding.ActivityLineGraphBinding;
import com.android.graphplay.widget.LineGraph;
import com.android.graphplay.widget.LineGraph;

import java.util.ArrayList;

public class LineGraphActivity extends AppCompatActivity {
    
    private ActivityLineGraphBinding binding;


    private int graphColor1 ;
    private int graphColor2 ;



//    private Runnable updateRunnable = new Runnable() {
//        @Override
//        public void run() {
//            Date date = Calendar.getInstance().getTime();
//            binding.graph.appendValue(new LineGraph.GraphValue(date.getTime(), Math.random() * 1000));
//            binding.graph.postDelayed(updateRunnable, (long) (1500 + 1000 * Math.random()));
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_line_graph);

        graphColor1= ContextCompat.getColor(this,R.color.graphColor1);
        graphColor2=ContextCompat.getColor(this,R.color.graphColor2);

        ArrayList<ArrayList<LineGraph.GraphValue>> dataList= new ArrayList<>();
        ArrayList<LineGraph.GraphValue> nestedList= new ArrayList<>();
        nestedList.add(new LineGraph.GraphValue(100.0f, 150,graphColor1));
        nestedList.add(new LineGraph.GraphValue(200.0f, 350,graphColor1));
        nestedList.add(new LineGraph.GraphValue(300.0f, 550,graphColor1));
        nestedList.add(new LineGraph.GraphValue(400.0f, 450,graphColor1));
        nestedList.add(new LineGraph.GraphValue(500.0f, 350,graphColor1));

        ArrayList<LineGraph.GraphValue> nestedList1= new ArrayList<>();
        nestedList1.add(new LineGraph.GraphValue(140.0f, 250,graphColor2 ));
        nestedList1.add(new LineGraph.GraphValue(250.0f, 450,graphColor2 ));
        nestedList1.add(new LineGraph.GraphValue(300.0f, 100,graphColor2));
        nestedList1.add(new LineGraph.GraphValue(440.0f, 150,graphColor2));
        nestedList1.add(new LineGraph.GraphValue(590.0f, 200,graphColor2));
        dataList.add(nestedList);
        dataList.add(nestedList1);

        binding.graph.showOverLayLable(true);
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
