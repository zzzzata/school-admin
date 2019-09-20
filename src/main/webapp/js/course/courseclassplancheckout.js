var vm = new Vue({
	el:'#rrapp',
	data:{
//		title: null,
		q: {
            phoneNum: "",
            orderNo: "",
            goodId: "",
            classplanliveId: "",
            classplanliveName: ""
        },
	},
	methods: {
		rest: function () {//重置
            vm.q = {
            	phoneNum: "",
            	orderNo: "",
            	goodId: "",
            	classplanliveId: "",
            	classplanliveName: ""
            };
        },
		
		query: function (event) {
			if ($.isNull(vm.q.phoneNum)) {
                alert("请输入手机号！！！");
                return;
            }
			if ($.isNull(vm.q.orderNo)) {
                alert("请输入订单号！！！");
                return;
            }
			if ($.isNull(vm.q.goodId)) {
                alert("请输入商品id！！！");
                return;
            }
			if ($.isNull(vm.q.classplanliveId)) {
                alert("请输入直播课次名称！！！");
                return;
            }
			$.ajax({
				type: "GET",
			    url: "../course/checkout?phoneNum="+vm.q.phoneNum+"&orderNo="+vm.q.orderNo+"&goodId="+vm.q.goodId+"&classplanliveId="+vm.q.classplanliveId,
//			    data: JSON.stringify(vm.q),
			    success: function(r){
			    	if(r.code === 0){
						alert(r.data);
					}else{
						alert(r.msg);
					}
				}
			});
		},
		
		//选择直播课次
		selectClassplanLive : function(){
			classplanliveLay.show(function(index,opt){
				vm.q.classplanliveId = opt.classplanLiveId;
				vm.q.classplanliveName = opt.classplanLiveName;
			});
		},
	}
});