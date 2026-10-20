package api;

import com.shaft.driver.SHAFT;
import io.restassured.http.ContentType;

/** The /booking service of Restful-Booker. */
public class BookingApi {

    private static final String SERVICE = "/booking";

    private final SHAFT.API api;

    public BookingApi(SHAFT.API api) {
        this.api = api;
    }

    /** Creates a booking and returns its id. */
    public String createBooking(String firstName, String lastName, String additionalNeeds) {
        String body = """
                {
                    "firstname" : "%s",
                    "lastname" : "%s",
                    "totalprice" : 111,
                    "depositpaid" : true,
                    "bookingdates" : {
                        "checkin" : "2018-01-01",
                        "checkout" : "2019-01-01"
                    },
                    "additionalneeds" : "%s"
                }
                """.formatted(firstName, lastName, additionalNeeds);
        api.post(SERVICE).setContentType(ContentType.JSON).setRequestBody(body)
                .setTargetStatusCode(StatusCodes.OK).perform();
        return api.getResponseJSONValue("bookingid");
    }

    /** Checks the response of {@link #createBooking}. */
    public void validateBookingCreation(String firstName, String lastName, String additionalNeeds) {
        api.verifyThatResponse().extractedJsonValue("booking.firstname").isEqualTo(firstName).perform();
        api.verifyThatResponse().extractedJsonValue("booking.lastname").isEqualTo(lastName).perform();
        api.verifyThatResponse().extractedJsonValue("booking.additionalneeds").isEqualTo(additionalNeeds).perform();
        api.verifyThatResponse().body().contains("\"bookingid\":").perform();
    }

    /** Searches the bookings by guest name and returns the response in the shared API object. */
    public void searchByName(String firstName, String lastName) {
        api.get(SERVICE).setUrlArguments("firstname=" + firstName + "&lastname=" + lastName)
                .setTargetStatusCode(StatusCodes.OK).perform();
    }

    public void validateBookingIsListed(String bookingId) {
        api.verifyThatResponse().body().contains("\"bookingid\":" + bookingId).perform();
    }

    public void getBooking(String bookingId, int expectedStatus) {
        api.get(SERVICE + "/" + bookingId).setTargetStatusCode(expectedStatus).perform();
    }

    public void deleteBooking(String bookingId) {
        api.delete(SERVICE + "/" + bookingId).setContentType(ContentType.JSON)
                .setTargetStatusCode(StatusCodes.CREATED).perform();
    }
}