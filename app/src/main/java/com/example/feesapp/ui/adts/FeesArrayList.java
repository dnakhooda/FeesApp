package com.example.feesapp.ui.adts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.feesapp.Fee;
import com.example.feesapp.MainActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

public class FeesArrayList extends ArrayList<Fee> {

    public FeesArrayList() {super();}

    // Override Existing Methods To Save When Fee Is Added/Removed
    @Override
    public void add(int index, Fee element) {
        super.add(index, element);
        MainActivity.instance.getStorageHandler().saveFees();
    }

    @Override
    public boolean add(Fee fee) {
        boolean toReturn = super.add(fee);
        MainActivity.instance.getStorageHandler().saveFees();
        return toReturn;
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends Fee> c) {
        boolean toReturn = super.addAll(c);
        MainActivity.instance.getStorageHandler().saveFees();
        return toReturn;
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends Fee> c) {
        boolean toReturn = super.addAll(index, c);
        MainActivity.instance.getStorageHandler().saveFees();
        return toReturn;
    }

    @Override
    public Fee remove(int index) {
        Fee toReturn = super.remove(index);
        MainActivity.instance.getStorageHandler().saveFees();
        return toReturn;
    }

    @Override
    public boolean remove(@Nullable Object o) {
        boolean toReturn = super.remove(o);
        MainActivity.instance.getStorageHandler().saveFees();
        return toReturn;
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        super.removeRange(fromIndex, toIndex);
        MainActivity.instance.getStorageHandler().saveFees();
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        boolean toReturn = super.removeAll(c);
        MainActivity.instance.getStorageHandler().saveFees();
        return toReturn;
    }

    @Override
    public boolean removeIf(@NonNull Predicate<? super Fee> filter) {
        boolean toReturn = super.removeIf(filter);
        MainActivity.instance.getStorageHandler().saveFees();
        return toReturn;
    }

    public Fee getFeeByTitle(String title) {
        for (int i = 0; i < size(); i++) {
            if (get(i).getTitle().equals(title))
                return get(i);
        }
        return null;
    }

    public Fee getFeeByTitleExcluding(String title, Fee fee) {
        for (int i = 0; i < size(); i++) {
            if (get(i).getTitle().equals(title) && !get(i).equals(fee))
                return get(i);
        }
        return null;
    }

    public void removeFeeByTitle(String title) {
        for (int i = 0; i < size(); i++) {
            if (get(i).getTitle().equals(title)) {
                remove(i);
                MainActivity.instance.getStorageHandler().saveFees();
                return;
            }
        }
    }

}
