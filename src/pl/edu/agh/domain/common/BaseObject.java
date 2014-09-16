package pl.edu.agh.domain.common;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Krzysiu on 2014-09-14.
 */
public abstract class BaseObject {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
