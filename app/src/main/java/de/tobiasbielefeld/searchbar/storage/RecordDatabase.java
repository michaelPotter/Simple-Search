package de.tobiasbielefeld.searchbar.storage;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Record.class}, version = 1)
public abstract class RecordDatabase extends RoomDatabase {
	public abstract RecordDao recordDao();
}
