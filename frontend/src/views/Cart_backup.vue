<template>
  <div class="cart">
    <div class="container">
      <ul>
        <li v-if="state.items.length === 0" class="cart_length_0">
          장바구니에 담은 상품이 없습니다.
        </li>
        <!-- <li v-for="(i, idx) in state.items" :key="idx"> -->
        <li v-else v-for="(i, idx) in state.items" :key="idx">
          &ensp;
          <!-- <input type="checkbox" v-model="selectedPlace" /> -->
          <span>
            <input
              type="checkbox"
              v-model="selectedPlaceIds"
              :value="i.id"
              @change="clickFunc(i.id)"
            />
          </span>
          <!-- &nbsp; / &ensp; / &emsp;-->
          &ensp;
          <img :src="i.img_Path" />
          <span class="name"> {{ i.name }} </span>
          <!-- <span class="price">
            첫번째 기존! :
            {{
              lib.getNumberFormatted(i.price - (i.price * i.discountPer) / 100)
            }}원
          </span> -->
          <span class="price"> 성인 : {{ i.adult_num }}명 </span>
          <span class="price"> 아동 : {{ i.kid_num }}명 </span>
          <span class="price"> 유아 : {{ i.baby_num }}명 </span>
          <span class="price">
            총액 : {{ lib.getNumberFormatted(i.total_price) }}원
          </span>
          <i class="fa fa-trash" @click="remove(i.id)"></i>
        </li>
      </ul>
      <button
        v-if="state.items.length !== 0"
        class="btn btn-primary"
        @click="onClickOrderSet"
      >
        구입하기
      </button>
      <!-- <router-link
        v-if="state.items.length !== 0"
        to="/order"
        class="btn btn-primary"
      >
        구입하기
      </router-link> -->
    </div>
  </div>
</template>

<script>
import { reactive } from "vue";
import axios from "axios";
import lib from "@/variousScript/lib";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Cart",

  data() {
    return {
      items: Object,
      img_Path: String,

      selectedPlaceIds: [],
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
    clickFunc(value) {
      console.log("value :", value);
      this.items = this.state.items;

      let value1 = [];
      let value2 = [];
      //let value1 = "";
      for (let i = 0; i < this.selectedPlaceIds.length; i++) {
        console.log("여기 selectedPlaceIds[i] :", this.selectedPlaceIds[i]);
        value1.push(this.selectedPlaceIds[i]);
        console.log("value1 :", value1);

        /* selectedPlaceIds[i] = placeId = 1번이고,
          back에서 불러온 하나의 리스트씩 들어있는 id(= placeId)가 그 1번이면
          그 정보를 다 가져와 array param으로 담기 */
        // if (this.selectedPlaceIds[i] === this.state.items[i].id) {
        //   console.log("이러면 완전 성공 :");
        // }
      }
      for (let j = 0; j < this.items.length; j++) {
        value2.push(this.items[j].id);
        if (value1.includes(j) === true && value2.includes(j) === true) {
          console.log("value1[j] :", value1[j]);
          console.log("value2[j] :", value2[j]);

          if (j in this.state.items) {
            console.log("this.items is ?:", this.state.items[j]);
          }
        }
      }

      console.log("이러면 최종 성공 :", value1);
    },

    onClickOrderSet(data) {
      console.log("onClickOrderSet :", data);
    },
  },
};
</script>

<style>
.cart ul {
  list-style: none;
  margin: 0;
  padding: 0;
}

.cart ul li {
  border: 1px solid #eee;
  margin-top: 25px;
  margin-bottom: 25px;
}

.cart ul li img {
  width: 150px;
  height: 150px;
}

.cart ul li .name {
  margin-left: 25px;
}

.cart ul li .price {
  margin-left: 25px;
}

.cart ul li i {
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