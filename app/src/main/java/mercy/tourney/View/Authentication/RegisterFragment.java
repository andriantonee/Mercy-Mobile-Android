package mercy.tourney.View.Authentication;

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
import mercy.tourney.Presenter.Authentication.RegisterPresenter;
import mercy.tourney.Presenter.iPresenterResponse;
import mercy.tourney.R;

/**
 * Created by Andrianto on 11/23/2016.
 */
public class RegisterFragment extends Fragment implements iPresenterResponse {
    /**
     * Declare Component in XML Layout
     */
    ScrollView register_scroll_view;
    EditText username_edit_text, password_edit_text, password_confirmation_edit_text, first_name_edit_text, last_name_edit_text, phone_edit_text;
    Button register_button;

    /**
     * Used for handle spamming button
     */
    ProgressDialog progress;

    /**
     * Declare Variable for Instance Object
     */
    RegisterPresenter instanceRegisterPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View _view = inflater.inflate(R.layout.fragment_register, container, false);

        // Set Action Bar Title
        ((AuthActivity) getActivity()).getSupportActionBar().setTitle("Register");

        // Initialize Component from View
        register_scroll_view = (ScrollView) _view.findViewById(R.id.ScrolViewRegister);
        username_edit_text = (EditText) _view.findViewById(R.id.EditTextUsername);
        password_edit_text = (EditText) _view.findViewById(R.id.EditTextPassword);
        password_confirmation_edit_text = (EditText) _view.findViewById(R.id.EditTextConfirmPassword);
        first_name_edit_text = (EditText) _view.findViewById(R.id.EditTextFirstName);
        last_name_edit_text = (EditText) _view.findViewById(R.id.EditTextLastName);
        phone_edit_text = (EditText) _view.findViewById(R.id.EditTextPhone);
        register_button = (Button) _view.findViewById(R.id.ButtonRegister);

        // Initialize Progress Dialog
        progress = new ProgressDialog(getContext());
        progress.setIndeterminate(true);
        progress.setMessage("Loading...");

        // Initialize Instance Object
        instanceRegisterPresenter = new RegisterPresenter(this);

        // Register Scroll View
        register_scroll_view.setVerticalScrollBarEnabled(false);
        register_scroll_view.setHorizontalScrollBarEnabled(false);

        // Register Button Set On Click Action
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = username_edit_text.getText().toString();
                String password = password_edit_text.getText().toString();
                String password_confirmation = password_confirmation_edit_text.getText().toString();
                String first_name = first_name_edit_text.getText().toString();
                String last_name = last_name_edit_text.getText().toString();
                String phone = phone_edit_text.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(password_confirmation) || TextUtils.isEmpty(first_name) || TextUtils.isEmpty(phone)) {
                    Toast.makeText(getContext(), "Please fill the blank fields.", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(password_confirmation)) {
                    Toast.makeText(getContext(), "Password and password confirmation doesn't match.", Toast.LENGTH_SHORT).show();
                } else {
                    progress.show();
                    instanceRegisterPresenter.doRegister(username, password, password_confirmation, first_name, last_name, phone);
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
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doConnectionError(int message) {
        progress.dismiss();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
