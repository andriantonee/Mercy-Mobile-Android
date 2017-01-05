package mercy.tourney.View.Portal;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import mercy.tourney.R;

public class PortalSearchResultActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal_search_result);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String keyword = intent.getStringExtra(SearchManager.QUERY);

            // Setting action bar title
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(keyword.concat(" search result"));

            // Add the fragment to the 'PortalSearchResultContainer' FrameLayout
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.PortalSearchResultContainer, TournamentFragment.newInstance(keyword, null, false))
                    .commit();
        } else {
            Bundle bundle = intent.getBundleExtra("TEAM");
            String title;
            Integer game_category = null;
            Boolean FLAG_TEAM = false;

            if (bundle == null) {
                game_category = intent.getBundleExtra("BUNDLE").getInt("GAME CATEGORY");

                switch (game_category) {
                    case 1:
                        title = "Dota 2";
                        break;
                    case 2:
                        title = "League of Legends";
                        break;
                    case 3:
                        title = "CS:GO";
                        break;
                    case 4:
                        title = "Overwatch";
                        break;
                    default:
                        title = "All Games";
                }
            } else {
                String team_name = bundle.getString("TEAM NAME");

                title = team_name.concat(" - Tournament List");
                FLAG_TEAM = true;
            }

            // Setting action bar title
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(title);

            // Add the fragment to the 'PortalSearchResultContainer' FrameLayout
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.PortalSearchResultContainer, TournamentFragment.newInstance(null, game_category, FLAG_TEAM))
                    .commit();
        }
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
