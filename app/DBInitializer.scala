import play.api._
import scalikejdbc._

class DBInitializer(app: Application) extends Plugin {

  DB autoCommit { implicit s =>
    try {
      SQL("select count(1) from user").map(rs => 1).single.apply()
    } catch { case e =>
      SQL("""
drop table user if exists;
create table user (
  id        bigint generated always as identity,
  email     varchar(255) not null,
  password  varchar(255) not null,
  name      varchar(255),
  birthday  date
);
      """).execute.apply()
       val insert = SQL("insert into user (id, email, password, name, birthday) values ({id}, {email}, {password}, {name}, {birthday})")
       insert.bindByName('id -> 1, 'email -> "seratch@gmail.com", 'password -> "password", 'name -> Some("Kazuhiro Sera"), 'birthday -> None).update.apply()
    }
  }

}

