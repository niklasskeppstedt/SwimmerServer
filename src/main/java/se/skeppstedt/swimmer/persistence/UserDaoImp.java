package se.skeppstedt.swimmer.persistence;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface UserDaoImp {
	  @SqlUpdate("create table user (username varchar(20) primary key, password varchar(20), swimmers varchar(300))")
	  void createUserTable();

	  @SqlUpdate("insert into user (username, password) values (:username, :password)")
	  void insert(@Bind("username") String username, @Bind("password") String password);

	  @SqlQuery("select password from user where username = :username")
	  String findNameById(@Bind("username") String username);

	  @SqlQuery("select swimmers from user where username = :username")
	  String findSwimmers(@Bind("username") String username);

	  /**
	   * close with no args is used to close the connection
	   */
	  void close();
}