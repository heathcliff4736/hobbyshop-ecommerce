import httpRequester from "@/libs/httpRequester";

// 상품 목록 조회
export const getItems = (page = 0, size = 12) => {
    return httpRequester.get("/v1/api/items", { page, size })
        .catch(e => e.response);
};