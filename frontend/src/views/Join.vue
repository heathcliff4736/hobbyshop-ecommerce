<!--//frontend/src/views/Join.vue-->
<!--//회원 가입 컴포넌트-->

<script setup>
import {reactive} from "vue";
import {join} from "@/services/accountService";
import {useRouter} from "vue-router";

// 반응형 상태
const state = reactive({ // ①
  form: {
    name: "",
    loginId: "",
    loginPw: "",
    phone: "",
    address: ""
  },
  errorMessage: "",
  isLoading: false
});

// 라우터 객체
const router = useRouter(); // ②

// 회원가입 데이터 제출
const submit = async () => {
  state.errorMessage = '';
  state.isLoading = true;

  try {
    const res = await join(state.form);

    if (res.status === 200) {
      await router.push("/login");
    }
  } catch (error) {
    if (error.response) {
      switch (error.response.status) {
        case 409:
          state.errorMessage = error.response.data.message || '이미 사용 중인 아이디입니다.';
          break;
        case 400:
          state.errorMessage = '입력하신 정보를 다시 확인해주세요.';
          break;
        default:
          state.errorMessage = '회원가입 중 오류가 발생했습니다.';
      }
    } else if (error.request) {
      state.errorMessage = '서버와 연결할 수 없습니다. 네트워크 연결을 확인해주세요.';
    } else {
      state.errorMessage = '회원가입 요청 중 오류가 발생했습니다.';
    }
  } finally {
    state.isLoading = false;
  }
};
</script>

<template>
  <div class="join">
    <div class="container">
      <!-- 에러 메시지 -->
      <div v-if="state.errorMessage" class="alert alert-danger alert-dismissible fade show" role="alert">
        <i class="bi bi-exclamation-circle-fill me-2"></i>
        {{ state.errorMessage }}
        <button type="button" class="btn-close" @click="state.errorMessage = ''" aria-label="Close"></button>
      </div>

      <form class="py-5 d-flex flex-column gap-3" @submit.prevent="submit">
        <h1 class="h5 mb-3">회원가입</h1>

        <div class="form-floating">
          <input type="text" class="form-control" id="name" placeholder="이름" v-model="state.form.name">
          <label for="name">이름</label>
        </div>

        <div class="form-floating">
          <input type="email" class="form-control" id="loginId" placeholder="이메일" v-model="state.form.loginId">
          <label for="loginId">이메일</label>
        </div>

        <div class="form-floating">
          <input type="password" class="form-control" id="loginPw" placeholder="패스워드" v-model="state.form.loginPw">
          <label for="loginPw">패스워드</label>
        </div>

        <div class="form-floating">
          <input type="text" class="form-control" id="phone" placeholder="010-1234-5678" v-model="state.form.phone">
          <label for="phone">휴대폰 번호</label>
        </div>

        <div class="form-floating">
          <input type="text" class="form-control" id="address" placeholder="주소" v-model="state.form.address">
          <label for="address">주소</label>
        </div>

        <button
            type="submit"
            class="w-100 h6 btn py-3 btn-primary"
            :disabled="state.isLoading"
        >
          <span v-if="state.isLoading" class="spinner-border spinner-border-sm me-2" role="status"></span>
          {{ state.isLoading ? '가입 중...' : '회원가입' }}
        </button>
      </form>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.join {
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