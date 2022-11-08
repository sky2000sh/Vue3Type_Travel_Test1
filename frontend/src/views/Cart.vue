<template>
  <div class="cart">
    <div class="container">
      <ul>
        <!-- <li v-for="(i, idx) in state.items" :key="idx"> -->
        <li v-for="(i, idx) in state.items" :key="idx">
          <img :src="i.imgPath" />
          <span class="name"> {{ i.name }} </span>
          <span class="price">
            {{
              lib.getNumberFormatted(i.price - (i.price * i.discountPer) / 100)
            }}원
          </span>
          <span class="price1">
            <totalPrice v-on:childEvent="updateParentValue" />
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
// import totalPrice from "@/components/Card.vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Cart",

  data() {
    return {
      items: Object,
      imgPath: String,

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
        // console.log("여기가 Cart.vue 의 totalPrice :", totalPrice);
        // console.log(
        //   "여기가 Cart.vue 의 totalPrice.data.methods :",
        //   totalPrice.methods.totalPrice.totalPricePerCnt
        // );
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

  // methods: {
  //   updateParentValue() {
  //     this.parentVaule++;
  //     console.log(this.parentVaule); // 21, 22, 22, 누를때마다 증가하는 것 확인 가능
  //   },
  // },
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