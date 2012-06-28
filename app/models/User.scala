package models

import scalikejdbc._
import org.joda.time.{LocalDate}

case class User(
  id: Long, 
  email: String, 
  password: String, 
  name: Option[String] = None, 
  birthday: Option[LocalDate] = None) { 

  def save()(implicit session: DBSession = AutoSession): User = User.save(this)(session)

  def destroy()(implicit session: DBSession = AutoSession): Unit = User.delete(this)(session)

}

object User {

  val tableName = "USER"

  object columnNames {
    val id = "ID"
    val email = "EMAIL"
    val password = "PASSWORD"
    val name = "NAME"
    val birthday = "BIRTHDAY"
    val all = Seq(id, email, password, name, birthday)
  }

  val * = {
    import columnNames._
    (rs: WrappedResultSet) => User(
      id = rs.long(id),
      email = rs.string(email),
      password = rs.string(password),
      name = Option(rs.string(name)),
      birthday = Option(rs.date(birthday)).map(_.toLocalDate))
  }

  def authenticate(email: String, password: String)(implicit session: DBSession = AutoSession): Option[User] = {
    SQL("""SELECT * FROM USER WHERE EMAIL = {email} and PASSWORD = {password}""")
      .bindByName('email -> email, 'password -> password).map(*).single.apply()
  }

  def find(id: Long)(implicit session: DBSession = AutoSession): Option[User] = {
    SQL("""SELECT * FROM USER WHERE ID = {id}""")
      .bindByName('id -> id).map(*).single.apply()
  }

  def findAll()(implicit session: DBSession = AutoSession): List[User] = {
    SQL("""SELECT * FROM USER""").map(*).list.apply()
  }

  def countAll()(implicit session: DBSession = AutoSession): Long = {
    SQL("""SELECT COUNT(1) FROM USER""").map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: String, params: (Symbol, Any)*)(implicit session: DBSession = AutoSession): List[User] = {
    SQL("""SELECT * FROM USER WHERE """ + where)
      .bindByName(params:_*).map(*).list.apply()
  }

  def countBy(where: String, params: (Symbol, Any)*)(implicit session: DBSession = AutoSession): Long = {
    SQL("""SELECT count(1) FROM USER WHERE """ + where)
      .bindByName(params:_*).map(rs => rs.long(1)).single.apply().get
  }

  def create(
    email: String,
    password: String,
    name: Option[String] = None,
    birthday: Option[LocalDate] = None)(implicit session: DBSession = AutoSession): User = {
    val generatedKey = SQL("""
      INSERT INTO USER (
        EMAIL,
        PASSWORD,
        NAME,
        BIRTHDAY
      ) VALUES (
        {email},
        {password},
        {name},
        {birthday}
      )
      """)
      .bindByName(
        'email -> email,
        'password -> password,
        'name -> name,
        'birthday -> birthday
      ).updateAndReturnGeneratedKey.apply()
    User(
      id = generatedKey, 
      email = email,
      password = password,
      name = name,
      birthday = birthday)
  }

  def save(m: User)(implicit session: DBSession = AutoSession): User = {
    SQL("""
      UPDATE 
        USER
      SET 
        ID = {id},
        EMAIL = {email},
        PASSWORD = {password},
        NAME = {name},
        BIRTHDAY = {birthday}
      WHERE 
        ID = {id}
      """)
      .bindByName(
        'id -> m.id,
        'email -> m.email,
        'password -> m.password,
        'name -> m.name,
        'birthday -> m.birthday
      ).update.apply()
    m
  }

  def delete(m: User)(implicit session: DBSession = AutoSession): Unit = {
    SQL("""DELETE FROM USER WHERE ID = {id}""")
      .bindByName('id -> m.id).update.apply()
  }

}
