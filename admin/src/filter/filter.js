/***
 * 数组过滤器
 * @param list
 * @param key
 * @returns {string}
 */
let optionKVArray = (list,key) => {
    if(!list || !key){
        return "";
    }else{
        let result = "";
        for(let i =0 ;i<list.length;i++){
            if(key === list[i]["key"]){
                result = list[i]["value"];
            }
        }
        return result;
    }
}
/****
 * 时长格式化
 * @param value 例如：36000
 * @return 例如：10:00:00
 */
function formatTime(time) {
    var hours = Math.floor(time / 3600);
    var minutes = Math.floor(Math.floor(time % 3600) / 60);
    var seconds = Math.floor(time % 60);
    var h = hours.toString().length === 1 ? `0${hours}` : hours;
    var m = minutes.toString().length === 1 ? `0${minutes}` : minutes;
    var s = seconds.toString().length === 1 ? `0${seconds}` : seconds;
    return `${h}:${m}:${s}`;
}
/****
 * 时长格式化
 * @param value 例如：36000
 * @return 例如：10:00:00
 */
let formatSecond = (value) => {
    value = value || 0;
    let second  = parseInt(value,10);//秒
    let minute = 0;//分
    let hour = 0;//小时
    if(second > 60){
        //当大于60秒时，才需要做转换
        minute = Math.floor(second / 60);
        second = Math.floor(second % 60);
        if(minute > 60){
            hour = Math.floor(minute / 60);
            minute = Math.floor(minute % 60);
        }
    }else{
        //小于60秒时，直接显示 不需要处理
    }
    let result = "" + PrefixInteger(second,2)+"";
    if(minute > 0){
        //拼上分钟
        result = "" + PrefixInteger(minute,2)+":"+ result;
        if(hour > 0){
            //拼上小时
            result = "" + PrefixInteger(hour,2)+":" + result;
        }
    }
    return result;
}

function PrefixInteger(num,length){
    return (Array(length).join('0')+num).slice(-length)
}

let optionKV = (object,key) => {
    if(!object || !key){
        return "";
    }else{
        let result = "";
        for(let enums in object){
            if(key === object[enums]["key"]){
                result = object[enums]["value"];
            }
        }
        return result;
    }
}



 export default {
    optionKV,
     optionKVArray,
     formatSecond,
     formatTime
 }
