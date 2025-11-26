<!--//Login.vue 로그인 컴포넌트-->
<script setup>
import {reactive} from "vue";
import {login} from "@/services/accountService";
import {useRouter} from "vue-router";

// 반응형 상태
const state = reactive({ // ①
  form: {
    loginId: "",
    loginPw: "",
  }
});

// 라우터 객체
const router = useRouter(); // ②

// 로그인 데이터 제출
const submit = async () => { // ③
  try {
    const res = await login(state.form);

    if (res.status === 200) {
      await router.push("/");
    }
  } catch (error) {
    // 에러 메시지 alert로 표시
    alert(error.response?.data?.message || "로그인에 실패했습니다.");

    // 비밀번호 초기화
    state.form.loginPw = "";
  }
};
</script>

<template>
  <div class="login">
    <div class="container"> <!-- ④ -->
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
        <button type="submit" class="w-100 h6 btn py-3 btn-primary">로그인</button> <!-- ⑦ -->
      </form>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.login > .container { // ⑧
  max-width: 576px;
}
</style>