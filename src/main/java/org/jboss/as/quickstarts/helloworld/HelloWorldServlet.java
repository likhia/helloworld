package org.jboss.as.quickstarts.helloworld;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.text.SimpleDateFormat;

@SuppressWarnings("serial")
@WebServlet("/HelloWorld")
public class HelloWorldServlet extends HttpServlet {

    static String PAGE_HEADER = "<html><head><title>Test Session Replication</title></head><body>";

    static String PAGE_FOOTER = "</body></html>";

    @Inject
    HelloService helloService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        HttpSession session = req.getSession();
        String name = (String) req.getParameter("name");

        System.out.println("======== Get shared session ======== " + (String) session.getAttribute("replica"));
        System.out.println("======== Get count session ======== " + session.getAttribute("count"));

        CountObject count = (CountObject) session.getAttribute("count");
        if(count != null) {
            count.increment();
        } else {
            count = new CountObject();
        }

        session.setAttribute("count", count);

        if ((name != null) && session.getAttribute("replica") == null)  {
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
                name = name + sdf.format(new Date());

                System.out.println("======== Set session ======== " + name);
                session.setAttribute("replica", name);
        }

        PrintWriter writer = resp.getWriter();
        writer.println(PAGE_HEADER);
        writer.println("<h1>" + helloService.createHelloMessage((String) session.getAttribute("replica")) + ":" + count.getCount() + "</h1>");
        writer.println(PAGE_FOOTER);
        writer.close();
    }

}
