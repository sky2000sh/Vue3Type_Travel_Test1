<template>
  <div class="cart">
    <div class="container">
      <ul>
        <!-- <li v-for="(i, idx) in state.items" :key="idx"> -->
        <li v-for="(i, idx) in state.items" :key="idx">
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
      <!-- <button class="btn btn-primary">구입하기</button> -->
      <router-link to="/order" class="btn btn-primary"> 구입하기 </router-link>
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

      parentVaule: 20,
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
    sendTotalPrice(data) {
      //this.parentVaule++;
      console.log("여기 드디어 그 데이터 :", data);
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
</style>