
layui.use(['laydate','layer',], function() {
	var laydate = layui.laydate //日期
		, layer = layui.layer //弹层
});
/**
 * 判断，弹框
 * @constructor
 */
function ASSERT(variable){
	if(variable==''||variable==null ||variable==undefined){
		return true;
	}else {
        return false;
    }


}