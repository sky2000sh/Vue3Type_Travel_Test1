<template>
  <!-- <header>
    <div class="collapse bg-dark" id="navbarHeader">
      <div class="container">
        <div class="row">
          <div class="col-sm-4 py-4">
            <h4 class="text-white">사이트맵</h4>
            <ul class="list-unstyled">
              <li>
                <router-link to="/" class="text-white">메인 화면</router-link>
              </li>
              아래 코드는 => 만약 사용자가 로그인을 했으면,
              <li v-if="$store.state.account.id">
                <router-link to="/orders" class="text-white"
                  >주문내역</router-link
                >
              </li>
              아래 코드는 => 만약 사용자가 로그아웃을 했거나 / 로그인을 하지 않았다면,
              <li>
                <router-link
                  to="/login"
                  class="text-white"
                  v-if="!$store.state.account.id"
                >
                  로그인
                </router-link>
                <a to="/login" class="text-white" @click="logout()" v-else
                  >로그아웃</a
                >
              </li>
            </ul>

            <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3" role="search">
              <input
                type="search"
                class="form-control form-control-dark text-bg-dark"
                placeholder="Search..."
                aria-label="Search"
              />
            </form>

            <div class="text-end">
              <button type="button" class="btn btn-outline-light me-2">
                Login
              </button>
              <button type="button" class="btn btn-warning">Sign-up</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="navbar navbar-dark bg-dark shadow-sm">
      <div class="container">
        <router-link to="/" class="navbar-brand d-flex align-items-center">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="20"
            height="20"
            fill="none"
            stroke="currentColor"
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            aria-hidden="true"
            class="me-2"
            viewBox="0 0 24 24"
          >
            <path
              d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z"
            />
            <circle cx="12" cy="13" r="4" />
          </svg>
          <strong>Shopping & Tour</strong>
        </router-link>

        <router-link to="/cart" class="cart btn" v-if="$store.state.account.id">
          <i class="fa fa-shopping-cart" aria-hidden="true"></i>
        </router-link>

        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarHeader"
          aria-controls="navbarHeader"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
      </div>
    </div>
  </header> -->

  <header class="p-3 text-bg-dark">
    <!-- <header
    class="
      d-flex
      flex-wrap
      align-items-center
      justify-content-center justify-content-md-between
      py-3
      mb-4
      border-bottom
    "
  > -->

    <div class="container">
      <div
        class="
          d-flex
          flex-wrap
          align-items-center
          justify-content-center justify-content-lg-start
        "
      >
        <router-link
          to="/"
          class="navbar-brand d-flex align-items-center"
          style="color: #ffffff"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="20"
            height="20"
            fill="none"
            stroke="currentColor"
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            aria-hidden="true"
            class="me-2"
            viewBox="0 0 24 24"
          >
            <path
              d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z"
            />
            <circle cx="12" cy="13" r="4" />
          </svg>
          <strong>Shopping & Tour</strong>
        </router-link>
        <!-- <ul
          class="
            nav
            col-12 col-mg-auto
            me-lg-auto
            justify-content-center
            mb-md-0
          "
        > -->
        <ul
          class="
            nav
            col-12 col-lg-auto
            me-lg-auto
            mb-2
            justify-content-center
            mb-md-0
          "
        >
          <li>
            <router-link to="/" class="nav-link px-2 text-white"
              >Home</router-link
            >
          </li>
          <!-- 아래 코드는 => 만약 사용자가 로그인을 했으면, -->
          <li v-if="$store.state.account.id">
            <router-link to="/orders" class="nav-link px-2 text-white"
              >주문내역</router-link
            >
          </li>
          <li>
            <a href="/login" class="nav-link px-2 text-white">Features</a>
          </li>
          <li><a href="#" class="nav-link px-2 text-white">Pricing</a></li>
          <li><a href="#" class="nav-link px-2 text-white">FAQs</a></li>
          <li><a href="#" class="nav-link px-2 text-white">About</a></li>
        </ul>

        <router-link to="/cart" class="cart btn" v-if="$store.state.account.id">
          <i class="fa fa-shopping-cart" aria-hidden="true"></i>
        </router-link>

        <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3" role="search">
          <input
            type="search"
            class="form-control form-control-dark text-bg-dark"
            placeholder="Search..."
            aria-label="Search"
          />
        </form>

        <div class="text-end">
          <button class="btn btn-outline-light me-2">
            <router-link
              to="/login"
              class="nav-link px-2 text-white"
              v-if="!$store.state.account.id"
            >
              로그인
            </router-link>
            <a
              to="/login"
              class="nav-link px-2 text-white"
              @click="logout()"
              v-else
              >로그아웃</a
            >
          </button>
          <button class="btn btn-warning">
            <router-link to="/join">Sign-up</router-link>
          </button>
        </div>
      </div>
    </div>
  </header>
</template>

<script>
import store from "@/store";
import router from "@/router";
import axios from "axios";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Header",

  methods: {
    logout: () => {
      axios.post("/api/account/logout").then(() => {
        store.commit("setAccount", 0);
        // sessionStorage.removeItem("id")
        router.push({ path: "/" });
      });
    },
  },
};
</script>

<style scoped>
header ul li a {
  cursor: pointer;
}

a {
  text-decoration: none;
  color: #000000;
}

header .container .d-flex .cart {
  margin-left: auto;
  color: #fff;
}
</style>>
