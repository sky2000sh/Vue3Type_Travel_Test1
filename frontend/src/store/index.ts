import { createStore } from 'vuex'

// export default createStore({
//   state: {
//   },
//   getters: {
//   },
//   mutations: {
//   },
//   actions: {
//   },
//   modules: {
//   }
// })

const store = createStore({
    state () {
        return {
            account: {
                id : 0,
            },
        }
    },
    mutations: {
        setAccount(state: any, payload) {
            state.account.id = payload
        }
    }
})

export default store