package wooteco.subway.web.member;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static wooteco.subway.domain.Token.TokenType.BEARER;
import static wooteco.subway.service.member.MemberServiceTest.TEST_USER_EMAIL;
import static wooteco.subway.service.member.MemberServiceTest.TEST_USER_NAME;
import static wooteco.subway.service.member.MemberServiceTest.TEST_USER_PASSWORD;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import wooteco.subway.doc.FavoriteDocumentation;
import wooteco.subway.domain.member.Member;
import wooteco.subway.infra.JwtTokenProvider;
import wooteco.subway.service.member.FavoritesService;
import wooteco.subway.service.member.MemberService;
import wooteco.subway.service.member.dto.FavoriteResponse;
import wooteco.subway.web.FavoritesController;
import wooteco.subway.web.member.interceptor.BearerAuthInterceptor;

@Import(value = {BearerAuthInterceptor.class, AuthorizationExtractor.class})
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = FavoritesController.class)
public class FavoritesControllerTest {
    private static final Member MEMBER_BROWN = new Member(1L, TEST_USER_EMAIL, TEST_USER_NAME, TEST_USER_PASSWORD);
    private static final String BEARER_JWT_TOKEN = BEARER + " BEARER_JWT_TOKEN";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    protected MockMvc mockMvc;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @MockBean
    private FavoritesService favoritesService;

    @MockBean
    private MemberService memberService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .build();

        given(memberService.findMemberByEmail(any())).willReturn(MEMBER_BROWN);
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(jwtTokenProvider.getSubject(any())).willReturn(TEST_USER_EMAIL);
    }

    @DisplayName("즐겨찾기 추가")
    @Test
    void addFavorite() throws Exception {
        mockMvc.perform(post("/members/favorites")
                .header(AUTHORIZATION_HEADER, BEARER_JWT_TOKEN)
                .content("{\"departStationId\": \"1\"," + "\"arriveStationId\": \"2\"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(FavoriteDocumentation.addFavorite());
    }

    @DisplayName("즐겨찾기 조회")
    @Test
    void getFavorites() throws Exception {
        given(favoritesService.getFavoritesBy(MEMBER_BROWN)).willReturn(
                Arrays.asList(
                        new FavoriteResponse(1L, "강남", 2L, "잠실"),
                        new FavoriteResponse(2L, "잠실", 3L, "잠실나루")));

        mockMvc.perform(get("/members/favorites")
                .header(AUTHORIZATION_HEADER, BEARER_JWT_TOKEN)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(FavoriteDocumentation.getFavorites());
    }

    @DisplayName("즐겨찾기 제거")
    @Test
    void deleteFavorite() throws Exception {
        mockMvc.perform(delete("/members/favorites")
                .header(AUTHORIZATION_HEADER, BEARER_JWT_TOKEN)
                .content("{\"departStationId\": \"1\"," + "\"arriveStationId\": \"2\"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(FavoriteDocumentation.deleteFavorite());
    }
}
