package wooteco.subway.web.member;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wooteco.subway.service.member.MemberService;
import wooteco.subway.service.member.dto.LoginRequest;
import wooteco.subway.service.member.dto.TokenResponse;

@RequestMapping("/oauth")
@RestController
public class OAuthController {
	private MemberService memberService;

	public OAuthController(MemberService memberService) {
		this.memberService = memberService;
	}

	@PostMapping("/token")
	public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest param) {
		String token = memberService.createToken(param);
		return ResponseEntity.ok().body(new TokenResponse(token, "bearer"));
	}
}
