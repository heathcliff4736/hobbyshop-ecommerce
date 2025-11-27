<!--//Login.vue 로그인 컴포넌트-->
<script setup>
import {reactive} from "vue";
import {login} from "@/services/accountService";
import {useRouter} from "vue-router";
import {useAccountStore} from "@/stores/account"; // ① 계정 스토어 객체 생성 시 필요한 메서드 임포트

// 반응형 상태
const state = reactive({ // ①
  form: {
    loginId: "",
    loginPw: "",
  },
  errorMessage: "",
  isLoading: false
});

// 라우터 객체
const router = useRouter(); // ②

// 계정 스토어
const accountStore = useAccountStore(); // ② 계정 스토어 객체

// 로그인 데이터 제출
const submit = async () => {
  state.errorMessage = ""; // 반응형 에러 메시지 초기화
  state.isLoading = true;
  try {
    const res = await login(state.form);

    if (res.status === 200) {
      // 로그인 성공시 응답받은 데이터(액세스 토큰)를 계정 스토어의 액세스 토큰에 입력(저장)
      accountStore.setAccessToken(res.data);
      await router.push("/");
    }
  } catch (error) {
    // HTTP 에러 응답 처리
    if (error.response) {
      switch (error.response.status) {
        case 404:
          state.errorMessage = "입력하신 정보와 일치하는 회원이 없습니다.";
          break;
        case 401:
          state.errorMessage = "아이디 또는 비밀번호가 올바르지 않습니다.";
          break;
        case 500:
          state.errorMessage = "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.";
          break;
        default:
          state.errorMessage = "로그인 중 오류가 발생했습니다.";
      }
    } else if (error.request) {
      // 요청은 보냈지만 응답을 받지 못한 경우
      state.errorMessage = "서버와 연결할 수 없습니다. 네트워크 연결을 확인해주세요.";
    } else {
      // 요청 설정 중 오류 발생
      state.errorMessage = "로그인 요청 중 오류가 발생했습니다.";
    }

    // 비밀번호 필드 초기화
    state.form.loginPw = "";

    console.error("Login error:", error);
  } finally {
    state.isLoading = false;
  }
};
</script>

<template>
  <div class="login">
    <div class="container"> <!-- ④ -->
      <div v-if="state.errorMessage" class="alert alert-danger alert-dismissible fade show" role="alert">
        <i class="bi bi-exclamation-circle-fill me-2"></i>
        {{ state.errorMessage }}
        <button type="button" class="btn-close" @click="state.errorMessage = ''" aria-label="Close"></button>
      </div>
      <form class="py-5 d-flex flex-column gap-3" @submit.prevent="submit"> <!-- ⑤ -->
        <h1 class="h5 mb-3">로그인</h1>
        <div class="form-floating">
          <input type="email" class="form-control" id="loginId" placeholder="이메일" v-model="state.form.loginId"> <!-- ⑥ -->
          <label for="loginId">이메일</label>
        </div>
        <div class="form-floating">
          <input type="password" class="form-control" id="loginPw" placeholder="패스워드" v-model="state.form.loginPw"> <!-- ⑥ -->
          <label for="loginPw">패스워드</label>
        </div>
        <button
            type="submit"
            class="w-100 h6 btn py-3 btn-primary"
            :disabled="state.isLoading"
        >
          <span v-if="state.isLoading" class="spinner-border spinner-border-sm me-2" role="status"></span>
          {{ state.isLoading ? '로그인 중...' : '로그인' }}
        </button>
      </form>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.login {
  min-height: 100vh;
  display: flex;
  align-items: center;
  background: #f8f9fa;

  > .container {
    max-width: 420px;
    background: white;
    border-radius: 16px;
    padding: 2rem;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.07), 0 10px 20px rgba(0, 0, 0, 0.1);
  }
}

.form-control {
  border-radius: 8px;

  &:focus {
    border-color: #667eea;
    box-shadow: 0 0 0 0.25rem rgba(102, 126, 234, 0.25);
  }
}

.btn-primary {
  border-radius: 8px;
  font-weight: 600;
  transition: all 0.3s ease;

  &:hover:not(:disabled) {
    transform: translateY(-2px);
    box-shadow: 0 8px 16px rgba(102, 126, 234, 0.4);
  }

  &:disabled {
    opacity: 0.7;
    cursor: not-allowed;
  }
}

.alert {
  border-radius: 8px;
  border: none;
}
</style>