package de.tobiasbielefeld.searchbar.helper;

import java.util.List;

// import android.database.sqlite;

import static de.tobiasbielefeld.searchbar.SharedData.*;

public class RecordsManager {
	// SQLiteDatabase db;

	public RecordsManager() {
		// db = openOrCreateDatabase("records.sql", MODE_PRIVATE, null);
		// db.execSQL("CREATE TABLE history (id INTEGER PRIMARY KEY, record TEXT);");
	}

	// public List<String> getRecords() {

	// 	// Cursor results = db.rawQuery("SELECT * from history", null);
	// 	results.moveToFirst();

	// 	List<String> records = new List<String>();

        // if (!recordsEnabled())
            // return records;

        // // int recordListLength = getSavedInt(PREF_RECORD_LIST_SIZE, 0);

        // // for (int i = 0; i < recordListLength; i++) {
        // //     String text = getSavedString(PREF_RECORD_ENTRY + i, "");
        // //     records.add(text);
        // // }

	// 	return records;
	// }

	public void addRecord(String r) {
	}

	/**
	 * Deletes the given record from history
	 */
	public void deleteRecord(String r) {
	}

	public void deleteAllRecords() {
	}

	private void createDatabase() {
		final String CREATE_TABLE = "CREATE TABLE history (id INTEGER PRIMARY KEY, record TEXT);";
	}

}
