<template>
  <div class="wrapper">
    <div class="title">Please sign in</div>
    <div class="form">
      <!-- 이메일 email -->
      <div class="inputfield">
        <label>Email address</label>
        <input
          type="email"
          class="input"
          @keyup.enter="submit()"
          v-model="form.email"
          ref="email"
        />
      </div>
      <!-- 비밀벌호 password -->
      <div class="inputfield">
        <label>Password</label>
        <input
          type="password"
          class="input"
          @keyup.enter="submit()"
          v-model="form.password"
          ref="password"
        />
      </div>
      <!-- <div class="inputfield">
        <label>Remember me</label>
        <input type="checkbox" value="remember-me" class="input" />
      </div> -->
      <div class="inputfield">
        <input value="Sign in" class="btn" @click="submit()" />
      </div>
    </div>
  </div>
</template>

<script>
// import { reactive } from "vue";
import axios from "axios";
import store from "@/store";
import router from "@/router";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Login",

  data() {
    return {
      form: {
        email: "",
        password: "",
      },
    };
  },

  methods: {
    submit() {
      //console.log("this.form? :", this.form);

      if (!this.fnValidateSave()) return;
      const param = this.form;

      console.log("const param :", param);
      axios
        .post("/api/account/login", param)
        .then((result) => {
          console.log("login submit하면 :", result.data);

          if (result.data === 0) {
            window.alert(
              "이메일 또는 비밀번호가 일치하지 않습니다. 다시 로그인해주세요."
            );
            this.form.email = null;
            this.form.password = null;
            this.$refs.email.focus();
          } else {
            store.commit("setAccount", result.data);
            // sessionStorage.setItem()에 id와 id value값을 저장 및 보관해서 마치 세션이 살아있도록 해준다.
            sessionStorage.setItem("id", result.data);
            router.push({ path: "/" });
            window.alert("로그인 성공했습니다. 환영합니다!");
          }
        })
        .catch(() => {
          window.alert("로그인 정보가 존재하지 않습니다.");
        });
    },

    /***************** Validation Function *******************/
    fnValidateSave() {
      let result = true;

      if ((this.form.email === null, undefined || this.form.email === "")) {
        window.alert("이메일을 입력해주세요.");
        this.$refs.email.focus();
        return false;
      }
      if (
        (this.form.password === null, undefined || this.form.password === "")
      ) {
        window.alert("비밀번호를 입력해주세요.");
        this.$refs.password.focus();
        return false;
      }

      return result;
    },
  },

  // setup() {
  //   const state = reactive({
  //     form: {
  //       email: "",
  //       password: "",
  //     },
  //   });

  //   const submit = () => {
  //     axios
  //       .post("/api/account/login", state.form)
  //       .then((res) => {
  //         console.log("login submit하면 :", res);
  //         res.data;
  //         store.commit("setAccount", res.data);
  //         // sessionStorage.setItem()에 id와 id value값을 저장 및 보관해서 마치 세션이 살아있도록 해준다.
  //         sessionStorage.setItem("id", res.data);
  //         router.push({ path: "/" });
  //         window.alert("로그인 성공했습니다. 환영합니다!");
  //       })
  //       .catch(() => {
  //         window.alert("로그인 정보가 존재하지 않습니다.");
  //       });
  //   };

  //   return { state, submit };
  // },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css?family=Montserrat:400,700&display=swap");

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "Montserrat", sans-serif;
}
body {
  background: #fec107;
  padding: 0 10px;
}
.wrapper {
  max-width: 500px;
  width: 100%;
  background: #fff;
  margin: 20px auto;
  box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.125);
  padding: 30px;
}

.wrapper .title {
  font-size: 24px;
  font-weight: 700;
  margin-bottom: 25px;
  color: #fec107;
  text-transform: uppercase;
  text-align: center;
}

.wrapper .form {
  width: 100%;
}

.wrapper .form .inputfield {
  margin-bottom: 15px;
  display: flex;
  align-items: center;
}

.wrapper .form .inputfield label {
  width: 200px;
  color: #757575;
  margin-right: 10px;
  font-size: 14px;
}

.wrapper .form .inputfield .input,
.wrapper .form .inputfield .textarea {
  width: 100%;
  outline: none;
  border: 1px solid #d5dbd9;
  font-size: 15px;
  padding: 8px 10px;
  border-radius: 3px;
  transition: all 0.3s ease;
}

.wrapper .form .inputfield .textarea {
  width: 100%;
  height: 125px;
  resize: none;
}

.wrapper .form .inputfield .custom_select {
  position: relative;
  width: 100%;
  height: 37px;
}

.wrapper .form .inputfield .custom_select:before {
  content: "";
  position: absolute;
  top: 12px;
  right: 10px;
  border: 8px solid;
  border-color: #d5dbd9 transparent transparent transparent;
  pointer-events: none;
}

.wrapper .form .inputfield .custom_select select {
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  outline: none;
  width: 100%;
  height: 100%;
  border: 0px;
  padding: 8px 10px;
  font-size: 15px;
  border: 1px solid #d5dbd9;
  border-radius: 3px;
}

.wrapper .form .inputfield .input:focus,
.wrapper .form .inputfield .textarea:focus,
.wrapper .form .inputfield .custom_select select:focus {
  border: 1px solid #fec107;
}

.wrapper .form .inputfield p {
  font-size: 14px;
  color: #757575;
}
.wrapper .form .inputfield .check {
  width: 15px;
  height: 15px;
  position: relative;
  display: block;
  cursor: pointer;
}
.wrapper .form .inputfield .check input[type="checkbox"] {
  position: absolute;
  top: 0;
  left: 0;
  opacity: 0;
}
.wrapper .form .inputfield .check .checkmark {
  width: 15px;
  height: 15px;
  border: 1px solid #fec107;
  display: block;
  position: relative;
}
.wrapper .form .inputfield .check .checkmark:before {
  content: "";
  position: absolute;
  top: 1px;
  left: 2px;
  width: 5px;
  height: 2px;
  border: 2px solid;
  border-color: transparent transparent #fff #fff;
  transform: rotate(-45deg);
  display: none;
}
.wrapper .form .inputfield .check input[type="checkbox"]:checked ~ .checkmark {
  background: #fec107;
}

.wrapper
  .form
  .inputfield
  .check
  input[type="checkbox"]:checked
  ~ .checkmark:before {
  display: block;
}

.wrapper .form .inputfield .btn {
  width: 100%;
  padding: 8px 10px;
  font-size: 15px;
  border: 0px;
  background: #fec107;
  color: #fff;
  cursor: pointer;
  border-radius: 3px;
  outline: none;
}

.wrapper .form .inputfield .btn:hover {
  background: #ffd658;
}

.wrapper .form .inputfield:last-child {
  margin-bottom: 0;
}

@media (max-width: 420px) {
  .wrapper .form .inputfield {
    flex-direction: column;
    align-items: flex-start;
  }
  .wrapper .form .inputfield label {
    margin-bottom: 5px;
  }
  .wrapper .form .inputfield.terms {
    flex-direction: row;
  }
}
</style>