package pl.edu.agh.serializers;

/**
 * Created by Krzysztof Kicinger on 2014-11-17.
 */
public class GoogleGeocodeSerializer {

	private String address;
	private String region;
	private String components;

	public GoogleGeocodeSerializer() {
	}

	public GoogleGeocodeSerializer(String address, String region, String components) {
		this.address = address;
		this.region = region;
		this.components = components;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getComponents() {
		return components;
	}

	public void setComponents(String components) {
		this.components = components;
	}
}
