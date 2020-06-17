package wooteco.subway.domain.station;

import java.util.Objects;

import org.springframework.data.annotation.Id;

import wooteco.subway.domain.BaseEntity;

public class Station extends BaseEntity {
	@Id
	private Long id;
	private String name;

	public Station() {
	}

	public Station(String name) {
		this.name = name;
	}

	public Station(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Station station = (Station)o;
		return Objects.equals(getId(), station.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}
