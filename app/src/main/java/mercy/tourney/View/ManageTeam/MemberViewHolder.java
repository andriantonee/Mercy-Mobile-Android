package mercy.tourney.View.ManageTeam;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import mercy.tourney.R;

/**
 * Created by Andrianto on 12/8/2016.
 */
public class MemberViewHolder extends RecyclerView.ViewHolder {
    protected TextView name_text_view;
    protected TextView username_text_view;

    public MemberViewHolder(View itemView) {
        super(itemView);

        name_text_view = (TextView) itemView.findViewById(R.id.TextViewName);
        username_text_view = (TextView) itemView.findViewById(R.id.TextViewUsername);
    }
}
