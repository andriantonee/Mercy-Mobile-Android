package mercy.tourney.View.Tournament;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mercy.tourney.R;

public class TournamentContentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_content);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        Integer tournament_id = bundle.getInt("TOURNAMENT ID");

        getSupportFragmentManager().beginTransaction().add(R.id.TournamentContentContainer, TournamentContentFragment.newInstance(tournament_id)).commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        //This method is called when the up button is pressed. Just the pop back stack.
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            this.finish();
        }

        return true;
    }
}
