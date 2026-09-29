import dao.wpd.WorkingProgramOfDisciplineImplement;
import entity.WPDCriterias;

public class Main {
    public static void main(String[] args) {
        WorkingProgramOfDisciplineImplement  workingProgramOfDisciplineImplement = new WorkingProgramOfDisciplineImplement();
        workingProgramOfDisciplineImplement.insertIntoWPD(new WPDCriterias("022f6739-6a58-43c5-931a-42a2cbb5885a", null, "TEST", null, null,null, null ));
    }}
