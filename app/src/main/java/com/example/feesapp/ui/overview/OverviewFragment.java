package com.example.feesapp.ui.overview;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.feesapp.Fee;
import com.example.feesapp.MainActivity;
import com.example.feesapp.R;
import com.example.feesapp.databinding.FragmentOverviewBinding;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;

public class OverviewFragment extends Fragment {

    private FragmentOverviewBinding binding;
    private MainActivity mainActivity;
    private double[][] costTable;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get Binding
        binding = FragmentOverviewBinding.inflate(inflater, container, false);

        // Set Main Activity Reference & Add Navigation Bar
        mainActivity = MainActivity.instance;
        mainActivity.bringBackNavBar();

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        // Create 2D Array Of Fees Costs By Days
        costTable = new double[mainActivity.getFees().size()][4];

        // Inflate Layouts To Make Rows For Fees
        inflateRows();

        // Calculate Costs In 2d Array
        calculateCostTable();

        // Update Table With Costs In 2d Array
        updateOverviewTable();

        // Add Pie Chart Categories
        PieChart pieChart = binding.categoryPieChart;
        pieChart.addPieSlice(new PieModel(Fee.changeFeeCategoryToString(Fee.Category.InsuranceFee),
                categoryPercentage(Fee.Category.InsuranceFee), Color.parseColor("#FE6DA8")));
        pieChart.addPieSlice(new PieModel(Fee.changeFeeCategoryToString(Fee.Category.RentOrPropertyTaxFee),
                categoryPercentage(Fee.Category.RentOrPropertyTaxFee), Color.parseColor("#56B7F1")));
        pieChart.addPieSlice(new PieModel(Fee.changeFeeCategoryToString(Fee.Category.MembershipFee),
                categoryPercentage(Fee.Category.MembershipFee), Color.parseColor("#CDA67F")));
        pieChart.addPieSlice(new PieModel(Fee.changeFeeCategoryToString(Fee.Category.ServiceFee),
                categoryPercentage(Fee.Category.ServiceFee), Color.parseColor("#FED70E")));
        pieChart.addPieSlice(new PieModel(Fee.changeFeeCategoryToString(Fee.Category.OtherFee),
                categoryPercentage(Fee.Category.OtherFee), Color.parseColor("#71c25f")));
        pieChart.startAnimation();
    }

    private void inflateRows() {
        TableLayout table = binding.overviewCostTable;

        // Inflate Rows For The Number Of Fees
        for (int i = 0; i < mainActivity.getFees().size(); i++) {
            LayoutInflater inflater = mainActivity.getLayoutInflater();
            View inflatedLayout = inflater.inflate(R.layout.item_overview_table_row, table, false);
            table.addView(inflatedLayout);
        }
    }

    private void calculateCostTable() {
        // Calculate Costs (In 1 Day & 10 Days & 100 Days & 1000 Days) In 2d Array
        for (int i = 0; i < mainActivity.getFees().size(); i++) {
            for (int j = 0; j < 4; j++) {
                costTable[i][j] = Fee.roundToTwoDecimalPlaces(mainActivity.getFees().get(i).getDailyAmount() * (Math.pow(10, j)));
            }
        }
    }

    private void updateOverviewTable() {
        // Go Through Each Element And Fill With Information From 2d Array
        for (int i = 1; (i < binding.overviewCostTable.getChildCount()) && (i <= costTable.length); i++) {
            TableRow row = (TableRow) binding.overviewCostTable.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                TextView textView = (TextView) row.getChildAt(j);
                if (j == 0)
                    textView.setText(mainActivity.getFees().get(i-1).getTitle());
                else
                    textView.setText(Fee.numberToStringWithTwoDecimals(costTable[i-1][j-1]));
            }
        }
    }

    private int categoryPercentage(Fee.Category feesCategory) {
        ArrayList<Fee> fees = mainActivity.getFees();

        double total = 0;
        double totalByCategory = 0;

        // Find Total And Total By Categories
        for (int i = 0; i < fees.size(); i++) {
            if (fees.get(i).getCategory().equals(feesCategory))
                totalByCategory += fees.get(i).getDailyAmount();
            total += fees.get(i).getDailyAmount();
        }

        // Divide Total By Category From Total To Get What Percentage Of Total A Category Is
        return (int) ((totalByCategory / total) * 100);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}