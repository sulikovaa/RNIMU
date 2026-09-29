package entity;
import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "education_plan")
public class EduPlanItemHiber {
    @Id
    @Column(name = "e_guid", nullable = false)
    private UUID guid;

    @Column(name = "p_status")
    private String status;

    @Column(name = "p_title")
    private String title;

    @Column(name = "p_department_guid")
    private UUID departmentGuid;

    @Column(name = "p_specialization_guid")
    private UUID specializationGuid;

    @Column(name = "p_standard_type_guid")
    private UUID standardTypeGuid;

    @Column(name = "p_study_form_guid")
    private UUID studyFormGuid;

    @Column(name = "p_is_foreign_language")
    private Boolean isForeignLanguage;

    @Column(name = "p_is_two_diplomas")
    private Boolean isTwoDiplomas;

    @Column(name = "edu_program_id")
    private UUID eduProgramId;

    @Column(name = "p_year_end")
    private Integer yearEnd;

    @Column(name = "p_year_start")
    private Integer yearStart;

    @Column(name = "e_deleted_at")
    private OffsetDateTime deletedAt;

    @Column(name = "p_foreign_guid", unique = true)
    private UUID foreignGuid;

    @Column(name = "p_faculty_guid")
    private UUID facultyGuid;

    @Column(name = "p_has_final_exams")
    private Boolean hasFinalExams;

    @Column(name = "p_has_final_qualifying_work")
    private Boolean hasFinalQualifyingWork;

    @Column(name = "p_is_sng")
    private Boolean isSng;

    @Column(name = "p_is_joint_form")
    private Boolean isJointForm;

    @Column(name = "p_doc_number")
    private String docNumber;

    @Column(name = "p_education_level_guid")
    private UUID educationLevelGuid;

    @Column(name = "p_archived")
    private Boolean archived;

    @Column(name = "is_medical")
    private Boolean medical;

    public String getGuid() {
        return String.valueOf(guid);
    }
    public String getForeignGuid() {
        return String.valueOf(foreignGuid);
    }
}
