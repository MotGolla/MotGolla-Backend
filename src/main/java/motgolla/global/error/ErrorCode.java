package motgolla.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    /* COMMON ERROR */
    INTERNAL_SERVER_ERROR(500, "COMMON001", "Internal Server Error"),
    INVALID_INPUT_VALUE(400, "COMMON002", "Invalid Input Value"),
    ENTITY_NOT_FOUND(404, "COMMON003", "Entity Not Found"),

    /* AUTH ERROR */
    INVALID_ACCESS_TOKEN(401, "AUTH001", "Invalid Access Token"),
    INVALID_REFRESH_TOKEN(401, "AUTH002", "Invalid Refresh Token"),
    LOGIN_FAILED(401, "AUTH003", "Login Failed"),
    RECENT_RESIGNED_MEMBER(401, "AUTH004", "Recent Resigned Member"),
    INVALID_ID_TOKEN(401, "AUTH005", "Invalid ID Token"),

    /* MEMBER ERROR */
    MEMBER_NOT_FOUND(404, "MEMBER001", "Member Not Found"),
    DUPLICATED_MEMBER(400, "MEMBER002", "Duplicated Member"),

    /* BARCODE ERROR */
    BARCODE_INFO_NOT_FOUND(400,"BARCODE001","등록된 상품 정보를 찾을 수 없습니다.\n"
        + "상품 바코드를 다시 한 번 확인해 주세요."),

    /* OPEN AI ERROR */
    OPENAI_RESPONSE_ERROR(500, "OPENAI001", "OpenAI 응답 처리 중 오류가 발생했습니다."),
    OPENAI_API_CALL_FAILED(500, "OPENAI002", "OpenAI API 호출에 실패했습니다."),

    /* RECORD ERROR */
    RECORD_NOT_FOUND(404, "RECORD001", "등록된 기록 정보를 찾을 수 없습니다."),
    RECORD_UPDATE_FAILED(400, "RECORD002", "기록 상품 상태를 변경할 수 없습니다."),


    /* PRODUCT ERROR */
    PRODUCT_NOT_FOUND(404, "PRODUCT001", "Product Not Found");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
