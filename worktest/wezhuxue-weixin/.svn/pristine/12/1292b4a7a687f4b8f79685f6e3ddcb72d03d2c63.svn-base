// JavaScript Document for timer
var InterValObj; //timer变量，控制时间
		var count = 60; //间隔函数，1秒执行
		var curCount;//当前剩余秒数
		var code = ""; //验证码
		var codeLength = 6;//验证码长度
		function sendMessage() {
		curCount = count;
		var dealType; //验证方式
		var uid=$("#uid").text();//用户uid
		//设置button效果，开始计时
			$("#btnSendCode").attr("disabled", "true");
			$("#btnSendCode").val( + curCount + "s后重新获取");
			$("#btnSendCode").css('background','#fff');
			$("#btnSendCode").css('color','#bbb');
			InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
		}
		//timer处理函数
		function SetRemainTime() {
			if (curCount == 0) {                
				window.clearInterval(InterValObj);//停止计时器
				$("#btnSendCode").removeAttr("disabled");//启用按钮
				$("#btnSendCode").val("获取验证码");
				$("#btnSendCode").css('color','#2cc');
				code = ""; //清除验证码。如果不清除，过时间后，输入收到的验证码依然有效    
			}
			else {
				curCount--;
				$("#btnSendCode").val( + curCount + "s后重新获取").css('background','#fff');
				$("#btnSendCode").css('color','#bbb');
			}
		}