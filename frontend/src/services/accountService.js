//계정 서비스 구현
// frontend/src/services/accountService.js
import axios from 'axios';

// 회원가입 HTTP POST 메서드로 회원가입 API를 호출하고 응답값을 리턴하는 기능
export const join = (args) => {
    return axios.post("/v1/api/account/join", args);
};

// 로그인 HTTP POST 메서드로 로그인 API를 호출하고 응답값을 리턴하는 기능
export const login = (args) => {
    return axios.post("/v1/api/account/login", args);
};

// 로그인 여부 확인 : HTTP GET 메서드로 로그인 여부 확인 API를 호출하고 응답값을 리턴하는 기능
export const check = () => {
    return axios.get("/v1/api/account/check").catch(e => e.response);
};

// 로그아웃 : HTTP POST 메서드로 로그아웃 API를 호출하고 응답값을 리턴하는 기능
export const logout = () => {
    return axios.post("/v1/api/account/logout").catch(e => e.response);
}