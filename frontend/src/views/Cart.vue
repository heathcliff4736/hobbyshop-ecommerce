<script setup>
import {getItems, removeItem} from "@/services/cartService"
import {reactive} from "vue";

// 반응형 상태
const state = reactive({ // ①
  items: []
});

// 장바구니 상품 조회
const load = async () => { // ②
  const res = await getItems();

  if (res.status === 200) {
    state.items = res.data;
  }
}

// 장바구니 상품 삭제
const remove = async (itemId) => { // ③
  const res = await removeItem(itemId);

  if (res.status === 200) {
    window.alert("선택하신 장바구니의 상품을 삭제했습니다.");
    await load();
  }
}

// 커스텀 생성 훅
(async function onCreated() { // ④
  await load();
})();
</script>

<template>
  <div class="cart">
    <div class="container"> <!-- ⑤ -->
      <template v-if="state.items.length">
        <ul class="items"> <!-- ⑥ -->
          <li v-for="i in state.items" style="position: relative;">
            <img :alt="`상품 사진(${i.name})`" :src="i.imgPath"/>
            <b class="name">{{ i.name }}</b>
            <span class="price">
            {{ (i.price - i.price * i.discountPer / 100).toLocaleString() }}원
          </span>
            <span class="remove" @click="remove(i.id)" title="삭제">&times;</span>
          </li>
        </ul>
        <div class="act">
          <router-link to="/order" class="btn btn-primary">주문하기</router-link>
        </div>
      </template>
      <div class="text-center py-5" v-else>장바구니가 비어있습니다.</div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.cart {
  .items {
    list-style: none;
    margin: 0;
    padding: 0;

    li { // ⑦
      border: 1px solid #eee;
      margin-top: 25px;
      margin-bottom: 25px;
    }

    img { // ⑧
      width: 145px;        // 최대 너비
      height: auto;        // 높이는 비율에 맞춰 자동
      max-height: 150px;   // 최대 높이
      object-fit: contain; // 이미지 비율 유지, 빈 공간은 비우기
    }

    .name {
      margin-left: 25px;
    }

    .price {
      margin-left: 25px;
    }

    .remove {
      position: absolute;
      top: 0;
      right: 0;
      cursor: pointer;
      font-size: 30px;
      padding: 5px 15px;
    }
  }

  .act .btn { // ⑨
    width: 300px;
    display: block;
    margin: 0 auto;
    padding: 30px 50px;
    font-size: 20px;
  }
}
</style>