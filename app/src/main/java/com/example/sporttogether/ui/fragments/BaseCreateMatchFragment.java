package com.example.sporttogether.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.sporttogether.exceptions.NotAllDataException;
import com.example.sporttogether.exceptions.TooMuchPlayersException;

public class BaseCreateMatchFragment extends Fragment {

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public void createMatch() throws NotAllDataException, TooMuchPlayersException {
    }

}

