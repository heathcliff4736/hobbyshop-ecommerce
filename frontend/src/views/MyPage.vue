<template>
  <div class="my-page">
    <!-- 왼쪽 메뉴 -->
    <aside class="side-menu">
      <ul>
        <li @click="selected = 'profile'">내 정보</li>
        <li @click="selected = 'password'">비밀번호 변경</li>
        <li @click="selected = 'delete'">회원탈퇴</li>
      </ul>
    </aside>

    <!-- 오른쪽 컨텐츠 -->
    <section class="content">
      <div v-if="selected === 'profile'">
        <!-- 프로필 화면 -->
        <h2>내 정보</h2>
      </div>

      <div v-if="selected === 'password'">
        <!-- 비밀번호 변경 -->
        <h2>비밀번호 변경</h2>
      </div>

      <div v-if="selected === 'delete'">
        <!-- 회원탈퇴 화면 -->
        <h2>회원탈퇴</h2>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const selected = ref('profile') // 기본 화면
</script>

<style scoped>
.my-page {
  display: flex;
  width: 100%;
  min-height: 400px;
}

.side-menu {
  width: 180px;
  border-right: 1px solid #eee;
}

.content {
  flex: 1;
  padding: 20px;
}
</style>
