package utils;

import java.util.ArrayList;

import model.User;

public class Utils {
	private ArrayList<User> users = new ArrayList<User>();
	
	private void generateUsers() {
		users.add(new User("user1", "password1"));
		users.add(new User("user2", "password2"));
		users.add(new User("user3", "password3"));
		users.add(new User("user4", "password4"));
		users.add(new User("user5", "password5"));
	}
	
	private User getUserFromUsername(String username) {
		for(User user: users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}
}
