package wooteco.subway.domain.member;

import org.springframework.data.annotation.Id;

public class Favorite {
    @Id
    private Long id;
    private Long startStationId;
    private Long endStationId;

    public Favorite() {}

    public Favorite(Long startStationId, Long endStationId) {
        this(null, startStationId, endStationId);
    }

    public Favorite(Long id, Long startStationId, Long endStationId) {
        this.id = id;
        this.startStationId = startStationId;
        this.endStationId = endStationId;
    }

    public Long getId() {
        return id;
    }

    public Long getStartStationId() {
        return startStationId;
    }

    public Long getEndStationId() {
        return endStationId;
    }
}