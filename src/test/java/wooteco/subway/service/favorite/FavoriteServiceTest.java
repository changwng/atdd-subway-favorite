package wooteco.subway.service.favorite;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
		when(stationRepository.findById(1L)).thenReturn(Optional.of(preStation));
		when(stationRepository.findById(2L)).thenReturn(Optional.of(station));

        FavoriteRequest favoriteRequest = new FavoriteRequest(preStation.getId(), station.getId());

        Favorite savedFavorite = favoriteService.createFavorite(member, favoriteRequest);

        assertThat(savedFavorite.getPreStation()).isEqualTo(preStation.getId());
        assertThat(savedFavorite.getStation()).isEqualTo(station.getId());
    }

    @DisplayName("즐겨찾기 목록 조회 기능 테스트")
    @Test
    void getFavorites() {
        Station gangnam = new Station(1L, "강남역");
        Station seolleung = new Station(2L, "선릉역");
        Station yeoksam = new Station(3L, "역삼역");

        Favorite favorite1 = new Favorite(gangnam.getId(), seolleung.getId());
        Favorite favorite2 = new Favorite(yeoksam.getId(), gangnam.getId());

        Member member = new Member(ID, EMAIL, NAME, PASSWORD);
        member.addFavorite(favorite1);
        member.addFavorite(favorite2);

        when(stationRepository.findAllById(anyList())).thenReturn(
            Arrays.asList(gangnam, seolleung, yeoksam));

        List<FavoriteResponse> favorites = favoriteService.getFavorites(member);

        assertThat(favorites.size()).isEqualTo(2);
        assertThat(favorites.get(0).getPreStation()).isIn(gangnam, yeoksam);
        assertThat(favorites.get(1).getPreStation()).isIn(gangnam, yeoksam);
        assertThat(favorites.get(0).getStation()).isIn(seolleung, gangnam);
        assertThat(favorites.get(1).getStation()).isIn(seolleung, gangnam);
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
}
