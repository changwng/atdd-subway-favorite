package wooteco.subway.doc;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.apache.http.HttpHeaders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultHandler;

public class MeDocumentation {
	public static RestDocumentationResultHandler getMember() {
		return document("me/view",
			requestHeaders(
				headerWithName(HttpHeaders.AUTHORIZATION).description("token to access")
			),

			responseFields(
				fieldWithPath("id").type(JsonFieldType.NUMBER).description("The user's id"),
				fieldWithPath("email").type(JsonFieldType.STRING).description("The user's email address"),
				fieldWithPath("name").type(JsonFieldType.STRING).description("The user's name")
			)
		);
	}

	public static RestDocumentationResultHandler getMemberException() {
		return document("me/viewException",
			requestHeaders(
				headerWithName(HttpHeaders.AUTHORIZATION).description("token to access")
			),
			responseHeaders(
				headerWithName(HttpHeaders.LOCATION).description("location to redirect")
			)
		);
	}

	public static RestDocumentationResultHandler updateMember() {
		return document("me/update",
			requestHeaders(
				headerWithName(HttpHeaders.AUTHORIZATION).description("token to access")
			),
			requestFields(
				fieldWithPath("name").type(JsonFieldType.STRING).description("The user's name"),
				fieldWithPath("password").type(JsonFieldType.STRING).description("The user's password")
			)
		);
	}

	public static RestDocumentationResultHandler updateMemberException() {
		return document("me/updateException",
			requestHeaders(
				headerWithName(HttpHeaders.AUTHORIZATION).description("token to access")
			),
			requestFields(
				fieldWithPath("name").type(JsonFieldType.STRING).description("The user's name"),
				fieldWithPath("password").type(JsonFieldType.STRING).description("The user's password")
			),
			responseHeaders(
				headerWithName(HttpHeaders.LOCATION).description("location to redirect")
			)
		);
	}

	public static ResultHandler deleteMember() {
		return document("me/delete",
			requestHeaders(
				headerWithName(HttpHeaders.AUTHORIZATION).description("token to access")
			)
		);
	}

	public static ResultHandler deleteMemberException() {
		return document("me/deleteException",
			requestHeaders(
				headerWithName(HttpHeaders.AUTHORIZATION).description("token to access")
			),
			responseHeaders(
				headerWithName(HttpHeaders.LOCATION).description("location to redirect")
			)
		);
	}
}
