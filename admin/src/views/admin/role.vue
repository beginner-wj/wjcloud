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
        <th>角色</th>
        <th>描述</th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="role in roles">
        <td>{{ role.id }}</td>
        <td>{{ role.name }}</td>
        <td>{{ role.roledesc }}</td>
        <td>
          <div class="hidden-sm hidden-xs btn-group">

            <button @click="editUser(role)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-user-plus bigger-120"></i>
            </button>
            <button @click="editResource(role)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-list bigger-120"></i>
            </button>
            <button @click="edit(role)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>
            <button @click="del(role.id)" class="btn btn-xs btn-danger">
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
                <label class="col-sm-2 control-label">角色</label>
                <div class="col-sm-10">
                  <input type="text" v-model="role.name" class="form-control" id="name" placeholder="角色">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">描述</label>
                <div class="col-sm-10">
                  <input type="text" v-model="role.roledesc" class="form-control" id="name" placeholder="描述">
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

    <div id="resource-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">角色资源关联配置</h4>
          </div>
          <div class="modal-body">
            <ul id="tree" class="ztree"></ul>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" @click="saveResource()">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <div id="user-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">角色用户关联配置</h4>
          </div>
          <div class="modal-body">
             <div class="row">
               <div class="col-md-6">
                 <table id="user-table" class="table table-hover">
                   <tbody>
                      <tr v-for="user in users ">
                         <td>{{user.loginName}}</td>
                        <td class="text-right">
                          <a v-on:click="addUser(user)" href="javascript:;" class="">
                             <i class="ace-icon fa fa-arrow-circle-right blue"></i>
                          </a>
                        </td>
                      </tr>
                   </tbody>
                 </table>
               </div>
               <div class="col-md-6">
                 <table id="user-table" class="table table-hover">
                   <tbody>
                   <tr v-for="user in roleUsers ">
                     <td>{{user.loginName}}</td>
                     <td class="text-right">
                       <a v-on:click="deleteUser(user)" href="javascript:;" class="">
                         <i class="ace-icon fa fa-trash blue"></i>
                       </a>
                     </td>
                   </tr>
                   </tbody>
                 </table>
               </div>
             </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" @click="saveUser()">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

  </div>

</template>

<script>
import Pagination from "../../components/pagination"

