<template>
  <div class="card shadow-sm">
    <span class="img" :style="{ backgroundImage: `url(${item.imgPath})` }" />
    <div class="card-body">
      <p class="card-text">
        <span> {{ item.name }} &nbsp;</span>
        <span class="discount badge bg-danger"> {{ item.discountPer }}% </span>
      </p>
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

<script lang='ts'>
import lib from "@/variousScript/lib";
import axios from "axios";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Card",

  props: {
    item: {
      type: Object,
      required: true,
    },
  },

  setup() {
    const addToCart = (placeId: number) => {
      axios.post(`/api/cart/places/${placeId}`).then(() => {
        console.log("placeId의 성공");
      });
    };

    return { lib, addToCart };
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

.card .card-body .price {
  text-decoration: line-through;
}
</style>