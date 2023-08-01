package com.mugosimon.students.Students;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentsRepository
        extends JpaRepository<Students,Long> {
    @Query("SELECT s FROM Students s WHERE s.firstName = :firstName AND s.lastName = :lastName AND s.middleName = :middleName")
    List<Students> findByFirstNameAndLastNameAndMiddleName(String firstName, String lastName, String middleName);

    Optional<Students> findByStudentCode(String studentCode);
}
