package pl.edu.agh.domain.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import pl.edu.agh.dbmodel.common.BaseObjectMapping;

/**
 * Created by Krzysiu on 2014-09-14.
 */
public abstract class GlobalBaseObject extends BaseObject {

    @DatabaseField(columnName = BaseObjectMapping.GLOBAL_ID_COLUMN_NAME, canBeNull = true, unique = true)
    private long globalId;

    @JsonProperty(value = "id")
    public long getGlobalId() {
        return globalId;
    }

    @JsonProperty(value = "id")
    public void setGlobalId(long globalId) {
        this.globalId = globalId;
    }
}
