<template>
  <div class="form-signin w-100 m-auto">
    <!-- <form> -->
    <h1 class="h3 mb-3 fw-normal">Please sign in</h1>

    <div class="form-floating">
      <!-- @keyup.enter="submit()" => 공란에 엔터키를 치면 넘어가는 형식 -->
      <input
        type="email"
        class="form-control"
        id="floatingInput"
        placeholder="name@example.com"
        @keyup.enter="submit()"
        v-model="state.form.email"
      />
      <label for="floatingInput">Email address</label>
    </div>
    <div class="form-floating">
      <input
        type="password"
        class="form-control"
        id="floatingPassword"
        placeholder="Password"
        @keyup.enter="submit()"
        v-model="state.form.password"
      />
      <label for="floatingPassword">Password</label>
    </div>

    <div class="checkbox mb-3">
      <label>
        <input type="checkbox" value="remember-me" @keyup.enter="submit()" />
        Remember me
      </label>
    </div>
    <button class="w-100 btn btn-lg btn-primary" @click="submit()">
      Sign in
    </button>
    &nbsp;
    <router-link
      to="/join"
      class="w-100 btn btn-lg btn-primary"
      style="background-color: pink"
    >
      Sign up
    </router-link>
    <p class="mt-5 mb-3 text-muted">&copy; 2017–2022</p>
    <!-- </form> -->
  </div>
</template>

<script>
import { reactive } from "vue";
import axios from "axios";
import store from "@/store";
import router from "@/router";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Login",

  setup() {
    const state = reactive({
      form: {
        email: "",
        password: "",
      },
    });

    const submit = () => {
      axios
        .post("/api/account/login", state.form)
        .then((res) => {
          console.log("login submit하면 :", res);
          res.data;
          store.commit("setAccount", res.data);
          // sessionStorage.setItem()에 id와 id value값을 저장 및 보관해서 마치 세션이 살아있도록 해준다.
          sessionStorage.setItem("id", res.data);
          router.push({ path: "/" });
          window.alert("로그인 성공했습니다. 환영합니다!");
        })
        .catch(() => {
          window.alert("로그인 정보가 존재하지 않습니다.");
        });
    };

    return { state, submit };
  },
};
</script>

<style scoped>
.form-signin {
  max-width: 330px;
  padding: 15px;
}

.form-signin .form-floating:focus-within {
  z-index: 2;
}

.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
</style>