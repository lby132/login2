package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        final String requestURI = request.getRequestURI();
        final String uuid = UUID.randomUUID().toString();

        request.setAttribute(LOG_ID, uuid);

        //@RequestMapping을 사용하면 HandlerMethod가 넘어오게 되고,
        //@Contoller가 아니라 /resource/static과 같은 정적 리소스를 사용하는 경우에는 핸들러 정보로 ResourceHttpRequestHandler가 넘어오게 된다.
        if (handler instanceof HandlerMethod) {
            final HandlerMethod hm = (HandlerMethod) handler;//호출할 컨트롤러 메서드의 모든 정보가 포함 되어 있다.
        }

        log.info("REQUEST [{}{}{}]", uuid, requestURI, handler);

        return true; //다음 핸들러가 호출이 된다. return false이면 아무것도 호출하지 않고 종료된다.
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandler [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        final String requestURI = request.getRequestURI();
        final String uuid = (String) request.getAttribute(LOG_ID);

        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);
        if (ex != null) {
            log.error("afterCompletion error!!", ex);
        }
    }
}
