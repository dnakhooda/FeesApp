package com.example.feesapp.ui.overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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