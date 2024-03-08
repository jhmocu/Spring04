<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이미지 업로드</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style type="text/css">
.file-drop {
	width : 100;
	height : 100px;
	border : 1px solid grey;
}
.thumbnail_item {
	position: relative; 
	display: inline-block;
	margin: 4px;
}

.thumbnail_delete {
	position: absolute; 
	right: 5px;
}
</style>
</head>
<body>
	<h1>Ajax를 이용한 파일 업로드</h1>
	<div class="file-drop"></div>
	<div class="upload-list"></div>
	
	<script type="text/javascript">
		$(document).ready(function(){
			// 파일 객체를 배열로 전송받아 검증하는 함수
			function validateImages(files){
				var maxSize = 10 * 1024 * 1024; // 10 MB 
				var allowedExtensions = /(\.jpg|\.jpeg|\.png|\.gif)$/i; // 허용된 확장자 정규식 (jpg, jpeg, png, gif)
				
				if(files.length > 3) { // 파일 개수 제한
					alert("파일은 최대 3개만 가능합니다.");
					return false;
				}
				
				for(var i = 0; i < files.length; i++) {
					console.log(files[i]);
					var fileName = files[i].name; // 파일 이름
					var fileSize = files[i].size; // 파일 크기
					
					// 파일 크기가 설정 크기보다 크면
					if (fileSize > maxSize) {
						alert("파일의 최대 크기는 10MB입니다.");
						return false;
					}
					
					// regExp.exec(string) : 지정된 문자열에서 정규식을 사용하여 일치 항목 확인
					// 지정된 문자열이 없는 경우 true를 리턴
			        if(!allowedExtensions.exec(fileName)) {
			            alert("이 파일은 업로드할 수 없습니다. jpg, jpeg, png, gif파일만 가능합니다."); // 알림 표시
			            return false;
			        }
				}

				return true; // 모든 조건을 만족하면 true 리턴
			} // end validateImage()
			
			// 파일을 끌어다 놓을 때(drag&drop)
			// 브라우저가 파일을 자동으로 열어주는 기능을 막음
			$('.file-drop').on('dragenter dragover', function(event){
				event.preventDefault();
				console.log('drag 테스트');
			}); 
			
			$('.file-drop').on('drop', function(event){
				event.preventDefault();
				console.log('drop 테스트');
								
				// 드래그한 파일 정보를 갖고 있는 객체
				var files = event.originalEvent.dataTransfer.files;
				console.log(files);
				
				if(!validateImages(files)) { 
					return;
				}
				
				// Ajax를 이용하여 서버로 파일을 업로드
				// multipart/form-data 타입으로 파일을 업로드하는 객체
				var formData = new FormData();

				for(var i = 0; i < files.length; i++) {
					formData.append("files", files[i]); 
					// input name="files"로 파일 저장 
				}
				
				$.ajax({
					type : 'post', 
					url : '/ex04/upload-ajax', 
					data : formData,
					processData : false,
					contentType : false,
					success : function(data) {
						console.log(data);
						var list = '';
						$(data).each(function(){
							// this : 컬렉션의 각 인덱스 데이터를 의미
							console.log(this);
						  	var attachDTO = this; // attachDTO 저장
						  	// encodeURIComponent() : 문자열에 포함된 특수 기호를 UTF-8로 
						  	// 인코딩하여 이스케이프시퀀스로 변경하는 함수 
							var attachPath = encodeURIComponent(this.attachPath);
						  	
						    // display() 메서드에서 이미지 호출을 위한 문자열 구성
						    list += '<div class="thumbnail_item" >'
						    	+ '<pre>'
						    	+ '<input type="hidden" id="attachPath" value="'+ this.attachPath +'">'
						    	+ '<input type="hidden" id="attachChgName" value="'+ attachDTO.attachChgName +'">'
						    	+ '<input type="hidden" id="extension" value="'+ attachDTO.attachExtension +'">'
						        + '<a href="display?attachPath=' + attachPath + '&attachChgName='
						        + attachDTO.attachChgName + "&extension=" + attachDTO.attachExtension
						        + '" target="_blank">'
						        + '<img width="100px" height="100px" src="display?attachPath=' 
						        + attachPath + '&attachChgName='
						        + 't_' + attachDTO.attachChgName 
						        + "&extension=" + attachDTO.attachExtension
						        + '" />'
						        + '</a>'
						        + '<button class="thumbnail_delete" >x</button>'
						        + '</pre>'
						        + '</div>';
						}); // end each()

						// list 문자열 upload-list div 태그에 적용
						$('.upload-list').html(list);
					} // end success
				
				}); // end $.ajax()
				
			}); // end file-drop()
			
			// $(document).on() : 이벤트를 동적으로 바인딩하기 위한 메서드
			$(document).on('click', '.thumbnail_item .thumbnail_delete', function(){
				console.log(this);
				
				if(!confirm('삭제하시겠습니까?')) {
					return;
				}
				var attachPath = $(this).prevAll('#attachPath').val();
				var attachChgName = $(this).prevAll('#attachChgName').val();
				var extension = $(this).prevAll('#extension').val();
				console.log(attachPath);
				
				// ajax 요청
				$.ajax({
					type : 'POST', 
					url : '/ex04/img-delete/', 
					data : {
						attachPath : attachPath, 
						attachChgName : attachChgName,
						extension : extension						
					}, 

					success : function(result) {
						console.log(result);
					}
				});
				
			}); // end document.on()
			
		}); // end document
	
	</script>
</body>
</html>







