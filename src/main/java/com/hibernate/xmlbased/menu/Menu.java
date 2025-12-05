package com.hibernate.xmlbased.menu;

import java.util.Scanner;

import com.hibernate.xmlbased.dao.DepartmentDAO;
import com.hibernate.xmlbased.dao.DeveloperDAO;
import com.hibernate.xmlbased.dao.UserDAO;
import com.hibernate.xmlbased.model.User;

public class Menu {

    User authUser;
    protected Scanner in = new Scanner(System.in);

    protected static DeveloperDAO developerDAO = new DeveloperDAO();
    protected static UserDAO userDAO = new UserDAO();
    protected static DepartmentDAO departmentDAO = new DepartmentDAO();

    public void menu(User authUser) {
        this.authUser = authUser;
        System.out.printf(
                "\nHi, %s. Your role: %s\n",
                authUser.getDeveloper().getName(),
                authUser.getUserRole().getRole()
        );
    }
}
