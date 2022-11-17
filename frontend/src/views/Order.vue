<template v-slot:aaa>
  <div class="cart">
    <div class="container">
      <main>
        <div class="py-5 text-center">
          <h2>주문하기</h2>
          <!-- <div :class="selectedPlaceIds" /> -->
          <p class="lead">
            Below is an example form built entirely with Bootstrap’s form
            controls. Each required form group has a validation state that can
            be triggered by attempting to submit the form without completing it.
          </p>
        </div>

        <div class="row g-5">
          <div class="col-md-5 col-lg-4 order-md-last">
            <h4 class="d-flex justify-content-between align-items-center mb-3">
              <span class="text-primary"> 구입 목록 </span>
              <span class="badge bg-primary rounded-pill">
                총 {{ state.items.length }}개
              </span>
            </h4>
            <ul class="list-group mb-3">
              <li
                class="list-group-item d-flex justify-content-between lh-sm"
                v-for="(i, idx) in state.items"
                :key="idx"
              >
                <div>
                  <h6 class="my-0">{{ i.name }}</h6>
                </div>
                <span class="text-muted">
                  {{
                    lib.getNumberFormatted(
                      i.price - (i.price * i.discountPer) / 100
                    )
                  }}원
                </span>
              </li>
            </ul>

            <h3 class="text-center total-price">
              {{ lib.getNumberFormatted(computedPrice) }}원
            </h3>
          </div>
          <div class="col-md-7 col-lg-8">
            <h4 class="mb-3">주문자 정보</h4>
            <div class="needs-validation" novalidate>
              <div class="row g-3">
                <div class="col-12">
                  <label for="username" class="form-label"> 이름 </label>
                  <input
                    type="text"
                    class="form-control"
                    id="username"
                    v-model="state.form.name"
                  />
                  <div class="invalid-feedback">Your username is required.</div>
                </div>

                <div class="col-12">
                  <label for="address" class="form-label"> 주소 </label>
                  <input
                    type="text"
                    class="form-control"
                    id="address"
                    v-model="state.form.address"
                  />
                  <div class="invalid-feedback">
                    Please enter your shipping address.
                  </div>
                </div>
              </div>

              <hr class="my-4" />

              <h4 class="mb-3">결제 수단</h4>
              <div class="my-3">
                <div class="form-check">
                  <input
                    id="credit"
                    name="paymentMethod"
                    type="radio"
                    class="form-check-input"
                    value="credit"
                    v-model="state.form.payment"
                  />
                  <label class="form-check-label" for="credit">
                    신용카드
                  </label>
                </div>
                <div class="form-check">
                  <input
                    id="debit"
                    name="paymentMethod"
                    type="radio"
                    class="form-check-input"
                    value="debit"
                    v-model="state.form.payment"
                  />
                  <label class="form-check-label" for="debit">
                    직불(체크)카드</label
                  >
                </div>
                <div class="form-check">
                  <input
                    id="sendwoaccount"
                    name="paymentMethod"
                    type="radio"
                    class="form-check-input"
                    value="sendwoaccount"
                    v-model="state.form.payment"
                  />
                  <label class="form-check-label" for="sendwoaccount"
                    >무통장입금</label
                  >
                </div>
                <div class="form-check">
                  <input
                    id="paypal"
                    name="paymentMethod"
                    type="radio"
                    class="form-check-input"
                    value="paypal"
                    v-model="state.form.payment"
                  />
                  <label class="form-check-label" for="paypal">PayPal</label>
                </div>
              </div>

              <div class="row gy-3">
                <div class="col-md-6">
                  <label for="cc-name" class="form-label"> 카드 번호 </label>
                  <input
                    type="text"
                    class="form-control"
                    id="cc-name"
                    v-model="state.form.cardNumber"
                  />
                  <div class="invalid-feedback">Name on card is required</div>
                </div>

                <div class="col-md-6">
                  <label for="cc-number" class="form-label"
                    >Credit card number</label
                  >
                  <input
                    type="text"
                    class="form-control"
                    id="cc-number"
                    placeholder=""
                    required
                  />
                  <div class="invalid-feedback">
                    Credit card number is required
                  </div>
                </div>
              </div>

              <hr class="my-4" />

              <button class="w-100 btn btn-primary btn-lg" @click="submit()">
                결제하기
              </button>
            </div>
          </div>
        </div>
      </main>
    </div>
  </div>
</template>

<script>
import { computed, reactive } from "@vue/reactivity";
import axios from "axios";
import lib from "@/variousScript/lib";
import router from "@/router";
// import cart from "@/views/Cart.vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Order",

  // components: {
  //   Cart: cart,
  // },

  props: {
    param: {
      type: Object,
      default: () => ({}),
    },
  },

  data() {
    return {
      selectedPlaceIds: "",
    };
  },

  created() {
    console.log("created this.route :", this.$route);
  },

  mounted() {
    let result = [this.$route.query];
    console.log("mounted의 result :", result);
    console.log("mounted의 query :", this.$route.query);
    console.log("mounted의 params :", this.$route.params);
  },

  setup() {
    const state = reactive({
      items: [],
      form: {
        name: "",
        address: "",
        payment: "",
        cardNumber: "",
        places: "",
      },
    });

    const load = () => {
      axios.get("/api/cart/places").then(({ data }) => {
        console.log("여기가 Cart.vue 의 data :", data);
        state.items = data;
      });
    };

    const submit = () => {
      let places = "";

      axios.get("/api/cart/places").then(({ data }) => {
        state.items = data;
      });

      for (let i = 0; i < state.items.length; i++) {
        places += state.items[i].name + ", ";
        state.form.places = places.replace(/,\s*$/, "");
      }

      const args = JSON.parse(JSON.stringify(state.form));
      args.items = JSON.stringify(state.items);

      axios.post("/api/orders", args).then(() => {
        console.log("여기가 Order.vue 의 args", args);
        alert("주문 완료했습니다.");

        // 후에 나의 구입목록으로 들어가
        router.push({ path: "/" });
      });
    };

    const computedPrice = computed(() => {
      let result = 0;

      for (let i of state.items) {
        result += i.price - (i.price * i.discountPer) / 100;
      }

      return result;
    });

    load();

    return { state, lib, submit, computedPrice };
  },
};
</script>

<style>
</style>