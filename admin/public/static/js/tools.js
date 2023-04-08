Tool = {
    /**
     * 空校验 null 或者 "" 都返回true
     * @param obj
     * @returns {boolean}
     */
    isEmpty:function (obj) {
        if((typeof obj == 'string')){
            return !obj || obj.replace(/\s+/g,"") == ""
        }else {
            return (!obj || JSON.stringify(obj) === "{}" || obj.length === 0)
        }
    },
    //非空校验
    isNotEmpty:function(obj){
        return !this.isEmpty(obj)
    },
    removeObj:function (list,obj){
        list.splice(obj,1)
    },
    /**
     * 长度校验
     * @param str
     * @param min
     * @param max
     * @returns {boolean}
     */
    isLength:function(str,min,max){
        return $.trim(str).length >= min && $.trim(str).length <= max
    },
    //保存登录信息
    setLoginUser:function (loginUser) {
        SessionStorage.set(SESSION_KEY_USER,loginUser)
    },
    getLoginUser:function () {
        return SessionStorage.get(SESSION_KEY_USER) || {}
    },
    logOut:function () {
        SessionStorage.clearAll()
    },
    /***
     * 随机生成指定长度的进制数
     * @param len
     * @param radis
     */
    uuid:function (len,radix) {
        let chars = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
         let uuid = [];
         radix = radix || chars.length;
         for(let i=0;i<len;i++){
             uuid[i] = chars[0 | Math.random() * radix]
         }
         return uuid.join('')
    },
    /***
     * 查看是否有权限
     * @param id--资源id
     */
    hasResource:function (id) {
        let _this =this;
        let resources = _this.getLoginUser().resources;
        if(_this.isEmpty(resources)){
             return false;
        }
        for(let i=0;i<resources.length;i++){
             if(id === resources[i].id){
                 return true;
             }
        }
        return false;
    }


}
