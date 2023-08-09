
//数据定义

data() {
    return {
      score: 0,						//表单提交后，后端返回的总分
      id: '',						//获取路由的 query，试卷的id
      iow: '',						//返回的题目信息
      dialogVisible: false,			//弹窗
      djStarttime: '',				//获取当前时间
      question: [],					//保存后端返回题的数组，页面上需要用它来循环显示每一道题
      ruleForm: {
        resource: [],				//每一道题的值
      },

      DSQ: null,					//定时器，到时间自动提交
      DSQTime: 0,					//后端返回的结束时间	
      questionArrText: [],			//答题保存的数组
    }
  },

逻辑

  created() {
    const djStarttime = new Date()
    const year = djStarttime.getFullYear()
    const month = (djStarttime.getMonth() + 1).toString()
    const day = djStarttime.getDate().toString()
    const hour = djStarttime.getHours().toString()
    const minute = djStarttime.getMinutes().toString()
    const second = djStarttime.getSeconds().toString()
    if (month.length == 1) {
      month = '0' + month
    }
    if (day.length == 1) {
      day = '0' + day
    }
    if (hour.length == 1) {
      hour = '0' + hour
    }
    if (minute.length == 1) {
      minute = '0' + minute
    }
    if (second.length == 1) {
      second = '0' + second
    }
    djStarttime.value =
      year + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second

    this.id = this.$route.query.id

	//获取当前系统时间，传给后端，用来判断他用了多长时间

    console.log(this.dateTime)
    this.init()		//初始化函数
  },

	//监听定时器，如果 当前系统时间的秒值等于或大于后端给定的时间，那么就自动提交题目

  watch: {
    DSQTime: function (val, oldVal) {
      console.log(this.DSQTime)
      console.log(this.DSQEndTime)
      if (this.DSQTime == this.DSQEndTime) {
        clearInterval(this.DSQ)
        this.submitForm('ruleForm', true)
      }
    },
  },


  methods: {
	//给多选框用，否则会出现一个bug，就是先点一下多选框，然后没反应，
	//然后点一下按钮或者点击下拉框后，这个多选框就高亮了
    const change =() => {
      this.$forceUpdate()
    },
    
    //关闭弹窗函数
    const handleClose = (done) => {
      this.dialogVisible = false
    },
    
    //跳页面的函数
    
    const go = () => {
      this.$router.push({ path: '/user' })
    },
    
    //返回上一页
    
    const back =() => {
      this.$router.go(-1)
    },

	//初始化函数

    const init = () => {
	//定时器，获取当前系统时间（毫秒），然后转化为秒
      this.DSQ = setInterval(() => {
        this.DSQTime = parseInt(Date.now() / 1000)
      }, 1000)
      
      //封装的接口，把试卷id传进去，返回所有题
      
      msgApi
        .questionsList(this.id)
        .then((res) => {
          if (res.data.code == 200) {
            this.question = res.data.returnObject.list			//页面循环的题
            for (let i = 0; i < this.question.length; i++) {
            
            	//如果他是多选题的话，把它初始化为一个数组，而不是普通的字符串
            	
              if (this.question[i].questionType == 3) {
                this.ruleForm.resource[i] = []
              }else{
				this.ruleForm.resource[i] = ''
			  }
            }
            this.questionArrText = res.data.returnObject.list	//重新赋值，用来判断他是否全部答完
            this.iow = res.data.returnObject.name				//题的名字
            this.DSQEndTime = parseInt(this.iow.endTime / 1000)	//后端返回的时间（毫秒）转化为秒
          }
        })
        .catch((err) => {})
    },
	
	//提交函数
	//ifs 判断是否是用户自己点击的提交按钮，如果不是，那么就是到时间了，系统自动提交的
	
    const submitForm = (formName, ifs) => {
      if (ifs) {

		//系统自动提交的话
		
        var arr = []											//最后给后端的是一个json串
        var that = this
        var b = this.ruleForm.resource.notempty()
        //获取他已经答过的题
        for (var i = 0; i < this.questionArrText.length; i++) {
          var json = {}											//最后给后端的是一个json串
          json.id = that.questionArrText[i].id					//json的id：题id
          
          
          //如果是数组（也就是多选题），那么就用join把他换为字符串
          if (that.ruleForm.resource[i] instanceof Array) {
            json.text = that.ruleForm.resource[i].join()
          } else {
            json.text = that.ruleForm.resource[i]
          }
          
          if (json.text == undefined) {						//如果这个题没答，那么就把他重置为空串，而不是给后端一个 undefined 字符串
            json.text = ''
          }
          json.isJD = this.questionArrText[i].questionType	//给后端题的类型

          arr.push(json)									//一个一个push进去
        }
        var obj = {											//传的采纳数
          djPaperid: this.id,
          djStarttime: this.djStarttime,
          djAnswers: JSON.stringify(arr),
        }
        msgApi
          .answers(obj)										//请求的接口
          .then((res) => {
            if (res.data.code == 200) {
              //console.log(res)
              this.dialogVisible = true
              this.score = res.data.returnObject
            }
          })
          .catch((err) => {})
      } else {
        this.$refs[formName].validate((valid) => {			//如果是用户自己点的
          if (valid) {
            var arr = []
            var that = this
            var b = this.ruleForm.resource.notempty()
            if (b.length !== this.questionArrText.length) {	

				//要是有未答的题目走if

              this.$confirm(
                '检测到您有未答完的题目，，是否确认提交试卷？',
                '确认信息',
                {
                  distinguishCancelAndClose: true,
                  confirmButtonText: '确认',
                  cancelButtonText: '取消',
                }
              )
                .then(() => {			
                		
                //逻辑和刚刚的一样，
                
                  for (var i = 0; i < this.questionArrText.length; i++) {
                    var json = {}
                    json.id = that.questionArrText[i].id
                    if (that.ruleForm.resource[i] instanceof Array) {
                      json.text = that.ruleForm.resource[i].join()
                    } else {
                      json.text = that.ruleForm.resource[i]
                    }
                    if (json.text == undefined) {
                      json.text = ''
                    }
                    json.isJD = this.questionArrText[i].questionType

                    arr.push(json)
                  }

                  var obj = {
                    djPaperid: this.id,
                    djStarttime: this.djStarttime,
                    djAnswers: JSON.stringify(arr),
                  }
                  msgApi
                    .answers(obj)
                    .then((res) => {
                      if (res.data.code == 200) {
                        //console.log(res)
                        this.dialogVisible = true
                        this.score = res.data.returnObject
                      }
                    })
                    .catch((err) => {})
                })
                .catch((action) => {})
            } else {													

				//如果都答了，就走else  （其实这个感觉没必要，不过就这吧）

              for (var i = 0; i < this.questionArrText.length; i++) {
                var json = {}
                json.id = that.questionArrText[i].id
                if (that.ruleForm.resource[i] instanceof Array) {
                  json.text = that.ruleForm.resource[i].join()
                } else {
                  json.text = that.ruleForm.resource[i]
                }

                json.isJD = this.questionArrText[i].questionType

                arr.push(json)
              }

              var obj = {
                djPaperid: this.id,
                djStarttime: this.djStarttime,
                djAnswers: JSON.stringify(arr),
              }
              msgApi
                .answers(obj)
                .then((res) => {
                  if (res.data.code == 200) {
                    //console.log(res)
                    this.dialogVisible = true
                    this.score = res.data.returnObject
                    // this.$message.success(res.data.returnObject)
                  }
                })
                .catch((err) => {})
            }
          } else {
            //console.log('error submit!!')
            return false
          }
        })
      }
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
  },




