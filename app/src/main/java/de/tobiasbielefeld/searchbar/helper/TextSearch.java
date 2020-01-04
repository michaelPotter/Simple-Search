
package de.tobiasbielefeld.searchbar.helper;

import android.content.Intent;
import android.os.Bundle;

import de.tobiasbielefeld.searchbar.classes.CustomAppCompatActivity;
import de.tobiasbielefeld.searchbar.helper.WebSearch;

import static de.tobiasbielefeld.searchbar.SharedData.*;

/**
 * This class is to provide searching from the text highlight menu.
 */
public class TextSearch extends CustomAppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CharSequence text = getIntent()
			.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
		WebSearch.webSearch(text.toString().trim(), this);
	}

}
