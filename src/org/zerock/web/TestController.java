//testcontroller를 통해 요청이 들어오면 여기서 분기하여 request와 response를 보냄. (*.do)를  

package org.zerock.web;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zerock.anno.GetMapping;

/**
 * Servlet implementation class TestController
 */
//@WebServlet("/test/*") ==> test로 시작하는 어떤 경로든간에 실행되어야 함 (localhost:8080/web4/test/doA~C 다 실행 되어야 함)
@WebServlet(value="*.do", initParams = {
		@WebInitParam(name="board", value="org.zerock.web.BoardController")})       //("/*")로 하면 모든 파일을 다 받아들이므로 확장자를 ("/*.do)로 써줌 ==> 분기를 /board/doA.do 식으로 해 줌

public class TestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> controllerMap;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestController() {
        super();
        // TODO Auto-generated constructor stub
    }
    

	@Override
	public void init() throws ServletException {
		System.out.println("init called.....");
		
		controllerMap = new HashMap<>();
		
		
		//forEach문과 비슷한 표현방식
		Enumeration<String> en = this.getInitParameterNames();
		
		//쓰레드가 동작하기 전 한번만 동작하는 코드
		while(en.hasMoreElements()) {
			
			String path = en.nextElement();
			String value = this.getInitParameter(path);
			try{
				
				System.out.println("PATH: "+ path);
				System.out.println("VALUE: " +value);
				
				controllerMap.put(path, Class.forName(value).newInstance());
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		//web4가 contextpath가 됨. 
		
		String httpMethod = request.getMethod();
		String path = request.getServletPath();  //doA냐 doB냐....
		
		String prefix = "/WEB-INF/views";
		
		String next = path.replaceAll(".do",".jsp");
		
		String nextPath = prefix + next;
		
		System.out.println(nextPath);
		
		//path /board/register.do
		
		// /WEB-INF/views/board/register.jsp
		
		//분기를 위한 split 작업
		String controllerPath = path.split("/")[1];
		
		System.out.println(controllerPath);
		
		Object obj = controllerMap.get(controllerPath);
		
		Class clz = obj.getClass();
		
		Method[] methods = clz.getDeclaredMethods();
		
		for(Method method2 : methods) {
			
			if(httpMethod.equals("GET")) {
				
				GetMapping mapping = method2.getAnnotation(GetMapping.class);
				
				if(mapping != null) {
					String annoValue = mapping.value();
					
					if(annoValue.equals(path.split("/")[2])) {
						
						try{
							
							Object result = method2.invoke(obj, request, response);
							
							if(result == null || result.getClass() == Void.class) {
								System.out.println("void return");
								request.getRequestDispatcher(nextPath).forward(request, response);
							}else if(result.getClass() == String.class) {
								response.sendRedirect((String)result);
							}
						} catch(Exception e) {
							e.printStackTrace();
						}
						
						} //end try
					}//end catch
				}//end if
			}//end if
		}//end for
		
}
		
		//@의 치명적 약점: xml은 파일이므로 파일 자체만 수정하면 된다. 반면 @는 소스코드에 적용되는 것이므로 소스를 다시 컴파일 해야 한다. 
		//System.out.println(controllerPath);
		
		/*System.out.println("PATH :" + path);
		System.out.println("METHOD: " + httpMethod);
		System.out.println("PATH2 : " +request.getServletPath());
		System.out.println("PAHT3: " + request.getPathInfo());  //doA냐 doB냐....
*/	



