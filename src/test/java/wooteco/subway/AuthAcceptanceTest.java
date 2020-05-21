package wooteco.subway;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.subway.service.member.dto.MemberResponse;
import wooteco.subway.service.member.dto.TokenResponse;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthAcceptanceTest extends AcceptanceTest {

	@DisplayName("Session Bearer 둘다")
	@Test
	void AuthorizeSessionAndBearer() {
		createMember(TEST_USER_EMAIL, TEST_USER_NAME, TEST_USER_PASSWORD);
		Response response = login(TEST_USER_EMAIL, TEST_USER_PASSWORD);

		String sessionId = response.getSessionId();

		TokenResponse tokenResponse = getTokenResponse(response);

		MemberResponse memberResponse = myInfoAuth(tokenResponse, sessionId);

		assertThat(memberResponse.getId()).isNotNull();
		assertThat(memberResponse.getEmail()).isEqualTo(TEST_USER_EMAIL);
		assertThat(memberResponse.getName()).isEqualTo(TEST_USER_NAME);
	}

	public Response login(String email, String password) {
		Map<String, String> params = new HashMap<>();
		params.put("email", email);
		params.put("password", password);

		return given().
				body(params).
				contentType(MediaType.APPLICATION_JSON_VALUE).
				accept(MediaType.APPLICATION_JSON_VALUE).
				when().
				post("/login");
	}

	private TokenResponse getTokenResponse(Response response) {
		return response.
				then().
				log().all().
				statusCode(HttpStatus.OK.value()).
				and().
				extract().as(TokenResponse.class);
	}

	public MemberResponse myInfoAuth(TokenResponse tokenResponse, String sessionId) {

		return given().
				cookie("JSESSIONID", sessionId).
				header("Authorization", tokenResponse.getTokenType() + " " + tokenResponse.getAccessToken()).
				accept(MediaType.APPLICATION_JSON_VALUE).
				when().
				get("/mypage").
				then().
				log().all().
				statusCode(HttpStatus.OK.value()).
				extract().as(MemberResponse.class);
	}
}
