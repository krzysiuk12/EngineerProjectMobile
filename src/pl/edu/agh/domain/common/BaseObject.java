package pl.edu.agh.domain.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by Magda on 2014-12-26.
 */

public abstract class BaseObject implements Serializable {

	@JsonIgnore
	@DatabaseField(columnName = "id", generatedId = true)
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
