package modele;

public class Intersection {


    private Long Id;
    private double latitude;
    private double longitude;

    public Intersection(Long Id, double latitude, double longitude) {

        this.Id = Id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    
    
	@Override
	public String toString() {
		return "Intersection [Id=" + Id + ", latitude=" + latitude + ", longitude=" + longitude + "]";
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


    
    
}
