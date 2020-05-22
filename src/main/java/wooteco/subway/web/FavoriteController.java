package wooteco.subway.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import wooteco.subway.domain.favorite.Favorite;
import wooteco.subway.domain.member.Member;
import wooteco.subway.service.favorite.FavoriteService;
import wooteco.subway.service.favorite.dto.FavoriteRequest;
import wooteco.subway.web.member.LoginMember;

@RestController
public class FavoriteController {
	private FavoriteService favoriteService;

	public FavoriteController(FavoriteService favoriteService) {
		this.favoriteService = favoriteService;
	}

	@PostMapping("/favorite/me")
	public ResponseEntity<Void> createFavorite(@LoginMember Member member,
		@RequestBody FavoriteRequest favoriteRequest) {
		Favorite persistFavorite = favoriteService.createFavorite(favoriteRequest.toFavorite(member.getId()));
		return ResponseEntity.created(URI.create("/favorite/me/" + persistFavorite.getId())).build();
	}
}
