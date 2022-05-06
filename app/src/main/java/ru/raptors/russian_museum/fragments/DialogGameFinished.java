package ru.raptors.russian_museum.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import ru.raptors.russian_museum.R;

public class DialogGameFinished extends DialogFragment {
    public static DialogGameFinished newInstance(String message) {
        DialogGameFinished fragment = new DialogGameFinished();
        Bundle args = new Bundle();
        args.putString("message", message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString("message");
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.well_done)
                .setMessage(message)
                .setPositiveButton(R.string.exit,
                        (dialogInterface, i) -> {
                            getActivity().finish();
                        });
        return adb.create();
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        getActivity().finish();
    }
}
