package com.example.feesapp.ui.overview;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.feesapp.Fee;
import com.example.feesapp.MainActivity;
import com.example.feesapp.R;
import com.example.feesapp.databinding.FragmentOverviewBinding;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;

public class OverviewFragment extends Fragment {

    private FragmentOverviewBinding binding;
    private double[][] overviewCostTable;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get Overview View Model
        OverviewViewModel viewModel = new ViewModelProvider(this).get(OverviewViewModel.class);

        binding = FragmentOverviewBinding.inflate(inflater, container, false);
        MainActivity.instance.bringBackViewBar();
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        overviewCostTable = new double[MainActivity.instance.getFees().size()][4];
        calculateOverviewCostTable(overviewCostTable);
        makeTable();
        updateOverviewTable(binding);

        PieChart pieChart = binding.categoryPieChart;
        pieChart.addPieSlice(new PieModel(Fee.changeFeeCategoryToString(Fee.FeesCategory.InsuranceFee), findPercentageByCategory(Fee.FeesCategory.InsuranceFee), Color.parseColor("#FE6DA8")));
        pieChart.addPieSlice(new PieModel(Fee.changeFeeCategoryToString(Fee.FeesCategory.RentOrPropertyTaxFee), findPercentageByCategory(Fee.FeesCategory.RentOrPropertyTaxFee), Color.parseColor("#56B7F1")));
        pieChart.addPieSlice(new PieModel(Fee.changeFeeCategoryToString(Fee.FeesCategory.MembershipFee), findPercentageByCategory(Fee.FeesCategory.MembershipFee), Color.parseColor("#CDA67F")));
        pieChart.addPieSlice(new PieModel(Fee.changeFeeCategoryToString(Fee.FeesCategory.ServiceFee), findPercentageByCategory(Fee.FeesCategory.ServiceFee), Color.parseColor("#FED70E")));
        pieChart.addPieSlice(new PieModel(Fee.changeFeeCategoryToString(Fee.FeesCategory.OtherFee), findPercentageByCategory(Fee.FeesCategory.OtherFee), Color.parseColor("#71c25f")));
        pieChart.startAnimation();

        binding.overviewLinearLayout.post(() -> {
            binding.overviewLinearLayout.setMinimumHeight(((ScrollView)binding.overviewLinearLayout.getParent()).getHeight());
        });
    }

    public int findPercentageByCategory(Fee.FeesCategory feesCategory) {
        ArrayList<Fee> fees = MainActivity.instance.getFees();
        double total = 0;
        double totalCategory = 0;
        for (int i = 0; i < fees.size(); i++) {
            if (fees.get(i).getCategory().equals(feesCategory))
                totalCategory += fees.get(i).getDailyAmount();
            total += fees.get(i).getDailyAmount();
        }
        return (int) ((totalCategory / total) * 100);
    }

    private void calculateOverviewCostTable(double[][] overviewCostTable) {
        for (int i = 0; i < MainActivity.instance.getFees().size(); i++) {
            for (int j = 0; j < 4; j++) {
                overviewCostTable[i][j] = Fee.roundToTwoDecimalPlaces(MainActivity.instance.getFees().get(i).getDailyAmount() * (Math.pow(10, j)));
            }
        }
    }

    private void makeTable() {
        TableLayout table = binding.overviewCostTable;
        for (int i = 0; i < MainActivity.instance.getFees().size(); i++) {
            LayoutInflater inflater = MainActivity.instance.getLayoutInflater();
            View inflatedLayout = inflater.inflate(R.layout.overview_table_views, table, false);
            table.addView(inflatedLayout);
        }
    }

    private void updateOverviewTable(FragmentOverviewBinding binding) {
        for (int i = 1; (i < binding.overviewCostTable.getChildCount()) && (i <= overviewCostTable.length); i++) {
            TableRow row = (TableRow) binding.overviewCostTable.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                TextView textView = (TextView) row.getChildAt(j);
                if (j == 0) {
                    textView.setText(MainActivity.instance.getFees().get(i-1).getTitle());
                }
                else {
                    textView.setText(Fee.numberToStringWithTwoDecimals(overviewCostTable[i-1][j-1]));
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}