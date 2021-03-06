package com.disc;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  @RequiresApi(api = Build.VERSION_CODES.M)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    LinearLayout linearLayout = findViewById(R.id.ll_container);
    List<DataItem> items = new ArrayList<>();
    items.add(new DataItem(1, "百度", "26.56", getColor(R.color.red)));
    items.add(new DataItem(1, "腾讯", "32.45", getColor(R.color.green)));
    items.add(new DataItem(1, "美团", "12.36", getColor(R.color.red)));
    items.add(new DataItem(23, "Google", "56.23", getColor(R.color.black)));
    items.add(new DataItem(1, "沃尔玛", "45.56", getColor(R.color.red)));
    items.add(new DataItem(35, "阿里巴巴", "45.56", getColor(R.color.blue)));
    items.add(new DataItem(2, "华为", "45.56", getColor(R.color.black)));
    items.add(new DataItem(3, "斗鱼", "45.56", getColor(R.color.blue)));
    items.add(new DataItem(4, "虎牙", "45.56", getColor(R.color.yellow)));
    items.add(new DataItem(24, "京东", "35.56", getColor(R.color.green)));
    items.add(new DataItem(23, "Windows", "37.25", getColor(R.color.yellow)));
    items.add(new DataItem(12, "头条", "334.25", getColor(R.color.blue)));
    items.add(new DataItem(13, "IBM", "37.25", getColor(R.color.yellow)));
    items.add(new DataItem(2, "甲骨文", "30.25", getColor(R.color.yellow)));
    DiscView discView = new DiscView(linearLayout.getContext());
    discView.setItems(new ArrayList<>(items));
    linearLayout.addView(discView);
    Collections.sort(items, new Comparator<DataItem>() {
      @Override
      public int compare(DataItem o1, DataItem o2) {
        return o2.getValue() - o1.getValue();
      }
    });
    DiscView discView1 = new DiscView(linearLayout.getContext());
    discView1.setItems(new ArrayList<>(items));
    linearLayout.addView(discView1);
    Collections.sort(items, new Comparator<DataItem>() {
      @Override
      public int compare(DataItem o1, DataItem o2) {
        return o1.getValue() - o2.getValue();
      }
    });
    DiscView discView2 = new DiscView(linearLayout.getContext());
    discView2.setItems(new ArrayList<>(items));
    linearLayout.addView(discView2);
  }
}
