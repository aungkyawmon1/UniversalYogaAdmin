package com.example.universalyogaadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universalyogaadmin.ClassUpdateListener;
import com.example.universalyogaadmin.R;
import com.example.universalyogaadmin.adapter.ClassAdapter;
import com.example.universalyogaadmin.database.DatabaseHelper;
import com.example.universalyogaadmin.model.YogaClass;
import com.example.universalyogaadmin.model.YogaCourse;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements ClassUpdateListener {

    ImageView ivFilter;

    SearchView svSearch;

    TextView tvFilterBy;

    RecyclerView rvPlanner;

    DatabaseHelper dbHelper;

    ArrayList<YogaClass> yogaClasses;

    String location = "", length = "", date = "", nameOfPlan = "";

    ClassAdapter classAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DatabaseHelper(this);

        defineLayout();

        setOnClickListener();

        setUpRecyclerView();
    }

    private void defineLayout() {
        svSearch = findViewById(R.id.svSearch);
        svSearch.setIconified(false);
        svSearch.requestFocus();

        ivFilter = findViewById(R.id.ivFilter);
        tvFilterBy = findViewById(R.id.tvFilterBy);
        rvPlanner = findViewById(R.id.rvPlanner);
    }

    private void setOnClickListener() {

        ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterBottomSheet();
            }
        });

        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle the search query
                // You can perform a search operation here
                Log.i("Search", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle query text changes

                if(newText.isEmpty()) {
                    classAdapter.updateView(yogaClasses);
                } else {
                    nameOfPlan = newText;
                    searchPlanner();
                }
                return true;
            }
        });

        svSearch.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                classAdapter.updateView(yogaClasses);
                return false;
            }
        });

    }

    private void searchPlanner() {
       // classAdapter.updateView(dbHelper.searchPlanner(nameOfPlan, location, length, date));
    }

    private void setUpRecyclerView() {
        yogaClasses = new ArrayList<>();
        classAdapter = new ClassAdapter(this,this, yogaClasses);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager( this, 1);
        rvPlanner.setLayoutManager(layoutManager);
        rvPlanner.setItemAnimator(new DefaultItemAnimator());
        rvPlanner.setAdapter(classAdapter);
    }

    private void showFilterBottomSheet() {
//        FilterBottomSheet bottomSheet = new FilterBottomSheet(this);
//        bottomSheet.show(getSupportFragmentManager(),
//                "ModalBottomSheet");
    }

//    @Override
//    public void search(String location, String length, String date) {
//
//        this.location = location;
//        this.length = length;
//        this.date = date;
//        String filteredBy = "Filtered By: ";
//        if (!location.isEmpty()) {
//            filteredBy += "Location, ";
//        }
//
//        if (!length.isEmpty()) {
//            filteredBy += "Length, ";
//        }
//
//        if(!date.isEmpty()) {
//            filteredBy += "Date";
//        }
//        tvFilterBy.setText(filteredBy);
//        searchPlanner();
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        if (item.getItemId() == android.R.id.home) {
            finish(); // or perform any custom action
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void deleteClass(int classID) {

    }

    @Override
    public void updateClass(int classID) {

    }
}