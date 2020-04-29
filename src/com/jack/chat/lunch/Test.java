package com.jack.chat.lunch;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/4/26 23:29
 */

public class Test {
    /*public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DbUtil.getConnection();
            preparedStatement = connection.prepareStatement("select avatar from user where user_id = ?");
            preparedStatement.setString(1, "1981530505");
            resultSet = preparedStatement.executeQuery();
            InputStream in = null;
            while (resultSet.next()) {
                in = resultSet.getBinaryStream("avatar");
                System.out.println(in.available());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }*/
}
