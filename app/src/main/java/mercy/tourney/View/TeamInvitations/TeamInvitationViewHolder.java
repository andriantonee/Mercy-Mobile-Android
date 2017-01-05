package mercy.tourney.View.TeamInvitations;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mercy.tourney.R;

/**
 * Created by Andrianto on 12/14/2016.
 */
public class TeamInvitationViewHolder extends RecyclerView.ViewHolder {
    protected TextView team_name_and_leader_username_text_view;
    protected TextView leader_name_text_view;
    protected TextView invite_at_text_view;
    protected ImageView accept_team_invitation_image_view;
    protected ImageView reject_team_invitation_image_view;

    public TeamInvitationViewHolder(View itemView) {
        super(itemView);

        team_name_and_leader_username_text_view = (TextView) itemView.findViewById(R.id.TextViewTeamNameAndLeaderUsername);
        leader_name_text_view = (TextView) itemView.findViewById(R.id.TextViewLeaderName);
        invite_at_text_view = (TextView) itemView.findViewById(R.id.TextViewInviteAt);
        accept_team_invitation_image_view = (ImageView) itemView.findViewById(R.id.AcceptTeamInvitationImageView);
        reject_team_invitation_image_view = (ImageView) itemView.findViewById(R.id.RejectTeamInvitationImageView);
    }
}
