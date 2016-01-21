package com.tbvanderleystudios.stormy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

public class AlertDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        // Create an AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Set AlertDialog title
        builder.setTitle(context.getString(R.string.error_title));
        // Set AlertDialog message
        builder.setMessage(context.getString(R.string.error_message));
        // Set AlertDialog PositiveButton
        builder.setPositiveButton(context.getString(R.string.error_dialog_positive_button), null);
        // Create the AlertDialog and store it in a variable
        AlertDialog dialog = builder.create();
        // Return the now created AlertDialog
        return dialog;
    }
}
