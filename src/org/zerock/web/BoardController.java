//1. 설계해 놓고 @GetMapping만 갖다붙이면 자동으로 호출(14~21행)
//2. 코드에서 request, send/reDirect 등 다 뺀다. @GetMapping으로 WEB-INF 아래 register.jsp 호출하게끔 한다.
//결과적으로는 하나의 서블릿에서 분기하게 해 주게끔 짜는 게 목적이다. @WebServlet("/board/*.do")이 서블릿 하나에서 URL을 분기(@GetMapping("/register"))

package org.zerock.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zerock.anno.GetMapping;

public class BoardController {
	
	@GetMapping("register.do")      //사용자가 register라고 하면 @ 하겠다.
	public void registerGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("register get");
		System.out.println("register get");
		System.out.println("register get");
		System.out.println("register get");
		System.out.println("register get");
	}
	@GetMapping("list.do")
	public void list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("list get");
	}
	
	public String registerPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("register Post");
		System.out.println("register Post");
		System.out.println("register Post");
		System.out.println("register Post");
		System.out.println("register Post");
		
		return "/board/list.do?msg=success";
	}
	
/*	public static void main(String[] args) throws Exception {
		
		//메소드가 있고 url이 있음. 메소드 중 GET이 있는 애만 찾고, URL이 register인 애와 연관된 애들만 호출한다.(?)
		String target ="/register";
		String method = "GET";
		
		String ClassName = "org.zerock.web.BoardController";
		
		//가능하면 특정한 타입을 안 쓰고 object 타입을 쓰겠다.
		Class clz = Class.forName(ClassName); //클래스를 메모리상 로딩
		Object obj = clz.newInstance();
		
		//메소드의 배열
		Method[] methods = clz.getDeclaredMethods();
		
		for(Method method2 : methods) {
			
			if(method.equals("GET")) {
				GetMapping mapping = method2.getAnnotation(GetMapping.class);
				
				if(mapping != null) {
					String annoValue = mapping.value();
					
					if(annoValue.equals(target)) {
						method2.invoke(obj, null);
					}
				}
			}
		}
		
	}*/

}
