package mercy.tourney.View.ManageTeam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import mercy.tourney.Model.Basic.Response;
import mercy.tourney.Model.Response.MemberResponse;
import mercy.tourney.Presenter.ManageTeam.InviteMemberPresenter;
import mercy.tourney.Presenter.iPresenterResponse;
import mercy.tourney.R;
import mercy.tourney.View.Authentication.AuthActivity;

/**
 * Created by Andrianto on 12/12/2016.
 */
public class InviteMemberFragment extends Fragment {
    private String keyword;

    /**
     * Declare Component in XML Layout
     */
    EditText search_member_edit_text;
    RelativeLayout ScreenLoadingContainer, ScreenErrorContainer, ScreenSearchMemberNoResult;
    RecyclerView search_member_result_recycler_view;
    TextView search_member_no_result_text_view;

    /**
     * Declare Variable for Instance Object
     */
    InviteMemberPresenter instanceSearchMemberPresenter, instanceManageMemberPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_invite_member, container, false);

        // Initialize Component from View
        search_member_edit_text = (EditText) _view.findViewById(R.id.EditTextSearchMember);
        ScreenLoadingContainer = (RelativeLayout) _view.findViewById(R.id.ScreenLoadingContainer);
        ScreenErrorContainer = (RelativeLayout) _view.findViewById(R.id.ScreenErrorContainer);
        ScreenSearchMemberNoResult = (RelativeLayout) _view.findViewById(R.id.ScreenSearchMemberNoResult);
        search_member_result_recycler_view = (RecyclerView) _view.findViewById(R.id.RecyclerViewSearchMemberResult);
        search_member_no_result_text_view = (TextView) _view.findViewById(R.id.SearchMemberNoResultTextView);

        // Initialize Instance Object
        instanceSearchMemberPresenter = new InviteMemberPresenter(new iPresenterResponse() {
            @Override
            public void doSuccess(Response response) {
                search_member_result_recycler_view.setAdapter(new SearchMemberAdapter(((MemberResponse) response).getMembers(), instanceManageMemberPresenter, getActivity(), false));

                if (((MemberResponse) response).getMembers() == null) {
                    search_member_no_result_text_view.setText("No member result for keyword [".concat(search_member_edit_text.getText().toString()).concat("]"));
                    doShowScreen("SuccessNoResult");
                } else {
                    doShowScreen("Success");
                }
            }

            @Override
            public void doFail(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

//                if (message.equals("Not Authorize!")) {
//                    Intent intent = new Intent(getContext(), AuthActivity.class);
//                    startActivity(intent);
//                    getActivity().finish();
//                } else {
                    doShowScreen("Error");
//                }
            }

            @Override
            public void doConnectionError(int message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                doShowScreen("Error");
            }
        });
        instanceManageMemberPresenter = new InviteMemberPresenter(new iPresenterResponse() {
            @Override
            public void doSuccess(Response response) {
                Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void doFail(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                doSearchMember();
            }

            @Override
            public void doConnectionError(int message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        // Set Click Listener to ScreenErrorContainer
        ScreenErrorContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSearchMember();
            }
        });

        // Configure Search Member Edit Text to Handle Action Search in Keyboard
        search_member_edit_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String search_member_keyword = search_member_edit_text.getText().toString();
                    if (!TextUtils.isEmpty(search_member_keyword)) {
                        keyword = search_member_keyword;
                        doSearchMember();

                        return false;
                    }

                    return true;
                }
                return false;
            }
        });

        // Configure Recyclerview
        search_member_result_recycler_view.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        search_member_result_recycler_view.setLayoutManager(linearLayoutManager);

        // Hide All Screen until user search
        doShowScreen("HideAll");

        return _view;
    }

    public void doShowScreen(String whichScreen) {
        if (whichScreen.equals("HideAll")) {
            ScreenLoadingContainer.setVisibility(View.GONE);
            ScreenErrorContainer.setVisibility(View.GONE);
            ScreenSearchMemberNoResult.setVisibility(View.GONE);
            search_member_result_recycler_view.setVisibility(View.GONE);
        } else if (whichScreen.equals("Error")) {
            ScreenLoadingContainer.setVisibility(View.GONE);
            ScreenSearchMemberNoResult.setVisibility(View.GONE);
            search_member_result_recycler_view.setVisibility(View.GONE);
            ScreenErrorContainer.setVisibility(View.VISIBLE);
        } else if (whichScreen.equals("Loading")) {
            ScreenErrorContainer.setVisibility(View.GONE);
            ScreenSearchMemberNoResult.setVisibility(View.GONE);
            search_member_result_recycler_view.setVisibility(View.GONE);
            ScreenLoadingContainer.setVisibility(View.VISIBLE);
        } else if (whichScreen.equals("Success")) {
            ScreenLoadingContainer.setVisibility(View.GONE);
            ScreenErrorContainer.setVisibility(View.GONE);
            ScreenSearchMemberNoResult.setVisibility(View.GONE);
            search_member_result_recycler_view.setVisibility(View.VISIBLE);
        } else if (whichScreen.equals("SuccessNoResult")) {
            ScreenLoadingContainer.setVisibility(View.GONE);
            ScreenErrorContainer.setVisibility(View.GONE);
            search_member_result_recycler_view.setVisibility(View.GONE);
            ScreenSearchMemberNoResult.setVisibility(View.VISIBLE);
        }
    }

    public void doSearchMember() {
        doShowScreen("Loading");
        instanceSearchMemberPresenter.search_member(((ManageMemberActivity) getActivity()).get_token_type(), ((ManageMemberActivity) getActivity()).get_access_token(), keyword);
    }
}
