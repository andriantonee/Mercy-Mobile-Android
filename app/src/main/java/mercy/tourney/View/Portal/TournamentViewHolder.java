package mercy.tourney.View.Portal;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mercy.tourney.R;

/**
 * Created by Andrianto on 12/15/2016.
 */
public class TournamentViewHolder extends RecyclerView.ViewHolder {
    protected CardView tournament_card_view;
    protected ImageView game_category_image_view;
    protected TextView tournament_name_text_view;
    protected TextView tournament_registration_close_date_text_view;

    public TournamentViewHolder(View itemView) {
        super(itemView);

        tournament_card_view = (CardView) itemView.findViewById(R.id.TournamentCardView);
        game_category_image_view = (ImageView) itemView.findViewById(R.id.GameCategoryImageView);
        tournament_name_text_view = (TextView) itemView.findViewById(R.id.TournamentNameTextView);
        tournament_registration_close_date_text_view = (TextView) itemView.findViewById(R.id.TournamentRegistrationCloseDateTextView);
    }
}
