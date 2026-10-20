package api;

import com.shaft.driver.SHAFT;
import io.restassured.http.ContentType;

/** The /auth service of Restful-Booker: logs in and keeps the token for later calls. */
public class AuthApi {

    private final SHAFT.API api;

    public AuthApi(SHAFT.API api) {
        this.api = api;
    }

    public void login(String username, String password) {
        String body = """
                {
                    "username" : "%s",
                    "password" : "%s"
                }
                """.formatted(username, password);
        api.post("/auth").setContentType(ContentType.JSON).setRequestBody(body)
                .setTargetStatusCode(StatusCodes.OK).perform();
        String token = api.getResponseJSONValue("token");
        api.addHeader("Cookie", "token=" + token);
    }
}