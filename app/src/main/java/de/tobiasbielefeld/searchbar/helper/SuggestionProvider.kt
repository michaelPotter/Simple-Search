package de.tobiasbielefeld.searchbar.helper;

import java.net.HttpURLConnection
import java.io.InputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import de.tobiasbielefeld.searchbar.Suggestion

import de.tobiasbielefeld.searchbar.SharedData.*;

class SuggestionProvider() {

	/*
	 * Returns an array of suggestion-like objects
	 */
	fun getSuggestions(searchText: String) : Array<Suggestion> {
		val jsonText = runSearch(searchText)
		val gson = Gson()
		val phraseType = object : TypeToken<Array<Suggestion>>() {}.type
		val suggestions : Array<Suggestion> = gson.fromJson(jsonText, phraseType)
		return suggestions
	}

	/*
	 * Runs the web search and returns the javascript string
	 */
	private fun runSearch(text: String) : String {
		val baseURL = getSuggestionUrl()
		val url = URL(baseURL.replace("%s", text))
		val connection = url.openConnection()
		connection.setRequestProperty("User-Agent", "curl/7.65.1")

		val reader = BufferedReader(InputStreamReader(connection.inputStream))

		val response = StringBuilder()

		var line = reader.readLine()
		while (line != null) {
			response.append(line)
			line = reader.readLine()
		}
		reader.close()

		return response.toString()
	}

}
