package JustFun.demo.Controller;


import JustFun.demo.Service.Service;
import JustFun.demo.entity.UserRole;
import JustFun.demo.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.sql.DataSource;
import javax.swing.tree.RowMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class controller {

    @Autowired
    private Service userService;

    @GetMapping("/detail")
    public List<Users> userDetails() {

        List<Users> userDetail = userService.userDetails();
        return userDetail;
    }

    @PostMapping("/searchUser")
    public Optional<Users> searchUser(@RequestBody Users userRequest) {

        Optional<Users> userDetail = userService.searchUser(userRequest.getId());
        List<UserRole> roles = null;


        if (userDetail.get().getUserRoles().isEmpty()) {
            System.out.println("user role is not available");
            userDetail.get().setUserRoles(roles);
        } else {
            System.out.println("user role is available");
        }
        return userDetail;
    }


    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        List<Users> dataList = userService.readDataFromExcel(file);
        return ResponseEntity.ok("File uploaded successfully!");
    }
}