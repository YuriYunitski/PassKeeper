package com.yunitski.passkeeper;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

public class PassInfoFragment extends Fragment implements View.OnClickListener {
    TextView tvResInfoFrag, tvNameInfoFrag, tvLinkInfoFrag, tvPassInfoFrag;
    String r, n, l, p;
    Button ok;
    public PassInfoFragment(String r, String n, String l, String p){
        this.r = r;
        this.n = n;
        this.l = l;
        this.p = p;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pass_info, container, false);
        tvResInfoFrag = view.findViewById(R.id.tvResInfoFrag);
        tvNameInfoFrag = view.findViewById(R.id.tvNameInfoFrag);
        tvLinkInfoFrag = view.findViewById(R.id.tvLinkInfoFrag);
        tvPassInfoFrag = view.findViewById(R.id.tvPassInfoFrag);
        ok = view.findViewById(R.id.frag_ok);
        ok.setOnClickListener(this);
        MainActivity.isFragActive = true;

        tvResInfoFrag.setText(r);
        tvNameInfoFrag.setText(n);
        tvLinkInfoFrag.setText(l);
        tvPassInfoFrag.setText(p);
        tvResInfoFrag.setClickable(true);
        tvNameInfoFrag.setClickable(true);
        tvLinkInfoFrag.setClickable(true);
        tvPassInfoFrag.setClickable(true);
        tvResInfoFrag.setOnClickListener(this);
        tvNameInfoFrag.setOnClickListener(this);
        tvLinkInfoFrag.setOnClickListener(this);
        tvPassInfoFrag.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_ok:
            assert getFragmentManager() != null;
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.remove(this).commit();
            MainActivity.isFragActive = false;
            break;
            case R.id.tvResInfoFrag:
                String s = tvResInfoFrag.getText().toString();
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("copied_text", s);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Скопировано", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvNameInfoFrag:
                String s1 = tvNameInfoFrag.getText().toString();
                ClipboardManager clipboard1 = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip1 = ClipData.newPlainText("copied_text", s1);
                clipboard1.setPrimaryClip(clip1);
                Toast.makeText(getContext(), "Скопировано", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvLinkInfoFrag:
                String s2 = tvLinkInfoFrag.getText().toString();
                ClipboardManager clipboard2 = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip2 = ClipData.newPlainText("copied_text", s2);
                clipboard2.setPrimaryClip(clip2);
                Toast.makeText(getContext(), "Скопировано", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvPassInfoFrag:
                String s3 = tvPassInfoFrag.getText().toString();
                ClipboardManager clipboard3 = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip3 = ClipData.newPlainText("copied_text", s3);
                clipboard3.setPrimaryClip(clip3);
                Toast.makeText(getContext(), "Скопировано", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