export default {
  name: 'system-role',
  components: {Pagination},
  data: function () {
    return {
      resources: [],
      roles: [],
      role: {},
      tree:{},
      users:[],
      roleUsers:[]
    }
  },
  mounted() {
    //admin.vue里面已经改成通过监听去处理。不需要每个页面对加一下这段代码
    //this.$parent.activeSidebar("system-role-sidebar");
    this.$refs.pagination.size = 5
    this.list(1);
  },
  methods: {
    saveResource(){
      let _this = this;
      let resources = _this.tree.getCheckedNodes();
      console.log("勾选的资源：",resources);
      //保存时,只需要保存资源id。所以使用id数组进行参数传递
      let resourceIds = [];
      for(let i=0;i<resources.length;i++){
        resourceIds.push(resources[i].id);
      }
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/role/save-resource", {
        id: _this.role.id,
        resourceIds: resourceIds
      }).then((response) => {
        Loading.hide();
         let resp = response.data
        if(resp.success){
          $("#resource-modal").modal('hide')
          toast.success("保存成功")
        }else{
          toast.error(resp.message)
        }
      })
    },
    list(page) {
      Loading.show();
      let _this = this;
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/role/list", {
        page: page,
        size: _this.$refs.pagination.size
      }).then((response) => {
        Loading.hide();
        let resp = response.data.content;
        _this.roles = resp.list
        _this.$refs.pagination.render(page, resp.total);
      })
    },
    add() {
      this.role = {}
      $("#form-modal").modal("show")
      $("#form-modal").modal({backdrop: "static"})
    },
    initTree() {
      let _this = this;
      let setting = {
        check: {
          enable: true
        },
        data: {
          simpleData: {
            idKey: "id",
            pIdKey: "parent",
            rootPId: "",
            enable: true
          }
        }
      };
      var zNodes = _this.resources;
      let tree = $.fn.zTree.init($("#tree"), setting, zNodes);
      _this.tree = tree;
      tree.expandAll(true);
      $("#resource-modal").modal("show")
      $("#resource-modal").modal({backdrop: "static"})
      _this.listRoleResource();
    },
    listRoleResource(){
      Loading.show();
      let _this = this;
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/role/list-resource/"+_this.role.id).then((response) => {
        Loading.hide();
        let resp = response.data;
        let resources = resp.content
        //勾选查询到的资源。先把树的所有节点清空勾选。再勾选查询到的节点
        _this.tree.checkAllNodes(false);
        for(let i=0;i<resources.length;i++){
             let node = _this.tree.getNodeByParam("id",resources[i])
             _this.tree.checkNode(node,true);
        }
      })
    },
    resourcelist() {
      Loading.show();
      let _this = this;
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/resource/load-tree",).then((response) => {
        Loading.hide();
        let resp = response.data.content;
        _this.resources = resp
        _this.initTree()
      })
    },
    deleteUser(user){
      let _this = this;
      Tool.removeObj(_this.roleUsers,user)
    },
    //角色中添加用户
    addUser(user){
      let _this = this;
       //如果要添加的用户在列表中已经有了。则不需要再添加
      let users = _this.roleUsers;
      for(var i=0;i<users.length;i++){
           if(user === users[i]){
                return;
           }
      }
      _this.roleUsers.push(user)
    },
    saveUser(){
       let _this =this;
       let users = _this.roleUsers;
       //保存时 只需要保存用户id 所以使用用户id来进行参数传递
      let userIds = [];
       for(var i=0;i<users.length;i++){
           userIds.push(users[i].id)
       }
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/role/saveuser",{
        id:_this.role.id,
        userIds:userIds
      }).then((response) => {
        Loading.hide();
        $("#user-modal").modal("hide")
        let resp = response.data;
        if(resp.success){
          toast.success("保存成功")
        }else{
          toast.error(resp.message)
        }
      })

    },
    editUser(role) {
      let _this = this;
      _this.role = role;
      _this.listUser();
      $("#user-modal").modal("show")
      $("#user-modal").modal({backdrop: "static"})
    },
    listRoleUser(){
      Loading.show();
      let _this = this;
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/role/list-user/"+_this.role.id).then((response) => {
        Loading.hide();
        let resp = response.data;
        if(resp.success){
          let userids = resp.content
          console.log("userids===>",userids);
          console.log("this.users===>",_this.users);
          //根据获取的用户id和s所有用户数组对比。如果id相同。加入
          for(let i=0;i<userids.length;i++){
              for(let j=0;j<_this.users.length;j++){
                  if(userids[i] === _this.users[j].id){
                     _this.roleUsers.push(_this.users[j])
                  }
              }
          }
        }else{
          toast.error(resp.message)
        }
      })
    },
    /**
     * 查询所有用户
     */
    listUser() {
      Loading.show();
      let _this = this;
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/user/list",{
        page:1,
        size:9999
      }).then((response) => {
        Loading.hide();
        let resp = response.data;
        if(resp.success){
          _this.roleUsers = []
          _this.users = resp.content.list
          _this.listRoleUser();
        }else{
          toast.error(resp.message)
        }
      })
    },
    editResource(role) {
      let _this = this;
      _this.role = role;
      _this.resourcelist();

    },
    edit(role) {
      let _this = this;
      _this.role = role;
      $("#form-modal").modal("show")
      $("#form-modal").modal({backdrop: "static"})
    },
    del(id) {
      let _this = this;
      Conform.show("删除角色表后无法恢复，确认删除?", function (result) {
        if (result) {
          Loading.show();
          _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/role/del/" + id).then((response) => {
            let resp = response.data;
            Loading.hide();
            if (resp.success) {
              $("#form-modal").modal("hide")
              _this.list(1)
              toast.success("删除成功")
            } else {
              toast.warning(resp.message)
            }
          })
        }
      })
    },
    save() {
      let _this = this;
      //保存校验
      if (1 != 1

          || !Validator.require(_this.role.name, "角色")
          || !Validator.length(_this.role.name, "角色", 1, 100)

          || !Validator.require(_this.role.roledesc, "描述")
          || !Validator.length(_this.role.roledesc, "描述", 1, 100)

      ) {
        return;
      }

      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/role/save", this.role).then((response) => {
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
