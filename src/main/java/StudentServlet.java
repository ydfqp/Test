import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.logging.SimpleFormatter;
import java.util.stream.Stream;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {
    JedisUtil jedisUtil = new JedisUtil();
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        System.out.println(method);
        switch (method){
            case "StudentList" : getlist(req,resp);  break;
            case "DeleteStudent" : delete(req,resp);break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String method = req.getParameter("method");
        System.out.println(method);
        switch (method){
            case "InsertStudent" : insert(req,resp);  break;
            case "UpdateStudent" : update(req,resp);  break;
        }
    }

    //localhost:8080/student?method=StudentList&&page=1
    private void getlist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = req.getParameter("page");
        Integer pageNumber = 1;
        if (page!=null){
            pageNumber = Integer.valueOf(page);
        }
        Page pageData = new Page();
        List<Student> student = jedisUtil.getList("student");
        int max = ((student.size()-1)/10)+1;
        if (pageNumber>max){
            pageNumber = 1;
        }
        student.sort((s1,s2)->{return s1.getAvgscore()-s2.getAvgscore();});
        int end = pageNumber * 10 >student.size() ? student.size():pageNumber * 10;
        List<Student> data = student.subList((pageNumber - 1) * 10, end);
        pageData.setData(data);
        pageData.setLimit(10);
        pageData.setMaxPage(max);
        pageData.setNowPage(pageNumber);
        req.setAttribute("studentlist", pageData);
        req.getRequestDispatcher("index.jsp").forward(req,resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String birthday = req.getParameter("birthday");
        String description = req.getParameter("description");
        String avgscore = req.getParameter("avgscore");
        if (id != null && name != null && birthday != null && description != null && avgscore != null
                && !id.equals("")  && !name.equals("") && !description.equals("") && !avgscore.equals("")) {
            Date parse = null;
            SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                parse = simpleFormatter.parse(birthday);
            } catch (ParseException e) {
            }
            int i=0;
            List<Student> list = jedisUtil.getList("student");
            Iterator<Student> iterator = list.iterator();
            while (iterator.hasNext()){
                Student next = iterator.next();
                if (id.equals(next.getId())){
                    if (parse == null || parse.equals("")) {
                        parse = next.getBirthday();
                    }
                    break;
                }
                i++;
            }
            Student student = new Student(id,name,parse,description,Integer.valueOf(avgscore));
            String student1 = jedisUtil.updateList("student", i, student);
            System.out.println(student1);
            resp.sendRedirect(req.getContextPath()+"/student?method=StudentList");
            return;
        }
        resp.sendRedirect(req.getContextPath()+"/error.jsp");
    }
    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id!=null&&!id.equals("")){
            Integer student = jedisUtil.getIndex(id, "student");
            if (student!=null){
                long num = jedisUtil.delList("student", student);
                if (num>0){
                    resp.sendRedirect(req.getContextPath()+"/student?method=StudentList");
                    return;
                }
            }
        }
        resp.sendRedirect(req.getContextPath()+"/error.jsp");

    }
    private void insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String birthday = req.getParameter("birthday");
        String description = req.getParameter("description");
        String avgscore = req.getParameter("avgscore");
        if (name != null&&birthday != null&&description != null&&avgscore != null
                &&!name.equals("")&&!birthday.equals("")&&!description.equals("")&&!avgscore.equals("")){
            SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = null;
            try {
                parse = simpleFormatter.parse(birthday);
            } catch (ParseException e) {

            }
            int score=Integer.valueOf(avgscore);
            Student student = new Student(UUID.randomUUID().toString(), name, parse, description, score);
            Long num = jedisUtil.set("student", student);
            System.out.println(num);
            if (num > 0){
                resp.sendRedirect(req.getContextPath()+"/student?method=StudentList");
                return;
            }
        }
        resp.sendRedirect(req.getContextPath()+"/error.jsp");
    }
}
