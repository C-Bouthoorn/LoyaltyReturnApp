package tk.cbouthoorn.loyalty.loyaltyreturn;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;

class AlertBuilder {
    private AlertDialog.Builder builder;

    AlertBuilder(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            this.builder = new AlertDialog.Builder(context);
        }
    }

    void neutralAlert(String title, String message) {
        builder.setTitle(title)
                .setMessage(message)
                .setNeutralButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    void choiceAlert(String title, String message, DialogInterface.OnClickListener yesListener, DialogInterface.OnClickListener noListener) {
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, yesListener)
                .setNegativeButton(android.R.string.no, noListener)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
