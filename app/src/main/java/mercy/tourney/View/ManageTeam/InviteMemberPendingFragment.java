package mercy.tourney.View.ManageTeam;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import mercy.tourney.Model.Basic.Response;
import mercy.tourney.Model.Response.MemberResponse;
import mercy.tourney.Presenter.ManageTeam.InviteMemberPresenter;
import mercy.tourney.Presenter.iPresenterResponse;
import mercy.tourney.R;

/**
 * Created by Andrianto on 12/12/2016.
 */
public class InviteMemberPendingFragment extends Fragment {
    /**
     * Declare Component in XML Layout
     */
    RelativeLayout ScreenLoadingContainer, ScreenErrorContainer;
    SwipeRefreshLayout invite_member_pending_swipe_refresh_layout, no_invite_member_pending_swipe_refresh_layout;
    RecyclerView invite_member_pending_recycler_view;

    /**
     * Declare Variable for Instance Object
     */
    InviteMemberPresenter instanceSearchMemberPresenter, instanceManageMemberPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_invite_member_pending, container, false);

        // Initialize Component from View
        ScreenLoadingContainer = (RelativeLayout) _view.findViewById(R.id.ScreenLoadingContainer);
        ScreenErrorContainer = (RelativeLayout) _view.findViewById(R.id.ScreenErrorContainer);
        invite_member_pending_swipe_refresh_layout = (SwipeRefreshLayout) _view.findViewById(R.id.SwipeRefreshLayoutInviteMemberPending);
        no_invite_member_pending_swipe_refresh_layout = (SwipeRefreshLayout) _view.findViewById(R.id.SwipeRefreshLayoutNoInviteMemberPending);
        invite_member_pending_recycler_view = (RecyclerView) _view.findViewById(R.id.RecyclerViewInviteMemberPending);

        // Initialize Instance Object
        instanceSearchMemberPresenter = new InviteMemberPresenter(new iPresenterResponse() {
            @Override
            public void doSuccess(Response response) {
                invite_member_pending_recycler_view.setAdapter(new SearchMemberAdapter(((MemberResponse) response).getMembers(), instanceManageMemberPresenter, getActivity(), true));

                invite_member_pending_swipe_refresh_layout.setRefreshing(false);
                no_invite_member_pending_swipe_refresh_layout.setRefreshing(false);

                if (((MemberResponse) response).getMembers() == null) {
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

                doGetPendingMember();
            }

            @Override
            public void doConnectionError(int message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        // Configure SwipeRefreshLayout
        invite_member_pending_swipe_refresh_layout.setColorSchemeColors(getResources().getColor(R.color.colorSelectedTextPager));
        no_invite_member_pending_swipe_refresh_layout.setColorSchemeColors(getResources().getColor(R.color.colorSelectedTextPager));
        invite_member_pending_swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doGetPendingMember();
            }
        });
        no_invite_member_pending_swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doGetPendingMember();
            }
        });

        // Configure Recyclerview
        invite_member_pending_recycler_view.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        invite_member_pending_recycler_view.setLayoutManager(linearLayoutManager);

        return _view;
    }

    public void doShowScreen(String whichScreen) {
        if (whichScreen.equals("Error")) {
            ScreenLoadingContainer.setVisibility(View.GONE);
            no_invite_member_pending_swipe_refresh_layout.setVisibility(View.GONE);
            invite_member_pending_swipe_refresh_layout.setVisibility(View.GONE);
            ScreenErrorContainer.setVisibility(View.VISIBLE);
        } else if (whichScreen.equals("Loading")) {
            ScreenErrorContainer.setVisibility(View.GONE);
            no_invite_member_pending_swipe_refresh_layout.setVisibility(View.GONE);
            invite_member_pending_swipe_refresh_layout.setVisibility(View.GONE);
            ScreenLoadingContainer.setVisibility(View.VISIBLE);
        } else if (whichScreen.equals("Success")) {
            ScreenLoadingContainer.setVisibility(View.GONE);
            ScreenErrorContainer.setVisibility(View.GONE);
            no_invite_member_pending_swipe_refresh_layout.setVisibility(View.GONE);
            invite_member_pending_swipe_refresh_layout.setVisibility(View.VISIBLE);
        } else if (whichScreen.equals("SuccessNoResult")) {
            ScreenLoadingContainer.setVisibility(View.GONE);
            ScreenErrorContainer.setVisibility(View.GONE);
            invite_member_pending_swipe_refresh_layout.setVisibility(View.GONE);
            no_invite_member_pending_swipe_refresh_layout.setVisibility(View.VISIBLE);
        }
    }

    public void doGetPendingMember() {
        doShowScreen("Loading");
        instanceSearchMemberPresenter.get_team_member_pending_invitations(((ManageMemberActivity) getActivity()).get_token_type(), ((ManageMemberActivity) getActivity()).get_access_token());
    }
}
