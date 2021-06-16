package com.itwillbs.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.JsonNode;
//import com.itwillbs.Mail.Email;
//import com.itwillbs.Mail.EmailSender;
import com.itwillbs.domain.MemberBean;
import com.itwillbs.domain.ReportBean;
//import com.itwillbs.service.KakaoService;
//import com.itwillbs.findhome.Kakao_restapi;
import com.itwillbs.service.MemberService;

@Controller
public class MemberController {
	// 스프링 DI(의존관계주입): 스프링 객체생성 방식
	// MemberService memberService=new MemberServiceImpl(); 객체생성
	// xml에서 MemberServiceImpl 객체생성 set메서드 호출해서
	// 부모인터페이스 멤버변수에 객체생성한 값을 전달 MemberService memberService
	@Inject
	private MemberService memberService;
	private JsonNode accessToken;
	private Kakao_restapi kakao_restapi = new Kakao_restapi();

	@RequestMapping(value = "/join_choice", method = RequestMethod.GET)
	public String join_choice() {
		return "join_choice";
	}

	
	@RequestMapping(value = "/admin_meberjoinPro", method = RequestMethod.GET)
	public String admin_meberjoinPro(Model model) {
		
		return "admin_meberjoin";
	}
	
	@RequestMapping(value = "/admin_listPro", method = RequestMethod.GET)
	public String admin_listPro(Model model, HttpSession session  ) {
		
		String id = (String) session.getAttribute("adminid");

		if (id != null) {
			
			// 매물신고리스트 넘기기
			List<MemberBean> nList = memberService.getMemberNList();
			List<ReportBean> reportList = memberService.getReportList();

			model.addAttribute("nList", nList);
			model.addAttribute("reportList", reportList);

			model.addAttribute("nList", nList);

			return "admin_list";

		} else {
			return "redirect:/adminlogin";
		}
		
	}
	
	@RequestMapping(value = "/admin_meberjoin", method = RequestMethod.GET)
	public String admin_meberjoin(Model model, HttpSession session  ) {
		
		String id = (String) session.getAttribute("adminid");

		if (id != null) {
			
			// 매물신고리스트 넘기기
			List<MemberBean> nList = memberService.getMemberNList();
			List<ReportBean> reportList = memberService.getReportList();

			model.addAttribute("nList", nList);
			model.addAttribute("reportList", reportList);

			model.addAttribute("nList", nList);

			return "admin_meberjoin";

		} else {
			return "redirect:/adminlogin";
		}
		
	}
	
	
	
	@RequestMapping(value = "/login_choice", method = RequestMethod.GET)
	public String login_choice() {
		return "login_choice";
	}


	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {
		return "join";
	}

