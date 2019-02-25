package com.kh.spring.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

@Controller
@SessionAttributes(value = { "memberLoggedIn" })
public class MemberController {
	Logger logger = Logger.getLogger(getClass());

	@Autowired
	MemberService memberService;

	@Autowired
	BCryptPasswordEncoder bcryptPasswordEncoder;

	@RequestMapping("/member/memberMoveLogin.do")
	public String memberLogin() {

		return "member/memberLogin";
	}

	/**
	 * 
	 * 회원가입 요청이 들어왔을 시 처리
	 */
	@RequestMapping("/member/memberEnroll.do")
	public String memberEnroll() {
		// logger가 Debug모드라면 실행해라 !
		// 효율적으로 사용하기 위한 조건문이다.
		if (logger.isDebugEnabled()) {
			logger.debug("회원등록페이지 요청");
		}
		return "member/memberEnroll";
	}

	@RequestMapping("/member/memberEnrollEnd.do")
	public String memberEnrollEnd(Member m, HttpServletRequest req) {
		if (logger.isDebugEnabled()) {
			logger.debug("회원등록 요청");
		}

		System.out.println("암호화전 : " + m.getMemberPassword());
		String temp = m.getMemberPassword();
		// BCrypt방식 암호화
		m.setMemberPassword(bcryptPasswordEncoder.encode(temp));
		System.out.println("암호화후 : " + m.getMemberPassword());

		int result = memberService.insertMember(m);
		System.out.println(result > 0 ? "회원등록성공" : "회원등록실패");

		String loc = "/";
		String msg = "";
		if (result > 0) {
			msg = "회원가입성공!";
		} else {
			msg = "회원가입실패!";
		}
		req.setAttribute("loc", loc);
		req.setAttribute("msg", msg);

		return "common/msg";
	}
	/*
	 * @RequestMapping(value="/member/memberLogin.do" , method=RequestMethod.POST)
	 * public String memberLogin(@RequestParam String memberId ,
	 * 
	 * @RequestParam String password , Model model , HttpSession session) { // 아이디를
	 * 통해서 selectOne메소드 호출결과 Member객체를 가져온다. Member m =
	 * memberService.selectOneMember(memberId); String msg = ""; String view =
	 * "common/msg"; String loc = "";
	 * 
	 * // 로그인처리 if(m == null) { msg = "아이디가 존재하지 않습니다."; loc = "/";
	 * model.addAttribute("msg" , msg); model.addAttribute("loc" , loc); } else {
	 * if(bcryptPasswordEncoder.matches(password, m.getPassword())) { // 세션 - 상태유지
	 * // session.setAttribute("", m); model.addAttribute("" , m);
	 * 
	 * view ="redirect:/"; } else { msg = "비밀번호가 틀렸습니다."; loc = "/";
	 * model.addAttribute("msg" , msg); model.addAttribute("loc" , loc); } } return
	 * view; }
	 */

	/**
	 *
	 * ModelAndView(2.0) - Model과 view단 정보를 하나의 객체에서 관리 ModelMap(2.0) : 일반클래스 -
	 * Model객체관리, view단은 문자열로 리턴 Model(2.5) : 인터페이스 - Model객체관리, view단은 문자열로 리턴
	 * 
	 * ModelAndView로 바꾸어보기
	 *
	 */

	@RequestMapping(value = "/member/memberLogin.do", method = RequestMethod.POST)
	public ModelAndView memberLogin(@RequestParam String memberId, @RequestParam String password, ModelAndView mav,
			HttpSession session) {
		if (logger.isDebugEnabled()) {
			logger.debug("회원로그인 요청");
		}

		// 아이디를 통해서 selectOne메소드 호출결과 Member객체를 가져온다.
		Member m = memberService.selectOneMember(memberId);
		logger.debug(m);
		String msg = "";
		String view = "common/msg";
		String loc = "";

		// 로그인처리
		if (m == null) {
			msg = "아이디가 존재하지 않습니다.";
			loc = "/member/memberMoveLogin.do";
			mav.addObject("msg", msg);
			mav.addObject("loc", loc);
		} else {
			if (bcryptPasswordEncoder.matches(password, m.getMemberPassword())) {
				// 세션 - 상태유지
				// session.setAttribute("", m);
				mav.addObject("memberLoggedIn", m);

				view = "redirect:/";
			} else {
				msg = "비밀번호가 틀렸습니다.";
				loc = "/member/memberMoveLogin.do";
				mav.addObject("msg", msg);
				mav.addObject("loc", loc);
			}
		}
		mav.setViewName(view);
		return mav;
	}

	@RequestMapping("/member/memberLogout.do")
	public String logout(SessionStatus sessionStatus) {
		if (logger.isDebugEnabled()) {
			logger.debug("회원로그아웃 요청");
		}

		// session.setAttribute() -> session.invalidate();
		// @SessionAttributes -> sessionStatus.setComplete()
		if (!sessionStatus.isComplete()) {
			sessionStatus.setComplete();
		}

		return "redirect:/";
	}

