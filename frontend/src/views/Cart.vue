<template>
  <div class="cart">
    <div class="container">
      <table>
        <tr class="getTh">
          <!-- <label for="chk"> -->
          <th>
            <input type="checkbox" @click="checkedAll($event.target.checked)" />
          </th>
          <th>이미지</th>
          <th>이름</th>
          <th>정보</th>
          <th>일자</th>
          <th>금액</th>
          <th></th>
          <th></th>
          <!-- </label> -->
          <th></th>
        </tr>
        <tr v-if="this.items.length === 0" class="cart_length_0">
          <td>장바구니에 담은 상품이 없습니다.</td>
        </tr>
        <tr v-else v-for="(i, idx) in this.items" :key="idx">
          <slot name="aaa">
            <!-- <label for="chk"> -->
            <td>
              <input type="checkbox" v-model="selectedPlaceIds" :value="i.id" />
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
              <span class="date">
                {{ i.date_from }} &nbsp;~&nbsp; {{ i.date_to }} ({{
                  Number(i.date_to.substring(8, 10)) -
                  Number(i.date_from.substring(8, 10))
                }}박
                {{
                  Number(i.date_to.substring(8, 10)) -
                  Number(i.date_from.substring(8, 10)) +
                  1
                }}일)
              </span>
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
        v-if="this.items.length !== 0"
        class="btn btn-primary"
        @click="onClickOrderSet"
      >
        구입하기
      </button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import router from "@/router";
// import store from "@/store/index";
import lib from "@/variousScript/lib";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Cart",

  data() {
    return {
      //items: Object,
      img_Path: String,

      items: {
        type: Object,
      },

      allChecked: false,
      selectedPlaceIds: [],
    };
  },

  computed: {
    lib() {
      return lib;
    },
  },

  created() {
    axios.get("/api/cart/places").then(({ data }) => {
      console.log("여기가 Cart.vue 의 created data :", data);
      this.items = data;
    });
  },

  methods: {
    checkedAll(checked) {
      this.allChecked = checked;
      console.log("checkedAll의 allChecked :", this.allChecked);

      console.log("checkedAll의 selectedPlaceIds :", this.selectedPlaceIds);

      let placeId = [];
      for (let i in this.items) {
        placeId.push(this.items[i].id);
        //this.selectedPlaceIds = placeId;
        console.log("checkedAll의 for문 placeId :", placeId);
      }
    },

    onClickOrderSet() {
      /* selectedPlaceIds[i] = placeId = 1번이고,
        back에서 불러온 하나의 리스트씩 들어있는 id(= placeId)가 그 1번이면
        그 정보를 다 가져와 array param으로 담기 */
      console.log("onClickOrderSet :", this.selectedPlaceIds);
      console.log("onClickOrderSet typeof :", typeof this.selectedPlaceIds);
      let selectedLength = this.selectedPlaceIds.length;
      if (
        selectedLength === 0 ||
        selectedLength === null ||
        selectedLength === undefined
      ) {
        alert("주문상품을 체크해주세요.");
      } else {
        // 후에 주문하기로 가기
        // path: "/order",
        router.push({
          name: "ReceiveTest",
          query: this.selectedPlaceIds,
          params: this.selectedPlaceIds,
        });
      }
    },

    remove(placeId) {
      axios.delete(`/api/cart/places/${placeId}`).then(() => {
        this.created();
      });
    },
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

.cart table .date {
  margin-left: 25px;
}

.cart table .price {
  margin-left: 25px;
}
.cart table .spans {
  width: 250px;
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