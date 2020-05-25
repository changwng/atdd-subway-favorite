package wooteco.subway.service.member.dto;

import wooteco.subway.domain.member.Member;
import wooteco.subway.service.favorite.dto.FavoriteResponse;

import java.util.Set;

public class MemberFavoriteResponse {
    private Long id;
    private Set<FavoriteResponse> favorites;

    public MemberFavoriteResponse(Long id, Set<FavoriteResponse> favorites) {
        this.id = id;
        this.favorites = favorites;
    }

    public static MemberFavoriteResponse of(Member member) {
        return new MemberFavoriteResponse(member.getId(), FavoriteResponse.setOf(member.getFavorites()));
    }

    public Long getId() {
        return id;
    }

    public Set<FavoriteResponse> getFavorites() {
        return favorites;
    }
}
