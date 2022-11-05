<template>
  <div class="orders">
    <div class="container">
      <table class="table table-bordered">
        <thead>
          <tr>
            <th>번호</th>
            <th>주문자명</th>
            <th>주소</th>
            <th>결제수단</th>
            <th>구입항목</th>
            <th>결제시간</th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="(o, idx1) in state.orders" :key="idx1">
            <td>{{ state.orders.length - idx1 }}</td>
            <td>{{ o.name }}</td>
            <td>{{ o.address }}</td>
            <td>{{ o.payment }}</td>
            <td>{{ o.places }}</td>
            <td>{{ o.paymentDate }}</td>
            <!-- <td>
              <div v-for="(i, idx2) in o.places" :key="idx2">
                {{ i.name }}
              </div>
            </td> -->
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import axios from "axios";
import lib from "@/variousScript/lib";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Orders",

  setup() {
    const state = reactive({
      orders: [],
    });

    axios.get("/api/orders").then(({ data }) => {
      // console.log('여기 Orders.vue의 data :', data)
      // state.orders = data

      console.log("여기 Orders.vue의 data :", data);

      state.orders = [];

      for (let d of data) {
        if (d.items) {
          d.items = JSON.parse(d.items);
        }
        state.orders.push(d);
      }

      state.orders = data;
    });

    return { state, lib };
  },
};
</script>

<style>
.table {
  margin-top: 30px;
}
.table > tbody {
  border-top: 1px solid #eee;
}
</style>