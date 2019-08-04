package com.example.dbbalalinjava;

import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class ActionBarDialog extends DialogFragment implements View.OnClickListener {

    Dialog dialog;
    TextView textView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        String title = args.getString("title");
        View v = inflater.inflate(R.layout.third_dialog, container, false);
        v.findViewById(R.id.textView3).setOnClickListener(this);
        v.findViewById(R.id.textView5).setOnClickListener(this);
        v.findViewById(R.id.textView6).setOnClickListener(this);
        v.findViewById(R.id.textView7).setOnClickListener(this);
        v.findViewById(R.id.textView8).setOnClickListener(this);
        v.findViewById(R.id.textView9).setOnClickListener(this);
        Toolbar toolbar = v.findViewById(R.id.toolbar_second);
        toolbar.inflateMenu(R.menu.menu_second);
        toolbar.setTitle(title);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i("click","click");
                dialog.dismiss();
                return true;
            }
        });
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,
                android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Override
    public void onClick(View v) {switch (v.getId()) {
        case R.id.textView3:
            Toast.makeText(getActivity(), "click", Toast.LENGTH_LONG).show();
            break;
        case R.id.textView5:
            Toast.makeText(getActivity(), "click 2", Toast.LENGTH_LONG).show();
            break;
        case R.id.textView6:
            Toast.makeText(getActivity(), "click 3", Toast.LENGTH_LONG).show();
            break;
        case R.id.textView7:
            Toast.makeText(getActivity(), "click 4", Toast.LENGTH_LONG).show();
            break;
        case R.id.textView8:
            Toast.makeText(getActivity(), "click 5", Toast.LENGTH_LONG).show();
            break;
        case R.id.textView9:
            Toast.makeText(getActivity(), "click 6", Toast.LENGTH_LONG).show();
            break;
        default:
            break;
    }
    }
}