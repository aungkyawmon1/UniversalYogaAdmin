package com.example.universalyogaadmin.bottomsheet;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.universalyogaadmin.FilterListener;
import com.example.universalyogaadmin.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class FilterBottomSheet extends BottomSheetDialogFragment {

    View v;

    ImageView ivBack;

    FilterListener listener;

    TextView tvChoose;

    TextInputEditText etDayOfWeek, etDate;

    public FilterBottomSheet(FilterListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.filter_bottom_sheet, container, false);

        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.FIRST_SYSTEM_WINDOW);

        defineLayout();

        setOnClickListener();

        return v;
    }

    private void defineLayout() {
        ivBack = v.findViewById(R.id.ivBack);
        tvChoose = v.findViewById(R.id.tvChoose);

        etDayOfWeek = v.findViewById(R.id.etDayOfWeek);
        etDate = v.findViewById(R.id.etDate);
    }

    private void setOnClickListener() {
        ivBack.setOnClickListener(view -> {
            dismiss();
        });

        tvChoose.setOnClickListener(view -> {
            String dayOfWeek = etDayOfWeek.getText().toString();
            String date = etDate.getText().toString();
            listener.search(date, dayOfWeek);
            dismiss();
        });

        etDate.setOnClickListener(view -> {
            showDatePickerDialog();
        });

        etDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickerDialog();
                }
            }
        });

    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        // Handle the selected date
                        String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDayOfMonth;
                        etDate.setText(selectedDate);
                    }
                },
                year,
                month,
                dayOfMonth
        );

        // Set a minimum date (in this case, the current date)
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }
}
