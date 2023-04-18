package tests;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiTest {
    private final String GET_BY_ID = "/booking/%s";
    private final String BASE_URI = "https://restful-booker.herokuapp.com";
    private final String BOOKING_PATH = "/booking";
    private final String AUTH_PATH = "/auth";
    private final String BOOKING_ID = "bookingid";

    private final String FIRSTNAME_PROPERTY = "firstname";
    private final String LASTNAME_PROPERTY = "lastname";
    private final String NEW_FIRSTNAME_VALUE = "John";
    private final String NEW_LASTNAME_VALUE = "Doe";
    private final String COOKIE_PROPERTY = "Cookie";
    private final String COOKIE_VALUE = "token=%s";
    private final Map<String, String> HEADERS = new HashMap<>() {{
        put("Content-Type", "application/json");
        put("Accept", "application/json");
    }};

    private APIRequestContext apiRequestContext;
    private String bookingId;
    private String authToken;

    @BeforeAll
    public void beforeAll() {
        apiRequestContext = Playwright.create().request()
                .newContext(new APIRequest.NewContextOptions()
                        .setBaseURL(BASE_URI)
                        .setExtraHTTPHeaders(HEADERS));
    }

    @Test
    @Order(1)
    public void createBookingUsingPostMethod() {
        JsonObject bookingJson = new JsonObject();
        bookingJson.addProperty("firstname", "Jim");
        bookingJson.addProperty("lastname", "Brown");
        bookingJson.addProperty("totalprice", 111);
        bookingJson.addProperty("depositpaid", true);
        JsonObject bookingJsonDates = new JsonObject();
        bookingJsonDates.addProperty("checkin", "2018-01-01");
        bookingJsonDates.addProperty("checkout", "2019-01-01");
        bookingJson.add("bookingdates", bookingJsonDates);
        bookingJson.addProperty("additionalneeds", "Breakfast");
        APIResponse responseCreateBooking = apiRequestContext.post(BOOKING_PATH, RequestOptions.create().setData(bookingJson));
        Assertions.assertTrue(responseCreateBooking.ok());
        JsonObject responseJsonObject = new Gson().fromJson(responseCreateBooking.text(), JsonObject.class);
        bookingId = responseJsonObject.get(BOOKING_ID).getAsString();
        Assertions.assertNotNull(bookingId);
    }


    @Test
    @Order(2)
    public void getByBookingId() {
        APIResponse responseGetById = apiRequestContext.get(String.format(GET_BY_ID, bookingId));
        Assertions.assertTrue(responseGetById.ok());
    }

    @Test
    @Order(3)
    public void createAuthToken() {
        JsonObject namePassword = new JsonObject();
        namePassword.addProperty("username", "admin");
        namePassword.addProperty("password", "password123");
        APIResponse responseCreateAuthToken = apiRequestContext.post(AUTH_PATH, RequestOptions.create().setData(namePassword));
        Assertions.assertTrue(responseCreateAuthToken.ok());
        JsonObject responseJsonObject = new Gson().fromJson(responseCreateAuthToken.text(), JsonObject.class);
        authToken = responseJsonObject.get("token").getAsString();
        Assertions.assertNotNull(authToken);
    }

    @Test
    @Order(4)
    public void partialUpdateBookingById() {
        HEADERS.put(COOKIE_PROPERTY, String.format(COOKIE_VALUE, authToken));
        apiRequestContext = Playwright.create().request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(BASE_URI)
                .setExtraHTTPHeaders(HEADERS));
        JsonObject updateUserJson = new JsonObject();
        updateUserJson.addProperty(FIRSTNAME_PROPERTY, NEW_FIRSTNAME_VALUE);
        updateUserJson.addProperty(LASTNAME_PROPERTY, NEW_LASTNAME_VALUE);
        APIResponse responseUpdateBookingById = apiRequestContext.patch(String.format(GET_BY_ID, bookingId), RequestOptions.create().setData(updateUserJson));
        Assertions.assertTrue(responseUpdateBookingById.ok());
        JsonObject responseJsonObject = new Gson().fromJson(responseUpdateBookingById.text(), JsonObject.class);
        Assertions.assertEquals(NEW_FIRSTNAME_VALUE, responseJsonObject.get(FIRSTNAME_PROPERTY).getAsString());
        Assertions.assertEquals(NEW_LASTNAME_VALUE, responseJsonObject.get(LASTNAME_PROPERTY).getAsString());
    }

    @Test
    @Order(5)
    public void deleteBookingById() {
        final String expectedMessage = "Created";
        apiRequestContext = Playwright.create().request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(BASE_URI)
                .setExtraHTTPHeaders(HEADERS));
        APIResponse responseDeleteBooking = apiRequestContext.delete(String.format(GET_BY_ID, bookingId));
        Assertions.assertTrue(responseDeleteBooking.ok());
        String message = responseDeleteBooking.statusText();
        Assertions.assertEquals(expectedMessage, message);
    }
}