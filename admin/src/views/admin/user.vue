<template>
  <div>
    <p>
      <button @click="add()" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-edit red2"></i>
        新增
      </button>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <button @click="list(1)" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh red2"></i>
        刷新
      </button>
    </p>
    <pagination ref="pagination" v-bind:list="list" v-bind:item-count="8"/>
    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>ID</th>
        <th>登录名</th>
        <th>昵称</th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="user in chapters">
        <td>{{ user.id }}</td>
        <td>{{ user.loginName }}</td>
        <td>{{ user.name }}</td>
        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <button v-show="hasResource('010103')" @click="resetPwd(user)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-key bigger-120"></i>
            </button>
            <button v-show="hasResource('010101')" @click="edit(user)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>
            <button v-show="hasResource('010102')" @click="del(user.id)" class="btn btn-xs btn-danger">
              <i class="ace-icon fa fa-trash-o bigger-120"></i>
            </button>
          </div>
        </td>
      </tr>
      </tbody>
    </table>

    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">表单</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-2 control-label">登录名</label>
                <div class="col-sm-10">
                  <input type="text" v-bind:disabled="user.id" v-model="user.loginName" class="form-control" id="name"
                         placeholder="登录名">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">昵称</label>
                <div class="col-sm-10">
                  <input type="text" v-model="user.name" class="form-control" id="name" placeholder="昵称">
                </div>
              </div>
              <div v-show="!user.id" class="form-group">
                <label class="col-sm-2 control-label">密码</label>
                <div class="col-sm-10">
                  <input v-model="user.password" type="password" class="form-control" id="name" placeholder="密码">
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" @click="save">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <div id="edit-password-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">修改密码</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-2 control-label">密码</label>
                <div class="col-sm-10">
                  <input type="password" v-model="user.password" class="form-control" name="password">
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" @click="savePwd">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

  </div>

</template>

<script>
import Pagination from "../../components/pagination"

export default {
  name: 'system-user',
  components: {Pagination},
  data: function () {
    return {
      chapters: [],
      user: {}
    }
  },
  mounted() {
    //admin.vue里面已经改成通过监听去处理。不需要每个页面对加一下这段代码
    //this.$parent.activeSidebar("system-user-sidebar");
    this.$refs.pagination.size = 5
    this.list(1);
  },
  methods: {
    hasResource(id){
      let isHasRes = Tool.hasResource(id)
      return isHasRes
    },
    list(page) {
      Loading.show();
      let _this = this;
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/user/list", {
        page: page,
        size: _this.$refs.pagination.size
      }).then((response) => {
        Loading.hide();
        let resp = response.data.content;
        _this.chapters = resp.list
        _this.$refs.pagination.render(page, resp.total);
      })
    },
    add() {
      this.user = {}
      $("#form-modal").modal("show")
      $("#form-modal").modal({backdrop: "static"})
    },
    resetPwd(user) {
      let _this = this;
      _this.user = user;
      _this.user.password = null
      $("#edit-password-modal").modal("show")
      $("#edit-password-modal").modal({backdrop: "static"})
    },
    edit(user) {
      let _this = this;
      _this.user = user;
      $("#form-modal").modal("show")
      $("#form-modal").modal({backdrop: "static"})
    },
    del(id) {
      let _this = this;
      Conform.show("删除用户后无法恢复，确认删除?", function (result) {
        if (result) {
          Loading.show();
          _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/user/del/" + id).then((response) => {
            let resp = response.data;
            Loading.hide();
            $("#form-modal").modal("hide")
            if (resp.success) {
              _this.list(1)
              toast.success("删除成功")
            } else {
              toast.warning(resp.message)
            }
          })
        }
      })
    },
    savePwd() {
      let _this = this;
      _this.user.password = md5(_this.user.password + KEY)
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/user/save-password", this.user).then((response) => {
        let resp = response.data;
        Loading.hide()
        if (resp.success) {
          $("#edit-password-modal").modal("hide")
          _this.list(1)
          toast.success("保存成功")
        } else {
          toast.error(resp.message)
        }

      })
    },
    save() {
      let _this = this;
      //保存校验
      if (1 != 1
          || !Validator.require(_this.user.loginName, "登录名")
          || !Validator.length(_this.user.loginName, "登录名", 1, 50)
          || !Validator.length(_this.user.name, "昵称", 1, 50)
          || !Validator.length(_this.user.password, "密码", 1, 32)
      ) {
        return;
      }
      _this.user.password = md5(_this.user.password + KEY)
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/user/save", this.user).then((response) => {
        let resp = response.data;
        Loading.hide()
        if (resp.success) {
          $("#form-modal").modal("hide")
          _this.list(1)
          toast.success("保存成功")
        } else {
          toast.error(resp.message)
        }

      })
    }

  }
}
</script>
