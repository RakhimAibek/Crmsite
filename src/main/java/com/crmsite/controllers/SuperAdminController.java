package com.crmsite.controllers;

import com.crmsite.beans.SuperAdminBean;
import com.crmsite.dao.Cabinets;
import com.crmsite.dao.Roles;
import com.crmsite.dao.SuperAdmins;
import com.crmsite.dao.Users;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
public class SuperAdminController {

    @Autowired
    private SuperAdminBean superAdminBean;

    @RequestMapping(value = "superAdminAuth", method = RequestMethod.GET)
    public ModelAndView superAdminAuth(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        SuperAdmins superAdmin = superAdminBean.getSuperAdminByLogin(login, password);
        ModelAndView modelView = new ModelAndView("superAdminAuth");

        if (superAdmin!=null) {
            if (superAdmin.getActive()==1) {
                HttpSession session = request.getSession();
                session.setAttribute("superUserData", superAdmin);
                modelView.setViewName("superAdminHome");
            }
        } else {
            modelView.setViewName("superAdminAuth");
        }

        return modelView;

    }


    @RequestMapping(value = "superAdminProfile", method = RequestMethod.GET)
    public ModelAndView superAdminProfile() {

        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("superAdminProfile");

        return modelView;

    }

    @RequestMapping(value = "editSuperAdminProfile", method = RequestMethod.POST)
    public ModelAndView editSuperAdminProfile(HttpServletRequest request, HttpServletResponse response) {

            ModelAndView modelView = new ModelAndView("editSuperAdminProfile");
            String login = request.getParameter("newLogin");
            String password = request.getParameter("newPassword");
            String oldPassword = request.getParameter("oldPassword");

            HttpSession session = request.getSession();
            SuperAdmins superAdmin = (SuperAdmins) session.getAttribute("superUserData");

            if (oldPassword.equals(superAdmin.getPassword())) {
                if (!(login.isEmpty())) {
                    superAdmin.setLogin(login);

                }
                if (!(password.isEmpty())) {
                    superAdmin.setPassword(password);
                }
                session.setAttribute("superUserData", superAdmin);
                superAdminBean.deleteAndEditSuperAdmin(superAdmin);
                List<SuperAdmins> superAdminsList = superAdminBean.getSuperAdminList();
                modelView.setViewName("superAdminProfile");
                modelView.addObject("allUsers", superAdminsList);
            }
            return modelView;
    }

    @RequestMapping(value = "redirectEditSuperAdminProfile", method = RequestMethod.GET)
    public ModelAndView redirectEditSuperAdminProfile(HttpServletRequest request) {
        ModelAndView modelView = new ModelAndView("editSuperAdminProfile");

        return modelView;
    }

    @RequestMapping(value = "redirectAddSuperAdmin", method = RequestMethod.POST)
    public ModelAndView redirectAddSuperAdmin() {
        ModelAndView modelView = new ModelAndView("addSuperAdmin");
        return modelView;
    }

