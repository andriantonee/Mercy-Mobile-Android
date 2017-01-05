package mercy.tourney.View.TeamInvitations;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mercy.tourney.Model.Basic.TeamInvitation;
import mercy.tourney.Presenter.TeamInvitation.TeamInvitationPresenter;
import mercy.tourney.R;

/**
 * Created by Andrianto on 12/14/2016.
 */
public class TeamInvitationAdapter extends RecyclerView.Adapter<TeamInvitationViewHolder> {
    private List<TeamInvitation> teamList;
    private TeamInvitationPresenter instanceManageTeamInvitationPresenter;
    private Activity activity;

    public TeamInvitationAdapter(List<TeamInvitation> teamList, TeamInvitationPresenter instanceManageTeamInvitationPresenter, Activity activity) {
        this.teamList = teamList;
        this.instanceManageTeamInvitationPresenter = instanceManageTeamInvitationPresenter;
        this.activity = activity;
    }

    @Override
    public int getItemCount() {
        if (teamList != null) {
            return teamList.size();
        } else {
            return 0;
        }
    }

    @Override
    public void onBindViewHolder(TeamInvitationViewHolder holder, int position) {
        final TeamInvitation team = teamList.get(position);

        holder.team_name_and_leader_username_text_view.setText(team.getTeam_name().concat(" (").concat(team.getLeader_username()).concat(")"));
        holder.leader_name_text_view.setText(team.getLeader_name());
        Date date = new Date(Long.parseLong(String.valueOf(team.getInvite_at())) * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        holder.invite_at_text_view.setText(simpleDateFormat.format(date));


        final int index = position;
        holder.accept_team_invitation_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instanceManageTeamInvitationPresenter.accept_team_invitation(((TeamInvitationActivity) activity).getTokenType(), ((TeamInvitationActivity) activity).getAccessToken(), team.getId());

                teamList.remove(index);
                notifyItemRemoved(index);
                notifyItemRangeChanged(0, teamList.size());
            }
        });

        holder.reject_team_invitation_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instanceManageTeamInvitationPresenter.reject_team_invitation(((TeamInvitationActivity) activity).getTokenType(), ((TeamInvitationActivity) activity).getAccessToken(), team.getId());

                teamList.remove(index);
                notifyItemRemoved(index);
                notifyItemRangeChanged(0, teamList.size());
            }
        });
    }

    @Override
    public TeamInvitationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View _view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.team_invitation_card_view, parent, false);

        return new TeamInvitationViewHolder(_view);
    }
}
