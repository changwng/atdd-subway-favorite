package wooteco.subway.service.favorite;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import wooteco.subway.domain.favorite.Favorite;
import wooteco.subway.domain.member.Member;
import wooteco.subway.domain.member.MemberRepository;
import wooteco.subway.domain.station.Station;
import wooteco.subway.domain.station.StationRepository;
import wooteco.subway.exception.DuplicateFavoriteException;
import wooteco.subway.exception.InvalidStationIdException;
import wooteco.subway.service.favorite.dto.FavoriteRequest;
import wooteco.subway.service.favorite.dto.FavoriteResponse;

@ExtendWith(MockitoExtension.class)
public class FavoriteServiceTest {

    static final String EMAIL = "chomily@woowahan.com";
    static final String NAME = "chomily";
    static final String PASSWORD = "chomily1234";
    static final Long ID = 1L;

    private FavoriteService favoriteService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private StationRepository stationRepository;

    @BeforeEach
    void setUp() {
        favoriteService = new FavoriteService(memberRepository, stationRepository);
    }

    @DisplayName("즐겨찾기 추가 기능 테스트")
    @Test
    void createFavorite() {
        Station preStation = new Station(1L, "강남역");
        Station station = new Station(2L, "선릉역");

        Favorite favorite = new Favorite(preStation.getId(), station.getId());

        Member member = new Member(ID, EMAIL, NAME, PASSWORD);

        Member updatedMember = new Member(ID, EMAIL, NAME, PASSWORD);
        updatedMember.addFavorite(favorite);

        when(memberRepository.save(any(Member.class))).thenReturn(updatedMember);

        FavoriteRequest favoriteRequest = new FavoriteRequest(preStation.getId(), station.getId());

        Favorite savedFavorite = favoriteService.createFavorite(member, favoriteRequest);

        assertThat(savedFavorite.getPreStationId()).isEqualTo(preStation.getId());
        assertThat(savedFavorite.getStationId()).isEqualTo(station.getId());
    }

    @DisplayName("예외테스트: 이미 존재하는 즐겨찾기를 추가하는 경우")
    @Test
    void createFavorite_withExistingFavorite() {
        //given
        Station preStation = new Station(1L, "강남역");
        Station station = new Station(2L, "선릉역");

        Favorite favorite = new Favorite(preStation.getId(), station.getId());

        Member member = new Member(ID, EMAIL, NAME, PASSWORD);
        member.addFavorite(favorite);

        FavoriteRequest favoriteRequest = new FavoriteRequest(preStation.getId(), station.getId());

        // when, then
        assertThatThrownBy(() -> favoriteService.createFavorite(member, favoriteRequest))
            .isInstanceOf(DuplicateFavoriteException.class)
            .hasMessage("이미 존재하는 즐겨찾기를 추가할 수 없습니다.");
    }

    @DisplayName("예외테스트: 추가하려는 즐겨찾기에 출발역, 도착역 정보가 비어있는 경우")
    @Test
    void createFavorite_withoutIds() {
        // given
        Member member = new Member(ID, EMAIL, NAME, PASSWORD);

        FavoriteRequest nullRequest = new FavoriteRequest(null, null);
        FavoriteRequest preStationNullRequest = new FavoriteRequest(null, 1L);
        FavoriteRequest stationNullRequest = new FavoriteRequest(1L, null);

        // when, then
        assertThatThrownBy(() -> favoriteService.createFavorite(member, nullRequest))
            .isInstanceOf(NullPointerException.class)
            .hasMessage("출발역이 null일 수 없습니다.");

        assertThatThrownBy(() -> favoriteService.createFavorite(member, preStationNullRequest))
            .isInstanceOf(NullPointerException.class)
            .hasMessage("출발역이 null일 수 없습니다.");

        assertThatThrownBy(() -> favoriteService.createFavorite(member, stationNullRequest))
            .isInstanceOf(NullPointerException.class)
            .hasMessage("도착역이 null일 수 없습니다.");
    }

