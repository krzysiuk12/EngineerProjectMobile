package pl.edu.agh.serializers;

import pl.edu.agh.domain.locations.Location;

/**
 * Created by Krzysztof Kicinger on 2014-11-17.
 */
public class LocationSerializer {

	private String name;
	private String description;
	private double latitude;
	private double longitude;
	private Location.Status status;
	private String addressStreet;
	private String addressPostalCode;
	private String addressCity;
	private String addressCountry;
	private String url;

	public LocationSerializer() {
	}

	public LocationSerializer(String name, String description, String url, double latitude, double longitude, Location.Status status, String addressStreet, String addressPostalCode, String addressCity, String addressCountry) {
		this.name = name;
		this.description = description;
		this.url = url;
		this.latitude = latitude;
		this.longitude = longitude;
		this.status = status;
		this.addressStreet = addressStreet;
		this.addressPostalCode = addressPostalCode;
		this.addressCity = addressCity;
		this.addressCountry = addressCountry;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getAddressCountry() {
		return addressCountry;
	}

	public void setAddressCountry(String addressCountry) {
		this.addressCountry = addressCountry;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddressStreet() {
		return addressStreet;
	}

	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}

	public String getAddressPostalCode() {
		return addressPostalCode;
	}

	public void setAddressPostalCode(String addressPostalCode) {
		this.addressPostalCode = addressPostalCode;
	}

	public Location.Status getStatus() {
		return status;
	}

	public void setStatus(Location.Status status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static LocationSerializer buildLocationSerializerFromLocation(Location location) {
		LocationSerializer serializer = new LocationSerializer();
		serializer.setName(location.getName());
		serializer.setLatitude(location.getLatitude());
		serializer.setLongitude(location.getLongitude());
		serializer.setDescription(location.getDescription());
		serializer.setUrl(location.getUrl());
		serializer.setStatus(location.getStatus());

		if ( location.getAddress() != null ) {
			serializer.setAddressCountry(location.getAddress().getCountry());
			serializer.setAddressCity(location.getAddress().getCity());
			serializer.setAddressStreet(location.getAddress().getStreet());
			serializer.setAddressPostalCode(location.getAddress().getPostalCode());
		}
		return serializer;
	}
}
