package com.clt.ledmanager.app.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikepenz.materialdrawer.app.R;


/**
 * A simple {@link Fragment} subclass.
 * This is just a demo fragment with a long scrollable view of editTexts. Don't see this as a reference for anything
 */
public class TerminalrefreshFragment extends Fragment {
    private static final String KEY_TITLE = "title";

    public TerminalrefreshFragment() {
        // Required empty public constructor

    }

    public static TerminalrefreshFragment newInstance(String title) {
        TerminalrefreshFragment f = new TerminalrefreshFragment();

        Bundle args = new Bundle();

        args.putString(KEY_TITLE, title);
        f.setArguments(args);

        return (f);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // don't look at this layout it's just a listView to show how to handle the keyboard
        View view = inflater.inflate(R.layout.fragment_upload_program, container, false);

        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText(getArguments().getString(KEY_TITLE));

        return view;
    }
}
