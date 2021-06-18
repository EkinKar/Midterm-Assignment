import java.util.ArrayList;
import java.util.Date;

public class Post {

    Location post_loc;
    String text;
    ArrayList<User> tagged_friends = new ArrayList<>();
    Date share_date;

    public static class Location {
        double latitude;
        double longitude;


        public Location(double latitude, double longitude){
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}