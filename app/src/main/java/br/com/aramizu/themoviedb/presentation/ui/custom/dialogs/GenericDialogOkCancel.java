package br.com.aramizu.themoviedb.presentation.ui.custom.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import br.com.aramizu.themoviedb.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Custom Dialog to show any kind of information, but with a custom layout.
 */
public class GenericDialogOkCancel extends Dialog {

    @BindView(R.id.dialog_title)
    TextView dialogTitle;
    @BindView(R.id.dialog_body)
    TextView dialogBody;
    @BindView(R.id.btn_positive)
    TextView btnPositive;
    @BindView(R.id.btn_negative)
    TextView btnNegative;

    boolean response;
    Context context;

    GenericDialogOkCancelListener mListener;

    public GenericDialogOkCancel(Context context, String title, String body, String negative, String positive) {
        super(context);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.generic_dialog_ok_cancel);
        ButterKnife.bind(this);

        this.context = context;
        configureLabel(dialogTitle, title);
        configureLabel(dialogBody, body);
        configureLabel(btnNegative, negative);
        configureLabel(btnPositive, positive);

        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
    }

    private void configureLabel(TextView view, String text) {
        if (text != null) {
            view.setText(text);
        }
        else {
            view.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_negative)
    public void onNegativeButtonClick() {
        returnResponse(response = false);
    }

    @OnClick(R.id.btn_positive)
    public void onPositiveButtonClick() {
        returnResponse(response = true);
    }

    /**
     * Return if the positive button option has been chosen
     * @param isPositive
     */
    public void returnResponse(boolean isPositive) {
        if (mListener != null) {
            mListener.response(isPositive);
        }
        dismiss();
    }

    public void setListenerResponse(GenericDialogOkCancelListener listener) {
        mListener = listener;
    }

    public void showDialog() {
        if (!((Activity) context).isFinishing()) {
            show();
        }
    }
}
