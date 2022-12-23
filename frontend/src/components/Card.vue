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
      <div class="inputfield">
        <small> 시작일자 </small>
        <span>
          <input type="date" class="input" v-model="date.date_from" />
        </span>

        <small> 종료일자 </small>
        <span>
          <input type="date" class="input" v-model="date.date_to" />
        </span>
      </div>

      <br />

      <div class="d-flex justify-content-between align-items-center">
        <!-- <small> 인원선택 </small> -->

        <small> 성 인 </small>
        <span class="num_count_group">
          <button
            class="btn_decrement down"
            @click="decrementAdult(cntNum.adultNum)"
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
          <button
            class="btn_decrement down"
            @click="decrementKid(cntNum.kidNum)"
          >
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
          <button
            class="btn_decrement down"
            @click="decrementBaby(cntNum.babyNum)"
          >
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
        <button
          v-if="this.values1.length === 0"
          class="btn btn-primary"
          @click="addToCart2(this.values2)"
        >
          <i class="fa fa-shopping-cart" aria-hidden="true"></i>
        </button>
        <button v-else class="btn btn-primary" @click="addToCart(this.values1)">
          <!-- @click="addToCart(item.id)" -->
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

          <!-- <button @click="updateParentValue">
            클릭시 부모의 데이터 값이 증가합니다.
          </button> -->
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
        adultNum: 1,
        kidName: "kidName",
        kidNum: 0,
        babyName: "babyName",
        babyNum: 0,
        // date_from: "",
        // date_to: "",
        // date_cart_put: "",
      },
      totalPricePerCnt: 0,
      realSendingTotalPrice: 0,
      valueTotalPrice: this.item.price,
      values1: [],
      values2: [this.item.id, this.item.price],

      date: {
        date_from: "",
        date_to: "",
        date_cart_put: "",
      },
    };
  },

  mounted() {
    this.totalPricePerCnt = this.item.price;
  },

  setup() {
    const addToCart2 = (param) => {
      // console.log("addToCart2 param :", param);
      // console.log("addToCart2 param.length :", param.length);

      let totalPrice = 0;
      let placeId = 0;
      let adultNum = 1;
      let kidNum = 0;
      let babyNum = 0;

      placeId = param[0];
      totalPrice = param[1];

      param.push(adultNum);
      param.push(kidNum);
      param.push(babyNum);

      console.log("addToCart2 최종 param :", param);

      axios
        .post(`/api/cart/places/${placeId}`, {
          totalPrice,
          placeId,
          adultNum,
          kidNum,
          babyNum,
        })
        .then(() => {
          console.log("addToCart2 placeId의 성공");
        })
        .catch();
    };

    const addToCart = (param) => {
      console.log("addToCart의 placeId 성공 param :", param);
      //console.log("addToCart의 date :", this.date);

      let totalPrice = 0;
      let placeId = 0;
      let adultNum = 0;
      let kidNum = 0;
      let babyNum = 0;
      let date_from = "";
      let date_to = "";
      let date_cart_put = "";

      for (let i = 0; i < param.length; i++) {
        date_cart_put = param[i];
        date_to = param[i - 1];
        date_from = param[i - 2];
        babyNum = param[i - 3];
        kidNum = param[i - 4];
        adultNum = param[i - 5];
        placeId = param[i - 6];
        totalPrice = param[i - 7];
      }

      axios
        .post(`/api/cart/places/${placeId}`, {
          totalPrice,
          placeId,
          adultNum,
          kidNum,
          babyNum,
          date_from,
          date_to,
          date_cart_put,
        })
        .then(() => {
          console.log("addToCart placeId의 성공");
        })
        .catch();
    };

    return { lib, addToCart, addToCart2 };
  },

  methods: {
    sendTotalPrice(data) {
      let id = this.item.id;
      let finalAdultNum = this.cntNum.adultNum;
      let finalKidNum = this.cntNum.kidNum;
      let finalBabyNum = this.cntNum.babyNum;
      let date_from = this.date.date_from;
      let date_to = this.date.date_to;
      let date_cart_put = this.date.date_cart_put;
      //console.log("여기! sendTotalPrice data 12345 :", id);

      this.values1.push(data);
      this.values1.push(id);
      this.values1.push(finalAdultNum);
      this.values1.push(finalKidNum);
      this.values1.push(finalBabyNum);
      this.values1.push(date_from);
      this.values1.push(date_to);
      this.values1.push(date_cart_put);

      console.log("sendTotalPrice의 this.values1 :", this.values1);

      return this.values1;
    },

    decrementAdult(data) {
      data--;

      if (data <= 0) {
        let data = 1;
        alert("예약인원수는 최소 1명 이상입니다.");
        return data;
      }
      if (data > 20) {
        let data = 1;
        alert("최대 20명까지 예약가능합니다.");
        return data;
      } else if (data !== Number(data)) {
        let data = 1;
        alert("숫자만 입력가능합니다.");
        return data;
      }
      console.log("decrement data :", data);
      this.cntNum.adultNum = data;
      let adult = [];
      adult.push(this.cntNum.adultNum);
      adult.push(this.cntNum.adultName);

      this.totalPrice(adult);

      return this.cntNum.adultNum;
    },

    decrementKid(data) {
      data--;

      if (data < 0) {
        let data = 0;
        alert("다시 지정해주세요.");
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
      console.log("decrement data :", data);
      this.cntNum.kidNum = data;
      let kid = [];
      kid.push(this.cntNum.kidNum);
      kid.push(this.cntNum.kidName);

      this.totalPrice(kid);

      return this.cntNum.kidNum;
    },

    decrementBaby(data) {
      data--;

      if (data < 0) {
        let data = 0;
        alert("다시 지정해주세요.");
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
      console.log("decrement data :", data);
      this.cntNum.babyNum = data;
      let baby = [];
      baby.push(this.cntNum.babyNum);
      baby.push(this.cntNum.babyName);

      this.totalPrice(baby);

      return this.cntNum.babyNum;
    },

    incrementAdult(data) {
      data++;

      if (data <= 0) {
        let data = 1;
        alert("예약인원수는 최소 1명 이상입니다.");
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
      let adult = [];
      adult.push(this.cntNum.adultNum);
      adult.push(this.cntNum.adultName);

      this.totalPrice(adult);

      return this.cntNum.adultNum;
    },

    incrementKid(data) {
      data++;

      if (data < 0) {
        let data = 0;
        alert("다시 지정해주세요.");
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

      this.totalPrice(kid);

      return this.cntNum.kidNum;
    },

    incrementBaby(data) {
      data++;

      if (data < 0) {
        let data = 0;
        alert("다시 지정해주세요.");
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

      this.totalPrice(baby);

      return this.cntNum.babyNum;
    },

    totalPrice(data) {
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
      let params = this.totalPricePerCnt;

      // let id = this.item.id;
      // console.log("여기! increment data 12345 :", id);
      this.sendTotalPrice(params);

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