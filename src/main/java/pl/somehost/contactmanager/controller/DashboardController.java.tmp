package pl.somehost.contactmanager.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import pl.somehost.contactmanager.domain.User;


@Controller
public class DashboardController {


    @GetMapping(path = "/dashboard", produces = "application/html")
    public String getDashboard(@AuthenticationPrincipal User user, ModelMap model)
    {
        model.put("user", user);
        // This will generate exception because user has no admin role
        //List<User> userList = adminService.retriveAllUsers();
        return "dashboard";
    }
}
