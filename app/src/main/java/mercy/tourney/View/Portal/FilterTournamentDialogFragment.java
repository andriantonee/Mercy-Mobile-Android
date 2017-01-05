package mercy.tourney.View.Portal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import mercy.tourney.R;

/**
 * Created by Andrianto on 12/16/2016.
 */
@SuppressLint("ValidFragment")
public class FilterTournamentDialogFragment extends DialogFragment {
    /**
     * Declare Component in XML Layout
     */
    NumberPicker game_category_number_picker;
    Button cancel_button, filter_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_dialog_filter_tournament, container, false);

        // Set Dialog Fragment Title
        getDialog().setTitle("Filter");

        // Initialize Component from View
        game_category_number_picker = (NumberPicker) _view.findViewById(R.id.NumberPickerGameCategory);
        game_category_number_picker.setMinValue(1);
        game_category_number_picker.setMaxValue(4);
        game_category_number_picker.setDisplayedValues(new String[]{"Dota 2", "League of Legends", "CS:GO", "Overwatch"});
        cancel_button = (Button) _view.findViewById(R.id.ButtonCancel);
        filter_button = (Button) _view.findViewById(R.id.ButtonFilter);

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        filter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PortalSearchResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("GAME CATEGORY", game_category_number_picker.getValue());
                intent.putExtra("BUNDLE", bundle);
                startActivity(intent);
                dismiss();
            }
        });

        return _view;
    }
}
