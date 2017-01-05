package mercy.tourney.View.ManageTeam;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mercy.tourney.Model.Basic.Response;
import mercy.tourney.Presenter.ManageTeam.TeamPresenter;
import mercy.tourney.Presenter.iPresenterResponse;
import mercy.tourney.R;

/**
 * Created by Andrianto on 12/11/2016.
 */
@SuppressLint("ValidFragment")
public class CreateTeamDialogFragment extends DialogFragment implements iPresenterResponse {
    TeamDetailFragment teamDetailFragment;
    TeamMemberFragment teamMemberFragment;

    /**
     * Declare Component in XML Layout
     */
    EditText team_name_edit_text;
    Button cancel_button, create_button;

    /**
     * Used for handle spamming button
     */
    ProgressDialog progress;

    /**
     * Declare Variable for Instance Object
     */
    TeamPresenter instanceCreateTeamPresenter;

    @SuppressLint("ValidFragment")
    public CreateTeamDialogFragment(TeamDetailFragment teamDetailFragment) {
        this.teamDetailFragment = teamDetailFragment;
    }

    @SuppressLint("ValidFragment")
    public CreateTeamDialogFragment(TeamMemberFragment teamMemberFragment) {
        this.teamMemberFragment = teamMemberFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_dialog_create_team, container, false);

        // Set Dialog Fragment Title
        getDialog().setTitle("Create Team");

        // Initialize Component from View
        team_name_edit_text = (EditText) _view.findViewById(R.id.EditTextTeamName);
        cancel_button = (Button) _view.findViewById(R.id.ButtonCancel);
        create_button = (Button) _view.findViewById(R.id.ButtonCreate);

        // Initialize Progress Dialog
        progress = new ProgressDialog(getContext());
        progress.setIndeterminate(true);
        progress.setMessage("Loading...");

        // Initialize Instance Object
        instanceCreateTeamPresenter = new TeamPresenter(this);

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String team_name = team_name_edit_text.getText().toString();
                if (TextUtils.isEmpty(team_name)) {
                    Toast.makeText(getContext(), "Please fill the blank field.", Toast.LENGTH_SHORT).show();
                } else {
                    progress.show();
                    instanceCreateTeamPresenter.create_team(((ManageTeamActivity) getActivity()).get_token_type(), ((ManageTeamActivity) getActivity()).get_access_token(), team_name);
                }
            }
        });

        return _view;
    }

    @Override
    public void doSuccess(Response response) {
        progress.dismiss();
        dismiss();
        if (teamMemberFragment != null) {
            teamMemberFragment.doGetTeamMember();
            ((ManageTeamActivity) getActivity()).setMustRefreshTeamDetail(true);
        } else if (teamDetailFragment != null) {
            teamDetailFragment.doGetTeamDetail();
            ((ManageTeamActivity) getActivity()).setMustRefreshTeamMember(true);
        }
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
