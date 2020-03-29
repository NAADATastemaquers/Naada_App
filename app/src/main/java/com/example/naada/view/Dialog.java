package com.example.naada.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog extends AppCompatDialogFragment {
    private DialogListener listener;

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Login Required")
                .setMessage("Please login to use this feature")
                .setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            listener.onYesClicked();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
    public interface DialogListener{
        void onYesClicked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try{
            listener=(DialogListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString()+"Must implement DialogListener");
        }
    }
}
