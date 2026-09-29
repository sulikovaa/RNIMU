package dao.edu_plan;

import entity.EduPlanItem;
import util.Util;

import java.sql.*;
import java.util.UUID;

public class EducationPlanImplement implements EducationPlanDao {
    private final Connection connection = Util.getjdbcConnection();
    @Override
    public EduPlanItem getEduPlanByGuid(String eduPlanGuid) {
        String sql = "SELECT * FROM education_plan WHERE e_guid = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, UUID.fromString(eduPlanGuid));
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return new EduPlanItem(
                        resultSet.getString("e_guid"),
                        resultSet.getString("edu_program_id"),
                        resultSet.getString("p_doc_number"),
                        resultSet.getString("e_deleted_at"),
                        resultSet.getString("p_foreign_guid"),
                        resultSet.getInt("p_year_start"),
                        resultSet.getInt("p_year_end")
                );
            }
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }
}
