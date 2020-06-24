package wooteco.subway.service.line;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import wooteco.subway.domain.line.Line;
import wooteco.subway.domain.line.LineRepository;
import wooteco.subway.domain.line.LineStation;
import wooteco.subway.domain.line.LineStationRepository;
import wooteco.subway.domain.station.Station;
import wooteco.subway.domain.station.StationRepository;
import wooteco.subway.service.line.dto.LineDetailResponse;

@ExtendWith(MockitoExtension.class)
public class LineStationServiceTest {
    private static final String STATION_NAME1 = "강남역";
    private static final String STATION_NAME2 = "역삼역";
    private static final String STATION_NAME3 = "선릉역";
    private static final String STATION_NAME4 = "삼성역";

    @Mock
    private LineRepository lineRepository;
    @Mock
    private StationRepository stationRepository;
    @Mock
    private LineStationRepository lineStationRepository;

    private LineStationService lineStationService;

    private Line line;
    private Station station1;
    private Station station2;
    private Station station3;
    private Station station4;

    @BeforeEach
    void setUp() {
        lineStationService = new LineStationService(lineRepository, stationRepository,
            lineStationRepository);

        station1 = new Station(1L, STATION_NAME1);
        station2 = new Station(2L, STATION_NAME2);
        station3 = new Station(3L, STATION_NAME3);
        station4 = new Station(4L, STATION_NAME4);

        line = new Line(1L, "2호선", LocalTime.of(05, 30), LocalTime.of(22, 30), 5);
        line.addLineStation(new LineStation(1L, line, null, station1, 10, 10));
        line.addLineStation(new LineStation(2L, line, station1, station2, 10, 10));
        line.addLineStation(new LineStation(3L, line, station2, station3, 10, 10));
    }

    @Test
    void findLineWithStationsById() {
        when(lineRepository.findById(anyLong())).thenReturn(Optional.of(line));

        LineDetailResponse lineDetailResponse = lineStationService.findLineWithStationsById(1L);

        assertThat(lineDetailResponse.getStations()).hasSize(3);
    }

    @Test
    void wholeLines() {
        Line newLine = new Line(2L, "신분당선", LocalTime.of(05, 30), LocalTime.of(22, 30), 5);
        newLine.addLineStation(new LineStation(4L, newLine, null, station4, 10, 10));
        newLine.addLineStation(
            new LineStation(5L, newLine, station4, new Station(5L, "양재역"), 10, 10));
        newLine.addLineStation(
            new LineStation(6L, newLine, new Station(5L, "양재역"), new Station(6L, "청계산입구역"), 10,
                10));

        List<Station> stations = Lists.newArrayList(new Station(1L, "강남역"), new Station(2L, "역삼역"),
            new Station(3L, "삼성역"), new Station(4L, "양재역"), new Station(5L, "양재시민의숲역"),
            new Station(6L, "청계산입구역"));

        when(lineRepository.findAll()).thenReturn(Arrays.asList(this.line, newLine));

        List<LineDetailResponse> lineDetails = lineStationService.findLinesWithStations()
            .getLineDetailResponse();

        assertThat(lineDetails).isNotNull();
        assertThat(lineDetails.get(0).getStations().size()).isEqualTo(3);
        assertThat(lineDetails.get(1).getStations().size()).isEqualTo(3);
    }
}
