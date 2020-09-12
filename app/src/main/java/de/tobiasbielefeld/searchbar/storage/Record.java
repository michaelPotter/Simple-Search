package de.tobiasbielefeld.searchbar.storage;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Record {
	@PrimaryKey(autoGenerate = true)
	public int id;
	
	@ColumnInfo(name = "record")
	public String record;

	public Record(String record) {
		this.record = record;
	}

	public String toString() {
		return this.record;
	}
}
