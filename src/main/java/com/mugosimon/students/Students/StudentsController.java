package com.mugosimon.students.Students;


import com.mugosimon.students.Utilities.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/product")
@CrossOrigin(origins = "${cors.allowed-origins}", allowCredentials = "true")
public class StudentsController {
    @Autowired
    private StudentsService studentsService;

    public StudentsController(StudentsService studentsService) {
        this.studentsService = studentsService;
    }
    @PostMapping("/create")
    public EntityResponse create(@RequestBody  Students students){
        return studentsService.create(students);
    }

    @GetMapping("/fetchAll")
    public EntityResponse fetchAll(){
        return studentsService.fetchAll();
    }

    @GetMapping("/fetchById")
    public EntityResponse fetchById(@RequestParam("id") Long id){
        return studentsService.fetchById(id);
    }

    @PutMapping("/modify")
    public EntityResponse modify(@RequestBody Students students){
        return studentsService.modify(students);
    }

    @DeleteMapping("/delete")
    public EntityResponse delete(@RequestParam("id") Long id){
        return studentsService.delete(id);
    }

    @GetMapping("/fetchByStudentCode")
    public EntityResponse fetchByStudentCode(@RequestParam("studentCode") String studentCode){
        return studentsService.fetchByStudentCode(studentCode);
    }
}
