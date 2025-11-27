<script setup>
import {addItem} from "@/services/cartService";
import {useRouter} from "vue-router";
import {computed} from "vue";
import {useAccountStore} from "@/stores/account.js";

const props = defineProps({
  item: {
    id: Number,
    imgPath: String,
    name: String,
    price: Number,
    discountPer: Number
  }
});

// 상품 할인가
const computedItemDiscountPrice = computed(() => {
  return (props.item.price - (props.item.price * props.item.discountPer / 100)).toLocaleString() + '원';
})

// 라우터 객체
const router = useRouter(); // ①

// 장바구니에 상품 담기
const put = async () => { // ②
  // 로그인 여부 체크
  const accountStore = useAccountStore();
  if (!accountStore.accessToken) {
    if (window.confirm("로그인이 필요한 서비스입니다. 로그인 페이지로 이동하시겠습니까?")) {
      await router.push("/login");
    }
    return; // 로그인 안 했으면 장바구니 API 호출 X
  }
  // (추가해야 하는 부분)
  const res = await addItem(props.item.id);

  if (res.status === 200 &&
      window.confirm("장바구니에 담았습니다. 장바구니로 이동하시겠습니까?")) {
    await router.push("/cart");
  }
};
</script>

<template>
  <div class="card shadow-sm">
    <!-- 상품 사진 출력 -->
    <span class="img" :style="{backgroundImage: `url(${props.item.imgPath})`}"
          :aria-label="`상품 사진(${props.item.name})`"></span>
    <div class="card-body">
      <p class="card-text">
        <!-- 상품 이름 -->
        <span class="me-2">{{ props.item.name }}</span>
        <!-- 상품 할인율 -->
        <span v-if="props.item.discountPer>0" class="discount badge bg-danger">{{ props.item.discountPer }}%</span>
      </p>
      <div class="d-flex justify-content-between align-items-center">
        <button class="btn btn-primary btn-sm" @click="put()">장바구니 담기</button>
        <!-- 상품 정가(숫자 데이터에 3자리마다 쉼표 표기) -->
        <!-- 할인이 없으면 정가만 표시, 할인이 있으면 정가는 muted + 할인가 표시 -->
        <div class="d-flex gap-2 align-items-center">
          <small v-if="props.item.discountPer > 0" class="price text-muted">{{ props.item.price.toLocaleString() }}원</small>
          <!-- 상품 할인가 -->
          <small :class="props.item.discountPer > 0 ? 'real text-danger' : 'price'">
            {{ props.item.discountPer > 0 ? computedItemDiscountPrice : props.item.price.toLocaleString() + '원' }}
          </small>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.card {
  .img {
    display: inline-block;
    width: 100%;
    height: 250px;
    margin-top: 0.5em;
    background-repeat: no-repeat;
    background-size: contain;
    background-position: center;
  }

  .card-body {
    .card-text {
      height: 3em; /* 2줄 높이로 고정 */
      line-height: 1.5em; /* 줄 간격 설정 */
      overflow: hidden; /* 넘치는 텍스트 숨김 */
      display: -webkit-box;
      -webkit-line-clamp: 2; /* 최대 2줄까지만 표시 */
      -webkit-box-orient: vertical;
      margin-bottom: 0.5rem;
    }

    .price.text-muted {
      text-decoration: line-through;
    }
  }
}
</style>