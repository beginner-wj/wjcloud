<template>
  <div class="main-container" id="login">
    <div class="main-content">
      <div class="row">
        <div class="col-sm-10 col-sm-offset-1">
          <div class="login-container">
            <div class="center">
              <h1>
                <i class="ace-icon fa fa-leaf green"></i>
                <span class="red">后台管理系统</span>
                <span class="white" id="id-text2"></span>
              </h1>
              <h4 class="blue" id="id-company-text">&copy; wj</h4>
            </div>

            <div class="space-6"></div>

            <div class="position-relative">
              <div id="login-box" class="login-box visible widget-box no-border">
                <div class="widget-body">
                  <div class="widget-main">
                    <h4 class="header blue lighter bigger">
                      <i class="ace-icon fa fa-coffee green"></i>
                      请输入登录信息
                    </h4>

                    <div class="space-6"></div>

                    <form>
                      <fieldset>
                        <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input v-model="user.loginName" type="text" class="form-control"
                                     placeholder="请输入用户名"/>
															<i class="ace-icon fa fa-user"></i>
														</span>
                        </label>

                        <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input v-model="user.password" type="password" class="form-control"
                                     placeholder="请输入密码"/>
															<i class="ace-icon fa fa-lock"></i>
														</span>
                        </label>
                        <label class="block clearfix">
                          <span class="block input-icon input-icon-right">
                            <div class="input-group" >
                              <input type="text" v-model="user.imageCode" class="form-control" placeholder="验证码">
                              <span class="input-group-addon" id="basic-addon2">
                                <img v-on:click="loadImageCode()" id="image-code" alt="验证码" />
                              </span>
                            </div>
                          </span>
                        </label>

                        <div class="space"></div>

                        <div class="clearfix">
                          <label class="inline">
                            <input v-model="remember" type="checkbox" class="ace"/>
                            <span class="lbl"> 记住账号</span>
                          </label>

                          <button type="button" class="width-35 pull-right btn btn-sm btn-primary"
                                  @click="login()">
                            <i class="ace-icon fa fa-key"></i>
                            <span class="bigger-110">登录</span>
                          </button>
                        </div>

                        <div class="space-4"></div>
                      </fieldset>
                    </form>
                  </div><!-- /.widget-main -->

                  <div class="toolbar clearfix">
                    <div>
                      <a href="#" data-target="#forgot-box" class="forgot-password-link">
                        <i class="ace-icon fa fa-arrow-left"></i>
                        忘记密码
                      </a>
                    </div>

                    <div>
                      <a href="#" data-target="#signup-box" class="user-signup-link">
                        注册账号
                        <i class="ace-icon fa fa-arrow-right"></i>
                      </a>
                    </div>
                  </div>
                </div><!-- /.widget-body -->
              </div><!-- /.login-box -->
            </div><!-- /.position-relative -->
          </div>
        </div><!-- /.col -->
      </div><!-- /.row -->
    </div><!-- /.main-content -->
  </div><!-- /.main-container -->
</template>

<script>
export default {
  name: 'login',
  data: function () {
    return {
      user: {},
      remember: true
    }
  },
  mounted() {
    let _this = this;
    $('body').removeClass('no-skin');
    $('body').attr('class', 'login-layout light-login');
    let rememberUser = LocalStorage.get(SESSION_KEY_USER)
    if (rememberUser) {
      _this.user = rememberUser
    }
    _this.loadImageCode()
  },
  methods: {
    loadImageCode(){
       let _this = this;
       _this.imageToken = Tool.uuid(8);
       $('#image-code').attr('src',process.env.VUE_APP_SERVER+'/system/admin/kaptcha/image-code/'+_this.imageToken)
    },
    login() {
      let _this = this;
      let rememberUser = LocalStorage.get(SESSION_KEY_USER) || {}
      let inputMd5Pwd = md5(_this.user.password);
      if(inputMd5Pwd != rememberUser.md5pwd){//如果缓存里面的密码和输入的密码不相等。就加密。否则直接使用缓存里面的
        _this.user.password = md5(_this.user.password + KEY)
      }
      _this.user.imageCodeToken = _this.imageToken
      console.log("user",_this.user);

      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/user/login", _this.user).then((response) => {
        _this.user.password = null;
        let resp = response.data;
        Loading.hide()
        if (resp.success) {
          Tool.setLoginUser(resp.content)
          if (_this.remember) {
            let md5pwd = md5(_this.user.password)
            LocalStorage.set(SESSION_KEY_USER, {
              loginName: _this.user.loginName,
              password: _this.user.password,
              md5pwd:md5pwd
            })
          } else {
            LocalStorage.set(SESSION_KEY_USER, null)
          }

          _this.$router.push("/welcome")
        } else {
          toast.error(resp.message)
          _this.user.password = ''
          _this.loadImageCode()
          _this.user.imageCode = ''
        }

      })
    }
  }
}
</script>
<style>
  .input-group-addon {
    padding: 0;
  }
</style>
