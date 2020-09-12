package de.tobiasbielefeld.searchbar.storage;

import java.util.List;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;

@Dao
public interface RecordDao {
	@Query("SELECT * FROM record ORDER BY id DESC")
	List<Record> getAll();

	@Delete
	void delete(Record record);

	@Query("DELETE FROM record")
	void deleteAll();

	@Insert
	void add(Record record);
}
