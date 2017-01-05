package mercy.tourney.View.ManageTeam;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mercy.tourney.R;

/**
 * Created by Andrianto on 12/12/2016.
 */
public class SearchMemberViewHolder extends RecyclerView.ViewHolder {
    protected TextView name_text_view;
    protected TextView username_text_view;
    protected TextView invited_at_text_view;
    protected ImageView add_member_image_view;
    protected ImageView remove_member_image_view;

    public SearchMemberViewHolder(View itemView) {
        super(itemView);

        name_text_view = (TextView) itemView.findViewById(R.id.TextViewName);
        username_text_view = (TextView) itemView.findViewById(R.id.TextViewUsername);
        invited_at_text_view = (TextView) itemView.findViewById(R.id.TextViewInvitedAt);
        add_member_image_view = (ImageView) itemView.findViewById(R.id.AddMemberImageView);
        remove_member_image_view = (ImageView) itemView.findViewById(R.id.RemoveMemberImageView);
    }
}
