<template>
  <div class="card shadow-sm">
    <span class="img" :style="{ backgroundImage: `url(${item.imgPath})` }" />
    <div class="card-body">
      <p class="card-text">
        <span>
          <strong> {{ item.name }} &nbsp; </strong>
        </span>
        <span class="discount badge bg-danger"> {{ item.discountPer }}% </span>
      </p>
      <div class="d-flex justify-content-between align-items-center">
        <!-- <small> 인원선택 </small> -->

        <small> 성 인 </small>
        <span class="num_count_group">
          <button
            class="btn_decrement down"
            @click="decrement(cntNum.adultNum)"
          >
            -
          </button>
          <span class="inpt_counter">
            <!-- <input class="inpt_counter1" type="text" readonly /> -->
            <input
              class="inpt_counter1"
              type="number"
              min="1"
              max="20"
              v-model="cntNum.adultNum"
              readonly
            />
          </span>
          <button class="btn_increment up" @click="increment(cntNum.adultNum)">
            +
          </button>
        </span>

        <small> 아 동 </small>
        <span class="num_count_group">
          <button class="btn_decrement down" @click="decrement(cntNum.kidNum)">
            -
          </button>
          <span class="inpt_counter">
            <input
              class="inpt_counter1"
              type="number"
              min="1"
              max="20"
              v-model="cntNum.kidNum"
              readonly
            />
          </span>
          <button class="btn_increment up" @click="increment(cntNum.kidNum)">
            +
          </button>
        </span>

        <small> 유 아 </small>
        <span class="num_count_group">
          <button class="btn_decrement down" @click="decrement(cntNum.babyNum)">
            -
          </button>
          <span class="inpt_counter">
            <input
              class="inpt_counter1"
              type="number"
              min="1"
              max="20"
              v-model="cntNum.babyNum"
              readonly
            />
          </span>
          <button class="btn_increment up" @click="increment(cntNum.babyNum)">
            +
          </button>
        </span>
      </div>
      <p class="card-text"></p>
      <div class="d-flex justify-content-between align-items-center">
        <button class="btn btn-primary" @click="addToCart(item.id)">
          <i class="fa fa-shopping-cart" aria-hidden="true"></i>
        </button>
        <small class="price text-muted">
          {{ lib.getNumberFormatted(item.price) }}원
        </small>
        <small class="current text-muted">
          현재 &nbsp;
          {{
            lib.getNumberFormatted(
              item.price - (item.price * item.discountPer) / 100
            )
          }}원
        </small>
      </div>
    </div>
  </div>
</template>

<script>
import lib from "@/variousScript/lib";
import axios from "axios";
// import { PropType } from "vue";
// import Item from "@/types/Item";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Card",

  props: {
    item: {
      type: Object,
      required: true,
      // type: Array as PropType<Item[]>,
    },
  },

  data() {
    return {
      cntNum: {
        totalNum: 0,
        adultNum: 1,
        kidNum: 1,
        babyNum: 1,
      },
    };
  },

  setup() {
    const addToCart = (placeId) => {
      axios.post(`/api/cart/places/${placeId}`).then(() => {
        console.log("placeId의 성공");
      });
    };

    return { lib, addToCart };
  },

  methods: {
    decrement(data) {
      data--;

      if (data <= 0) {
        let data = 1;
        alert("예약인원수는 최소 1명 이상입니다.");
        return data;
      } else if (data !== Number(data)) {
        let data = 1;
        alert("숫자만 입력가능합니다.");
        return data;
      }
      console.log("decrement data :", data);
      this.cntNum.adultNum = data;

      return this.cntNum.adultNum;
    },

    increment(data) {
      data++;

      if (data === 0) {
        let data = 1;
        alert("예약인원수를 입력해주세요.");
        return data;
      } else if (data > 20) {
        let data = 1;
        alert("최대 20명까지 예약가능합니다.");
        return data;
      } else if (data !== Number(data)) {
        let data = 1;
        alert("숫자만 입력가능합니다.");
        return data;
      }
      console.log("increment data :", data);
      this.cntNum.adultNum = data;

      return this.cntNum.adultNum;
    },
  },
};
</script>

<style scoped>
.card .img {
  width: 100%;
  height: 250px;
  background-size: cover;
  background-position: center;
}

.card .card-body .card-text {
  font-size: 1.5em;
}

.card .card-body .price {
  text-decoration: line-through;
}

.card .card-body .num_count_group .inpt_counter1 {
  width: 27px;
  height: 30px;
}

input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}
</style>