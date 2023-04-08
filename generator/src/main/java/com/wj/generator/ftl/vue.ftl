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
            <tr><#list fieldList as field>
                    <th>${field.nameCn}</th></#list>
                    <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="${domain} in ${domain}s">
                <#list fieldList as field>
                    <td>{{${domain}.${field.nameHump}}}</td>
                </#list>
                <td>
                    <div class="hidden-sm hidden-xs btn-group">

                        <button @click="edit(${domain})" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>
                        <button @click="del(${domain}.id)" class="btn btn-xs btn-danger">
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
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">表单</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                            <#list fieldList as field>
                                <#if field.nameHump!='id'&&field.nameHump!='createAt'&&field.nameHump!='updateAt'>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">${field.nameCn}</label>
                                    <div class="col-sm-10">
                                        <input type="text" v-model="${domain}.${field.nameHump}" class="form-control" id="name" placeholder="${field.nameCn}">
                                    </div>
                                </div>
                                </#if>
                            </#list>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-primary" @click="save" >保存</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
    </div>

</template>

<script>
    import Pagination from "../../components/pagination"
    export default {
        name: '${module}-${domain}',
        components:{Pagination},
        data: function(){
            return {
                ${domain}s: [],
                ${domain}:{}
            }
        },
        mounted() {
            //admin.vue里面已经改成通过监听去处理。不需要每个页面对加一下这段代码
            //this.$parent.activeSidebar("${module}-${domain}-sidebar");
            this.$refs.pagination.size=5
            this.list(1);
        },
        methods:{
            list(page){
                Loading.show();
                let _this = this;
                _this.$ajax.post(process.env.VUE_APP_SERVER+"/${module}/admin/${domain}/list",{
                    page:page,
                    size:_this.$refs.pagination.size
                }).then((response)=>{
                    Loading.hide();
                    let resp = response.data.content;
                    _this.${domain}s = resp.list
                    _this.$refs.pagination.render(page,resp.total);
                })
            },
            add(){
                this.${domain} = {}
                $("#form-modal").modal("show")
                $("#form-modal").modal({backdrop:"static"})
            },
            edit(${domain}){
                let _this = this;
                _this.${domain} = ${domain};
                $("#form-modal").modal("show")
                $("#form-modal").modal({backdrop:"static"})
            },
            del(id){
                let _this = this;
                Conform.show("删除${tableNameCn}后无法恢复，确认删除?",function (result) {
                    if(result){
                        Loading.show();
                        _this.$ajax.post(process.env.VUE_APP_SERVER+"/${module}/admin/${domain}/del/"+id).then((response)=>{
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
                //保存校验
                if( 1 != 1
                    <#list fieldList as field>
                      <#if field.nameHump!='id' && field.nameHump!='createAt' && field.nameHump!='updatedAt'>
                    <#if !field.nullAble>
                    || !Validator.require(_this.${domain}.${field.nameHump},"${field.nameCn}")
                    </#if>
                    <#if (field.length > 0 )>
                    || !Validator.length(_this.${domain}.${field.nameHump},"${field.nameCn}",1,${field.length?c})
                    </#if>
                      </#if>

                </#list>){
                    return;
                }

                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER+"/${module}/admin/${domain}/save",this.${domain}).then((response)=>{
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
