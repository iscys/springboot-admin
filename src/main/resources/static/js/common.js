
layui.use(['form','laydate', 'laypage', 'layer', 'table', 'carousel', 'element', 'slider'], function() {
	var laydate = layui.laydate //日期
		, laypage = layui.laypage //分页
		, layer = layui.layer //弹层
		, table = layui.table //表格
		, element = layui.element //元素操作
		, slider = layui.slider //滑块
		,form = layui.form

	/**
	 laydate.render({
            elem: '#teacher_apply'
        });
	 **/


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