    @DisplayName("예외테스트: 존재하지 않는 역의 ID를 즐겨찾기에 등록하는 경우")
    @Test
    void createFavorite_withNotExistingStationIds() {
        // given
        Member member = new Member(ID, EMAIL, NAME, PASSWORD);

        when(memberRepository.save(any(Member.class))).thenThrow(
            new InvalidStationIdException("해당 역이 존재하지 않습니다."));

        FavoriteRequest requestWithNotExistingStations = new FavoriteRequest(100L, 3000L);

        // when, then
        assertThatThrownBy(
            () -> favoriteService.createFavorite(member, requestWithNotExistingStations))
            .isInstanceOf(InvalidStationIdException.class)
            .hasMessage("해당 역이 존재하지 않습니다.");
    }

    @DisplayName("즐겨찾기 목록 조회 기능 테스트")
    @Test
    void getFavorites() {
        Station gangnam = new Station(1L, "강남역", LocalDateTime.now());
        Station seolleung = new Station(2L, "선릉역", LocalDateTime.now());
        Station yeoksam = new Station(3L, "역삼역", LocalDateTime.now());

        Favorite favorite1 = new Favorite(gangnam.getId(), seolleung.getId());
        Favorite favorite2 = new Favorite(yeoksam.getId(), gangnam.getId());

        Member member = new Member(ID, EMAIL, NAME, PASSWORD);
        member.addFavorite(favorite1);
        member.addFavorite(favorite2);

        when(stationRepository.findAllById(anyList())).thenReturn(
            Arrays.asList(gangnam, seolleung, yeoksam));

        List<FavoriteResponse> favorites = favoriteService.getFavorites(member);

        assertThat(favorites).hasSize(2)
            .extracting("preStation")
            .extracting("name")
            .containsOnly(gangnam.getName(), yeoksam.getName());

        assertThat(favorites)
            .extracting("station")
            .extracting("name")
            .containsOnly(seolleung.getName(), gangnam.getName());
    }

    @DisplayName("즐겨찾기를 삭제하는 기능 테스트")
    @Test
    void deleteFavorite() {
        Station gangnam = new Station(1L, "강남역");
        Station seolleung = new Station(2L, "선릉역");
        Station yeoksam = new Station(3L, "역삼역");

        Favorite favorite1 = new Favorite(1L, gangnam.getId(), seolleung.getId());
        Favorite favorite2 = new Favorite(2L, yeoksam.getId(), gangnam.getId());

        Member member = new Member(ID, EMAIL, NAME, PASSWORD);
        member.addFavorite(favorite1);
        member.addFavorite(favorite2);

        Member updatedMember = new Member(ID, EMAIL, NAME, PASSWORD);
        updatedMember.addFavorite(favorite2);

        when(memberRepository.save(any(Member.class))).thenReturn(updatedMember);

        favoriteService.deleteFavorite(member, 1L);

        assertThat(member.getFavorites().size()).isEqualTo(1);
    }

    @DisplayName("예외테스트: 존재하지 않는 즐겨찾기를 삭제하는 경우")
    @Test
    void deleteFavorite_withNotExistingFavoriteId() {
        Station gangnam = new Station(1L, "강남역");
        Station seolleung = new Station(2L, "선릉역");
        Station yeoksam = new Station(3L, "역삼역");

        Favorite favorite1 = new Favorite(1L, gangnam.getId(), seolleung.getId());
        Favorite favorite2 = new Favorite(2L, yeoksam.getId(), gangnam.getId());

        Member member = new Member(ID, EMAIL, NAME, PASSWORD);
        member.addFavorite(favorite1);
        member.addFavorite(favorite2);

        Member updatedMember = new Member(ID, EMAIL, NAME, PASSWORD);
        updatedMember.addFavorite(favorite2);

        assertThatThrownBy(() -> favoriteService.deleteFavorite(member, 3L))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("해당하는 id를 가진 즐겨찾기가 없습니다.");
    }
}
