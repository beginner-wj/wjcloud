<template>
  <div>
     <div id="navbar" class="navbar navbar-default          ace-save-state">
    <div class="navbar-container ace-save-state" id="navbar-container">
      <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
        <span class="sr-only">Toggle sidebar</span>

        <span class="icon-bar"></span>

        <span class="icon-bar"></span>

        <span class="icon-bar"></span>
      </button>

      <div class="navbar-header pull-left">
        <a href="index.html" class="navbar-brand">
          <small>
            <i class="fa fa-leaf"></i>
             后台管理系统
          </small>
        </a>
      </div>

      <div class="navbar-buttons navbar-header pull-right" role="navigation">
        <ul class="nav ace-nav">

          <li class="light-blue dropdown-modal">
            <a data-toggle="dropdown" href="#" class="dropdown-toggle">
              <img class="nav-user-photo" src="../../public/ace/assets/images/avatars/user.jpg" alt="Jason's Photo" />
              <span class="user-info">
									<small>欢迎,</small>
									{{loginUser.name}}
								</span>

              <i class="ace-icon fa fa-caret-down"></i>
            </a>

            <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
              <li>
                <a href="#">
                  <i class="ace-icon fa fa-cog"></i>
                  系统设置
                </a>
              </li>

              <li>
                <a href="profile.html">
                  <i class="ace-icon fa fa-user"></i>
                  个人中心
                </a>
              </li>

              <li class="divider"></li>

              <li>
                <a href="#" v-on:click="logOut()">
                  <i class="ace-icon fa fa-power-off"></i>
                  退出登录
                </a>
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </div><!-- /.navbar-container -->
  </div>

     <div class="main-container ace-save-state" id="main-container">


    <div id="sidebar" class="sidebar                  responsive                    ace-save-state">


      <div class="sidebar-shortcuts" id="sidebar-shortcuts">
        <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
          <button class="btn btn-success">
            <i class="ace-icon fa fa-signal"></i>
          </button>

          <button class="btn btn-info">
            <i class="ace-icon fa fa-pencil"></i>
          </button>

          <button class="btn btn-warning">
            <i class="ace-icon fa fa-users"></i>
          </button>

          <button class="btn btn-danger">
            <i class="ace-icon fa fa-cogs"></i>
          </button>
        </div>

        <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
          <span class="btn btn-success"></span>

          <span class="btn btn-info"></span>

          <span class="btn btn-warning"></span>

          <span class="btn btn-danger"></span>
        </div>
      </div><!-- /.sidebar-shortcuts -->

      <ul class="nav nav-list">
        <li class="" id="welcome-sidebar">
          <router-link to="/welcome">
            <i class="menu-icon fa fa-tachometer"></i>
            <span class="menu-text"> 欢迎: {{loginUser.name}} </span>
          </router-link>

          <b class="arrow"></b>
        </li>
        <li v-show="hasResource('01')">
          <a href="#" class="dropdown-toggle">
            <i class="menu-icon fa fa-list"></i>
            <span class="menu-text"> 系统管理 </span>

            <b class="arrow fa fa-angle-down"></b>
          </a>

          <b class="arrow"></b>

          <ul  class="submenu">
            <li  v-show="hasResource('0101')" id="system-user-sidebar">
              <router-link to="/system/user">
                <i class="menu-icon fa fa-caret-right"></i>
                用户管理
              </router-link>
              <b class="arrow"></b>
            </li>

            <li v-show="hasResource('0102')"  id="system-resource-sidebar">
              <router-link to="/system/resource">
                <i class="menu-icon fa fa-caret-right"></i>
                资源管理
              </router-link>
              <b class="arrow"></b>
            </li>
            <li v-show="hasResource('0103')" id="system-role-sidebar">
              <router-link to="/system/role">
                <i class="menu-icon fa fa-caret-right"></i>
                角色管理
              </router-link>
              <b class="arrow"></b>
            </li>
          </ul>
        </li>
      </ul><!-- /.nav-list -->

      <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
        <i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
      </div>
    </div>

    <div class="main-content">
      <div class="main-content-inner">
        <div class="page-content">
          <div class="row">
            <div class="col-xs-12">
              <!-- PAGE CONTENT BEGINS -->
               <router-view />
              <!-- PAGE CONTENT ENDS -->
            </div><!-- /.col -->
          </div><!-- /.row -->
        </div><!-- /.page-content -->
      </div>
    </div><!-- /.main-content -->

    <div class="footer">
      <div class="footer-inner">
        <div class="footer-content">
						<span class="bigger-120">
							<span class="blue bolder">WJ  cloud admin</span>
							Application &copy; 2013-2014
						</span>
        </div>
      </div>
    </div>

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
      <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>
  </div>
  </div>
</template>

<script>

export default {
  name: 'admin',
  data: function () {
    return {
      loginUser: {}
    }
  },
  mounted() {
    let _this = this;
    $('body').removeClass('login-layout light-login');
    $('body').attr('class', 'no-skin');
    _this.activeSidebar(_this.$route.name.replace("/","-") + "-sidebar");
    //解决侧边栏首次进入无法点击问题。
    $.getScript("/ace/assets/js/ace.min.js")
    console.log('router.name===>' + _this.$route.name);
    if(!_this.hasResourceRouter(_this.$route.name)){
        _this.$router.push("/login")
    }
    //加载登录用户信息
     _this.loginUser = Tool.getLoginUser()
  },
  methods:{
    hasResource(id){
      let isHasRes = Tool.hasResource(id)
      return isHasRes
    },
    activeSidebar: function(id) {
      if(id != "welcome-sidebar"){
        $("#welcome-sidebar").siblings().removeClass("active");
      }
      //兄弟菜单去掉active 自己加上active
      $("#" + id).siblings().removeClass("active");
      $("#" + id).siblings().find("li").removeClass("active");
      $("#" + id).addClass("active");
      //如果有父菜单 父菜单的兄弟去掉open active 父菜单加上 open active
      let parnetli = $("#" + id).parents("li");
      if (parnetli) {
        parnetli.siblings().removeClass("active");
        parnetli.siblings().find("li").removeClass("active");
        parnetli.addClass("open active");
      }
    },logOut(){
      let _this = this;
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/user/logout/"+_this.loginUser.token, this.loginUser).then((response) => {
        let resp = response.data;
        Loading.hide()
        if (resp.success) {
          Tool.logOut()
          this.$router.push("/login")
        } else {
          toast.error(resp.message)
        }
      })

    },
    /****
     * 查找是否有权限
     */
    hasResourceRouter(router){
       let _this = this;
       let resources = Tool.getLoginUser().resources;
       if(Tool.isEmpty(resources)){
          return false;
       }
       for(let i=0;i<resources.length;i++){
         if(router == resources[i].page){
             return true;
         }
       }
       return false;
    }

  },
  watch:{
      $route:{
         handler:function(val,oldVal){
           let _this = this;
           if(!_this.hasResourceRouter(val.name)){
             debugger
             _this.$router.push("/login")
           }
           _this.$nextTick(function(){
             _this.activeSidebar(_this.$route.name.replace("/","-") + "-sidebar");
           })
         }
      }
  }


}
</script>
