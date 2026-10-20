package tests;

import api.AuthApi;
import api.BookingApi;
import api.StatusCodes;
import com.shaft.driver.SHAFT;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Tests the booking service of https://restful-booker.herokuapp.com. Each test creates the booking it needs, so
 * the tests can run alone and in any order.
 */
public class BookingTests {

    private static final String BASE_URL = "https://restful-booker.herokuapp.com";

    private BookingApi bookingApi;

    @BeforeClass
    public void login() {
        SHAFT.API api = new SHAFT.API(BASE_URL);
        new AuthApi(api).login("admin", "password123");
        bookingApi = new BookingApi(api);
    }

    @Test
    public void createBooking() {
        bookingApi.createBooking("Jim", "Brown", "Breakfast");
        bookingApi.validateBookingCreation("Jim", "Brown", "Breakfast");
    }

    @Test
    public void createdBookingCanBeFoundByName() {
        String lastName = "Findable" + System.currentTimeMillis();
        String bookingId = bookingApi.createBooking("Sally", lastName, "Dinner");

        bookingApi.searchByName("Sally", lastName);

        bookingApi.validateBookingIsListed(bookingId);
    }

    @Test
    public void deletedBookingIsGone() {
        String bookingId = bookingApi.createBooking("Jim", "Brown", "Breakfast");

        bookingApi.deleteBooking(bookingId);

        bookingApi.getBooking(bookingId, StatusCodes.NOT_FOUND);
    }
}