package com.example.sporttogether.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.sporttogether.R;
import com.example.sporttogether.ui.fragments.BaseCreateMatchFragment;
import com.example.sporttogether.ui.fragments.BasketballFragment;
import com.example.sporttogether.ui.fragments.FootballFragment;
import com.example.sporttogether.ui.fragments.PadelFragment;
import com.example.sporttogether.ui.fragments.TennisFragment;

import java.util.Objects;

public class CreateMatchDialog extends DialogFragment {

    private Toolbar toolbar;
    private Spinner spinner;

    public static void display(FragmentManager fragmentManager) {
        CreateMatchDialog exampleDialog = new CreateMatchDialog();
        exampleDialog.show(fragmentManager, "");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.create_match_layout, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        spinner = view.findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.sports_array));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar.setNavigationOnClickListener(v -> dismiss());
        toolbar.inflateMenu(R.menu.dialog_menu);

        final BaseCreateMatchFragment[] baseCreateMatchFragment = {new BaseCreateMatchFragment()};

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        baseCreateMatchFragment[0] = new TennisFragment();
                        getChildFragmentManager().beginTransaction().replace(R.id.fragment, baseCreateMatchFragment[0]).commit();
                        break;
                    case 1:
                        baseCreateMatchFragment[0] = new PadelFragment();
                        getChildFragmentManager().beginTransaction().replace(R.id.fragment, baseCreateMatchFragment[0]).commit();
                        break;
                    case 2:
                        baseCreateMatchFragment[0] = new FootballFragment();
                        getChildFragmentManager().beginTransaction().replace(R.id.fragment, baseCreateMatchFragment[0]).commit();
                        break;
                    case 3:
                        baseCreateMatchFragment[0] = new BasketballFragment();
                        getChildFragmentManager().beginTransaction().replace(R.id.fragment, baseCreateMatchFragment[0]).commit();
                        break;
                }
                toolbar.setOnMenuItemClickListener(item -> {
                    try {
                        baseCreateMatchFragment[0].createMatch();
                        dismiss();
                        return true;
                    } catch (Exception e) {
                        Toast.makeText(getContext(), R.string.create_match_data_error, Toast.LENGTH_LONG).show();
                        return false;
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}

