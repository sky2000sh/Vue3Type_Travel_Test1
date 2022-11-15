<template>
  <div class="cart">
    <div class="container">
      <table>
        <tr class="getTh">
          <!-- <label for="chk"> -->
          <th><input type="checkbox" /></th>
          <th>이미지</th>
          <th>이름</th>
          <th>정보</th>
          <th>금액</th>
          <th></th>
          <th></th>
          <!-- </label> -->
          <th></th>
        </tr>
        <tr v-if="state.items.length === 0" class="cart_length_0">
          <td>장바구니에 담은 상품이 없습니다.</td>
        </tr>
        <tr v-else v-for="(i, idx) in state.items" :key="idx">
          <slot name="aaa">
            <!-- <label for="chk"> -->
            <td>
              <input
                type="checkbox"
                v-model="selectedPlaceIds"
                :value="i.id"
                @change="getCheckboxValue"
              />
              <!-- true-value="Y"
                false-value="N" -->
              <!-- id="chk" -->
              <!-- :value="i.id" -->
              <!-- @change="getCheckboxValue" -->
            </td>
            <td><img :src="i.img_Path" /></td>
            <td>
              <span class="name"> {{ i.name }} </span>
            </td>
            <td>
              <span class="price"> 성인 : {{ i.adult_num }}명 </span>
              <span class="price"> 아동 : {{ i.kid_num }}명 </span>
              <span class="price"> 유아 : {{ i.baby_num }}명 </span>
            </td>
            <td>
              <span class="price">
                총액 : {{ lib.getNumberFormatted(i.total_price) }}원
              </span>
            </td>
            <td class="spans"></td>
            <td></td>
            <!-- </label> -->
            <td><i class="fa fa-trash" @click="remove(i.id)"></i></td>
          </slot>
        </tr>
      </table>
      <br />
      <button
        v-if="state.items.length !== 0"
        class="btn btn-primary"
        @click="onClickOrderSet(state.items[0].memberId)"
      >
        구입하기
      </button>
    </div>
  </div>
</template>

<script>
import { reactive } from "vue";
import axios from "axios";
import router from "@/router";
// import store from "@/store/index";
import lib from "@/variousScript/lib";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Cart",

  data() {
    return {
      items: Object,
      img_Path: String,

      selectedPlaceIds: [],
      // { type: Boolean,},
    };
  },

  setup() {
    const state = reactive({
      items: {
        type: Object,
      },
    });

    const load = () => {
      axios.get("/api/cart/places").then(({ data }) => {
        console.log("여기가 Cart.vue 의 data :", data);
        state.items = data;
      });
    };

    const remove = (placeId) => {
      axios.delete(`/api/cart/places/${placeId}`).then(() => {
        load();
      });
    };

    load();

    return { state, lib, remove };
  },

  methods: {
    onClickOrderSet(value) {
      /* selectedPlaceIds[i] = placeId = 1번이고,
        back에서 불러온 하나의 리스트씩 들어있는 id(= placeId)가 그 1번이면
        그 정보를 다 가져와 array param으로 담기 */
      console.log("onClickOrderSet :", this.selectedPlaceIds);
      console.log("onClickOrderSet typeof :", typeof this.selectedPlaceIds);

      let memberId = value;
      console.log(" memberId :", memberId);
      console.log(" memberId typeof :", typeof memberId);

      let settin = [{ memberId: memberId, placeId: this.selectedPlaceIds }];
      //this.emitter.emit("sendselectedPlaceIds", this.selectedPlaceIds);

      console.log("settin :", settin);
      console.log("settin typeof :", typeof settin);

      axios
        .post("/api/cart/placesForOrder", this.selectedPlaceIds)
        // .post("/api/cart/placesForOrder", settin)
        .then(({ data }) => {
          console.log("여기가 Cart.vue의 최종 주문 data :", data);
          //alert("주문 완료했습니다.");

          // const param = {
          //   actId: '',
          //   eventIds: strEvent,
          // };

          // 후에 주문하기로 가기
          // router.push({ path: "/order", params: param });
        });
    },

    // getCheckboxValue(event) {
    //   console.log("getCheckboxValue checked :", event.target.checked); // true
    //   console.log("getCheckboxValue value :", event.target.value); // 1 또는 2 (= placeId)
    //   console.log("selectedPlaceIds :", this.selectedPlaceIds);
    // },
  },
};
</script>

<style>
.cart table {
  list-style: none;
  margin: 0;
  margin-top: 25px;
  padding: 0;
}

.cart table .getTh {
  text-align: center;
}

.cart table tr {
  /* .cart table td { */
  border: 1px solid #eee;
  margin-top: 25px;
  margin-bottom: 25px;
}

.cart table img {
  width: 150px;
  height: 150px;
}

.cart table .name {
  margin-left: 25px;
}

.cart table .price {
  margin-left: 25px;
}
.cart table .spans {
  width: 500px;
}

.cart table i {
  float: right;
  font-size: 20px;
  margin-top: 65px;
  margin-right: 50px;
}

.cart .btn {
  width: 300px;
  display: block;
  margin: 0 auto;
  padding: 30px 50px;
  font-size: 20px;
}

.cart .cart_length_0 {
  text-align: center;
  border: 0;
}
</style>