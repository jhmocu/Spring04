<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- jquery 라이브러리 import -->
<script src="https://code.jquery.com/jquery-3.7.1.js">
</script>
<title>첨부 파일 조회</title>
</head>
<body>
	<p><a href="download?attachId=${attachDTO.attachId }">${attachDTO.attachRealName }.${attachDTO.attachExtension }</a></p>
	
	<button id="modifyAttach">첨부 파일 수정</button>
	<button id="deleteAttach">첨부 파일 삭제</button>
	
	<form id="modifyForm" action="modify" method="GET">
		<input type="hidden" name="attachId">
	</form>
	<form id="deleteForm" action="delete" method="POST">
		<input type="hidden" name="attachId">
	</form>
	
	<script type="text/javascript">
	$(document).ready(function(){
			
		$("#modifyAttach").click(function(){
			var modifyForm = $("#modifyForm"); // form 객체 참조
			var attachId = "<c:out value='${attachDTO.attachId }' />";
		
			modifyForm.find("input[name='attachId']").val(attachId);				
			modifyForm.submit(); // form 전송
		}); // end click()
		
		$('#deleteAttach').click(function(){
			if(confirm('삭제하시겠습니까?')) {
				var deleteForm = $("#deleteForm"); // form 객체 참조
				
				var attachId = "<c:out value='${attachDTO.attachId }' />";
				
				deleteForm.find("input[name='attachId']").val(attachId);				
				deleteForm.submit(); // form 전송
			}
		}); // end click()
	});
	</script>
</body>
</html>