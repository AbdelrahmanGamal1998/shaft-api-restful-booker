# SHAFT API Tests: Restful-Booker

[![Build](https://github.com/AbdelrahmanGamal1998/shaft-api-restful-booker/actions/workflows/build.yml/badge.svg)](https://github.com/AbdelrahmanGamal1998/shaft-api-restful-booker/actions/workflows/build.yml)

REST API tests in Java for the practice service [Restful-Booker](https://restful-booker.herokuapp.com/), written with
the [SHAFT engine](https://github.com/ShaftHQ/SHAFT_ENGINE) (a wrapper around REST Assured and TestNG).

## What is tested

| Test | Checks |
| --- | --- |
| `createBooking` | `POST /booking` answers 200 and returns the booking with the same name and extra needs, and an id |
| `createdBookingCanBeFoundByName` | `GET /booking?firstname=..&lastname=..` lists the new booking |
| `deletedBookingIsGone` | `DELETE /booking/{id}` answers 201 and `GET /booking/{id}` then answers 404 |

Logging in (`POST /auth`) happens once before the tests and the token is sent as a cookie. Each test creates its own
booking, so the tests do not depend on each other.

## Structure

```
src/test/java/api/      AuthApi, BookingApi (one class per service) and StatusCodes
src/test/java/tests/    BookingTests
src/main/resources/properties/   SHAFT settings (Configurations.properties)
```

## Run it

You need JDK 17 or newer and Maven (about 1 GB of dependencies is downloaded the first time).

```bash
git clone https://github.com/AbdelrahmanGamal1998/shaft-api-restful-booker.git
cd shaft-api-restful-booker
mvn test
```

The result is shown in the console and in `target/surefire-reports`. The Allure and Extent reports of SHAFT are
switched off in `Configurations.properties` because they download extra tools; turn them on there if you want them.

## Notes

- The service is a public sandbox and is reset regularly. The credentials in the test (`admin` / `password123`) are
  the published demo credentials of Restful-Booker.
- Changes made when it was cleaned up: the search request was sent with a malformed query string, the delete test
  needed another test to run first, and the IntelliJ template `Main` class was removed. Test code now lives in
  `src/test`, and the `.bat` file with a path on my computer was removed.

## License

[MIT](LICENSE)