	@RequestMapping(value = "/join2", method = RequestMethod.GET)
	public String join2() {
		return "join2";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	// /member/loginPro
	@RequestMapping(value = "/loginPro", method = RequestMethod.POST)
	public String loginPro(MemberBean mb, HttpSession session, Model model, HttpServletRequest request) {

		MemberBean mb2 = memberService.userCheck(mb);
		if (mb2 != null) {
			// 세션값 생성 "id"
			session.setAttribute("id", mb.getId());

			session.setAttribute("member_type", "normal");

			MemberBean mb3 = memberService.userCheck(mb);
			if (!mb3.getPhone_number().equals("")) {
				session.setAttribute("phone", mb.getPhone_number());
				return "redirect:/";
			} else {
				return "redirect:/addPhone";
			}

		} else {
			// 입력하신 정보가 틀립니다.
			model.addAttribute("msg", "입력하신 정보가 틀립니다.");
			// /WEB-INF/views/member/msg.jsp
			return "msg";

		}

	}

	@RequestMapping(value = "/loginPro2", method = RequestMethod.POST)
	public String loginPro2(MemberBean mb, HttpSession session, Model model) {

		MemberBean mb2 = memberService.userCheck2(mb);
		if (mb2 != null) {
			// 세션값 생성 "id"
			session.setAttribute("id", mb.getId());
			session.setAttribute("seller_id", mb.getId());
			session.setAttribute("is_confirm", mb.getIs_confirm());

			// 회원구분 세션값 생성 "member_type"
			session.setAttribute("member_type", "seller");
			
			session.setAttribute("phone_number", mb2.getPhone_number());				

			return "redirect:/";
			
		} else {
			// 입력하신 정보가 틀립니다.
			model.addAttribute("msg", "입력하신 정보가 틀립니다.");
			// /WEB-INF/views/member/msg.jsp
			return "msg";
		}

	}

	@RequestMapping(value = "/joinPro", method = RequestMethod.POST)
	public String joinPro(MemberBean mb, HttpSession session, HttpServletRequest request, Model model) {

		memberService.insertMember(mb);

		return "redirect:/";
	}

	@RequestMapping(value = "/joinPro2", method = RequestMethod.POST)
	public String joinPro2(MemberBean mb) {

		memberService.insertMember2(mb);

		return "redirect:/";
	}

	@RequestMapping(value = "/member/main", method = RequestMethod.GET)
	public String main() {
		// /WEB-INF/views/member/main.jsp
		return "member/main";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {

		session.invalidate();

		return "redirect:/login";
	}

	@RequestMapping(value = "/memberDelete", method = RequestMethod.GET)
	public String delete(HttpSession session, Model model) {

		// String id = 세션값 가져오기
		String id = (String) session.getAttribute("id");
		// 1.세션값 변수에 저장
		String member_type = (String) session.getAttribute("member_type");

		// MemberBean mb = 세션에 해당하는 정보 조회 getMember(id) MemberBean 리턴
		MemberBean mb;
		if (member_type == "normal") {
			mb = memberService.getMember(id);
		} else {
			mb = memberService.getMember2(id);
		}
		// 2.멤버빈에 저장
		mb.setMember_type(member_type);

		// model "mb",mb
		model.addAttribute("mb", mb);

		// /WEB-INF/views/member/deleteForm.jsp
		return "memberDelete";
	}

	@RequestMapping(value = "/memberDeletePro1", method = RequestMethod.POST)
	public String deletePro(MemberBean mb, HttpSession session, Model model) {

		session.setAttribute("id", mb.getId());

		MemberBean mb2 = memberService.userCheck(mb);

		if (mb2 != null) {
			// 삭제
			memberService.deleteMember(mb);
			session.invalidate();
			return "redirect:/";

		} else {
			// 입력하신 정보가 틀립니다.
			model.addAttribute("msg", "입력하신 정보가 틀립니다.");
			// /WEB-INF/views/member/msg.jsp
			return "member/msg";
		}
	}

	@RequestMapping(value = "/memberDeletePro2", method = RequestMethod.POST)
	public String deletePro2(MemberBean mb, HttpSession session, Model model) {

		session.setAttribute("id", mb.getId());

		MemberBean mb2 = memberService.userCheck2(mb);

		if (mb2 != null) {

			// 삭제
			memberService.deleteMember2(mb);
			session.invalidate();
			return "redirect:/";
		} else {
			// 입력하신 정보가 틀립니다.
			model.addAttribute("msg", "입력하신 정보가 틀립니다.");
			// /WEB-INF/views/member/msg.jsp
			return "member/msg";
		}
	}

	@RequestMapping(value = "/memberInfo", method = RequestMethod.GET)
	public String update(HttpSession session, Model model) {
		// String id = 세션값 가져오기
		String id = (String) session.getAttribute("id");
		// 1.세션값 변수에 저장
		String member_type = (String) session.getAttribute("member_type");

		// MemberBean mb = 세션에 해당하는 정보 조회 getMember(id) MemberBean 리턴
		MemberBean mb;
		if (member_type.equals("normal") ) {
			mb = memberService.getMember(id);
		} else {
			mb = memberService.getMember2(id);
		}
		
		if (mb == null) {
			model.addAttribute("msg", "해당 회원정보가 없습니다");
			// /WEB-INF/views/member/msg.jsp
			return "msg";
		}
		
		// 2.멤버빈에 저장
		mb.setMember_type(member_type);

		// model "mb",mb
		model.addAttribute("mb", mb);

		// /WEB-INF/views/member/updateForm.jsp
		return "memberInfo";
	}

	@RequestMapping(value = "/memberInfoPro", method = RequestMethod.POST)
	public String updatePro(MemberBean mb, HttpServletRequest request, Model model) {

		MemberBean mb2 = memberService.userCheck(mb);

		if (mb2 != null) {
			mb.setPassword(request.getParameter("newPassword"));
			// 수정
			memberService.updateMember(mb);

			return "redirect:/";
		} else {
			// 입력하신 정보가 틀립니다.
			model.addAttribute("msg", "입력하신 정보가 틀립니다.");
			// /WEB-INF/views/member/msg.jsp
			return "msg";
		}
	}

	@RequestMapping(value = "/memberInfoPro2", method = RequestMethod.POST)
	public String updatePro2(MemberBean mb, HttpServletRequest request, Model model) {

		MemberBean mb2 = memberService.userCheck2(mb);
		if (mb2 != null) {
			mb.setPassword(request.getParameter("newPassword"));
			// 수정
			memberService.updateMember2(mb);
			return "redirect:/";
		} else {
			// 입력하신 정보가 틀립니다.
			model.addAttribute("msg", "입력하신 정보가 틀립니다.");
			// /WEB-INF/views/member/msg.jsp
			return "msg";
		}
	}

	@RequestMapping(value = "/join/id_check", method = RequestMethod.GET)
	public ResponseEntity<String> id_check(HttpServletRequest request) {

		ResponseEntity<String> entity = null;
		String result = "";
		try {
			String id = request.getParameter("id");
			System.out.println(id);
			MemberBean mb = memberService.getMember(id);
			if (mb != null) {
				result = "iddup";
			} else {
				result = "idok";
			}
			entity = new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			;
		}
		return entity;
	}

	@RequestMapping(value = "/join2/id_check2", method = RequestMethod.GET)
	public ResponseEntity<String> id_check2(HttpServletRequest request) {

		ResponseEntity<String> entity = null;
		String result = "";
		try {
			String id = request.getParameter("id");
			System.out.println(id);
			MemberBean mb = memberService.getMember2(id);
			if (mb != null) {
				result = "iddup";
			} else {
				result = "idok";
			}
			entity = new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			;
		}
		return entity;
	}

	@RequestMapping(value="/oauth",method= RequestMethod.GET)
	 public String kakaoConnect() {

	  StringBuffer url = new StringBuffer();
	  url.append("https://kauth.kakao.com/oauth/authorize?");
	  url.append("client_id=" + "cb04aa3b14b605e3f4ce52f66ec99303");
	  url.append("&redirect_uri=http://itwillbs2.cafe24.com/findhome/kakaologin");
	  url.append("&response_type=code");

	  return "redirect:" + url.toString();
	 
	 }
	 
	 @RequestMapping(value="/kakaologin", produces="application/json", method= {RequestMethod.GET, RequestMethod.POST})
	 public String kakaoLogin(@RequestParam("code")String code,RedirectAttributes ra,HttpSession session,HttpServletResponse response ,String autorize_code)throws IOException {
	  
	  System.out.println("kakao code:"+code);
	  JsonNode access_token = kakao_restapi.getAccessToken(code);
	  access_token.get("access_token");
	  System.out.println("access_token:" + access_token.get("access_token"));

	  JsonNode userInfo = kakao_restapi.getKakaoUserInfo(access_token.get("access_token"));

	         
	         String id = userInfo.path("id").asText();
	         String name = null;
	         String email = null;
	        
	     
	         JsonNode properties = userInfo.path("properties");
	         JsonNode kakao_account = userInfo.path("kakao_account");
	         name = properties.path("nickname").asText(); //이름 정보 가져오는 것
	         email = kakao_account.path("account_email").asText();
	         
	         session.setAttribute("isLogOn",true);
	         session.setAttribute("id",name);  
	         session.setAttribute("account_email", kakao_account);  
	        
	         System.out.println("id : " + id);    //여기에서 값이 잘 나오는 것 확인 가능함.
	         System.out.println("name : " + name);
	         System.out.println("email : " + email);
	  
	         return "redirect:/";
	     
	 }
	 
	 @RequestMapping(value = "/findPassword", method = RequestMethod.GET)
	 public String findPassword(HttpSession session, Model model) {
		    
		 return "/findPassword";
	 }
	 
	 @RequestMapping(value = "/findPassword2", method = RequestMethod.GET)
	 public String findPassword2() {
	 
		 return "/findPassword2";
	 }

	 
	 @RequestMapping(value = "/findPasswordPro", method= {RequestMethod.GET, RequestMethod.POST})
		public String findPasswordPro(MemberBean mb, HttpServletRequest request, HttpServletResponse response, Model model) throws AddressException, MessagingException, Exception {
			String id = (String)request.getParameter("id");
			mb.setId(id);
			MemberBean mb2 = memberService.userCheck3(mb);
				
			if(mb2 == null) {
				
				  request.setAttribute("msg", "가입되지 않은 이메일입니다.");
		          request.setAttribute("id", mb.getId());
		          return "msg";
		          
			} 

				  String host = "smtp.naver.com";
	              String user = "kimc106@naver.com"; //자신의 네이버 계정
	              String password = "U2GYZY1ESRB6";//자신의 네이버 패스워드
	                
	                //메일 받을 주소
	              String to_email = mb.getId();
         
//	                //SMTP 서버 정보를 설정한다.
	              Properties props = new Properties();
	              props.put("mail.smtp.host", host);
	              props.put("mail.smtp.port", 465);
	              props.put("mail.smtp.auth", "true");
	              props.put("mail.smtp.ssl.enable", "true");
	              props.put("mail.smtp.ssl.trust", host);


	              StringBuffer temp =new StringBuffer();
	                Random rnd = new Random();
	                for(int i=0; i<10; i++) {
	                	int rIndex = rnd.nextInt(3);
	                    switch (rIndex) {
	                    case 0:
	                        // a-z
	                        temp.append((char) ((int) (rnd.nextInt(26)) + 97));
	                        break;
	                    case 1:
	                        // A-Z
	                        temp.append((char) ((int) (rnd.nextInt(26)) + 65));
	                        break;
	                    case 2:
	                        // 0-9
	                        temp.append((rnd.nextInt(10)));
	                        break;
	                    }
	                }
	               
	                
	           
	                
	                String AuthenticationKey = temp.toString();
	                System.out.println(AuthenticationKey);
	                
	                Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication(user, password);
	                    }
	                });
	                
