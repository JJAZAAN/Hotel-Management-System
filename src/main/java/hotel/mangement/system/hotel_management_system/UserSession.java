package hotel.mangement.system.hotel_management_system;


/*
How the roomDisplay, UserLogin and UserSession works

The user is shown the roomDisplay page
	the user clicks book
	if the user is not logged in
		-> Takes the user to the login page
		-> Sets the room details in UserSession and sets bookingcontext to true
			-> The user logsin
				-> if it checks isbookingcontext to see if bookingcontext is true.
					-> if it is then it runs bookingRoom page and gets the room details from UserSession that were previously set when the user clicks book

	if the user is logged in
		-> The user is shown the bookingRoom page
 */

public class UserSession {

    // Tracks whether a user is logged in
    private static boolean loggedIn = false;
    private static boolean bookingContext = false;
    private static String roomType;
    private static String bedType;
    private static String fee;
    private static java.sql.Date checkInDate;
    private static java.sql.Date checkOutDate;
    private static int userid;

    // Getter to check if the user is logged in
    public static boolean isLoggedIn() {
        return loggedIn;
    }

    // Method to log in a user
    public static void login() {
        loggedIn = true;
    }

    // Method to log out a user
    public static void logout() {
        loggedIn = false;
        bookingContext = false; // Reset context on logout
        roomType = null;
        bedType = null;
        fee = null;
        checkInDate = null;
        checkOutDate = null;
    }

    // Methods to handle booking context
    public static void setBookingContext(String roomType, String bedType, String fee, java.sql.Date checkInDate, java.sql.Date checkOutDate) {
        bookingContext = true;
        UserSession.roomType = roomType;
        UserSession.bedType = bedType;
        UserSession.fee = fee;
        UserSession.checkInDate = checkInDate;
        UserSession.checkOutDate = checkOutDate;
    }

    public static boolean isBookingContext() {
        return bookingContext;
    }

    public static String getRoomType() {
        return roomType;
    }

    public static String getBedType() {
        return bedType;
    }

    public static String getFee() {
        return fee;
    }

    public static java.sql.Date getCheckInDate() {
        return checkInDate;
    }

    public static java.sql.Date getCheckOutDate() {
        return checkOutDate;
    }

    public static void setUserid(int userid) {
        UserSession.userid = userid;
    }

    public static int getUserid() {
        return userid;
    }

}
