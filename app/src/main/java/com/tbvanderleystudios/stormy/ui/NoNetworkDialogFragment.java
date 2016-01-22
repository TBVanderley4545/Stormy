package com.tbvanderleystudios.stormy.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import com.tbvanderleystudios.stormy.R;

public class NoNetworkDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        // Create AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Set AlertDialog Title, Message, and Positive Button in one step instead of
        // the multiple steps that were used in the AlertDialogFragment
        builder.setTitle(context.getString(R.string.no_network_dialog_title))
                .setMessage(context.getString(R.string.no_network_dialog_message))
                .setPositiveButton(context.getString(R.string.error_dialog_positive_button), null);

        // Store the AlertDialog in a variable so that it can be called after it is created
        AlertDialog dialog = builder.create();

        return dialog;
    }
}
