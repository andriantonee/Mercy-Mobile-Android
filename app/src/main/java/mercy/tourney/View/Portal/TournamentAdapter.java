package mercy.tourney.View.Portal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mercy.tourney.Model.Basic.Tournament;
import mercy.tourney.Model.Connection.TourneyConnectionAPI;
import mercy.tourney.R;
import mercy.tourney.View.Tournament.TournamentContentActivity;

/**
 * Created by Andrianto on 12/15/2016.
 */
public class TournamentAdapter extends RecyclerView.Adapter<TournamentViewHolder> {
    private List<Tournament> tournamentList;
    private Context context;

    public TournamentAdapter(List<Tournament> tournamentList) {
        this.tournamentList = tournamentList;
    }

    @Override
    public int getItemCount() {
        if (tournamentList != null) {
            return tournamentList.size();
        } else {
            return 0;
        }
    }

    @Override
    public void onBindViewHolder(TournamentViewHolder holder, int position) {
        final Tournament tournament = tournamentList.get(position);

        holder.tournament_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TournamentContentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("TOURNAMENT ID", tournament.getId());
                intent.putExtra("BUNDLE", bundle);
                context.startActivity(intent);
            }
        });
        Picasso.with(context)
                .load(TourneyConnectionAPI.getBASEURL().concat(tournament.getCategory().getImage_url()))
                .resize(768, 1024)
                .centerCrop()
                .into(holder.game_category_image_view);
        holder.tournament_name_text_view.setText(tournament.getName());
        Date date = new Date(Long.parseLong(String.valueOf(tournament.getRegistration_close())) * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        holder.tournament_registration_close_date_text_view.setText("Registration close date : ".concat(simpleDateFormat.format(date)));
    }

    @Override
    public TournamentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View _view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.tournament_card_view, parent, false);

        context = parent.getContext();

        return new TournamentViewHolder(_view);
    }
}
