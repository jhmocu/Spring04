<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>첨부 파일 수정 페이지</title>
<!-- jquery 라이브러리 import -->
<script src="https://code.jquery.com/jquery-3.7.1.js">
</script>
</head>
<body>
	<h1>파일 업로드</h1>
	<form id="modifyForm" action="modify" method="post" enctype="multipart/form-data">
		<input type="text" name="attachId" value="${attachId }" readonly="readonly">
		<input type="file" name="file">
		<input type="submit" value="업로드">
	</form>
	
	<script>
		$(document).ready(function() {
			// 파일 전송 form validation
			$("#modifyForm").submit(function(event) {
				var fileInput = $("input[name='file']"); // File input 요소 참조
				var file = fileInput.prop('files')[0]; // file 객체 참조

				if (!file) { // file이 없으면
					alert("파일을 선택하세요.");
					event.preventDefault();
					return;
				}

				var maxSizeInBytes = 10 * 1024 * 1024; // 10 MB 
				if (file.size > maxSizeInBytes) {
					alert("파일 크기가 너무 큽니다. 최대 크기는 10MB입니다.");
					event.preventDefault();
				}
			});
		});
	</script>

</body>
</html>