	@RequestMapping("/member/memberView.do")
	public String view(Model model, @RequestParam String memberId) {
		Member m = memberService.selectOneMember(memberId);
		model.addAttribute("m", m);
		return "member/memberView";
	}

	@RequestMapping("/member/memberUpdateEnd.do")
	public String memberUpdate(Model model, Member m) {
		int result = memberService.memberUpdate(m);
		System.out.println(result > 0 ? "회원정보수정성공" : "회원정보수정실패");
		return "redirect:/member/memberView.do?memberId=" + m.getMemberId();
	}
	/*
	 * @RequestMapping("/member/checkDuplicate.do") public void
	 * checkDuplicate(@RequestParam("memberId") String memberId ,
	 * HttpServletResponse response) throws IOException {
	 * logger.debug("ID중복체크 : "+memberId);
	 * 
	 * Member m = memberService.selectOneMember(memberId); boolean isUsable = m ==
	 * null?true:false;
	 * 
	 * response.getWriter().print(isUsable); }
	 */

	/**
	 * BeanNameViewResolver를 이용해 jsonView라는 뷰 클래스를 지정
	 * 
	 */
	/*
	 * @RequestMapping("/member/checkDuplicate.do") public String
	 * checkDuplicate(@RequestParam("memberId") String memberId , Model model) {
	 * logger.debug("ID중복체크 : "+memberId); Member m =
	 * memberService.selectOneMember(memberId); boolean isUsable = m ==
	 * null?true:false; model.addAttribute("isUsable" , isUsable); return
	 * "jsonView"; // BeanNameViewResolver에 의해 처리될 bean객체 이름 }
	 */

	/**
	 * @ResponseBody어노테이션을 이용하여 리턴된 객체를 jsonString으로 자동변환하여 전송
	 * 
	 */
	@RequestMapping("/member/checkDuplicate.do")
	@ResponseBody
	public Map<String, Object> checkDuplicate(@RequestParam("memberId") String memberId) {
		logger.debug("ID중복체크 : " + memberId);
		Map<String, Object> map = new HashMap<>();
		Member m = memberService.selectOneMember(memberId);
		boolean isUsable = m == null ? true : false;
		map.put("isUsable", isUsable);
		return map;
	}

	@RequestMapping("/member/memberInterest.do")
	public ModelAndView selectInterest(ModelAndView mav) {

		List<Map<String, String>> list = memberService.selectAllCategory();

		mav.addObject("category", list);
		mav.setViewName("/member/memberInterest");

		return mav;
	}

	/*
	 * 페이스북 관련: 로그인 관련
	 */
	@RequestMapping("/member/facebookLogin")
	@ResponseBody
	public Map<String, Object> facebookLogin(@RequestParam("memberId") String memberId, HttpSession session) {

		Map<String, Object> map = new HashMap<>();
		Member m = memberService.selectOneMember(memberId);
		boolean fbisUsable = m == null ? true : false;
		logger.debug(m);
		logger.debug(fbisUsable);

		if (fbisUsable == false) {
			session.setAttribute("memberLoggedIn", m);
			// map.put("memberLoggedIn", m);
		}

		map.put("fbisUsable", fbisUsable);
		return map;

	}

	/*
	 * 페이스북 관련: 로그인하면 컨테이너 떨어짐 ajax로 넘기기
	 */
	@RequestMapping("/member/facebookEnroll")
	@ResponseBody
	public ModelAndView facebookEnroll(@RequestParam("fbId") String fbId, @RequestParam("fbName") String fbName,
			@RequestParam("fbEmail") String fbEmail, ModelAndView mav) {

		mav.setViewName("member/memberEnroll");
		return mav;

	}

	/*
	 * facebook 가입 ajax 창으로 받은거 넘겨주기...
	 */
	@RequestMapping("/member/facebookEnrollEnd")
	@ResponseBody
	public ModelAndView facebookEnrollEnd(@RequestParam("fbId") String fbId, @RequestParam("fbName") String fbName,
			@RequestParam("fbBirth") String fbBirth, @RequestParam("fbEmail") String fbEmail,
			@RequestParam("fgender") String gender, ModelAndView mav) {

		Member m = new Member();
		m.setMemberId(fbId);
		m.setMemberName(fbName);
		m.setMemberBirth(fbBirth);
		m.setMemberEmail(fbEmail);
		m.setGender(gender);

		int result = memberService.insertFacebookMember(m);
		System.out.println(result > 0 ? "회원등록성공" : "회원등록실패");

		mav.addObject("result", result);
		mav.setViewName("member/memberLogin");

		return mav;

	}

	/*
	 * facebook 폼안에 있는 아이디값 중복검사!
	 */
	@RequestMapping("/member/facebookCheckDuplicate.do")
	@ResponseBody
	public Map<String, Object> facebookCheckDuplicate(@RequestParam("fbIdcheck") String fbIdcheck) {

		logger.debug("검사할 faceBookID : " + fbIdcheck);

		Map<String, Object> map = new HashMap<>();
		Member m = memberService.selectOneFBMember(fbIdcheck);
		boolean FBisUsable = m == null ? true : false;
		System.out.println("검사할건!" + FBisUsable);
		map.put("FBisUsable", FBisUsable);

		return map;

	}

