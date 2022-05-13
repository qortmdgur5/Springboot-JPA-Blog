let index = {
	init: function() {
		$("#btn-save").on("click", () => {		//on("이벤트", 행위), function(){}, 대신에 ()=>{}	this를 바인딩하기위해														 
			this.save();
		});
		
		$("#btn-delete").on("click", () => {		//on("이벤트", 행위), function(){}, 대신에 ()=>{}	this를 바인딩하기위해														 
			this.deleteById();
		});
	}
	,
	save: function() {
		let data = {
			title: $("#title").val(),
			content: $("#content").val(),
		};
 

		$.ajax({
			type: "POST",
			url: "/api/board", 
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8", 
			dataType: "json"
		}).done(function(resp) { 
			alert("글작성이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
	
	deleteById: function() {
		var id = $("#id").text();		//삭제를 할땐 val() 값이 아닌 text() 값을 가져와야함
		
		$.ajax({
			type: "DELETE",
			url: "/api/board/"+id, 
			dataType: "json"
		}).done(function(resp) { 
			alert("삭제가 완료되었습니다.");
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},

}

index.init();