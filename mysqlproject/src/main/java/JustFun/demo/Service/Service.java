package JustFun.demo.Service;

import JustFun.demo.entity.UserRole;
import JustFun.demo.entity.Users;
import JustFun.demo.repository.UsersRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class Service {
@Autowired
private UsersRepository repo;

@Autowired
private JustFun.demo.repository.UserRole userRoleRepo;
    public List<Users> userDetails()
    {

     List<Users> userDetails=null;
     userDetails=repo.findAll();
     return userDetails ;
    }


    public Optional<Users> searchUser(Long userId)
    {

        Optional<Users> userDetails=null;
        userDetails=repo.findById(userId);

        return userDetails ;
    }


    public List<Users> readDataFromExcel(MultipartFile file) {


        List<Users> dataList = new ArrayList<>();
        List<UserRole> dataRole = new ArrayList<>();


        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next(); // Skip header row

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Users data = new Users();
                UserRole roleData=new UserRole();

        //        data.setId(Long.valueOf(12));
                data.setName(row.getCell(0).toString());
                data.setEmail(row.getCell(1).toString());
                data.setMobileNo(row.getCell(2).toString());

                roleData.setUserRoles(row.getCell(3).toString());
                roleData.setDesignation(row.getCell(4).toString());
                roleData.setUsers(data);


//                dataRole.add(roleData);
                dataList.add(data);
            }
            repo.saveAll(dataList);


        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        }

        return dataList;
    }
}