	/* 카카오 관련 code */
	// 카카오 로그인
	@RequestMapping("/member/kakaoLogin")
	@ResponseBody
	public Map<String, Object> kakaoLogin(@RequestParam("kakaoId") String kakaoId, HttpSession session) {

		Member m = memberService.selectOneMember(kakaoId);

		Map<String, Object> map = new HashMap<>();

		boolean kisUsable = m == null ? true : false;
		logger.debug(m);
		logger.debug(kisUsable);

		if (kisUsable == false) {
			session.setAttribute("memberLoggedIn", m);
			// map.put("memberLoggedIn", m);
		}

		map.put("kisUsable", kisUsable);
		return map;

	}

	/* kakao 회원 등록하기 */
	@RequestMapping("/member/kakaoEnrollEnd")
	@ResponseBody
	public ModelAndView kakaoEnrollEnd(@RequestParam("kId") String kId, @RequestParam("kName") String kName,
			@RequestParam("kBirth") String kBirth, @RequestParam("kEmail") String kEmail,
			@RequestParam("kgender") String kgender, ModelAndView mav) {

		Member m = new Member();
		m.setMemberId(kId);
		m.setMemberName(kName);
		m.setMemberBirth(kBirth);
		m.setMemberEmail(kEmail);
		m.setGender(kgender);

		int result = memberService.insertKakaoMember(m);
		System.out.println(result > 0 ? "회원등록성공" : "회원등록실패");

		mav.addObject("result", result);
		mav.setViewName("member/memberLogin");

		return mav;

	}

	@RequestMapping("/member/kakaoEnroll")
	@ResponseBody
	public ModelAndView kakaoEnroll(@RequestParam("kId") String kId, @RequestParam("kName") String kName,
			ModelAndView mav) {

		mav.setViewName("member/memberEnroll");
		return mav;

	}

	/*
	 * kakao 폼안에 있는 아이디값 중복검사!
	 */
	@RequestMapping("/member/kakaoCheckDuplicate.do")
	@ResponseBody
	public Map<String, Object> kakaoCheckDuplicate(@RequestParam("kIdcheck") String kIdcheck) {

		logger.debug("검사할 kakaoID : " + kIdcheck);

		Map<String, Object> map = new HashMap<>();
		Member m = memberService.selectOnekakaoMember(kIdcheck);
		boolean kisUsable = m == null ? true : false;
		System.out.println("검사할건!" + kisUsable);
		map.put("kisUsable", kisUsable);

		return map;

	}

	/* 구글 관련 */
	/* 구글에서 정보 뽑아오기 */
	@RequestMapping("/member/googleLogin")
	@ResponseBody
	public Map<String, Object> googleLogin(@RequestParam("googleId") String googleId, HttpSession session) {

		Member m = memberService.selectOneMember(googleId);

		Map<String, Object> map = new HashMap<>();

		boolean gisUsable = m == null ? true : false;
		logger.debug(m);
		logger.debug(gisUsable);

		if (gisUsable == false) {
			session.setAttribute("memberLoggedIn", m);
			// map.put("memberLoggedIn", m);
		}

		map.put("gisUsable", gisUsable);
		return map;

	}

	/* 구글 버튼 누를시 가입 창이 나온다 */
	@RequestMapping("/member/googleEnroll")
	public ModelAndView googleEnroll(@RequestParam("gId") String gId, @RequestParam("gName") String gName,
			@RequestParam("gEmail") String gEmail, ModelAndView mav) {

		mav.setViewName("member/memberEnroll");
		return mav;

	}

	// 구글로 회원 가입하기
	@RequestMapping("/member/googleEnrollEnd")
	public ModelAndView googleEnrollEnd(@RequestParam("gId") String gId, @RequestParam("gName") String gName,
			@RequestParam("gBirth") String gBirth, @RequestParam("gEmail") String gEmail,
			@RequestParam("ggender") String ggender, ModelAndView mav) {

		Member m = new Member();
		m.setMemberId(gId);
		m.setMemberName(gName);
		m.setMemberBirth(gBirth);
		m.setMemberEmail(gEmail);
		m.setGender(ggender);

		int result = memberService.insertgoogleMember(m);
		System.out.println(result > 0 ? "회원등록성공" : "회원등록실패");

		mav.addObject("result", result);
		mav.setViewName("member/memberLogin");

		return mav;

	}

	@RequestMapping("/member/googleCheckDuplicate.do")
	@ResponseBody
	public Map<String, Object> googleCheckDuplicate(@RequestParam("gIdcheck") String gIdcheck) {

		logger.debug("검사할 건 googleID : " + gIdcheck);
		Map<String, Object> map = new HashMap<>();
		Member m = memberService.selectOnegoogleMember(gIdcheck);
		boolean gisUsable = m == null ? true : false;
		System.out.println("검사할건!" + gisUsable);
		map.put("gisUsable", gisUsable);
		return map;

	}

}
