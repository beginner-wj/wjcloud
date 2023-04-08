<template>
    <div>
        <p>
            <button @click="list(1)" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-refresh red2"></i>
                刷新
            </button>
        </p>
        <div class="row">
            <div class="col-md-6">
              <textarea v-model="resourceStr" id="recourse-textarea" class="form-control" style="height: 240px;"></textarea>
              <button id="save-btn" type="button" class="btn-info" v-on:click="save">保存</button>
            </div>
          <div class="col-md-6">
            <ul id="tree" class="ztree"></ul>
          </div>
        </div>


    </div>

</template>

<script>
    import Pagination from "../../components/pagination"
    export default {
        name: 'system-resource',
        components:{Pagination},
        data: function(){
            return {
                resources: [],
                resource:{},
              resourceStr:"",
              tree:{}
            }
        },
        mounted() {
            //admin.vue里面已经改成通过监听去处理。不需要每个页面对加一下这段代码
            //this.$parent.activeSidebar("system-resource-sidebar");
            this.list();
        },
        methods:{
          initTree() {
            let _this = this;
            let setting = {
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
          },
            list(){
                Loading.show();
                let _this = this;
                _this.$ajax.post(process.env.VUE_APP_SERVER+"/system/admin/resource/load-tree",).then((response)=>{
                    Loading.hide();
                    let resp = response.data.content;
                    _this.resources = resp
                    _this.initTree()
                })
            },
            del(id){
                let _this = this;
                Conform.show("删除资源表后无法恢复，确认删除?",function (result) {
                    if(result){
                        Loading.show();
                        _this.$ajax.post(process.env.VUE_APP_SERVER+"/system/admin/resource/del/"+id).then((response)=>{
                            let resp = response.data;
                            Loading.hide();
                            if(resp.success){
                                $("#form-modal").modal("hide")
                                _this.list(1)
                                toast.success("删除成功")
                            }else{
                                toast.warning(resp.message)
                            }
                        })
                    }
                })
            },
            save(){
                let _this = this;
                if(Tool.isEmpty(_this.resourceStr)){
                  toast.warning("资源不能为空")
                  return
                }
                let json = JSON.parse(_this.resourceStr)
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER+"/system/admin/resource/save",json).then((response)=>{
                    let resp = response.data;
                    Loading.hide()
                    if(resp.success){
                        $("#form-modal").modal("hide")
                        _this.list(1)
                        toast.success("保存成功")
                    }else{
                        toast.error(resp.message)
                    }

                })
            }

        }
    }
</script>
