import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class Command {

    public static void read() throws IOException, ParseException { // reads the commands.txt line by line.
        BufferedReader read_buffer = new BufferedReader(new FileReader("commands.txt"));
        String strRead;
        while ((strRead = read_buffer.readLine()) != null) {
            String[] split_array = strRead.split("\t");

            if (split_array[0].equals("ADDUSER")) { // calls the add user method if all the parameters are provided.
                System.out.println("Command: " + strRead);
                if (split_array.length == 6) {
                    User.add_user(split_array[1], split_array[2], split_array[3], split_array[4], split_array[5]);
                    System.out.println(split_array[1] + " has been successfully added.");
                }
                else {
                    System.out.println("Please provide all the necessary information!");
                }
                System.out.println("-----------------------");
            }


            else if (split_array[0].equals("REMOVEUSER")) { // calls the remove user method if there is a user with the provided user id.
                System.out.println("Command: " + strRead);
                boolean user_found = false;
                for (int i = 0; i < User.userList.size(); i++) {
                    if (User.userList.get(i).userID == Integer.parseInt(split_array[1])) {
                        User.find_user = User.userList.get(i);
                        User.remove_user();
                        System.out.println("User has been successfully removed.");
                        user_found = true;
                    }
                }
                if (!user_found){
                    System.out.println("There is no such user!");
                }
                System.out.println("-----------------------");
            }


            else if (split_array[0].equals("SIGNIN")) { // calls the sign in method if such user exists.
                System.out.println("Command: " + strRead);
                User.find_from_username(split_array[1]);
                if (!(User.find_user == null)) {
                    if (User.find_user.getPassword().equals(split_array[2])) {
                        User.signIn();
                        System.out.println("You have successfully signed in.");
                    } else if (!User.find_user.getPassword().equals(split_array[2])) {
                        System.out.println("Wrong Password!");
                    }
                }
                System.out.println("-----------------------");
            }


            else if (split_array[0].equals("LISTUSERS")) { // calls the list all method.
                System.out.println("Command: " + strRead);
                if (!(User.currentUser == null)) {
                    User.list_all();
                } else {
                    System.out.println("Please login and try again!");
                }
            }


            else if (split_array[0].equals("UPDATEPROFILE")) { // updates the attributes of the current user if all the information is provided.
                System.out.println("Command: " + strRead);
                if (!(User.currentUser == null)) {
                    if (split_array.length == 4) {
                        User.update_profile(split_array[1], split_array[2], split_array[3]);
                    } else {
                        System.out.println("Please provide all the information!");
                    }
                }
                else {
                    System.out.println("Please login and try again!");
                }
                System.out.println("-----------------------");
            }


            else if (split_array[0].equals("CHPASS")) { // calls the set password method.
                System.out.println("Command: " + strRead);
                if (!(User.currentUser == null)) {
                    User.setPassword(split_array[1], split_array[2]);
                }
                else {
                    System.out.println("Please login and try again!");
                }
                System.out.println("-----------------------");
            }


            else if (split_array[0].equals("ADDFRIEND")) { // adds user to current user's friend list if such user exists and if not already a friend.
                System.out.println("Command: " + strRead);
                if (!(User.currentUser == null)) {
                    User.find_from_username(split_array[1]);
                    if (!(User.find_user == null)) {
                        if (User.currentUser.friends.contains(User.find_user)) {
                            System.out.println(split_array[1] + " is already in your friend list!");
                        } else {
                            User.currentUser.friends.add(User.find_user);
                            System.out.println(split_array[1] + " has been successfully added to your friend list.");
                        }
                    }
                }
                else {
                    System.out.println("Please login and try again!");
                }
                System.out.println("-----------------------");
            }


            else if (split_array[0].equals("REMOVEFRIEND")) { // removes the user from current user's friend list if such user exists.
                if (!(User.currentUser == null)) {
                    System.out.println("Command: " + strRead);
                    User.find_from_username(split_array[1]);
                    if (!(User.find_user == null)) {
                        if (!User.currentUser.friends.contains(User.find_user)) {
                            System.out.println("No such friend!");
                        } else {
                            User.currentUser.friends.remove(User.find_user);
                            System.out.println(User.find_user.name + " has been successfully removed from your friend list.");
                        }
                    }
                }
                else {
                    System.out.println("Please login and try again!");
                }
                System.out.println("-----------------------");
            }


            else if (split_array[0].equals("LISTFRIENDS")){ // calls the lists friends method if the current user has at least one friend.
                System.out.println("Command: " + strRead);
                if (!(User.currentUser == null)) {
                    if (User.currentUser.friends.size() == 0) {
                        System.out.println("You have not added any friend yet!");
                        System.out.println("-----------------------");
                    }
                    else {
                        User.listFriends();
                    }
                }
                else {
                    System.out.println("Please login and try again!");
                    System.out.println("-----------------------");
                }
            }


            else if (split_array[0].equals("ADDPOST-TEXT")){ // adds a new text post.
                System.out.println("Command: " + strRead);
                if (!(User.currentUser == null)) {
                Post new_txt_post = new Post();
                Post.Location ll = new Post.Location(Double.parseDouble(split_array[2]),
                        Double.parseDouble(split_array[3]));
                new_txt_post.text = split_array[1];
                new_txt_post.post_loc = ll;
                String[] tagfriend = split_array[4].split(":");
                for (String name : tagfriend) {
                    User.find_from_username(name);
                    if (!(User.find_user == null)) {
                        if (User.currentUser.friends.contains(User.find_user)) {// checks if the user is current user's friend.
                            new_txt_post.tagged_friends.add(User.find_user);
                        }
                        else {
                            System.out.println(name + " is not your friend, and will not be tagged! ");
                        }
                    }
                }
                new_txt_post.share_date = new Date();
                User.currentUser.txt_vid_pic_post.add(new_txt_post);
                System.out.println("The post has been successfully added.");
                }
                else {
                    System.out.println("Please login and try again!");
                }
                System.out.println("-----------------------");
            }


            else if (split_array[0].equals("ADDPOST-IMAGE")){ // adds a new image post.
                System.out.println("Command: " + strRead);
                if (!(User.currentUser == null)) {
                Picture new_picture_post = new Picture();
                Post.Location ll = new Post.Location(Double.parseDouble(split_array[2]),
                        Double.parseDouble(split_array[3]));
                new_picture_post.text = split_array[1];
                new_picture_post.post_loc = ll;
                Picture.size =split_array[6];
                Picture.pic_name = split_array[5];
                String[] tag_friend = split_array[4].split(":");
                for (String name : tag_friend) {
                    User.find_from_username(name);
                    if (User.currentUser.friends.contains(User.find_user)) { // checks if the user is current user's friend.
                        new_picture_post.tagged_friends.add(User.find_user);
                    }
                    else {
                        System.out.println(name + " is not your friend, and will not be tagged! ");
                    }
                }
                new_picture_post.share_date = new Date();
                User.currentUser.txt_vid_pic_post.add(new_picture_post);
                System.out.println("The post has been successfully added.");
                }
                else {
                    System.out.println("Please login and try again!");
                }
                System.out.println("-----------------------");
            }


            else if (split_array[0].equals("ADDPOST-VIDEO")){// adds a new video post.
                System.out.println("Command: " + strRead);
                if (!(User.currentUser == null)) {
                    if (Integer.parseInt(split_array[6]) > Video.max_duration){
                        System.out.println("This video is too long to update, post won't be added.");
                    }
                    else {
                        Video new_video_post = new Video();
                        Post.Location ll = new Post.Location(Double.parseDouble(split_array[2]),
                                Double.parseDouble(split_array[3]));
                        new_video_post.text = split_array[1];
                        new_video_post.post_loc = ll;
                        Video.duration = Integer.parseInt(split_array[6]);
                        Video.video_name = split_array[5];
                        String[] tag_friend = split_array[4].split(":");
                        for (String name : tag_friend) {
                            User.find_from_username(name);
                            if (User.currentUser.friends.contains(User.find_user)) {// checks if the user is current user's friend.
                                new_video_post.tagged_friends.add(User.find_user);
                            }
                            else {
                                System.out.println(name + " is not your friend, and will not be tagged! ");
                            }
                        }
                        new_video_post.share_date = new Date();
                        User.currentUser.txt_vid_pic_post.add(new_video_post);
                        System.out.println("The post has been successfully added.");
                    }
                }
                else {
                    System.out.println("Please login and try again!");
                }
                System.out.println("-----------------------");
            }


            else if (split_array[0].equals("REMOVELASTPOST")){
                System.out.println("Command: " + strRead);
                if (!(User.currentUser == null)) {
                    if (User.currentUser.txt_vid_pic_post.size() == 0) {
                        System.out.println(" You do not have any post.");
                    } else {
                        int size = User.currentUser.txt_vid_pic_post.size() - 1;
                        User.currentUser.txt_vid_pic_post.remove(size);
                        System.out.println("Your last post has been successfully removed.");
                    }
                }
                else {
                    System.out.println("Please login and try again!");
                }
                System.out.println("-----------------------");
            }


            else if (split_array[0].equals("SHOWPOSTS")) { // shows posts of the provided user name if such user exists.
                System.out.println("Command: " + strRead);
                User.find_from_username(split_array[1]);
                if (!(User.find_user == null)) {
                    if (User.find_user.txt_vid_pic_post.size() == 0) {
                        System.out.println(User.find_user.name + " has no posts.");
                        System.out.println("-----------------------");
                    }
                    else {
                        System.out.println("**************");
                        System.out.println(User.find_user.name + "'s Posts: ");
                        System.out.println("**************");
                        User.show_posts();
                    }
                }
                else {
                    System.out.println("-----------------------");
                }
            }


            else if (split_array[0].equals("BLOCK")) { //
                System.out.println("Command: " + strRead);
                if (!(User.currentUser == null)) {
                    User.find_from_username(split_array[1]);
                    if (!(User.find_user == null)){
                        User.blockUser();
                    }
                }
                else {
                    System.out.println("Please login and try again!");
                }
                System.out.println("-----------------------");
            }


            else if (split_array[0].equals("SHOWBLOCKEDFRIENDS")){ // shows the blocked friend list of current user if there are any.
                System.out.println("Command: " + strRead);
                if (!(User.currentUser == null)) {
                    if (User.currentUser.blocked_friends.size() == 0) {
                        System.out.println("You don't have any blocked friends!");
                        System.out.println("-----------------------");
                    } else {
                        User.listBlockedFriends();
                    }
                }
                else {
                    System.out.println("Please login and try again!");
                    System.out.println("-----------------------");
                }
            }


            else if (split_array[0].equals("UNBLOCK")){ // removes the provided username from the current users blocked list if such user exists.
                System.out.println("Command: " + strRead);
                if (!(User.currentUser == null)) {
                    User.find_from_username(split_array[1]);
                    if (!(User.find_user == null)){
                        User.unblock_user();
                    }
                }
                else {
                    System.out.println("Please login and try again!");
                }
                System.out.println("-----------------------");
            }


            else if (split_array[0].contains("SHOWBLOCKEDUSERS")){ // shows blocked users of the current user if there are any.
                System.out.println("Command: " + strRead);
                if (!(User.currentUser == null)) {
                    if (User.currentUser.blocked.size() == 0) {
                        System.out.println("You don't have anyone in your blocked list!");
                    }
                    else {
                        User.listBlocked();
                    }
                }
                else {
                    System.out.println("Please login and try again!");
                    System.out.println("-----------------------");
                }
            }


            else if (split_array[0].equals("SIGNOUT")){ // sets current user's value as null. No command can work when there is no current user.
                System.out.println("Command: " + strRead);
                if (!(User.currentUser == null)) {
                User.currentUser = null;
                System.out.println("You have successfully signed out.");
                }
                else {
                    System.out.println("Please login and try again!");
                }
                System.out.println("-----------------------");
            }
        }
    }
}