import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static listeners.CustomAllureListener.withCustomTemplates;
import static org.hamcrest.Matchers.is;

public class DemowebshopTests {

    @Test
    void cartCheck() {
        given()
                .filter(withCustomTemplates())
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body("product_attribute_16_5_4=13&product_attribute_16_6_5=17&" +
                        "product_attribute_16_3_6=18&product_attribute_16_4_7=44" +
                        "&product_attribute_16_8_8=22&product_attribute_16_8_8=23&addtocart_16.EnteredQuantity=1")
                .when()
                .post("http://demowebshop.tricentis.com/addproducttocart/details/16/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your " +
                        "<a href=\"/cart\">shopping cart</a>"))
                .body("updatetopcartsectionhtml", is("(1)"));
    }

    @Test
    void wishlistCheck() {
        given()
//              .filter(new AllureRestAssured())  -- общий фильтр
                .filter(withCustomTemplates())
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body("giftcard_2.RecipientName=Max&" +
                        "giftcard_2.RecipientEmail=mm%40mail.ru&" +
                        "giftcard_2.SenderName=Vasya&giftcard_2.SenderEmail=vas%40mail.ru&" +
                        "giftcard_2.Message=&addtocart_2.EnteredQuantity=4")
                .when()
                .post("http://demowebshop.tricentis.com/addproducttocart/details/2/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your " +
                        "<a href=\"/wishlist\">wishlist</a>"))
                .body("updatetopwishlistsectionhtml", is("(4)"));
    }

    @Test
    void subscribeCheck() {
        given()
                .filter(withCustomTemplates())
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body("email=kk%40mail.ru")
                .when()
                .post("http://demowebshop.tricentis.com/subscribenewsletter")
                .then()
                .log().all()
                .statusCode(200)
                .body("Success", is(true))
                .body("Result", is("Thank you for signing up!" +
                        " A verification email has been sent. We appreciate your interest."));
    }
}