package com.example.zoron.carbom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by zoron on 17-3-21.
 */

public class InFragment extends BaseFragment {
    private EditText id;
    private EditText type;
    private EditText name;
    private EditText stage;
    private EditText status;
    private EditText time;
    private EditText location;
    private EditText keeper;
    private EditText note;

    public InFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in, container, false);
        id = (EditText) view.findViewById(R.id.id);
        type = (EditText) view.findViewById(R.id.type);
        name = (EditText) view.findViewById(R.id.name);
        stage = (EditText) view.findViewById(R.id.stage);
        status = (EditText) view.findViewById(R.id.status);
        time = (EditText) view.findViewById(R.id.time);
        location = (EditText) view.findViewById(R.id.location);
        keeper = (EditText) view.findViewById(R.id.keeper);
        note = (EditText) view.findViewById(R.id.note);

        id.setText(epc);
        keeper.setText(R.string.stored);

        setCurrentDate(time);

        Button left = (Button) view.findViewById(R.id.left);
        Button right = (Button) view.findViewById(R.id.right);

        left.setOnClickListener(this);
        right.setOnClickListener(this);
        return view;
    }

    @Override
    protected void update() {
        mapToWrite = new HashMap<>();
        getInput(CsvReader.INDEX.ID, id);
        getInput(CsvReader.INDEX.TYPE, type);
        getInput(CsvReader.INDEX.NAME, name);
        getInput(CsvReader.INDEX.STAGE, stage);
        getInput(CsvReader.INDEX.STATUS, status);
        getInput(CsvReader.INDEX.TIME, time);
        getInput(CsvReader.INDEX.LOCATION, location);
        getInput(CsvReader.INDEX.KEEPER, keeper);
        getInput(CsvReader.INDEX.NOTE, note);
        if (!reader.hasData(mapToWrite)) {
            reader.append(mapToWrite);
        }
    }
}
