<!--//frontend/src/views/Join.vue-->
<!--//회원 가입 컴포넌트-->

<script setup>
import {reactive, ref} from "vue";
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
  }
});

const errorMessage = ref('');  // 에러 메시지 상태

// 라우터 객체
const router = useRouter(); // ②

// 회원가입 데이터 제출
const submit = async () => { // ③
  try {
    errorMessage.value = '';  // 에러 메시지 초기화

    const res = await join(state.form);

    if (res.status === 200) {
      window.alert("회원가입을 완료했습니다.");
      await router.push("/login");
    }
  } catch (error) {
    if (error.response && error.response.data) {
      if (error.response.status === 409) {
        const message = error.response.data.message || '이미 사용 중인 아이디입니다.';
        alert(message);
        errorMessage.value = message;
      } else {
        alert('회원가입 중 오류가 발생했습니다.');
        errorMessage.value = '회원가입 중 오류가 발생했습니다.';
      }
    } else {
      alert('서버와의 연결에 실패했습니다.');
      errorMessage.value = '서버와의 연결에 실패했습니다.';
    }
  }
};
</script>

<template>
  <div class="join">
    <div class="container"> <!-- ④ -->
      <form class="py-5 d-flex flex-column gap-3" @submit.prevent="submit"> <!-- ⑤ -->
        <h1 class="h5 mb-3">회원가입</h1>
        <div class="form-floating">
          <input type="text" class="form-control" id="name" placeholder="이름" v-model="state.form.name">  <!-- ⑥ -->
          <label for="name">이름</label>
        </div>
        <div class="form-floating">
          <input
              type="email"
              class="form-control"
              :class="{ 'is-invalid': errorMessage }"
              id="loginId"
              placeholder="이메일"
              v-model="state.form.loginId"
          >
          <div v-if="errorMessage" class="invalid-feedback">
            {{ errorMessage }}
          </div>
        </div>
        <div class="form-floating">
          <input type="password" class="form-control" id="loginPw" placeholder="패스워드" v-model="state.form.loginPw"> <!-- ⑥ -->
          <label for="loginPw">패스워드</label>
        </div>
        <div class="form-floating">
          <input type="text" class="form-control" id="phone" placeholder="010-1234-5678" v-model="state.form.phone"> <!-- ⑥ -->
          <label for="phone">휴대폰 번호</label>
        </div>
        <div class="form-floating">
          <input type="text" class="form-control" id="address" placeholder="주소" v-model="state.form.address"> <!-- ⑥ -->
          <label for="address">주소</label>
        </div>
        <button type="submit" class="w-100 h6 btn py-3 btn-primary">회원가입</button> <!-- ⑦ -->
      </form>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.join > .container { // ⑧
  max-width: 576px;
}
</style>