package com.crmsite.controllers;

import com.crmsite.beans.CourseBean;
import com.crmsite.beans.LessonBean;
import com.crmsite.beans.NewsBean;
import com.crmsite.beans.UserBean;
import com.crmsite.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserBean userBean;

    @Autowired
    private CourseBean courseBean;

    @Autowired
    private LessonBean lessonBean;

    @Autowired
    private NewsBean newsBean;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("/user/loginUser");
        return view;
    }

    // all user auth via the method
    @RequestMapping(value = "userAuth", method = RequestMethod.POST)
    public ModelAndView userAuth(HttpServletRequest request) {

        ModelAndView view = new ModelAndView("/user/loginUser");

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        Users user = userBean.getUserLoginAndPassword(login, password);

        if (user!=null && user.getActive()==1) {
            if (user.getRole_id().getId()==2) {

                HttpSession session = request.getSession();
                session.setAttribute("adminSession", user);
                view.setViewName("/user/adminHome");

            } else if (user.getRole_id().getId()==1) {

                HttpSession session = request.getSession();
                session.setAttribute("teacherSession", user);
                List<News> newsList = newsBean.getNewsList();
                view.setViewName("/user/teacherHome");
                view.addObject("newsList", newsList);

            } else if (user.getRole_id().getId()==3) {

                HttpSession session = request.getSession();
                session.setAttribute("studentSession", user);
                List<News> newsList = newsBean.getNewsList();
                view.setViewName("/user/studentHome");
                view.addObject("newsList", newsList);

            }
        }

        return view;
    }

    @RequestMapping(value = "sendEditProfile", method = RequestMethod.GET)
    public ModelAndView sendEditProfile(HttpSession session) {

        ModelAndView view = new ModelAndView("/user/editProfile");

        if (session.getAttribute("adminSession")!=null) {
            Users user = (Users) session.getAttribute("adminSession");
            view.addObject("editedUser", user);
        }

        if (session.getAttribute("teacherSession")!=null) {
            Users user = (Users) session.getAttribute("teacherSession");
            view.addObject("editedUser", user);
        }

        if (session.getAttribute("studentSession")!=null) {
            Users user = (Users) session.getAttribute("studentSession");
            view.addObject("editedUser", user);
        }

        return view;
    }

    @RequestMapping(value = "editProfile", method = RequestMethod.POST)
    public ModelAndView editProfile(@RequestParam(value = "id") Long id,
                                    @RequestParam(value = "newLogin") String login,
                                    @RequestParam(value = "newPassword") String password,
                                    @RequestParam(value = "newName") String name,
                                    @RequestParam(value = "newSurname") String surname,
                                    HttpServletRequest request
                                    ) {

        ModelAndView view = new ModelAndView("/user/adminHome");
        Users user = userBean.getUserById(id);
        HttpSession session = request.getSession();

        if (login!=null && !user.getLogin().equals(login)) {
            user.setLogin(login);
        }
        if (password!=null && !user.getPassword().equals(password)) {
            user.setPassword(password);
        }
        if (name!=null && !user.getName().equals(name)) {
            user.setName(name);
        }
        if (surname!=null && !user.getSurname().equals(surname)) {
            user.setSurname(surname);
        }

        userBean.updateUser(user);

        if (user.getRole_id().getId()==3) {
            view.setViewName("/user/studentHome");
            session.setAttribute("studentSession", user);
        } else if (user.getRole_id().getId()==1) {
            view.setViewName("/user/teacherHome");
            session.setAttribute("teacherSession", user);
        }


        session.setAttribute("adminSession", user);

        return view;
    }

    //add teacher/student
    @RequestMapping(value = "newUser", method = RequestMethod.POST)
    public ModelAndView newUser() {

        ModelAndView view = new ModelAndView("/user/addUser");
        List<Roles> rolesList = userBean.getRolesList();
        view.addObject("rolesList", rolesList);

        List<Groups> groupsList = courseBean.getGroupList();
        view.addObject("groupsList", groupsList);

        return view;

    }

    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public ModelAndView addUser(HttpServletRequest request,
                                @RequestParam(value = "login") String login,
                                @RequestParam(value = "password") String password,
                                @RequestParam(value = "name") String name,
                                @RequestParam(value = "surname") String surname,
                                @RequestParam(value = "roleName") String roleName,
                                @RequestParam(value = "groupName") String groupName
                                ) {

        ModelAndView view = new ModelAndView("/user/addUser");
        List<Roles> rolesList = userBean.getRolesList();
        List<Groups> groupsList = courseBean.getGroupList();
        view.addObject("rolesList", rolesList);
        view.addObject("groupsList", groupsList);

        Users user = userBean.getUserByLogin(login);
        HttpSession session = request.getSession();
        Users meUser = (Users) session.getAttribute("adminSession");

        if (user==null) {
            if (login != null && password!=null && name!=null && surname!=null && roleName!=null && groupName!=null) {
                Roles role = userBean.getRoleByName(roleName);
                Groups myGroup = courseBean.getGroupByName(groupName);

                Users user1 = new Users(meUser.getCabinet_id(), login, password, role, name, surname, 1);
                user1.getGroups().add(myGroup);
                userBean.addUser(user1);

                session.removeAttribute("adminSession");
                view.setViewName("/user/adminHome");
                session.setAttribute("adminSession", meUser);
            }
        }

        return view;
    }

    @RequestMapping(value = "redirectEditTeacherAndStudent", method = RequestMethod.POST)
    public ModelAndView redirectEditTeacherAndStudent(HttpServletRequest request) {

        ModelAndView view = new ModelAndView("/user/editUser");
        List<Users> usersList = userBean.getUsersList();
        List<Users> users = new ArrayList<Users>();

        HttpSession session = request.getSession();
        Users admin = (Users) session.getAttribute("adminSession");

        for (Users u: usersList) {

            if (u.getRole_id().getId()!=2 && u.getCabinet_id().getName().equals(admin.getCabinet_id().getName())) {
                users.add(u);
            }

        }
        view.addObject("teacherAndStudentList", users);

        return view;

    }

    @RequestMapping(value = "deleteTeacherAndStudent", method = RequestMethod.POST)
    public ModelAndView deleteTeacherAndStudent(@RequestParam(value = "userId") Long id, HttpServletRequest request) {

        ModelAndView view = new ModelAndView("/user/editUser");

        Users deletedUser = userBean.getUserById(id);
        deletedUser.setActive(0);
        userBean.updateUser(deletedUser);

        List<Users> usersList = userBean.getUsersList();
        List<Users> users = new ArrayList<Users>();

        HttpSession session = request.getSession();
        Users admin = (Users) session.getAttribute("adminSession");

        for (Users u: usersList) {

            if (u.getRole_id().getId()!=2 && u.getCabinet_id().getName().equals(admin.getCabinet_id().getName())) {
                users.add(u);
            }

        }
        view.addObject("teacherAndStudentList", users);

        return view;
    }

    @RequestMapping(value = "redirectUserEditPage", method = RequestMethod.POST)
    public ModelAndView redirectUserEditPage(@RequestParam(value = "userId") Long id) {

        ModelAndView view = new ModelAndView("/user/updateTeacherStudent");
        Users user = userBean.getUserById(id);

        List<Roles> roles = userBean.getRolesList();
        view.addObject("allRoles", roles);

        List<Groups> groupsList = courseBean.getGroupList();
        view.addObject("allGroups", groupsList);

        if (user!=null) {
            view.addObject("editedUser", user);
        }


        return view;

    }

    @RequestMapping(value = "updateTeacherAndStudent", method = RequestMethod.POST)
    public ModelAndView updateTeacherAndStudent(@RequestParam(value = "userId") Long id,
                                                @RequestParam(value = "login") String login,
                                                @RequestParam(value = "password") String password,
                                                @RequestParam(value = "name") String name,
                                                @RequestParam(value = "surname") String surname,
                                                @RequestParam(value = "roleName") String roleName,
                                                @RequestParam(value = "groupName") String groupName,
                                                HttpServletRequest request

                                                ) {

        ModelAndView view = new ModelAndView("/user/editUser");
        Users user = userBean.getUserById(id);
        Groups group = courseBean.getGroupByName(groupName);

        if(login!=null && password!=null && name!=null && surname!=null && roleName!=null && groupName!=null) {
            user.setLogin(login);
            user.setPassword(password);
            user.setName(name);
            user.setSurname(surname);
            if (!user.getRole_id().getName().equals(roleName)) {
                Roles role = userBean.getRoleByName(roleName);
                user.setRole_id(role);
            }
            if (!user.getGroups().contains(group)) {
                if (user.getRole_id().getId()==3) {
                    user.getGroups().clear();
                    user.getGroups().add(group);
                }
            }
            userBean.updateUser(user);
        }

        List<Users> usersList = userBean.getUsersList();
        List<Users> users = new ArrayList<Users>();

        HttpSession session = request.getSession();
        Users admin = (Users) session.getAttribute("adminSession");

        for (Users u: usersList) {

            if (u.getRole_id().getId()!=2 && u.getCabinet_id().getName().equals(admin.getCabinet_id().getName())) {
                users.add(u);
            }

        }
        view.addObject("teacherAndStudentList", users);


        return view;
    }

    @RequestMapping(value = "redirectAddGroup", method = RequestMethod.GET)
    public ModelAndView redirectAddGroup() {

        ModelAndView view = new ModelAndView("/user/addGroup");


        return view;
    }

    //adding new group
    @RequestMapping(value = "addGroup", method = RequestMethod.POST)
    public ModelAndView addGroup(HttpServletRequest request, @RequestParam(value = "nameGroup") String nameGroup) {

        ModelAndView view = new ModelAndView("/user/addGroup");
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("adminSession");

        if (nameGroup!=null) {
            courseBean.addGroup(new Groups(user.getCabinet_id(), nameGroup, new Date(), 1));
            view.setViewName("/user/adminHome");
        }

        return view;
    }

    @RequestMapping(value = "allGroups", method = RequestMethod.GET)
    public ModelAndView allGroups(HttpServletRequest request) {

        ModelAndView view = new ModelAndView("/user/allGroups");
        List<Groups> groupsList = courseBean.getGroupList();

        if (groupsList!=null) {
            view.addObject("allGroupsList", groupsList);
        }

        return view;

    }

    @RequestMapping(value = "deleteGroup", method = RequestMethod.POST)
    public ModelAndView deleteGroup(@RequestParam(value = "groupId") Long groupId) {

        ModelAndView view = new ModelAndView("/user/allGroups");
        Groups group = courseBean.getGroupById(groupId);
        group.setActive(0);
        courseBean.updateGroup(group);

        List<Groups> groupsList = courseBean.getGroupList();

        if (groupsList!=null) {
            view.addObject("allGroupsList", groupsList);
        }

        return view;
    }

    @RequestMapping(value = "redirectUpdateGroup", method = RequestMethod.GET)
    public ModelAndView redirectUpdateGroup(@RequestParam(value = "groupId") Long groupId) {

        ModelAndView view = new ModelAndView("/user/editGroup");
        Groups group = courseBean.getGroupById(groupId);

        List<Users> usersList = group.getUsers();

        view.addObject("editedGroup", group);
        view.addObject("usersInGroup", usersList);

        return view;
    }

    @RequestMapping(value = "editGroup", method = RequestMethod.POST)
    public ModelAndView editGroup(@RequestParam(value = "id") Long id,
                                  @RequestParam(value = "groupName") String groupName
                                    ) {
        ModelAndView view = new ModelAndView("/user/allGroups");
        Groups group = courseBean.getGroupById(id);

        if (groupName!=null) {
            group.setName(groupName);
            courseBean.updateGroup(group);
        } else {
            view.setViewName("/user/editGroup");
        }

        List<Groups> groupsList = courseBean.getGroupList();
        view.addObject("allGroupsList", groupsList);

        return view;
    }

    @RequestMapping(value = "deleteFromGroup", method = RequestMethod.POST)
    public ModelAndView deleteFromGroup(@RequestParam(value = "userId") Long userId,
                                        @RequestParam(value = "groupId") Long groupId) {

        ModelAndView view = new ModelAndView("/user/editGroup");

        Users user = userBean.getUserById(userId);
        Groups group = courseBean.getGroupById(groupId);

        if (!user.getGroups().isEmpty()) {
            List<Groups> newGroups = new ArrayList<Groups>();
            for (Groups g:user.getGroups()) {
                if (!g.getName().equals(group.getName())) {
                    newGroups.add(g);
                }
            }
            user.getGroups().clear();
            user.setGroups(newGroups);

        }

        userBean.updateUser(user);

        List<Users> usersList = group.getUsers();

        view.addObject("editedGroup", group);
        view.addObject("usersInGroup", usersList);

        return view;


    }

    @RequestMapping(value = "redirectAddCourse", method = RequestMethod.POST)
    public ModelAndView redirectAddCourse() {

        ModelAndView view = new ModelAndView("/user/addCourse");
        return view;
    }


    //adding new course
    @RequestMapping(value = "addCourse", method = RequestMethod.POST)
    public ModelAndView addCourse(HttpServletRequest request,
                                  @RequestParam(value = "nameCourse") String nameCourse,
                                  @RequestParam(value = "descCourse") String descCourse) {

        ModelAndView view = new ModelAndView("/user/addCourse");
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("adminSession");
        List<Courses> courses = courseBean.getCoursesAllList();
        boolean check = true;
        for (Courses c:courses) {
            if (c.getName().equals(nameCourse)) {
                check = false;
            }
        }

        if (check==true) {
            if (nameCourse!=null && descCourse!=null) {
                Courses newCourse = new Courses(user.getCabinet_id(), nameCourse, descCourse, 1 );
                courseBean.addCourse(newCourse);


                view.setViewName("/user/adminHome");
            }
        }

        return view;
    }

    @RequestMapping(value = "allCourses", method = RequestMethod.GET)
    public ModelAndView allCourses() {

        ModelAndView view = new ModelAndView("/user/allCourses");
        List<Courses> coursesList = courseBean.getCoursesAllList();

        if (coursesList!=null) {
            view.addObject("allCourseList", coursesList);
        }

        return view;

    }

    @RequestMapping(value = "deleteCourse", method = RequestMethod.POST)
    public ModelAndView deleteCourse(@RequestParam(value = "courseId") Long courseId) {

        ModelAndView view = new ModelAndView("/user/allCourses");
        Courses course = courseBean.getCourseById(courseId);
        course.setActive(0);
        courseBean.updateCourse(course);

        List<Courses> coursesList = courseBean.getCoursesAllList();

        if (coursesList!=null) {
            view.addObject("allCourseList", coursesList);
        }

        return view;
    }

    @RequestMapping(value = "redirectEditCourse", method = RequestMethod.GET)
    public ModelAndView redirectEditCourse(@RequestParam(value = "courseId") Long courseId) {

        ModelAndView view = new ModelAndView("/user/editCourse");

        Courses course = courseBean.getCourseById(courseId);
        List<Groups> groupsList = courseBean.getGroupList();
        List<Groups> newGroupsList = new ArrayList<Groups>();

        if (groupsList!=null) {
            int count = 0;

            for (Groups g:groupsList) {
                if (!course.getGroups().isEmpty()) {

                for (Groups g1:course.getGroups()) {
                    if (!g.getName().equals(g1.getName())) {
                        count++;
                    }
                    if (count==course.getGroups().size()) {
                        newGroupsList.add(g);
                        view.addObject("otherGroups", newGroupsList);
                        view.addObject("groupsInCourse", course.getGroups());

                    }
                }

                } else if (course.getGroups().isEmpty()) {
                    newGroupsList = groupsList;
                    view.addObject("otherGroups", newGroupsList);
                }
            }
        }

        view.addObject("editedCourse", course);

        return view;
    }

    @RequestMapping(value = "editCourse", method = RequestMethod.POST)
    public ModelAndView editCourse(@RequestParam(value = "id") Long id,
                                   @RequestParam(value = "courseName") String courseName,
                                   @RequestParam(value = "courseDesc") String courseDesc ) {

        ModelAndView view = new ModelAndView("/user/allCourses");
        Courses course = courseBean.getCourseById(id);

        if (courseName!=null && courseDesc!=null) {
            course.setName(courseName);
            course.setDescription(courseDesc);
            courseBean.updateCourse(course);
        } else {
            view.setViewName("/user/editCourse");
        }

        List<Courses> coursesList = courseBean.getCoursesAllList();
        view.addObject("allCourseList", coursesList);

        return view;
    }

    @RequestMapping(value = "allLessons", method = RequestMethod.GET)
    public ModelAndView allLessons(@RequestParam(value = "courseId") Long courseId) {

        ModelAndView view = new ModelAndView("/user/allLessons");
        Courses course = courseBean.getCourseById(courseId);

        List<Lessons> lessonsList = course.getLessons();
        if (!lessonsList.isEmpty()) {
            view.addObject("allLessonsList", lessonsList);
            view.addObject("course", course);
        }

        return view;
    }

    @RequestMapping(value = "redirectAssignTeacher", method = RequestMethod.GET)
    public ModelAndView redirectAssignTeacher(@RequestParam(value = "courseId") Long courseId) {

        ModelAndView view = new ModelAndView("/user/teacherAndStudentList");
        Courses course = courseBean.getCourseById(courseId);
        List<Users> usersList = userBean.getUsersList();
        List<Users> teacherAndStudents = new ArrayList<Users>();

        if (!usersList.isEmpty()) {
            for (Users t:usersList) {
                if (t.getActive()==1) {
                    if (t.getRole_id().getId()==1 || t.getRole_id().getId()==3) {
                        teacherAndStudents.add(t);
                    }
                }
            }
        }
        view.addObject("course", course);
        view.addObject("teacherAndStudentList", teacherAndStudents);
        return view;
    }

    @RequestMapping(value = "addCourse4group", method = RequestMethod.POST)
    public ModelAndView addCourse4group(@RequestParam(value = "courseId") Long courseId,
                                        @RequestParam(value = "groupId") Long groupId
                                        ) {

        ModelAndView view = new ModelAndView("/user/allCourses");
        Courses course = courseBean.getCourseById(courseId);
        Groups group = courseBean.getGroupById(groupId);

        course.getGroups().add(group);
        courseBean.updateCourse(course);

        List<Courses> coursesList = courseBean.getCoursesAllList();
        if (coursesList!=null) {
            view.addObject("allCourseList", coursesList);
        }

        return view;
    }

    @RequestMapping(value = "deleteCourse4group", method = RequestMethod.POST)
    public ModelAndView deleteCourse4group(@RequestParam(value = "courseId") Long courseId,
                                        @RequestParam(value = "groupId") Long groupId) {

        ModelAndView view = new ModelAndView("/user/allCourses");

        Courses courses = courseBean.getCourseById(courseId);
        Groups group = courseBean.getGroupById(groupId);

        if (!group.getCourses().isEmpty()) {
            List<Courses> newCourses = new ArrayList<Courses>();
            for (Courses courses1:group.getCourses()) {
                if (!courses1.getName().equals(courses.getName())) {
                    newCourses.add(courses1);
                }
            }
            group.getCourses().clear();
            group.setCourses(newCourses);
        }

        courseBean.updateGroup(group);

        List<Courses> coursesList = courseBean.getCoursesAllList();

        if (coursesList!=null) {
            view.addObject("allCourseList", coursesList);
        }

        return view;
    }

    @RequestMapping(value = "redirectAddLesson", method = RequestMethod.GET)
    public ModelAndView redirectAddLesson() {
        ModelAndView view = new ModelAndView("/user/addLesson");
        List<Courses> coursesList = courseBean.getCoursesAllList();
        if (!coursesList.isEmpty()) {
            view.addObject("coursesList", coursesList);
        }
        return view;
    }

    @RequestMapping(value = "addLesson", method = RequestMethod.POST)
    public ModelAndView addLesson(HttpServletRequest request,
                                  @RequestParam(value = "titleLesson") String titleLesson,
                                  @RequestParam(value = "contentLesson") String contentLesson,
                                  @RequestParam(value = "courseName") String courseName
                                    ) {

        ModelAndView view = new ModelAndView("/user/adminHome");
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("adminSession");

        if (titleLesson!=null && contentLesson!=null && courseName!=null) {
            Courses course = courseBean.getCourseByName(courseName);
            Lessons newLesson = new Lessons(course, titleLesson, contentLesson, new Date(), 1);
            lessonBean.addLesson(newLesson);
            view.setViewName("/user/adminHome");
        }

        return view;
    }

    @RequestMapping(value = "deleteLesson", method = RequestMethod.POST)
    public ModelAndView deleteLesson(@RequestParam(value = "lessonId") Long lessonId,
                                     @RequestParam(value = "courseId") Long courseId) {

        ModelAndView view = new ModelAndView("/user/allLessons");
        Lessons lesson = lessonBean.getLessonById(lessonId);
        Courses courses = courseBean.getCourseById(courseId);

        lesson.setActive(0);
        lessonBean.updateLesson(lesson);

        List<Lessons> lessonsList = courses.getLessons();

        if (lessonsList!=null) {
            view.addObject("allLessonsList", lessonsList);
            view.addObject("course", courses);
        }

        return view;
    }

    @RequestMapping(value = "updateLesson", method = RequestMethod.POST)
    public ModelAndView updateLesson(@RequestParam(value = "lessonId") Long lessonId,
                                     @RequestParam(value = "courseId") Long courseId,
                                     @RequestParam(value = "lessonTitle") String lessonTitle,
                                     @RequestParam(value = "lessonContent") String lessonContent) {

        ModelAndView view = new ModelAndView("/user/allLessons");
        Lessons lesson = lessonBean.getLessonById(lessonId);
        Courses courses = courseBean.getCourseById(courseId);

        if (!lessonTitle.isEmpty()) {
            lesson.setTitle(lessonTitle);
        }
        if  (!lessonContent.isEmpty()) {
            lesson.setContent(lessonContent);
        }

        lessonBean.updateLesson(lesson);

        List<Lessons> lessonsList = courses.getLessons();

        if (lessonsList!=null) {
            view.addObject("allLessonsList", lessonsList);
            view.addObject("course", courses);
        }

        return view;
    }

    @RequestMapping(value = "redirectEditLesson", method = RequestMethod.GET)
    public ModelAndView redirectEditLesson(@RequestParam(value = "lessonId") Long lessonId,
                                           @RequestParam(value = "courseId") Long courseId) {

        ModelAndView view = new ModelAndView("/user/editLesson");
        Lessons lesson = lessonBean.getLessonById(lessonId);
        Courses courses = courseBean.getCourseById(courseId);

        if (lesson!=null && courses!=null) {
            view.addObject("lesson", lesson);
            view.addObject("course", courses);
        }

        return view;
    }


    @RequestMapping(value = "techMyGroups", method = RequestMethod.GET)
    public ModelAndView techMyGroups(HttpServletRequest request) {

        ModelAndView view = new ModelAndView("/user/techMyGroups");
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("teacherSession");

        List<Groups> groupsList = user.getGroups();

        if (groupsList!=null) {
            List<Groups> groupsList1 = new ArrayList<Groups>();
            for (Groups g:groupsList) {
                if (g.getActive()==1) {
                    groupsList1.add(g);
                }
            }
            view.addObject("allTechGroups", groupsList1);
        }

        return view;

    }

    @RequestMapping(value = "techMyCourses", method = RequestMethod.GET)
    public ModelAndView techMyCourses(HttpServletRequest request) {

        ModelAndView view = new ModelAndView("/user/techMyCourses");
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("teacherSession");

        List<Courses> coursesList = user.getCourses();

        if (coursesList!=null) {
            List<Courses> coursesList1 = new ArrayList<Courses>();
            for (Courses c:coursesList) {
                if (c.getActive()==1) {
                    coursesList1.add(c);
                }
            }
            view.addObject("allTechCourses", coursesList1);
        }

        return view;

    }


    @RequestMapping(value = "allStudentsInMyGroup", method = RequestMethod.GET)
    public ModelAndView allStudentsInMyGroup(HttpServletRequest request,
                                             @RequestParam(value = "groupId") Long groupdId
                                             ) {

        ModelAndView view = new ModelAndView("/user/allStudents");
        Groups group = courseBean.getGroupById(groupdId);

        List<Users> users = group.getUsers();
        List<Users> students = new ArrayList<Users>();
        if (!users.isEmpty()) {
            for (Users u:users) {
                if (u.getRole_id().getId()==3) {
                    students.add(u);
                }
            }
        }

        view.addObject("allStudents", students);

        return view;

    }


    @RequestMapping(value = "assignUser4Course", method = RequestMethod.POST)
    public ModelAndView assignUser4Course(@RequestParam(value = "courseId") Long courseId,
                                          @RequestParam(value = "userId") Long userId) {

        ModelAndView view = new ModelAndView("");
        Courses course = courseBean.getCourseById(courseId);
        Users user = userBean.getUserById(userId);

        if (user!=null && course!=null) {
            course.getUsers().add(user);
            courseBean.updateCourse(course);
        }




        return view;
    }

    @RequestMapping(value = "redirectNewPost", method = RequestMethod.GET)
    public ModelAndView redirectNewPost(HttpServletRequest request) {

        ModelAndView view = new ModelAndView("/user/addPost");


        return view;
    }

    @RequestMapping(value = "addPost", method = RequestMethod.POST)
    public ModelAndView addPost(HttpServletRequest request,
                                @RequestParam(value = "titlePost") String titlePost,
                                @RequestParam(value = "contentPost") String contentPost
                                ) {

        ModelAndView view = new ModelAndView("/user/addPost");
        HttpSession session = request.getSession();
        Users admin = (Users) session.getAttribute("adminSession");


        if (!titlePost.isEmpty() && !contentPost.isEmpty()) {
            newsBean.addNews(new News(admin.getCabinet_id(), admin, titlePost, contentPost, new Date(),1 ));
            view.setViewName("/user/adminHome");
        }

        return view;
    }


}
