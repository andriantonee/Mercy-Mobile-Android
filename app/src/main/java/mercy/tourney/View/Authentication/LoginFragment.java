package mercy.tourney.View.Authentication;

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
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import mercy.tourney.Model.Basic.Response;
import mercy.tourney.Model.Response.LoginResponse;
import mercy.tourney.Model.SessionManager;
import mercy.tourney.Presenter.Authentication.LoginPresenter;
import mercy.tourney.Presenter.iPresenterResponse;
import mercy.tourney.R;

/**
 * Created by Andrianto on 11/23/2016.
 */
public class LoginFragment extends Fragment implements iPresenterResponse {
    /**
     * Declare Component in XML Layout
     */
    ScrollView login_scroll_view;
    EditText username_edit_text, password_edit_text;
    TextView register_text_view;
    Button login_button;

    /**
     * Used for handle spamming button
     */
    ProgressDialog progress;

    /**
     * Declare Variable for Instance Object
     */
    LoginPresenter instanceLoginPresenter;
    SessionManager instanceSessionManager;

    /**
     * Custom Text for Register Text View
     */
    SpannableString full_register_text;
    final ClickableSpan register_text = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            ((AuthActivity) getActivity()).doChangeFragment(new RegisterFragment());
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(Color.WHITE);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View _view = inflater.inflate(R.layout.fragment_login, container, false);

        // Set Action Bar Title
        ((AuthActivity) getActivity()).getSupportActionBar().setTitle("Login");

        // Initialize Component from View
        login_scroll_view = (ScrollView) _view.findViewById(R.id.ScrollViewLogin);
        username_edit_text = (EditText) _view.findViewById(R.id.EditTextUsername);
        password_edit_text = (EditText) _view.findViewById(R.id.EditTextPassword);
        register_text_view = (TextView) _view.findViewById(R.id.TextViewRegister);
        login_button = (Button) _view.findViewById(R.id.ButtonLogin);

        // Initialize Progress Dialog
        progress = new ProgressDialog(getContext());
        progress.setIndeterminate(true);
        progress.setMessage("Loading...");

        // Initialize Instance Object
        instanceLoginPresenter = new LoginPresenter(this);
        instanceSessionManager = new SessionManager(getContext());

        // Login Scroll View
        login_scroll_view.setVerticalScrollBarEnabled(false);
        login_scroll_view.setHorizontalScrollBarEnabled(false);

        // Password Edit Text
        password_edit_text.setImeOptions(EditorInfo.IME_ACTION_DONE);
        password_edit_text.setNextFocusForwardId(R.id.ButtonLogin);

        // Register Text View
        full_register_text = new SpannableString("Not register yet? (Register Now)");
        full_register_text.setSpan(register_text, 19, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        register_text_view.setText(full_register_text);
        register_text_view.setMovementMethod(LinkMovementMethod.getInstance());

        // Login Button
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = username_edit_text.getText().toString();
                String password = password_edit_text.getText().toString();
                String client_id = getResources().getString(R.string.client_id);
                String client_secret = getResources().getString(R.string.client_secret);
                String scope = getResources().getString(R.string.scope);

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Please fill the username and password fields.", Toast.LENGTH_SHORT).show();
                } else {
                    progress.show();
                    instanceLoginPresenter.doLogin(username, password, client_id, client_secret, scope);
                }
            }
        });

        return _view;
    }

    @Override
    public void doSuccess(Response response) {
        instanceSessionManager.doCreateSession((LoginResponse) response);
        progress.dismiss();
        Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    @Override
    public void doFail(String message) {
        progress.dismiss();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doConnectionError(int message) {
        progress.dismiss();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
