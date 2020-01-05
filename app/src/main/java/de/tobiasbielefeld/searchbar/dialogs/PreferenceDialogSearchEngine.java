/*
 * Copyright (C) 2017  Tobias Bielefeld
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * If you want to contact me, send me an e-mail at tobias.bielefeld@gmail.com
 */

package de.tobiasbielefeld.searchbar.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import de.tobiasbielefeld.searchbar.R;

import static de.tobiasbielefeld.searchbar.SharedData.*;

/**
 * Acts like a normal list preference, but when pressing the "custom" entry, a new dialog shows
 * to enter a custom search engine. In that case, the entered url will be shown as the summary.
 */

public class PreferenceDialogSearchEngine extends ListPreference{


    private int clickedDialogEntryIndex = -1;

    public PreferenceDialogSearchEngine(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        View view = super.onCreateView(parent);

        updateSummary();

        return view;
    }

    /**
     * If a custom search engine was selected, show its url, else show the title of the engine
     */
    private void updateSummary(){
        int d = getContext().getResources().getInteger(R.integer.default_search_engine_v2);
        String selectedValue = getPersistedString(d + "");
        int index = findIndexOfValue(selectedValue);

        if (index < 3){    //custom search engine
            String summary = getCustomSearchUrl(index);
            summary = summary.replace("%","%%");    //used to escape the %-character in the summary
            setSummary(summary);
        } else {            //a search engine from the list
            String[] searchUris = getContext().getResources().getStringArray(R.array.search_engine_uris);
            setSearchUrl(searchUris[index]);
            setSummary(getEntries()[index]);
        }
    }

    /**
     * THe user can enter a custom search engine url here
     */
    private void showCustomSearchEngineDialog(final int which){


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.custom_search_engine_dialog, null);


        final EditText customEditText = v.findViewById(R.id.custom_search_url);
        customEditText.setText(getCustomSearchUrl(which));

        builder.setView(v);
        builder.setTitle(R.string.dialog_custom_search_engine_title)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String text = customEditText.getText().toString();
                        setSearchUrl(text);
                        setCustomSearchUrl(which, text);
                        clickedDialogEntryIndex = which;
                        getDialog().dismiss();
                    }})
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //cancel
                    }});

        builder.show();
    }

    /*
     * Returns the url for the nth custom search.
     * If there is no url stored for the nth search, the current chosen search
     * url is provided
     */
    private String getCustomSearchUrl(int n) {
        return getSavedString(customSearchPrefkey(n), getSearchUrl());
    }

    /*
     * Sets the url for the nth custom search
     */
    private void setCustomSearchUrl(int n, String url) {
        putSavedString(customSearchPrefkey(n), url);
    }

    /*
     * Returns the prefkey for the nth custom Search url
     */
    private String customSearchPrefkey(int n) {
        return n > 0 ? PREF_CUSTOM_SEARCH_URL + String.valueOf(n) : PREF_CUSTOM_SEARCH_URL;
    }





    /*
     * Change what happens when pressing an entry. If "Custom" was clicked, show its dialog,
     * else act like a normal list dialog
     */
    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        clickedDialogEntryIndex = findIndexOfValue(getValue());

        String[] entries = getContext().getResources().getStringArray(R.array.pref_search_engine_titles);

        entries[0] += " I";
        entries[1] += " II";
        entries[2] += " III";

        builder.setSingleChoiceItems(entries, clickedDialogEntryIndex,
                new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int which) {
                        if(which < 3){
                            showCustomSearchEngineDialog(which);
                        }
                        else{
                            clickedDialogEntryIndex = which;
                            dialog.dismiss();
                        }
                    }
                });

        builder.setPositiveButton(null, null);
    }

    /*
     * Saves the selected value when closing the dialog. If none was selected, the
     * clickedDialogEntryIndex is still -1, so nothing happens
     */
    @Override
    protected void onDialogClosed(boolean positiveResult) {

        if (clickedDialogEntryIndex >= 0) {
            String value = getEntryValues()[clickedDialogEntryIndex].toString();

            if (callChangeListener(value)) {
                setValue(value);
                updateSummary();
            }
        }
    }
}
