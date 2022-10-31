<template>
  <div class="container">
    <!-- <div class="title">Registration</div> -->
    <!-- <div class="content"> -->
    <!-- <form action="#"> -->
    <h1 class="h3 mb-3 fw-normal">Registration</h1>
    <div class="user-details">
      <!-- <div class="form-floating">
        <span class="details">Full Name</span>
        <input type="text" placeholder="Enter your name" required />
      </div> -->
      <div class="form-floating">
        <span class="details">Email</span>
        <input
          type="text"
          v-model="member.email"
          placeholder="Enter your email"
          required
        />
      </div>
      <!-- <div class="form-floating">
        <span class="details">Phone Number</span>
        <input type="text" placeholder="Enter your number" required />
      </div> -->
      <div class="form-floating">
        <span class="details">Membername</span>
        <input
          type="text"
          v-model="member.memberName"
          placeholder="Enter your membername"
          required
        />
      </div>
      <div class="form-floating">
        <span class="details">Password</span>
        <input
          type="password"
          v-model="member.password"
          placeholder="Enter your password"
          required
        />
      </div>
      <div class="form-floating">
        <span class="details">Confirm Password</span>
        <input
          type="password"
          v-model="member.passwordConfirm"
          placeholder="Confirm your password"
          required
        />
      </div>
    </div>
    <!-- <div class="gender-details">
      <input type="radio" name="gender" id="dot-1" />
      <input type="radio" name="gender" id="dot-2" />
      <input type="radio" name="gender" id="dot-3" />
      <span class="gender-title">Gender</span>
      <div class="category">
        <label for="dot-1">
          <span class="dot one"></span>
          <span class="gender">Male</span>
        </label>
        <label for="dot-2">
          <span class="dot two"></span>
          <span class="gender">Female</span>
        </label>
        <label for="dot-3">
          <span class="dot three"></span>
          <span class="gender">Prefer not to say</span>
        </label>
      </div>
    </div> -->
    <button class="w-100 btn btn-lg btn-primary" @click="onClickSave">
      가입하기
    </button>
    <!-- </form> -->
    <!-- </div> -->
  </div>
</template>

<script>
import axios from "axios";
import router from "@/router";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Join",

  data() {
    return {
      member: {
        email: "",
        password: "",
        passwordConfirm: "",
        memberName: "",
        //birthDate: undefined,
        //gender: null,
        //tel: undefined,
        //roleName: 'USER'
      },
    };
  },

  methods: {
    onClickSave() {
      //console.log("this.member? :", this.member);

      if (!this.fnValidateSave()) return;
      const param = this.member;

      console.log("const param :", param);
      axios.post("/api/joinProc", param).then((result) => {
        console.log("여기는 조인의 진행상황 로그 : ", result.data);
        window.alert(
          result.data + "님, 가입을 축하합니다. 새롭게 로그인하세요."
        );
        router.push({ path: "/" });
      });
    },

    /***************** Validation Function *******************/
    fnValidateSave() {
      let result = true;

      if (this.member.passwordConfirm !== this.member.password) {
        window.alert("비밀번호를 재확인하세요.");
        return false;
      }

      return result;
    },
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

.container {
  max-width: 700px;
  width: 100%;
  background-color: #fff;
  padding: 25px 30px;
  border-radius: 5px;
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.15);
}

.user-details {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  margin: 20px 0 12px 0;
}
.user-details .input-box {
  margin-bottom: 15px;
  width: calc(100% / 2 - 20px);
}
.input-box span.details {
  display: block;
  font-weight: 500;
  margin-bottom: 5px;
}

.user-details .input-box input {
  height: 45px;
  width: 100%;
  outline: none;
  font-size: 16px;
  border-radius: 5px;
  padding-left: 15px;
  border: 1px solid #ccc;
  border-bottom-width: 2px;
  transition: all 0.3s ease;
}
.user-details .input-box input:focus,
.user-details .input-box input:valid {
  border-color: #9b59b6;
}
</style>