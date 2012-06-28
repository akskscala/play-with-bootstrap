package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._

import models._
import views._

object Application extends Controller {
  
  def index = Action {
    Ok(html.index("Your new application is ready."))
  }

  val loginForm = Form(
    mapping(
      "email" -> email,
      "password" -> nonEmptyText
    )(User.authenticate)(_.map(user => (user.email, "")))
      .verifying("Invalid email or password", res => res.isDefined)
  )

  def loginInput = Action {
    Ok(html.login.input(loginForm))
  }  

  def loginSubmit = Action { implicit req => 
    loginForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.login.input(formWithErrors)),
      user => Ok(html.index("Hello, " + user.get.email + "!"))
    ) 
  }

  def loginInputWithoutHelper = Action {
    Ok(html.login.inputWithoutHelper(loginForm))
  }  

  def loginSubmitWithoutHelper = Action { implicit req => 
    loginForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.login.inputWithoutHelper(formWithErrors)),
      user => Ok(html.index("Hello, " + user.get.email + "!"))
    ) 
  }

}
