package wooteco.subway.service.favorite.dto;

import javax.validation.constraints.NotNull;

import wooteco.subway.domain.favorite.Favorite;

public class FavoriteRequest {
    @NotNull
    private Long preStationId;
    @NotNull
    private Long stationId;

    public FavoriteRequest() {
    }

    public FavoriteRequest(Long preStationId, Long stationId) {
        this.preStationId = preStationId;
        this.stationId = stationId;
    }

    public Favorite toFavorite() {
        return new Favorite(preStationId, stationId);
    }

    public Long getPreStationId() {
        return preStationId;
    }

    public Long getStationId() {
        return stationId;
    }
}
