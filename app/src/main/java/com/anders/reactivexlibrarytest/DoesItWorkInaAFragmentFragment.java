package com.anders.reactivexlibrarytest;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by anders on 11/2/2016.
 */

public class DoesItWorkInaAFragmentFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_layout,container,false);

        TextView textView = (TextView) root.findViewById(R.id.observable_text);



        return root;
    }
}
