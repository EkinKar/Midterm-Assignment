import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;


public class User {

    int userID;
    String name;
    String username;
    private String password;
    Date birthday;
    String school;
    Date logInDate;
    ArrayList<User> friends = new ArrayList<>();
    ArrayList<User> blocked = new ArrayList<>();
    ArrayList<User> blocked_friends = new ArrayList<>();
    ArrayList<Post> txt_vid_pic_post = new ArrayList<>();


    public User(int userID, String name, String username, String password, String school) {
        this.userID = userID;
        this.name = name;
        this.username = username;
        this.password = password;
        this.school = school;
    }


    public User() {
    }


    static int id = 1;
    static User currentUser = null; // user that already logged in.
    static ArrayList<User> userList = new ArrayList<>(); // list that contains every user.
    static User find_user = null; // the variable that will be used in find by username method.


    public static void read() throws IOException, ParseException { // reads the user.txt and creates user objects.
        BufferedReader read_buffer = new BufferedReader(new FileReader("users.txt"));
        String strRead;
        while ((strRead = read_buffer.readLine()) != null) {
            String[] split_array = strRead.split("\t");
            User u = new User(id, split_array[0], split_array[1], split_array[2], split_array[4]);
            SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
            u.birthday = date.parse(split_array[3]);
            userList.add(u);  // adding created objects to user list.
            id++;
        }
    }

    public static void find_from_username(String username) { // finds users object from their username.
        find_user = null;
        for (User user : userList) {
            if (user.username.equals(username)) {
                find_user = user;
            }
        }
        if (find_user == null){
            System.out.println("There is no such user!");
        }
    }


    public String getPassword() {
        return password;
    }


    public static void setPassword(String old_password, String new_password) { // password changer method.
        if (old_password.equals(currentUser.getPassword())) {
            currentUser.password = new_password;
            System.out.println("Password changed successfully!");
        }
        else {
            System.out.println("Password mismatch!");
        }
    }


    public static void signIn() {
        currentUser = find_user;
        currentUser.logInDate = new Date();
    }


    public static void add_user(String name, String username, String password,
                                String birthday, String school) throws ParseException {
        id++;
        User new_user = new User();
        new_user.name = name;
        new_user.username = username;
        new_user.password = password;
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        new_user.birthday = formatter.parse(birthday);
        new_user.school = school;
        userList.add(new_user);
    }


    public static void remove_user() {
        userList.remove(find_user);
    }


    public static void list_all() { // lists everyone in the user list.
        for (User user : userList) {
            if (!user.name.equals("a")) {
                System.out.println("Name: " + user.name);
                System.out.println("Username: " + user.username);
                String dateString = new SimpleDateFormat("dd/MM/yyyy").format(user.birthday);
                System.out.println("Date of birth: " + dateString);
                System.out.println("School: " + user.school);
                System.out.println("-----------------------");
            }
        }
    }


    public static void update_profile(String new_name, String new_birthday, String new_school) throws ParseException {
        // updates the current users attributes.
        currentUser.name = new_name;
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        currentUser.birthday = formatter.parse(new_birthday);
        currentUser.school = new_school;
        System.out.println("Changes saved successfully!");
    }


    public static void listFriends() { // lists friends of the current user.
        System.out.println(currentUser.name + "'s friends are: ");
        for (int i = 0; i < currentUser.friends.size(); i++) {
            System.out.println("Name: " + currentUser.friends.get(i).name);
            System.out.println("Username: " + currentUser.friends.get(i).username);
            String dateString = new SimpleDateFormat("dd/MM/yyyy").format(currentUser.friends.get(i).birthday);
            System.out.println("Date of birth: " + dateString);
            System.out.println("School: " + currentUser.friends.get(i).school);
            System.out.println("-----------------------");
        }
    }


    public static void show_posts() { // lists the posts of the current user according to their subclass.
        for (int i = 0; i < find_user.txt_vid_pic_post.size(); i++){
            System.out.println(find_user.txt_vid_pic_post.get(i).text);
            System.out.println("Date: " + find_user.txt_vid_pic_post.get(i).share_date);
            System.out.println("Location: " + find_user.txt_vid_pic_post.get(i).post_loc.latitude + "  " +
                    find_user.txt_vid_pic_post.get(i).post_loc.longitude);
            System.out.print("Friends tagged in this post: ");
            for (int j = 0; j < find_user.txt_vid_pic_post.get(i).tagged_friends.size(); j++) {
                System.out.print(find_user.txt_vid_pic_post.get(i).tagged_friends.get(j).name + "  ");
            }
            System.out.println();
            if (find_user.txt_vid_pic_post.get(i) instanceof Video){ // checks if the post is a member of video subclass.
                System.out.println("Video: " + Video.video_name);
                System.out.println("Video Duration: " + Video.duration + " minutes");
            }
            else if (find_user.txt_vid_pic_post.get(i) instanceof Picture){ // checks if the post is a member of picture subclass.
                System.out.println("Image: " + Picture.pic_name);
                System.out.println("Image resolution: " + Picture.size);
            }
            System.out.println("-----------------------");
        }
    }


    public static void blockUser() { // blocks users if the user exists and not blocked yet.
        if (currentUser.blocked.contains(find_user)){
            System.out.println("User is already blocked");
        }
        else {
            currentUser.blocked.add(find_user);
            System.out.println(find_user.name + " has been successfully blocked.");
            if (currentUser.friends.contains(find_user)) {
                currentUser.blocked_friends.add(find_user);
            }
        }
    }


    public static void listBlockedFriends() { // lists the blocked friends of the current user.
        System.out.println(currentUser.name + " blocked friends: ");
        for (int i = 0; i < currentUser.blocked_friends.size(); i++) {
            System.out.println("Name: " + currentUser.blocked_friends.get(i).name);
            System.out.println("Username: " + currentUser.blocked_friends.get(i).username);
            String dateString = new SimpleDateFormat("dd/MM/yyyy").format(currentUser.blocked_friends.get(i).birthday);
            System.out.println("Date of birth: " + dateString);
            System.out.println("School: " + currentUser.blocked_friends.get(i).school);
            System.out.println("-----------------------");
        }
    }


    public static void unblock_user() { // unblocks the username that is found by find user method.
        if (currentUser.blocked.contains(find_user)){
            currentUser.blocked.remove(find_user);
            currentUser.blocked_friends.remove(find_user);
            System.out.println(find_user.name + " has been successfully unblocked.");
        }
        else{
            System.out.println("No such user in your blocked-user list!");
        }
    }


    public static void listBlocked() { // lists the blocked user list of the current user.
        System.out.println(currentUser.name + "'s blocked list: ");
        for (int i = 0; i < currentUser.blocked.size(); i++) {
            System.out.println("Name: " + currentUser.blocked.get(i).name);
            System.out.println("Username: " + currentUser.blocked.get(i).username);
            String dateString = new SimpleDateFormat("dd/MM/yyyy").format(currentUser.blocked.get(i).birthday);
            System.out.println("Date of birth: " + dateString);
            System.out.println("School: " + currentUser.blocked.get(i).school);
            System.out.println("-----------------------");
        }
    }
}