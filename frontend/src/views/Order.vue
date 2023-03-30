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
                총 {{ this.items.length }}개
              </span>
            </h4>
            <ul class="list-group mb-3">
              <li
                class="list-group-item d-flex justify-content-between lh-sm"
                v-for="(i, idx) in this.items"
                :key="idx"
              >
                <div>
                  <h6 class="my-0">{{ i.name }}</h6>
                </div>
                <span class="text-muted">
                  {{ lib.getNumberFormatted(i.total_price) }}원
                </span>
                <span class="text-muted">
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
                    v-model="form.name"
                  />
                  <div class="invalid-feedback">Your username is required.</div>
                </div>

                <div class="col-12">
                  <label for="address" class="form-label"> 주소 </label>
                  <input
                    type="text"
                    class="form-control"
                    id="address"
                    v-model="form.address"
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
                    v-model="form.payment"
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
                    v-model="form.payment"
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
                    v-model="form.payment"
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
                    v-model="form.payment"
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
                    v-model="form.cardNumber"
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
import axios from "axios";
import lib from "@/variousScript/lib";
// import router from "@/router";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Order",

  props: {
    param: {
      type: Object,
      default: () => ({}),
    },
  },

  data() {
    return {
      form: {
        name: "",
        address: "",
        payment: "",
        cardNumber: "",
        places: "",
      },
      items: [],
      selectedPlaceIds: "",
    };
  },

  computed: {
    lib() {
      return lib;
    },

    computedPrice() {
      let result = 0;
      for (let i of this.items) {
        result += i.total_price;
      }
      return result;
    },
  },

  created() {
    //this.fnSelectedPlace(sendingData);

    const sendingData = this.$route.query;
    console.log("created sendingData :", sendingData);

    axios.post("/api/cart/placesForOrder", sendingData).then(({ data }) => {
      this.items = data;
      //console.log("created의 this.items :", this.items);
    });
  },

  methods: {
    // fnSelectedPlace(value) {
    //   axios.post("/api/cart/placesForOrder", value).then(({ data }) => {
    //     console.log("여기가 order 페이지의 최종 주문 result :", data);
    //     this.items = dataㅇ
    //   });
    // },

    // 결제하기 버튼 누를 때
    submit() {
      let places = "";

      for (let i = 0; i < this.items.length; i++) {
        places += this.items[i].name + ", ";
        this.form.places = places.replace(/,\s*$/, "");
      }

      const args = JSON.parse(JSON.stringify(this.form));
      //args.items = JSON.stringify(this.items);
      args.items = this.items;

      // axios.post("/api/orders", args).then(() => {
      //   console.log("여기가 Order.vue 의 args", args);
      //   alert("주문 완료했습니다.");

      //   // 후에 나의 구입목록으로 들어가기
      //   router.push({ path: "/" });
      // });
    },
  },
};
</script>

<style>
</style>