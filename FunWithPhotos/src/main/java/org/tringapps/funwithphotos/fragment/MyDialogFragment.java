package org.tringapps.funwithphotos.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by tring-ajay on 7/30/14.
 */
public class MyDialogFragment extends DialogFragment {

    public static final String KEY_DIALOG_TYPE = "MyDialogFragment";

    public static enum DialogType {
        DIALOG_TYPE_DETAIL;

        public int toInt() {
            switch (this) {
                case DIALOG_TYPE_DETAIL:
                    return 0;
            }

            return -1;
        }
    }

    public static MyDialogFragment getDialogFragment(DialogType dialogType) {
        MyDialogFragment appDialogFragment = new MyDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(KEY_DIALOG_TYPE, dialogType.toInt());

        appDialogFragment.setArguments(bundle);

        return appDialogFragment;
    }

    // ---

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();

        if (bundle != null) {
            switch (bundle.getInt(KEY_DIALOG_TYPE)) {
                case 0: {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("FunWithPhotos");
                    builder.setMessage("Features Implemented:\n"
                            + " 1. Instagram API \n"
                            + " 2. ListView - Lazy Loading \n"
                            + " 3. Custom pattern display logic \n"
                            + " 4. Infinite scrolling effect - pagination \n"
                            + " 5. Click to enlarge.");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    return builder.create();
                }
            }
        }

        return super.onCreateDialog(savedInstanceState);
    }
}