    @RequestMapping(value = "addSuperAdmin", method = RequestMethod.POST)
    public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam(value = "login") String login,
                        @RequestParam(value = "password") String password
                        ) {

        ModelAndView view = new ModelAndView("superAdminProfile");
        superAdminBean.addSuperAdmin(new SuperAdmins(login, password, 1));

        List<SuperAdmins> superAdmins = superAdminBean.getSuperAdminList();
        view.addObject("allUsers", superAdmins);
        return view;
    }

    @RequestMapping(value = "allSuperAdmins", method = RequestMethod.GET)
    public ModelAndView getAllSuperAdmins() {
        ModelAndView view = new ModelAndView("superAdminProfile");
        List<SuperAdmins> superAdminsList = superAdminBean.getSuperAdminList();
        view.addObject("allUsers", superAdminsList);

        return view;
    }

    @RequestMapping(value = "deactivateSuperAdmin", method = RequestMethod.POST)
    public ModelAndView deactivateSuperAdmin(HttpServletRequest request,
                                             @RequestParam(value = "superId") Long id
                                            ) {

        ModelAndView view = new ModelAndView("superAdminProfile");

        SuperAdmins superAdmin = superAdminBean.getSuperAdminById(id);
        superAdmin.setActive(0);
        superAdminBean.deleteAndEditSuperAdmin(superAdmin);

        List<SuperAdmins> superAdmins = superAdminBean.getSuperAdminList();
        view.addObject("allUsers", superAdmins);

        return view;
    }

    @RequestMapping(value = "redirectConfigSuperAdmin", method = RequestMethod.GET)
    public ModelAndView redirectConfigSuperAdmin(HttpServletRequest request) {
        ModelAndView modelView = new ModelAndView("configSuperAdmins");
        String superId = request.getParameter("superId");
        HttpSession session = request.getSession();
        session.setAttribute("superId", superId);
        return modelView;
    }

    @RequestMapping(value = "configSuperAdmins", method = RequestMethod.GET)
    public ModelAndView configSuperAdmins(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("superAdminProfile");

        String newLogin = request.getParameter("newLogin");
        String newPassword = request.getParameter("newPassword");

        HttpSession session = request.getSession();
        String superId = (String) session.getAttribute("superId");

        SuperAdmins superAdmin = superAdminBean.getSuperAdminById(Long.parseLong(superId));

        if (superAdmin!=null) {
            if (!newLogin.isEmpty()) {
                superAdmin.setLogin(newLogin);
            }
            if (!newPassword.isEmpty()) {
                superAdmin.setPassword(newPassword);
            }
            superAdminBean.deleteAndEditSuperAdmin(superAdmin);
        }

        List<SuperAdmins> superAdminsList = superAdminBean.getSuperAdminList();
        view.addObject("allUsers", superAdminsList);

        return view;
    }

    @RequestMapping(value = "addCabinet", method = RequestMethod.POST)
    public ModelAndView addCabinet(HttpServletRequest request,
                                   @RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "description", required = false) String description
                                   ) {

        ModelAndView view = new ModelAndView("addCabinet");

        if (name!=null && description!=null) {
            if (!name.isEmpty() && !description.isEmpty()) {
                Cabinets existCabinet = superAdminBean.getCabinetByName(name);
                if (existCabinet!=null) {
                    System.out.println("Такая компания уже существует");
                } else {
                    superAdminBean.addCabinet(new Cabinets(name, description, 1));
                    name = null;
                    description = null;
                }
            }
        }

        return view;
    }


    @RequestMapping(value = "allCabinets", method = RequestMethod.GET)
    public ModelAndView getAllCabinets() {

        ModelAndView view = new ModelAndView("editCabinet");
        List<Cabinets> cabinetsList = superAdminBean.getAllCabinetsList();
        view.addObject("allCabinets", cabinetsList);

        return view;
    }


    @RequestMapping(value = "deleteCabinet", method = RequestMethod.POST)
    public ModelAndView deleteCabinet(@RequestParam(value = "cabinetId") Long id) {

        ModelAndView view = new ModelAndView("editCabinet");

        Cabinets cabinet = superAdminBean.getCabinetById(id);
        cabinet.setActive(0);
        superAdminBean.updateCabinet(cabinet);

        List<Cabinets> cabinetsList = superAdminBean.getAllCabinetsList();
        view.addObject("allCabinets", cabinetsList);

        return view;
    }

    @RequestMapping(value = "editCabinet", method = RequestMethod.POST)
    public ModelAndView editCabinet(@RequestParam(value = "cabinetId") Long id) {

        ModelAndView view = new ModelAndView("updateCabinet");
        Cabinets cabinet = superAdminBean.getCabinetById(id);
        view.addObject("editedCabinet", cabinet);

        return view;
    }

    @RequestMapping(value = "updateCabinet", method = RequestMethod.POST)
    public ModelAndView updateCabinet(@RequestParam(value = "cabinetId") Long id,
                                      @RequestParam(value = "cabinetName") String name,
                                      @RequestParam(value = "cabinetDesc") String desc
                                     ) {

        ModelAndView view = new ModelAndView("editCabinet");

        Cabinets cabinet = superAdminBean.getCabinetById(id);

        if (!name.isEmpty()) {
            if (!cabinet.getName().equals(name)) {
                cabinet.setName(name);

            }
        }

        if (!desc.isEmpty()) {
            if (!cabinet.getDescription().equals(desc)) {
                cabinet.setDescription(desc);
            }
        }

        superAdminBean.updateCabinet(cabinet);

        List<Cabinets> cabinetsList = superAdminBean.getAllCabinetsList();
        view.addObject("allCabinets", cabinetsList);

        return view;
    }

    @RequestMapping(value = "newAdmin", method = RequestMethod.GET)
    public ModelAndView newAdmin() {

        ModelAndView view = new ModelAndView("addAdmin");
        List<Cabinets> cabinetsList = superAdminBean.getAllCabinetsList();
        view.addObject("allCabinets", cabinetsList);

        return view;

    }


    @RequestMapping(value = "addAdmin", method = RequestMethod.POST)
    public ModelAndView addAdmin(@RequestParam(value = "login") String login,
                                 @RequestParam(value = "password") String password,
                                 @RequestParam(value = "cabinetName") String cabinetName,
                                 @RequestParam(value = "name") String name,
                                 @RequestParam(value = "surname") String surname
                                 ) {

        ModelAndView view = new ModelAndView("addAdmin");
        List<Cabinets> cabinetsList = superAdminBean.getAllCabinetsList();
        view.addObject("allCabinets", cabinetsList);

        if (!login.isEmpty() && !password.isEmpty() && !cabinetName.isEmpty() && !name.isEmpty() && !surname.isEmpty()) {

            Cabinets cabinet = superAdminBean.getCabinetByName(cabinetName);

            if (cabinet!=null) {
                Roles role = new Roles();
                role.setId(2);
                role.setName("Admin");
                superAdminBean.addUsers(new Users(cabinet, login, password, role, name, surname, 1));


                List<Users> users = superAdminBean.getUsersList();
                List<Users> usersList = new ArrayList<Users>();
                for (Users u: users) {
                    if (u.getRole_id().getId()==2) {

                        usersList.add(u);

                    }
                }
                view.setViewName("editAdmin");
                view.addObject("allAdmins", usersList);
            }

        }


        return view;
    }

    @RequestMapping(value = "editAdmin", method = RequestMethod.POST)
    public ModelAndView editAdmin() {

        ModelAndView view = new ModelAndView("editAdmin");
        List<Users> admins = superAdminBean.getUsersList();

        List<Users> adminUsers = new ArrayList<Users>();
        for (Users u: admins) {
            if (u.getRole_id().getId()==2) {
                adminUsers.add(u);
            }
        }
        view.addObject("allAdmins", adminUsers);

        return view;

    }

    @RequestMapping(value = "deleteAdmin", method = RequestMethod.POST)
    public ModelAndView deleteAdmin(@RequestParam(value = "adminId") Long id) {

        ModelAndView view = new ModelAndView("editAdmin");

        Users admin = superAdminBean.getUsersById(id);
        admin.setActive(0);
        superAdminBean.updateUser(admin);

        List<Users> admins = superAdminBean.getUsersList();
        List<Users> adminsUser = new ArrayList<Users>();
        for (Users u: admins) {
            if (u.getRole_id().getId()==2) {

                adminsUser.add(u);
            }
        }
        view.addObject("allAdmins", adminsUser);

        return view;
    }

    @RequestMapping(value = "redirectUpdatePage", method = RequestMethod.POST)
    public ModelAndView updateAdmin(@RequestParam(value = "adminId") Long id) {

        ModelAndView view = new ModelAndView("updateAdmin");
        List<Cabinets> cabinetsList = superAdminBean.getAllCabinetsList();
        Users user = superAdminBean.getUsersById(id);
        view.addObject("editedUser", user);
        view.addObject("allCabinets", cabinetsList);

        return view;

    }

    @RequestMapping(value = "updateAdmin", method = RequestMethod.POST)
    public ModelAndView updateAdmin(@RequestParam(value = "adminId") Long id,
                                    @RequestParam(value = "login") String login,
                                    @RequestParam(value = "password") String password,
                                    @RequestParam(value = "name") String name,
                                    @RequestParam(value = "surname") String surname,
                                    @RequestParam(value = "cabinetName") String cabinetName
                                    ) {

        ModelAndView view = new ModelAndView("editAdmin");
        Users user = superAdminBean.getUsersById(id);

        if (!user.getLogin().equals(login)) {
            user.setLogin(login);
        }
        if (!user.getPassword().equals(password)) {
            user.setPassword(password);
        }
        if (!user.getName().equals(name)) {
            user.setName(name);
        }
        if (!user.getSurname().equals(surname)) {
            user.setSurname(surname);
        }
        if (!user.getCabinet_id().getName().equals(cabinetName)) {
            Cabinets cabinet = superAdminBean.getCabinetByName(cabinetName);
            user.setCabinet_id(cabinet);
        }

        superAdminBean.updateUser(user);

        List<Users> admins = superAdminBean.getUsersList();
        List<Users> adminUsers = new ArrayList<Users>();
        for (Users u: admins) {
            if (u.getRole_id().getId()==2) {
                adminUsers.add(u);
            }
        }
        view.addObject("allAdmins", adminUsers);

        return view;

    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {

        ModelAndView view = new ModelAndView("/user/loginUser");
        HttpSession session = request.getSession();

        if (session.getAttribute("superUserData")!=null) {

            session.removeAttribute("superUserData");
            view.setViewName("superAdminAuth");

        } else if (session.getAttribute("adminSession")!=null) {

            session.removeAttribute("adminSession");
            view.setViewName("/user/loginUser");

        } else if (session.getAttribute("studentSession")!=null) {

            session.removeAttribute("studentSession");
            view.setViewName("/user/loginUser");

        } else if (session.getAttribute("teacherSession")!=null) {

            session.removeAttribute("teacherSession");
            view.setViewName("/user/loginUser");

        }

        return view;

    }


}
