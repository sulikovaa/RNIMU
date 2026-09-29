package entity;


public class WPDCriterias {
    private String eGuid;
    private String pForeignGuid;
    private String pDisciplineGuid;
    private String pGoal;
    private String pBlockCode;
    private String pStatus;
    private String isNeededToQualificationWork;
    private String isNeededToAttestation;
    private String eduProgramId;
    private String eduPlanId;
    private String baseProgramId;
    private Integer pLmsId;
    private String currentRatingParametersId;
    private String pNeedReassignAccesses;
    private String eDeletedAt;
    private String pLoadingSessionUid;
    private Boolean pNeedSendToLms;
    private Boolean pNeedImportRatingFromAos;
    private Boolean pRatingImportedFromAos;
    private Boolean pRatingImportError;
    private Integer groupsCount;
    private Boolean isElective;
    private Boolean isFacultative;
    private Boolean isPractice;
    private Boolean isGia;
    private Boolean isVkr;
    private Boolean isBrs;

    public WPDCriterias(String eGuid, String pForeignGuid, String pStatus, String eduProgramId, String eduPlanId, String eDeletedAt, Boolean isBrs) {
        this.eGuid = eGuid;
        this.pForeignGuid = pForeignGuid;
        this.pStatus = pStatus;
        this.eduProgramId = eduProgramId;
        this.eduPlanId = eduPlanId;
        this.eDeletedAt = eDeletedAt;
        this.isBrs = isBrs;
    }

    public String geteGuid() {
        return eGuid;
    }

    public String getpForeignGuid() {
        return pForeignGuid;
    }

    public String getpDisciplineGuid() {
        return pDisciplineGuid;
    }

    public String getpGoal() {
        return pGoal;
    }

    public String getpBlockCode() {
        return pBlockCode;
    }

    public String getpStatus() {
        return pStatus;
    }

    public String getIsNeededToQualificationWork() {
        return isNeededToQualificationWork;
    }

    public String getIsNeededToAttestation() {
        return isNeededToAttestation;
    }

    public String getEduProgramId() {
        return eduProgramId;
    }

    public String getEduPlanId() {
        return eduPlanId;
    }

    public String getBaseProgramId() {
        return baseProgramId;
    }

    public Integer getpLmsId() {
        return pLmsId;
    }

    public String getCurrentRatingParametersId() {
        return currentRatingParametersId;
    }

    public String getpNeedReassignAccesses() {
        return pNeedReassignAccesses;
    }

    public String geteDeletedAt() {
        return eDeletedAt;
    }

    public String getpLoadingSessionUid() {
        return pLoadingSessionUid;
    }

    public Boolean getpNeedSendToLms() {
        return pNeedSendToLms;
    }

    public Boolean getpNeedImportRatingFromAos() {
        return pNeedImportRatingFromAos;
    }

    public Boolean getpRatingImportedFromAos() {
        return pRatingImportedFromAos;
    }

    public Boolean getpRatingImportError() {
        return pRatingImportError;
    }

    public Integer getGroupsCount() {
        return groupsCount;
    }

    public Boolean getElective() {
        return isElective;
    }

    public Boolean getFacultative() {
        return isFacultative;
    }

    public Boolean getPractice() {
        return isPractice;
    }

    public Boolean getGia() {
        return isGia;
    }

    public Boolean getVkr() {
        return isVkr;
    }

    public Boolean getBrs() {
        return isBrs;
    }
}
