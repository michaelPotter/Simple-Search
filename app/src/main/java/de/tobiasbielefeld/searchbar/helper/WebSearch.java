
package de.tobiasbielefeld.searchbar.helper;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import de.tobiasbielefeld.searchbar.R;

import static de.tobiasbielefeld.searchbar.SharedData.*;

/**
 * Simple class to run a web search
 */
public class WebSearch {

	public static void webSearch(String text, Context context) {
        Resources res = context.getResources();
        String baseUrl = getSavedString(PREF_SEARCH_URL, DEFAULT_SEARCH_URL);

        // workaround for the wrong google url
        if (baseUrl.equals("https://www.google.de/#q=%s")){
            baseUrl = "https://www.google.de/search?q=%s";
        } else if (baseUrl.equals("https://www.google.com/#q=%s")){
            baseUrl = "https://www.google.com/search?q=%s";
        }

        Uri searchUrl = null;

        // custom search url must contain the %s part
        if (!baseUrl.contains("%s")) {
            showToast(res.getString(R.string.string_doesnt_contain_placeholder), context);
            return;
        }

        try {
            //try to encode the string to a url. eg "this is a test" gets converted to "this+is+a+test"
            searchUrl = Uri.parse(baseUrl.replace("%s",URLEncoder.encode(text, "UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            showToast(res.getString(R.string.unsupported_search_character), context);
        }

        if (searchUrl != null) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, searchUrl);

            try {
                //try to start the browser, if there is one installed
                context.startActivity(browserIntent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                showToast(res.getString(R.string.unsupported_search_string), context);
            }
        }
	}

}
