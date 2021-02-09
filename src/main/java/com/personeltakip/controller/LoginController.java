package com.personeltakip.controller;

import com.personeltakip.model.*;
import com.personeltakip.services.*;
import com.sun.net.httpserver.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

@Scope("session")
@Controller

//@RequestMapping
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    ManagerService managerService;

    @Autowired
    ProjectService projectService;

    @GetMapping({"/"})

    public String home() {
        return "employee_login";
    }

    @GetMapping({"/employee_login"})

    public String EmployeeGiris() {

        return "employee_login";
    }


    @GetMapping({"/manager_login"})

    public String managerGiris() {

        return "employee_login";

    }

/*

    @GetMapping({"/studentOgrenci_list"})

    public String studentOgrenciGiris() {

        return "studentOgrenci_list";

    }
*/


    @RequestMapping(value="/employee_login", method=RequestMethod.POST)

    public ModelAndView employeeGirisKontrol(HttpSession request, @ModelAttribute("user") User user){

        User user2 = getUserEmployee(user);
        request.setAttribute("user", user2);

        if (user2 != null){
            Employee employee = getEmployee(user2.getId());
            request.setAttribute("employee", employee);
        }

        if (user2 == null){
            return new ModelAndView("redirect:/employee_login");
        }
        else
            return new ModelAndView("redirect:/employee/list");
    }

    private User getUserEmployee(User user){

        for (User user2: userService.getAllUsers())
        {

            for(Employee employee : employeeService.getAllEmployees())
            {

                if (user2.getUsername().equals(user.getUsername()) && user2.getPassword().equals(user.getPassword())
                        && employee.getUser().getUsername().equals(user.getUsername())
                        && employee.getUser().getPassword().equals(user.getPassword())
                )
                {

                    return user2;

                }
            }
        }

        return null;

    }

    private Employee getEmployee(int user_id){

        for (Employee employee: employeeService.getAllEmployees()){

            if(employee.getUser().getId() == user_id){
                return employee;
            }
        }
        return null;
    }

    // Staj bilgisi tablosundan student_id'si bizim gönderdiğimiz student_id'ye eşit olan student'ın
    // staj birim id'si alınır. Sonra bu id ile staj birim tablosundaki eşleşen kaydın şirket bilgisinin idsi elde edilir
    // bu id yoluyla şirket bilgisi bulunur.
    // Eğer student'idsi bulunamazsa boş kayıt döndürür.
/*
    private  Sirketbilgisi getSirket(int student_id){

//        try {
        for (Stajbilgisi stajbilgisi: stajbilgisiService.getAllStajbilgisi()){

            if (stajbilgisi.getEmployee() != null && stajbilgisi.getEmployee().getId() == student_id){

                int stajbirim_id = stajbilgisi.getStajbirim().getId();

                for (Stajbirim stajbirim: stajbirimService.getAllStajbirim()){

                    if(stajbirim.getId() == stajbirim_id){

                        for(Sirketbilgisi sirketbilgisi: sirketbilgisiService.getAllSirketbilgisi()){

                            if(sirketbilgisi.getId() == stajbirim.getSirketbilgisi().getId()){

                                return sirketbilgisi;

                            }
                        }
                    }
                }
            }
        }
//        }catch(Exception e){
//            System.out.println("Catch - Hata Alındı.");
//        }
        return null;
    }

    private  Stajbirim getStajbirim(int student_id){

        for (Stajbilgisi stajbilgisi: stajbilgisiService.getAllStajbilgisi()){

            if (stajbilgisi.getEmployee().getId() == student_id){

                int stajbirim_id = stajbilgisi.getStajbirim().getId();

                for (Stajbirim stajbirim: stajbirimService.getAllStajbirim()){

                    if(stajbirim.getId() == stajbirim_id){
                        return stajbirim;
                    }
                }
            }
        }
        return null;
    }

    private  Stajbilgisi getStajbilgisi(int student_id){

        for (Stajbilgisi stajbilgisi: stajbilgisiService.getAllStajbilgisi()){

            if (stajbilgisi.getEmployee().getId() == student_id){
                return stajbilgisi;
            }
        }
        return null;
    }*/


    @RequestMapping(value="/manager_login", method=RequestMethod.POST)

    public ModelAndView managerGirisKontrol(HttpSession request, @ModelAttribute("user") User user, @ModelAttribute("user") Manager manager){

        for (User user2: userService.getAllUsers())
        {
            for(Manager manager2: managerService.getAllManagers())
            {

                if (
                        user2.getUsername().equals(user.getUsername()) && user2.getPassword().equals(user.getPassword())
                                && manager2.getUser().getUsername().equals(user.getUsername())
                                && manager2.getUser().getPassword().equals(user.getPassword())
                )

                {

                    request.setAttribute("user", user2);
                    request.setAttribute("manager", manager2);
                    return new ModelAndView("redirect:/user/list");

                }
            }
        }
        return new ModelAndView("redirect:/employee_login");
    }
}