package mercy.tourney.View.ManageAccount;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import mercy.tourney.Model.Basic.Response;
import mercy.tourney.Model.Basic.Token;
import mercy.tourney.Model.Basic.User;
import mercy.tourney.Model.Response.UserResponse;
import mercy.tourney.Model.SessionManager;
import mercy.tourney.Presenter.ManageAccount.AccountPresenter;
import mercy.tourney.Presenter.iPresenterResponse;
import mercy.tourney.R;

/**
 * Created by Andrianto on 12/2/2016.
 */
public class ManageProfileFragment extends Fragment implements iPresenterResponse {
    /**
     * Declare Component in XML Layout
     */
    ScrollView manage_profile_scroll_view;
    EditText first_name_edit_text, last_name_edit_text, phone_edit_text;
    TextView manage_password_text_view;
    Button update_profile_button;

    /**
     * Used for handle spamming button
     */
    ProgressDialog progress;

    /**
     * Declare Variable for Instance Object
     */
    AccountPresenter instanceAccountPresenter;
    SessionManager instanceSessionManager;

    /**
     * Custom Text for Register Text View
     */
    SpannableString full_manage_password_text;
    final ClickableSpan manage_password_text = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            ((ManageAccountActivity) getActivity()).doChangeFragment(new ManagePasswordFragment());
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(Color.WHITE);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_manage_profile, container, false);

        // Set Action Bar Title
        ((ManageAccountActivity) getActivity()).getSupportActionBar().setTitle("Manage Profile");

        // Initialize Component from View
        manage_profile_scroll_view = (ScrollView) _view.findViewById(R.id.ScrollViewManageProfile);
        first_name_edit_text = (EditText) _view.findViewById(R.id.EditTextFirstName);
        last_name_edit_text = (EditText) _view.findViewById(R.id.EditTextLastName);
        phone_edit_text = (EditText) _view.findViewById(R.id.EditTextPhone);
        manage_password_text_view = (TextView) _view.findViewById(R.id.TextViewManagePassword);
        update_profile_button = (Button) _view.findViewById(R.id.ButtonUpdateProfile);

        // Initialize Progress Dialog
        progress = new ProgressDialog(getContext());
        progress.setIndeterminate(true);
        progress.setMessage("Loading...");

        // Initialize Instance Object
        instanceAccountPresenter = new AccountPresenter(this);
        instanceSessionManager = new SessionManager(getContext());

        // Manage Profile Scroll View
        manage_profile_scroll_view.setVerticalScrollBarEnabled(false);
        manage_profile_scroll_view.setHorizontalScrollBarEnabled(false);

        // Set EditText value from SharedPreference
        User user = instanceSessionManager.getUserLoggedIn();
        first_name_edit_text.setText(user.getFirst_name());
        last_name_edit_text.setText(TextUtils.isEmpty(user.getLast_name()) ? "" : user.getLast_name());
        phone_edit_text.setText(user.getPhone());

        // Manage Password Text View
        full_manage_password_text = new SpannableString("Want to change password? (Manage Password)");
        full_manage_password_text.setSpan(manage_password_text, 26, 41, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        manage_password_text_view.setText(full_manage_password_text);
        manage_password_text_view.setMovementMethod(LinkMovementMethod.getInstance());

        // Button Update Profile
        update_profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first_name = first_name_edit_text.getText().toString();
                String last_name = last_name_edit_text.getText().toString();
                String phone = phone_edit_text.getText().toString();

                if (TextUtils.isEmpty(first_name) || TextUtils.isEmpty(phone)) {
                    Toast.makeText(getContext(), "Please fill the blank fields.", Toast.LENGTH_SHORT).show();
                } else {
                    progress.show();
                    Token token = instanceSessionManager.getTokenLoggedIn();
                    instanceAccountPresenter.update_profile(token.getToken_type(), token.getAccess_token(), first_name, last_name, phone);
                }
            }
        });

        return _view;
    }

    @Override
    public void doSuccess(Response response) {
        instanceSessionManager.doChangeUserDatas(((UserResponse) response).getUsers());
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
