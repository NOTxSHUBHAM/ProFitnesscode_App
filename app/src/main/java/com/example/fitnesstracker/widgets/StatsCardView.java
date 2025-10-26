package com.example.profitness.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.profitness.R;

public class StatsCardView extends LinearLayout {

    private TextView tvValue;
    private TextView tvLabel;
    private TextView tvUnit;

    public StatsCardView(Context context) {
        super(context);
        init(context);
    }

    public StatsCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StatsCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_stats_card, this, true);
        tvValue = findViewById(R.id.tv_value);
        tvLabel = findViewById(R.id.tv_label);
        tvUnit = findViewById(R.id.tv_unit);
    }

    public void setValue(String value) {
        tvValue.setText(value);
    }

    public void setLabel(String label) {
        tvLabel.setText(label);
    }

    public void setUnit(String unit) {
        tvUnit.setText(unit);
    }

    public void setValueColor(int color) {
        tvValue.setTextColor(color);
    }
}