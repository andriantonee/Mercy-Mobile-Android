package mercy.tourney.View.Tournament;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mercy.tourney.Model.Basic.Match;
import mercy.tourney.R;

/**
 * Created by Andrianto on 12/19/2016.
 */
public class MatchAdapter extends RecyclerView.Adapter<MatchViewHolder> {
    private List<Match> matches;

    public MatchAdapter(List<Match> matches) {
        this.matches = matches;
    }

    @Override
    public int getItemCount() {
        if (matches != null) {
            return matches.size();
        } else {
            return 0;
        }
    }

    @Override
    public void onBindViewHolder(MatchViewHolder holder, int position) {
        Match match = matches.get(position);

        holder.player_1_text_view.setText(match.getPlayer_1());
        holder.player_2_text_view.setText(match.getPlayer_2());
        holder.player_1_score_text_view.setText(String.valueOf(match.getPlayer_1_score()));
        holder.player_2_score_text_view.setText(String.valueOf(match.getPlayer_2_score()));

        if (match.getHas_winner()) {
            if (match.getPlayer_1_win()) {
                holder.player_1_text_view.setTextColor(Color.parseColor("#43a047"));
            } else {
                holder.player_1_text_view.setTextColor(Color.parseColor("#e53935"));
            }

            if (match.getPlayer_2_win()) {
                holder.player_2_text_view.setTextColor(Color.parseColor("#43a047"));
            } else {
                holder.player_2_text_view.setTextColor(Color.parseColor("#e53935"));
            }
        } else {
            holder.player_1_score_text_view.setText("?");
            holder.player_2_score_text_view.setText("?");
            holder.player_1_text_view.setTextColor(Color.parseColor("#ffffff"));
            holder.player_2_text_view.setTextColor(Color.parseColor("#ffffff"));
        }

        Date scheduled_date = new Date(Long.parseLong(String.valueOf(match.getScheduled_time())) * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, d MMM yyyy HH:mm:ss");
        holder.scheduled_time_text_view.setText(simpleDateFormat.format(scheduled_date));
    }

    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View _view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.match_card_view, parent, false);

        return new MatchViewHolder(_view);
    }
}
