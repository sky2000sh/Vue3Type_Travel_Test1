<template>
  <div class="wrapper">
    <div class="title">Registration</div>
    <div class="form">
      <!-- <div class="inputfield">
        <label>First Name</label>
        <input type="text" class="input" />
      </div>
      <div class="inputfield">
        <label>Last Name</label>
        <input type="text" class="input" />
      </div> -->
      <!-- 이메일 email -->
      <div class="inputfield">
        <label>Email</label>
        <input type="email" class="input" v-model="member.email" required />
      </div>
      <!-- 비밀벌호 password -->
      <div class="inputfield">
        <label>Password</label>
        <input
          type="password"
          class="input"
          v-model="member.password"
          required
        />
      </div>
      <div class="inputfield">
        <label>Confirm Password</label>
        <input
          type="password"
          class="input"
          v-model="member.passwordConfirm"
          required
        />
      </div>
      <!-- 유저이름 membername -->
      <div class="inputfield">
        <label>Membername</label>
        <input type="text" class="input" v-model="member.memberName" required />
      </div>
      <!-- 생년월일 birth -->
      <div class="inputfield">
        <label>Birth</label>
        <input type="date" class="input" v-model="member.birth" />
      </div>
      <!-- 성별 gender -->
      <div class="inputfield">
        <label>Gender</label>
        <div class="custom_select">
          <select v-model="member.gender">
            <option value="">Select</option>
            <option id="Male" value="Male">Male</option>
            <option id="Female" value="Female">Female</option>
          </select>
        </div>
      </div>
      <div class="inputfield">
        <label>Phone Number</label>
        <input type="text" class="input" v-model="member.phone" />
      </div>
      <!-- 주소 address -->
      <div class="inputfield">
        <label>Main Address</label>
        <input type="text" class="input" v-model="member.address1" />
      </div>
      <div class="inputfield">
        <label>Sub Address</label>
        <input type="text" class="input" v-model="member.address2" />
      </div>
      <!-- <div class="inputfield">
        <label>Postal Code</label>
        <input type="text" class="input" />
      </div>
      <div class="inputfield terms">
        <label class="check">
          <input type="checkbox" />
          <span class="checkmark"></span>
        </label>
        <p>Agreed to terms and conditions</p>
      </div> -->
      <div class="inputfield">
        <input value="Register" class="btn" @click="onClickSave" />
      </div>
    </div>
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
        //roleName: 'USER'
        phone: "",
        birth: "",
        gender: "",
        address1: "",
        address2: "",
      },
    };
  },

  methods: {
    onClickSave() {
      //console.log("this.member? :", this.member);

      if (!this.fnValidateSave()) return;
      const param = this.member;

      console.log("const param :", param);
      axios
        .post("/api/joinProc", param)
        .then((result) => {
          console.log("여기는 조인의 진행상황 로그 : ", result.data);
          window.alert(
            result.data + "님, 가입을 축하합니다. 새롭게 로그인하세요."
          );
          router.push({ path: "/" });
        })
        .catch((error) => {
          window.alert(error);
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