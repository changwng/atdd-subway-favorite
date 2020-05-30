package wooteco.subway.web.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooteco.subway.domain.member.Member;
import wooteco.subway.service.member.MemberService;
import wooteco.subway.service.member.dto.MemberRequest;
import wooteco.subway.service.member.dto.MemberResponse;
import wooteco.subway.service.member.dto.UpdateMemberRequest;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public ResponseEntity<Void> createMember(@Valid @RequestBody MemberRequest view) {
        Member member = memberService.createMember(view.toMember());
        return ResponseEntity
                .created(URI.create("/members/" + member.getId()))
                .build();
    }

    @GetMapping("/members")
    public ResponseEntity<MemberResponse> getMemberByEmail(@RequestParam String email) {
        Member member = memberService.findMemberByEmail(email);
        return ResponseEntity.ok().body(MemberResponse.of(member));
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<MemberResponse> updateMember(@LoginMember Member member, @PathVariable Long id, @Valid @RequestBody UpdateMemberRequest param) {
        memberService.updateMember(member, id, param);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<MemberResponse> deleteMember(@LoginMember Member member, @PathVariable Long id) {
        memberService.deleteMember(member, id);
        return ResponseEntity.noContent().build();
    }
}
