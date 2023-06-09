<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/views/common/header.jsp"%> 
<section id=enroll-container>
        <h2>회원 가입 정보 입력</h2>
        <form action="<%=request.getContextPath()%>/member/enrollMemberEnd.do" method="post"> 
        
        <!-- onsubmit="return check_enroll();" -->
        <table>
			<tr>
				<th>아이디</th>
				<td>
					<input type="text" placeholder="4글자이상" name="userId" id="userId_" >
					<input type="button" onclick="fn_duplicatedId();" value="증복확인">
				</td>
			</tr>
			<tr>
				<th>패스워드</th>
				<td>
					<input type="password" name="password" id="password_" ><br>
				</td>
			</tr>
			<tr>
				<th>패스워드확인</th>
				<td>	
					<input type="password" id="password_2" ><br>
				</td>
			</tr> 
			<tr>
				<th></th>
				<td>
				</td>
			</tr>
			 
			<tr>
				<th>이름</th>
				<td>	
				<input type="text"  name="userName" id="userName" ><br>
				</td>
			</tr>
			<tr>
				<th>나이</th>
				<td>	
				<input type="number" name="age" id="age"><br>
				</td>
			</tr> 
			<tr>
				<th>이메일</th>
				<td>	
					<input type="email" placeholder="abc@xyz.com" name="email" id="email"><br>
				</td>
			</tr>
			<tr>
				<th>휴대폰</th>
				<td>	
					<input type="tel" placeholder="(-없이)01012345678" name="phone" id="phone" maxlength="11" required><br>
				</td>
			</tr>
			<tr>
				<th>주소</th>
				<td>	
					<input type="text" placeholder="" name="address" id="address"><br>
				</td>
			</tr>
			<tr>
				<th>성별 </th>
				<td>
					<input type="radio" name="gender" id="gender0" value="M" >
					<label for="gender0">남</label>
					<input type="radio" name="gender" id="gender1" value="F">
					<label for="gender1">여</label>
				</td>
			</tr>
			<tr>
				<th>취미 </th>
				<td>
					<input type="checkbox" name="hobby" id="hobby0" value="운동"><label for="hobby0">운동</label>
					<input type="checkbox" name="hobby" id="hobby1" value="등산"><label for="hobby1">등산</label>
					<input type="checkbox" name="hobby" id="hobby2" value="독서"><label for="hobby2">독서</label><br />
					<input type="checkbox" name="hobby" id="hobby3" value="게임"><label for="hobby3">게임</label>
					<input type="checkbox" name="hobby" id="hobby4" value="여행"><label for="hobby4">여행</label><br />
					<!-- 라디오나 체크박스는 값들을 잘 넘기기위해서 value값을 설정해야한다 -->
				</td>
			</tr>
		</table>
			<input type="submit" value="가입" 
			onclick="return fn_validate2();">
			<input type="reset" value="취소">
	        </form>
    	</section>
 		
 		
		<script>
	
			function fn_validate2(){
				const userId=$("#userId_").val();
				if(userId.length<4){
					alert("아이디는 4글자 이상으로 작성하세요!!");
					$("#userId_").focus();
					return false;
				}
			}	
		
			$("#password_2").keyup(e=>{
				const password=$("#password_").val();
				const passwordCheck=$(e.target).val();
				let color,msg;
				if(password==passwordCheck){
					color="green"; msg="비밀번호가 일치합니다.";
					//$("<p>").css("color","green").text("비밀번호가 일치합니다.");
				}else{
					color="red"; msg="비밀번호가 일치하지 않습니다.";
					//$("<p>").css("color","red").text("비밀번호가 일치하지않습니다.");	
				}
				const td = $(e.target).parents("tr").next().find("td");
				td.html("");
				$("<p>").css("color",color).text(msg).appendTo(td);
			});
			
	
			const fn_duplicatedId=()=>{
				
				const userId=$("#userId_").val();
				if(userId.length>=4){
					open("<%=request.getContextPath()%>/member/idDuplicate.do?userId="+userId,
							"_blank","width=300 height=200");
				}else{
					alert("아이디는 4글자이상 입력하세요!");
				}
				

			}
				
		
		
			/* const check_enroll=()=>{
				const userId=$("#userId_").val();
				const pass = $("#password_").val();
				const checkpass = $("#password2_").val();
				
		
				if(userId.length<4 && pass != checkpass){
					alert("아이디 4글자이상 입력 + 비밀번호가 동일한지 확인해주세요");
					return false;
					
				}else if(pass != checkpass){
					alert("비밀번호가 일치하지 않습니다.");
					$("#password2_").val("");  // 문구띄어주고 아이디 빈칸으로바꾸고
					$("#passwrod2_").focus(); 
					return false;
					
				}else if(userId.length<4){
					alert("아이디는 4글자 이상 입력하세요.");
					$("#userId").val("");  // 문구띄어주고 아이디 빈칸으로바꾸고
					$("#userId").focus(); // 아이디부분에 포커스줌
					return false; // false면 값이 넘어가지 않음
				}
			} */
		</script>
 		
 		
 <%@ include file="/views/common/footer.jsp"%> 