	                session.setDebug(true); //for debug  

	                
	                try {
		                    MimeMessage msg = new MimeMessage(session);
		                    msg.setFrom(new InternetAddress(user));
		                    msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to_email));
		                    
		                    //메일 제목
		                    msg.setSubject("[findhome] 고객님의 임시 비밀번호입니다.");
		                    //메일 내용
		                    msg.setText(new StringBuffer().append("안녕하세요 findhome 고객님. \n")
	                    		    .append("요청하신 임시 비밀 번호는 " + "[" + temp + "]" + "입니다. \n")
	                    		    .append("고객님의 개인정보 보호를 위해서 \n")
	                    		    .append("findhome 웹사이트에 로그인을 하신 후 회원정보 페이지에서 \n")
	                    		    .append("발급받은 임시비밀번호를 새로운 비밀번호로 재설정한 후에 이용해주세요.").toString());
		                    
		                    Transport.send(msg);
		                    System.out.println("이메일 전송");
		                    
		                }catch (Exception e) {
		                    e.printStackTrace();
		                }
	
		                
	                mb.setPassword(temp.toString());
	                mb.setId(id);
	                memberService.updateMemberPw(mb);
	                model.addAttribute("msg2", "입력하신 메일로 비밀번호가 발급 되었습니다.");
			        request.setAttribute("id", mb.getId());

			          return "msg2";
 
	 		}	
		    	        
		        
	
	 
	 @RequestMapping(value = "/findPasswordPro2", method= {RequestMethod.GET, RequestMethod.POST})
		public String findPasswordPro2(MemberBean mb, HttpServletRequest request, HttpServletResponse response, Model model) throws AddressException, MessagingException, Exception {
			String id = (String)request.getParameter("id");
			mb.setId(id);
			MemberBean mb2 = memberService.userCheck4(mb);
				
			if(mb2 == null) {
				
				  request.setAttribute("msg", "가입되지 않은 이메일입니다.");
		          request.setAttribute("id", mb.getId());
		          return "msg";
		          
			} 

				  String host = "smtp.naver.com";
	              String user = "kimc106@naver.com"; //자신의 네이버 계정
	              String password = "U2GYZY1ESRB6";//자신의 네이버 패스워드
	                
	                //메일 받을 주소
	              String to_email = mb.getId();
         
//	                //SMTP 서버 정보를 설정한다.
	              Properties props = new Properties();
	              props.put("mail.smtp.host", host);
	              props.put("mail.smtp.port", 465);
	              props.put("mail.smtp.auth", "true");
	              props.put("mail.smtp.ssl.enable", "true");
	              props.put("mail.smtp.ssl.trust", host);


	              StringBuffer temp =new StringBuffer();
	                Random rnd = new Random();
	                for(int i=0; i<10; i++) {
	                	int rIndex = rnd.nextInt(3);
	                    switch (rIndex) {
	                    case 0:
	                        // a-z
	                        temp.append((char) ((int) (rnd.nextInt(26)) + 97));
	                        break;
	                    case 1:
	                        // A-Z
	                        temp.append((char) ((int) (rnd.nextInt(26)) + 65));
	                        break;
	                    case 2:
	                        // 0-9
	                        temp.append((rnd.nextInt(10)));
	                        break;
	                    }
	                }
	               
	                
	           
	                
	                String AuthenticationKey = temp.toString();
	                System.out.println(AuthenticationKey);
	                
	                Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication(user, password);
	                    }
	                });
	                
	                session.setDebug(true); //for debug  

	                
	                try {
		                    MimeMessage msg = new MimeMessage(session);
		                    msg.setFrom(new InternetAddress(user));
		                    msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to_email));
		                    
		                    //메일 제목
		                    msg.setSubject("[findhome] 고객님의 임시 비밀번호입니다.");
		                    //메일 내용
		                    msg.setText(new StringBuffer().append("안녕하세요 findhome seller 고객님. \n")
	                    		    .append("요청하신 임시 비밀 번호는 " + "[" + temp + "]" + "입니다. \n")
	                    		    .append("고객님의 개인정보 보호를 위해서 \n")
	                    		    .append("findhome 웹사이트에 로그인을 하신 후 회원정보 페이지에서 \n")
	                    		    .append("발급받은 임시비밀번호를 새로운 비밀번호로 재설정한 후에 이용해주세요.").toString());
		                    Transport.send(msg);
		                    System.out.println("이메일 전송");
		                    
		                }catch (Exception e) {
		                    e.printStackTrace();
		                }
	
		                
	                mb.setPassword(temp.toString());
	                mb.setId(id);
	                memberService.updateMemberPw2(mb);
	                model.addAttribute("msg2", "입력하신 메일로 비밀번호가 발급 되었습니다.");
			        request.setAttribute("id", mb.getId());

			          return "msg2";
 
	 		}	
	 
	 @RequestMapping(value = "addPhone", method = RequestMethod.GET)
		public String addPhone(MemberBean mb, HttpServletRequest request) {
		
			return "addPhone";
		}
	 
	 @RequestMapping(value = "/addPhonePro", method = RequestMethod.POST)
		public String addPhonePro(MemberBean mb, HttpServletRequest request, Model model, HttpSession session) {
			
		 		String id = (String)session.getAttribute("id");
		 		mb.setId(id);
				memberService.updateMember3(mb);
	
			return "redirect:/";
			
			
		}
	 

	
}