import { createStore } from "vuex";
 
const store = createStore({
  modules: {
    product: {
      namespaced: true,
      state: {
        remCondition: '',
		remPageIndex: 0,
		remPageSize: 0,
		remProducttypeId: 0,
		remOrderType: '',
		remOrderColumn: ''
      },
      mutations: {
        savePageSize(state, payload){
		  state.remPageSize = payload.pageSize
        },
		savePageIndex(state, payload){
		  state.remPageIndex = payload.pageIndex
		},
		saveCondition(state, payload){
		  state.remCondition = payload.condition
		},
		saveProducttypeId(state, payload){
		  state.remProducttypeId = payload.producttypeId
		},
		saveOrderType(state, payload){
		  state.remOrderType = payload.orderType
		},
		saveOrderColumn(state, payload){
		  state.remOrderColumn = payload.orderColumn
		}
      }
    },
	solution: {
	  namespaced: true,
	  state: {
	    remCondition: '',
		remPageIndex: 0,
		remPageSize: 0,
		remSolutiontypeId: 0,
		remOrderType: '',
		remOrderColumn: ''
	  },
	  mutations: {
	    savePageSize(state, payload){
		  state.remPageSize = payload.pageSize
	    },
		savePageIndex(state, payload){
		  state.remPageIndex = payload.pageIndex
		},
		saveCondition(state, payload){
		  state.remCondition = payload.condition
		},
		saveSolutiontypeId(state, payload){
		  state.remSolutiontypeId = payload.solutiontypeId
		},
		saveOrderType(state, payload){
		  state.remOrderType = payload.orderType
		},
		saveOrderColumn(state, payload){
		  state.remOrderColumn = payload.orderColumn
		}
	  }
	}
  }
})
 
export default store;
