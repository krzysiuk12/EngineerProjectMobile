package pl.edu.agh.domain.locations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import pl.edu.agh.dbmodel.locations.CommentsMapping;
import pl.edu.agh.domain.accounts.UserAccount;

import java.util.Date;

/**
 * Created by Magda on 2014-11-23.
 */
@DatabaseTable(tableName= CommentsMapping.TABLE_NAME)
public class Comment {

	public enum Rating {
		VERY_BAD(1),
		BAD(2),
		OK(3),
		GOOD(4),
		EXCELLENT(5);

		private int value;

		Rating(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(columnName = CommentsMapping.USERACCOUNT_COLUMN_NAME, canBeNull = false, foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	private UserAccount userAccount;

	@DatabaseField(columnName = CommentsMapping.LOCATION_COLUMN_NAME, canBeNull = false, foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	private Location location;

	@DatabaseField(columnName = CommentsMapping.RATING_COLUMN_NAME, canBeNull = false)
	private Rating rating;

	@DatabaseField(columnName = CommentsMapping.COMMENT_COLUMN_NAME)
	private String comment;

	@DatabaseField(columnName = CommentsMapping.DATE_COLUMN_NAME)
	private Date date;

	public Comment() { }

	// <editor-fold desc="Getters and Setter">

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	// </editor-fold>
}
