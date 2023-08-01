package com.mugosimon.students.Students;

import com.mugosimon.students.Utilities.CodeGenerator;
import com.mugosimon.students.Utilities.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StudentsService {

    @Autowired
    private final StudentsRepository studentsRepository;
    @Autowired
    private final CodeGenerator codeGenerator;

    public StudentsService(StudentsRepository studentsRepository, CodeGenerator codeGenerator) {
        this.studentsRepository = studentsRepository;
        this.codeGenerator = codeGenerator;
    }

    public EntityResponse create(Students students) {
        EntityResponse entityResponse = new EntityResponse<>();
        try {
            log.info("====> create <====");

            // Check if a student with the same firstname, lastname, and middle-name already exists
            List<Students> existingStudents = studentsRepository.findByFirstNameAndLastNameAndMiddleName(
                    students.getFirstName(),
                    students.getLastName(),
                    students.getMiddleName()
            );

            if (!existingStudents.isEmpty()) {
                log.warn("====> student already exists <====");
                //
                entityResponse.setEntity(null);
                entityResponse.setMessage("Student with the same firstname, lastname, and middle-name already exists.");
                entityResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
                return entityResponse;
            }

            // If no duplicates found, proceed with creating the new student
            students.setFirstName(students.getFirstName());
            students.setMiddleName(students.getMiddleName());
            students.setLastName(students.getLastName());
            students.setStudentEmail(students.getStudentEmail());
            students.setCreatedOn(LocalDate.now());
            students.setCreatedBy("ADMIN");
            students.setCreated('Y');
            students.setStudentCode(codeGenerator.generateRandomCode(5));
            students.setStudentHome(students.getStudentHome());

            entityResponse.setEntity(studentsRepository.save(students));
            entityResponse.setMessage("Created Successfully");
            entityResponse.setStatusCode(HttpStatus.OK.value());

            return entityResponse;
        } catch (Exception exception) {
            exception.printStackTrace();
            log.error(">+++++> error while creating user <++++++<: " + exception.getLocalizedMessage());
            entityResponse.setMessage(HttpStatus.EXPECTATION_FAILED.getReasonPhrase());
            entityResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return entityResponse;
        }
    }

    public EntityResponse fetchAll() {
        EntityResponse entityResponse = new EntityResponse<>();
        try {
            log.info("====> fetch all <=====");
            List<Students> studentsList = studentsRepository.findAll();
            if (studentsList.size() > 0) {

                entityResponse.setEntity(studentsList.stream().filter(p -> p.getDeleted().equals('N')));
                entityResponse.setStatusCode(HttpStatus.OK.value());
                entityResponse.setMessage("Fetched Successfully");
                //
                return entityResponse;
            } else {

                log.warn(" >+++++> database might be empty <+++++<");
                entityResponse.setEntity(null);
                entityResponse.setMessage("database is empty");
                entityResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
                //
                return entityResponse;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            log.error(">+++++> error while fetching all users <++++++<: " + exception.getLocalizedMessage());
            entityResponse.setMessage(HttpStatus.EXPECTATION_FAILED.getReasonPhrase());
            entityResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return entityResponse;
        }
    }

    public EntityResponse fetchById(Long id) {
        EntityResponse entityResponse = new EntityResponse<>();
        try {
            log.info("====> fetch by id <====");
            Optional<Students> optionalStudents = studentsRepository.findById(id);
            if (optionalStudents.isPresent()) {
                entityResponse.setEntity(optionalStudents);
                entityResponse.setStatusCode(HttpStatus.OK.value());
                entityResponse.setMessage("Retrieved Successfully");
                return entityResponse;
            } else {

                log.warn(" >+++++> student not found <+++++<");
                entityResponse.setEntity(null);
                entityResponse.setMessage("database is empty");
                entityResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
                //
                return entityResponse;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            log.error(">+++++> error while fetching student by id <++++++<: " + exception.getLocalizedMessage());
            entityResponse.setMessage(HttpStatus.EXPECTATION_FAILED.getReasonPhrase());
            entityResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return entityResponse;
        }
    }

    public EntityResponse modify(Students students) {
        EntityResponse entityResponse = new EntityResponse<>();
        try {
            log.info(" ====> modifying students info <====");
            Optional<Students> optionalStudents = studentsRepository.findById(students.getId());
            if (optionalStudents.isPresent()) {
                Students existingDetails = optionalStudents.get();
                students.setFirstName(students.getFirstName());
                students.setLastName(students.getLastName());
                students.setMiddleName(students.getMiddleName());
                students.setStudentPhone(students.getStudentPhone());
                students.setStudentEmail(students.getStudentEmail());
                students.setStudentCode(existingDetails.getStudentCode());
                students.setCreatedBy(existingDetails.getCreatedBy());
                students.setCreatedOn(existingDetails.getCreatedOn());
                students.setCreated(existingDetails.getCreated());
                students.setStudentHome(students.getStudentHome());

                entityResponse.setMessage("Modified Successfully");
                entityResponse.setEntity(studentsRepository.save(students));
                entityResponse.setStatusCode(HttpStatus.OK.value());
                return entityResponse;

            } else {
                log.warn(" >+++++> student not found <+++++<");
                entityResponse.setEntity(null);
                entityResponse.setMessage("database is empty");
                entityResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
                //
                return entityResponse;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            log.error(">+++++> error while modifying student <++++++<: " + exception.getLocalizedMessage());
            entityResponse.setMessage(HttpStatus.EXPECTATION_FAILED.getReasonPhrase());
            entityResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return entityResponse;
        }
    }

    public EntityResponse delete(Long id) {
        EntityResponse entityResponse = new EntityResponse<>();
        try {
            Optional<Students> optionalStudents = studentsRepository.findById(id);
            if (optionalStudents.isPresent()) {
                studentsRepository.deleteById(id);

                entityResponse.setStatusCode(HttpStatus.OK.value());
                entityResponse.setMessage("Deleted Successfully");
                entityResponse.setEntity(null);
                //
                return entityResponse;
            } else {
                log.warn(" >+++++> student not found <+++++<");
                entityResponse.setEntity(null);
                entityResponse.setMessage("no record found with id: " + id);
                entityResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
                //
                return entityResponse;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            log.error(">+++++> error while deleting  student <++++++<: " + exception.getLocalizedMessage());
            entityResponse.setMessage(HttpStatus.EXPECTATION_FAILED.getReasonPhrase());
            entityResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return entityResponse;
        }
    }

    public EntityResponse fetchByStudentCode(String studentCode){
        EntityResponse entityResponse = new EntityResponse<>();
        try{
            log.info("====> fetch by student code <===");
            Optional<Students> optionalStudents = studentsRepository.findByStudentCode(studentCode);
            if (optionalStudents.isPresent()){
                entityResponse.setStatusCode(HttpStatus.OK.value());
                entityResponse.setMessage("Deleted Successfully");
                entityResponse.setEntity(optionalStudents);
                //
                return entityResponse;
            }else {
                log.warn(" >+++++> student not found <+++++<");
                entityResponse.setEntity(null);
                entityResponse.setMessage("no record found with student-code: " +studentCode);
                entityResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
                //
                return entityResponse;
            }
        }catch (Exception exception) {
            exception.printStackTrace();
            log.error(">+++++> error while deleting  student <++++++<: " + exception.getLocalizedMessage());
            entityResponse.setMessage(HttpStatus.EXPECTATION_FAILED.getReasonPhrase());
            entityResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return entityResponse;
        }
    }

}
