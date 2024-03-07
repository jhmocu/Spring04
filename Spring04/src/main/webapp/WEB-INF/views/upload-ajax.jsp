<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이미지 업로드</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<style type="text/css">
.file-drop {
	width : 100;
	height : 100px;
	border : 1px solid grey;
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
						  	var attachDTO = this;
							var attachPath = encodeURIComponent(this.attachPath);
						  	
							list += '<div>'
								+ '<img width="100px" height="100px" src="display?attachPath=' + attachPath + '&fileName='
								+ 't_' + this.attachChgName + ".jpg"
								+ '" />'
								+ 't_' + this.attachRealName
								+ '</div>';
						}); // end each()

						
						$('.upload-list').html(list);
					} // end success
				
				}); // end $.ajax()
				

			}); // end file-drop()
			
		}); // end document
	
	</script>
</body>
</html>







