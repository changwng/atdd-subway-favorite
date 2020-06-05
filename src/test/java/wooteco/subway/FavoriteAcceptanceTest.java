package wooteco.subway;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import wooteco.subway.service.favorite.dto.FavoriteRequest;
import wooteco.subway.service.favorite.dto.FavoriteResponse;
import wooteco.subway.service.member.dto.TokenResponse;
import wooteco.subway.service.station.dto.StationResponse;

public class FavoriteAcceptanceTest extends AcceptanceTest {

	@Test
	@DisplayName("지하철 노선도 즐겨찾기를 관리한다")
	void favorite() {
		/**
		 * Feature: 지하철 노선 즐겨찾기 기능
		 * Scenario: 지하철 노선 즐겨찾기 기능을 사용한다
		 *
		 * Given 역이 등록되어 있다
		 *  And 로그인이 되어있다
		 * When 경로에 즐겨찾기 추가를 한다
		 * Then 즐겨찾기에 경로가 추가된다
		 *
		 * Given 로그인이 되어있고, 즐겨찾기에 경로가 추가되어 있다
		 * When 즐겨찾기 조회한다
		 * Then 즐겨찾기가 조회된다
		 *
		 * Given 즐겨찾기에 등록이 되어있다
		 * When 즐겨찾기에 등록되어있는 경로의 삭제 요청을 한다
		 * Then 등록된 즐겨찾기 경로가 삭제된다
		 */

		StationResponse stationOne = createStation("일원역");
		StationResponse stationTwo = createStation("이대역");

		createMember(TEST_USER_EMAIL, TEST_USER_NAME, TEST_USER_PASSWORD);
		final TokenResponse tokenResponse = login(TEST_USER_EMAIL, TEST_USER_PASSWORD);

		FavoriteRequest favoriteRequest = new FavoriteRequest(stationOne.getId(),
			stationTwo.getId());
		addFavorite(tokenResponse, favoriteRequest);

		final List<FavoriteResponse> favorite = getFavorite(tokenResponse);
		assertThat(favorite.get(0)).isNotNull();
		assertThat(favorite.get(0).getSourceStation()).isEqualTo("일원역");
		assertThat(favorite.get(0).getTargetStation()).isEqualTo("이대역");

		deleteFavorite(tokenResponse, favorite.get(0).getId());
		assertThat(getFavorite(tokenResponse).size()).isEqualTo(0);
	}

	void addFavorite(TokenResponse token, FavoriteRequest favoriteRequest) {
		Map<String, String> params = new HashMap<>();
		params.put("sourceStationId", String.valueOf(favoriteRequest.getSourceStationId()));
		params.put("targetStationId", String.valueOf(favoriteRequest.getTargetStationId()));

		given()
			.header("Authorization", token.getTokenType() + " " + token.getAccessToken())
			.body(params)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when()
			.post("/me/favorites")
			.then()
			.log().all()
			.statusCode(HttpStatus.CREATED.value());
	}

	void deleteFavorite(TokenResponse token, Long favoriteId) {
		given()
			.header("Authorization", token.getTokenType() + " " + token.getAccessToken())
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when()
			.delete("/me/favorites/" + favoriteId)
			.then()
			.log().all()
			.statusCode(HttpStatus.NO_CONTENT.value());
	}

	List<FavoriteResponse> getFavorite(TokenResponse token) {
		return given()
			.header("Authorization", token.getTokenType() + " " + token.getAccessToken())
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when()
			.get("/me/favorites")
			.then()
			.log().all()
			.extract().jsonPath().getList(".", FavoriteResponse.class);
	}
}
