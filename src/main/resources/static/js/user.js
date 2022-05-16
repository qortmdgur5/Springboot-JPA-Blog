let index = {
	init: function() {
		$("#btn-save").on("click", () => {		//on("이벤트", 행위), function(){}, 대신에 ()=>{}	this를 바인딩하기위해														 
			this.save();
		});
		$("#btn-update").on("click", () => {		//on("이벤트", 행위), function(){}, 대신에 ()=>{}	this를 바인딩하기위해														 
			this.update();
		});
	}
	,
	save: function() {
		// alert('user의 save함수 호출됨');
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};

		// console.log(data);

		//ajax 호출시 default가 비동기 호출
		$.ajax({
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data),  // http body 데이터
			contentType: "application/json; charset=utf-8",  //body데이터가 어떤 타입인지(MIME)
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든것이 문자열 (생긴게 json이라면) => javascript오브젝트로 변경
		}).done(function(resp) {  //resp 에 UserApiController save함수의 return값이 들어옴
			alert("회원가입이 완료되었습니다.");
			// console.log(resp);
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
	
	update: function() {
		// alert('user의 save함수 호출됨');
		let data = {
			id: $("#id").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};

		$.ajax({
			type: "PUT",
			url: "/user",
			data: JSON.stringify(data),  // http body 데이터
			contentType: "application/json; charset=utf-8",  //body데이터가 어떤 타입인지(MIME)
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든것이 문자열 (생긴게 json이라면) => javascript오브젝트로 변경
		}).done(function(resp) {  //resp 에 UserApiController save함수의 return값이 들어옴
			alert("회원수정이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},

}

index.init();