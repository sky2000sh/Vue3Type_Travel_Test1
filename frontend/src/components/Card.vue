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
          <button
            class="btn_increment up"
            @click="incrementAdult(cntNum.adultNum)"
          >
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
          <button class="btn_increment up" @click="incrementKid(cntNum.kidNum)">
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
          <button
            class="btn_increment up"
            @click="incrementBaby(cntNum.babyNum)"
          >
            +
          </button>
        </span>
      </div>
      <p class="card-text"></p>
      <div class="d-flex justify-content-between align-items-center">
        <button class="btn btn-primary" @click="addToCart(item.id)">
          <i class="fa fa-shopping-cart" aria-hidden="true"></i>
        </button>
        <!-- <small class="price text-muted">
          {{ lib.getNumberFormatted(item.price) }}원
        </small> -->
        <small class="current text-muted">
          현재 &nbsp;
          <!-- item.price * item.adultDisPer => 가격의 성인 1인당 할인율 x 성인 수 -->
          <!-- {{ lib.getNumberFormatted(item.price * item.adultDisPer * cntNum.adultNum)}}원 -->
          {{ lib.getNumberFormatted(totalPricePerCnt) }}원
        </small>
        <!-- <small class="current text-muted">
          현재 &nbsp;
          {{
            lib.getNumberFormatted(
              item.price - (item.price * item.discountPer) / 100
            )
          }}원
        </small> -->
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
        adultName: "adultName",
        adultNum: 0,
        kidName: "kidName",
        kidNum: 0,
        babyName: "babyName",
        babyNum: 0,
      },
      totalPricePerCnt: 0,
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

    incrementAdult(data) {
      data++;

      // if (data === 0) {
      //   let data = 1;
      //   alert("예약인원수를 입력해주세요.");
      //   return data;
      // }
      if (data > 20) {
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
      let adult = [];
      adult.push(this.cntNum.adultNum);
      adult.push(this.cntNum.adultName);

      this.increment(adult);

      return this.cntNum.adultNum;
    },

    incrementKid(data) {
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
      this.cntNum.kidNum = data;
      let kid = [];
      kid.push(this.cntNum.kidNum);
      kid.push(this.cntNum.kidName);

      this.increment(kid);

      return this.cntNum.kidNum;
    },

    incrementBaby(data) {
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
      this.cntNum.babyNum = data;
      let baby = [];
      baby.push(this.cntNum.babyNum);
      baby.push(this.cntNum.babyName);

      this.increment(baby);

      return this.cntNum.babyNum;
    },

    increment(data) {
      let getNum = data[0];
      let getName = data[1];
      // for (const [key, value] of Object.entries(this.cntNum)) {}

      if (getName === "adultName") {
        this.cntNum.adultNum = getNum; // 성인 인당 수
      } else if (getName === "kidName") {
        this.cntNum.kidNum = getNum; // 아동 인당 수
      } else if (getName === "babyName") {
        this.cntNum.babyNum = getNum; // 유아 인당 수
      }

      let price = this.item.price; // 가격
      let adultPricePerCnt = price * this.cntNum.adultNum; // 가격 x 성인 인당수
      let kidPicePerCnt = price * this.cntNum.kidNum; // 가격 x 아동 인당수
      let babyPricePerCnt = price * this.cntNum.babyNum; // 가격 x 유아 인당수
      let totalAdultPricePerCnt = 0;
      let totalKidPricePerCnt = 0;
      let totalBabyPricePerCnt = 0;

      // places별 할인된 총 가격 = (가격 x 인당수) - ((가격 x 인당수) x 사람별 할인율)
      if (getName === "adultName") {
        console.log("지금 여기 adultPricePerCnt 타?");
        // 성인 기준 총 가격
        totalAdultPricePerCnt =
          adultPricePerCnt - (adultPricePerCnt * this.item.adultDisPer) / 100;

        totalKidPricePerCnt =
          kidPicePerCnt - (kidPicePerCnt * this.item.kidDisPer) / 100;

        totalBabyPricePerCnt =
          babyPricePerCnt - (babyPricePerCnt * this.item.babyDisPer) / 100;
      } else if (getName === "kidName") {
        console.log("지금 여기 kidPicePerCnt 타?");
        // 아동 기준 총 가격
        totalKidPricePerCnt =
          kidPicePerCnt - (kidPicePerCnt * this.item.kidDisPer) / 100;

        totalAdultPricePerCnt =
          adultPricePerCnt - (adultPricePerCnt * this.item.adultDisPer) / 100;

        totalBabyPricePerCnt =
          babyPricePerCnt - (babyPricePerCnt * this.item.babyDisPer) / 100;
      } else if (getName === "babyName") {
        console.log("지금 여기 babyPricePerCnt 타?");
        // 아동 기준 총 가격
        totalBabyPricePerCnt =
          babyPricePerCnt - (babyPricePerCnt * this.item.babyDisPer) / 100;

        totalAdultPricePerCnt =
          adultPricePerCnt - (adultPricePerCnt * this.item.adultDisPer) / 100;

        totalKidPricePerCnt =
          kidPicePerCnt - (kidPicePerCnt * this.item.kidDisPer) / 100;
      }

      this.totalPricePerCnt =
        totalAdultPricePerCnt + totalKidPricePerCnt + totalBabyPricePerCnt;

      // console.log("this.totalPricePerCnt :", this.totalPricePerCnt);

      return this.totalPricePerCnt;
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