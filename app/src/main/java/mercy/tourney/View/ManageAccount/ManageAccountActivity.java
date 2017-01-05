package mercy.tourney.View.ManageAccount;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import mercy.tourney.R;

public class ManageAccountActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (findViewById(R.id.ManageAccountFragmentContainer) != null) {
            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ExampleFragment
            ManageProfileFragment fragment = new ManageProfileFragment();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            fragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'AuthFragmentContainer' FrameLayout
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.ManageAccountFragmentContainer, fragment)
                    .commit();
        }
    }

    public void doChangeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.ManageAccountFragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
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
