package com.example.zoron.carbom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zoron on 17-3-22.
 */

public class QueryFragment extends Fragment {
    public static final int NORMAL = 0;
    public static final int BYKEEPER = 1;

    private CsvReader reader;

    private SearchView typeQuery;
    private SearchView statusQuery;
    private SearchView locationQuery;
    private SearchView keeperQuery;

    public QueryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reader = new CsvReader(CsvReader.SAVE_FILE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_query, container, false);
        typeQuery = (SearchView) view.findViewById(R.id.type_query);
        statusQuery = (SearchView) view.findViewById(R.id.status_query);
        locationQuery = (SearchView) view.findViewById(R.id.location_query);
        keeperQuery = (SearchView) view.findViewById(R.id.keeper_query);
        Button query = (Button) view.findViewById(R.id.query);

        typeQuery.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(CsvReader.INDEX.TYPE, typeQuery.getQuery().toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        statusQuery.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(CsvReader.INDEX.STATUS, statusQuery.getQuery().toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        locationQuery.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(CsvReader.INDEX.LOCATION, locationQuery.getQuery().toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        keeperQuery.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(CsvReader.INDEX.KEEPER, keeperQuery.getQuery().toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSearch();
            }
        });

        return view;
    }

    private void search(CsvReader.INDEX key, String value) {
        if (!value.isEmpty()) {
            ArrayList<String> results = reader.search(key, value);
            if (results.isEmpty()) {
                Toast toast = Toast.makeText(getContext(), "无搜索结果", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                QueryResultFragment queryResultFragment = new QueryResultFragment();
                if (key == CsvReader.INDEX.KEEPER) {
                    queryResultFragment.setResult(results, BYKEEPER);
                } else {
                    queryResultFragment.setResult(results, NORMAL);
                }
                MainActivity.replaceFragment(getActivity().getSupportFragmentManager(),
                        R.id.container, queryResultFragment);
            }
        }
    }

    private void multiSearch() {
        Map<CsvReader.INDEX, String> queries = new HashMap<>();
        if (searchViewhasQuery(typeQuery)) {
            queries.put(CsvReader.INDEX.TYPE, typeQuery.getQuery().toString());
        }
        if (searchViewhasQuery(statusQuery)) {
            queries.put(CsvReader.INDEX.STATUS, statusQuery.getQuery().toString());
        }
        if (searchViewhasQuery(locationQuery)) {
            queries.put(CsvReader.INDEX.LOCATION, locationQuery.getQuery().toString());
        }
        if (searchViewhasQuery(keeperQuery)) {
            queries.put(CsvReader.INDEX.KEEPER, keeperQuery.getQuery().toString());
        }
        ArrayList<String> results = reader.search(queries);
        if (results == null) {
            Toast toast = Toast.makeText(getContext(), "无搜索结果", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            QueryResultFragment queryResultFragment = new QueryResultFragment();
            queryResultFragment.setResult(results, NORMAL);
            MainActivity.replaceFragment(getActivity().getSupportFragmentManager(),
                    R.id.container, queryResultFragment);
        }
    }

    private boolean searchViewhasQuery(SearchView sv) {
        return sv.getQuery().toString().trim().length() > 0;
    }
}