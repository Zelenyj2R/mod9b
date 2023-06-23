package servlets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.ZoneId;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {
  @Override
  protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
    res.setContentType("text/html; charset=utf-8");

    String zoneId  = req.getParameter("zoneId");
    if (zoneId == null || validateTimeZone(zoneId.replace(' ', '+'))) {
      chain.doFilter(req, res);
    } else {
      res.getWriter().write("there is no time zone");
      res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      res.getWriter().close();
    }
  }

  private boolean validateTimeZone(String timezone) {
    try {
      ZoneId.of(timezone);
      return true;
    } catch (DateTimeException e) {
      return false;
    }
  }
}
