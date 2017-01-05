package mercy.tourney.View.Tournament;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import mercy.tourney.R;

/**
 * Created by Andrianto on 12/19/2016.
 */
public class MatchViewHolder extends RecyclerView.ViewHolder {
    protected TextView player_1_text_view;
    protected TextView player_2_text_view;
    protected TextView player_1_score_text_view;
    protected TextView player_2_score_text_view;
    protected TextView scheduled_time_text_view;

    public MatchViewHolder(View itemView) {
        super(itemView);

        player_1_text_view = (TextView) itemView.findViewById(R.id.TextViewPlayer_1);
        player_2_text_view = (TextView) itemView.findViewById(R.id.TextViewPlayer_2);
        player_1_score_text_view = (TextView) itemView.findViewById(R.id.TextViewPlayer_1_Score);
        player_2_score_text_view = (TextView) itemView.findViewById(R.id.TextViewPlayer_2_Score);
        scheduled_time_text_view = (TextView) itemView.findViewById(R.id.TextViewScheduledTime);
    }
}
