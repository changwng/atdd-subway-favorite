package wooteco.subway.acceptance.member;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import wooteco.subway.AcceptanceTest;
import wooteco.subway.service.member.dto.MemberResponse;

public class MemberAcceptanceTest extends AcceptanceTest {

	@DisplayName("회원 관리 기능")
	@Test
	void manageMember() {
		String location = createMember(TEST_USER_EMAIL, TEST_USER_NAME, TEST_USER_PASSWORD);
		assertThat(location).isNotBlank();

		MemberResponse memberResponse = getMember(TEST_USER_EMAIL);
		assertThat(memberResponse.getId()).isNotNull();
		assertThat(memberResponse.getEmail()).isEqualTo(TEST_USER_EMAIL);
		assertThat(memberResponse.getName()).isEqualTo(TEST_USER_NAME);

		updateMember(memberResponse);
		MemberResponse updatedMember = getMember(TEST_USER_EMAIL);
		assertThat(updatedMember.getName()).isEqualTo("NEW_" + TEST_USER_NAME);

		deleteMember(memberResponse);
	}

	/*
	 * when 회원가입 요청을 한다.
	 * and 로그인 요청을 한다.
	 * then 회원 조회가 가능하다.
	 *
	 * when 회원 정보를 수정한다.
	 * then 회원 정보를 조회해서 수정 잘됐는지 확인한다.
	 *
	 * when 회원 탈퇴 한다.
	 * then 회원조회를 해서 탈퇴되었는지 확인한다.
	 * */
}
