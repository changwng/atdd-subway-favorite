package wooteco.subway.web.member;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import wooteco.subway.domain.member.Member;
import wooteco.subway.service.member.MemberService;
import wooteco.subway.service.member.dto.LoginRequest;
import wooteco.subway.service.member.dto.MemberRequest;
import wooteco.subway.service.member.dto.MemberResponse;
import wooteco.subway.service.member.dto.TokenResponse;
import wooteco.subway.service.member.dto.UpdateMemberRequest;

@RestController
public class LoginMemberController {
	private MemberService memberService;

	public LoginMemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping("/me")
	public ResponseEntity<MemberResponse> getMemberOfMineBasic(@LoginMember Member member) {
		return ResponseEntity.ok().body(MemberResponse.of(member));
	}

	@PostMapping("/me")
	public ResponseEntity createMember(@RequestBody MemberRequest view) {
		memberService.createMember(view.toMember());
		return ResponseEntity
			.noContent()
			.build();
	}

	@PostMapping("/me/login")
	public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest param) {
		String token = memberService.createToken(param);
		return ResponseEntity.ok().body(new TokenResponse(token, "bearer"));
	}

	@PutMapping("/me")
	public ResponseEntity<Void> editMemberOfMineBasic(@LoginMember Member member, @RequestBody UpdateMemberRequest request) {
		memberService.updateMember(member.getId(), request);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/me")
	public ResponseEntity<Void> deleteMemberOfMineBasic(@LoginMember Member member) {
		memberService.deleteMember(member.getId());
		return ResponseEntity.noContent().build();
	}
}
