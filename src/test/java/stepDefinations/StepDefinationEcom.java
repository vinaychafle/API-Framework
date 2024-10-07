package stepDefinations;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.LoginDetails;
import pojo.LoginResponse;
import resources.APIResources;
import resources.Utils2;

public class StepDefinationEcom extends Utils2  {
	String id;
	String token;
	@Given("Login payload {string} {string}")
	public void login_payload(String username, String password) throws IOException {
		//url to open website https://rahulshettyacademy.com/client/
		
				LoginDetails login=new LoginDetails();
				login.setUserEmail(username);
				login.setUserPassword(password);
				
				res=given().spec(requestSpecification()).body(login);
				
				RequestSpecification loginReq=	given().log().all().spec(res).body(login);
				LoginResponse resp=loginReq.when().post("/api/ecom/auth/login").then().extract()
						.response().as(LoginResponse.class);
			System.out.println(resp.getToken());
			token=resp.getToken();
			System.out.println(resp.getUserId());
			 id=resp.getUserId();

	}
	
	@Then("verify product is added to {string} using {string}")
	public void verify_product_is_added_to_using(String name, String token) {
	    // Write code here that turns the phrase above into concrete actions
			res=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();
				
				RequestSpecification reqAddProduct=given().log().all().spec(res).param("productName", name)
				.param("productAddedBy", id).param("productCategory", "fashion")
				.param("productSubCategory", "shirts").param("productPrice", "11500").param("productDescription", "Addias OriginalsShoes")
				.param("productFor", "women")
				.multiPart("productImage",new File("C:\\Users\\vchafle\\Downloads\\pexels-math-90946.jpg"));
						
				String addProductResponse=reqAddProduct.when().post("/api/ecom/product/add-product").then().log().all().extract().response().asString();
				JsonPath js=new JsonPath(addProductResponse);
				String productId=js.get("productId");
	}

}
