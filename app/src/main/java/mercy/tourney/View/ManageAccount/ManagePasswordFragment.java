package mercy.tourney.View.ManageAccount;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import mercy.tourney.Model.Basic.Response;
import mercy.tourney.Model.Basic.Token;
import mercy.tourney.Model.SessionManager;
import mercy.tourney.Presenter.ManageAccount.AccountPresenter;
import mercy.tourney.Presenter.iPresenterResponse;
import mercy.tourney.R;

/**
 * Created by Andrianto on 12/2/2016.
 */
public class ManagePasswordFragment extends Fragment implements iPresenterResponse {
    /**
     * Declare Component in XML Layout
     */
    ScrollView manage_password_scroll_view;
    EditText old_password_edit_text, new_password_edit_text, new_password_confirmation_edit_text;
    Button update_password_button;

    /**
     * Used for handle spamming button
     */
    ProgressDialog progress;

    /**
     * Declare Variable for Instance Object
     */
    AccountPresenter instanceAccountPresenter;
    SessionManager instanceSessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_manage_password, container, false);

        // Set Action Bar Title
        ((ManageAccountActivity) getActivity()).getSupportActionBar().setTitle("Manage Password");

        // Initialize Component from View
        manage_password_scroll_view = (ScrollView) _view.findViewById(R.id.ScrollViewManagePassword);
        old_password_edit_text = (EditText) _view.findViewById(R.id.EditTextOldPassword);
        new_password_edit_text = (EditText) _view.findViewById(R.id.EditTextNewPassword);
        new_password_confirmation_edit_text = (EditText) _view.findViewById(R.id.EditTextNewPasswordConfirmation);
        update_password_button = (Button) _view.findViewById(R.id.ButtonUpdatePassword);

        // Initialize Progress Dialog
        progress = new ProgressDialog(getContext());
        progress.setIndeterminate(true);
        progress.setMessage("Loading...");

        // Initialize Instance Object
        instanceAccountPresenter = new AccountPresenter(this);
        instanceSessionManager = new SessionManager(getContext());

        // Manage Password Scroll View
        manage_password_scroll_view.setVerticalScrollBarEnabled(false);
        manage_password_scroll_view.setHorizontalScrollBarEnabled(false);

        // Button Update Password
        update_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_password = old_password_edit_text.getText().toString();
                String password = new_password_edit_text.getText().toString();
                String password_confirmation = new_password_confirmation_edit_text.getText().toString();

                if (TextUtils.isEmpty(old_password) || TextUtils.isEmpty(password) || TextUtils.isEmpty(password_confirmation)) {
                    Toast.makeText(getContext(), "Please fill the blank fields.", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(password_confirmation)) {
                    Toast.makeText(getContext(), "Password and password confirmation doesn't match.", Toast.LENGTH_SHORT).show();
                } else {
                    progress.show();
                    Token token = instanceSessionManager.getTokenLoggedIn();
                    instanceAccountPresenter.update_profile_password(token.getToken_type(), token.getAccess_token(), old_password, password, password_confirmation);
                }
            }
        });

        return _view;
    }

    @Override
    public void doSuccess(Response response) {
        progress.dismiss();
        Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doFail(String message) {
        progress.dismiss();
//        if (message.equals("Not Authorize!")) {
//            // If not authorize destroy this activity and call authentication activity
//            Intent intent = new Intent(getContext(), AuthActivity.class);
//            startActivity(intent);
//            getActivity().finish();
//        }
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doConnectionError(int message) {
        progress.dismiss();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
