package wooteco.subway.web.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import wooteco.subway.domain.member.Member;
import wooteco.subway.service.member.MemberService;
import wooteco.subway.service.member.dto.LoginRequest;
import wooteco.subway.service.member.dto.MemberResponse;
import wooteco.subway.service.member.dto.TokenResponse;

@RestController
public class LoginMemberController {
    private MemberService memberService;

    public LoginMemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/oauth/token")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = memberService.createToken(loginRequest);
        return ResponseEntity.ok().body(new TokenResponse(token, "bearer"));
    }

    @GetMapping({"/me/bearer"})
    public ResponseEntity<MemberResponse> getMemberOfMineBasic(@LoginMember Member member) {
        return ResponseEntity.ok().body(MemberResponse.of(member));
    }
}
