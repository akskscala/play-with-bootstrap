package views.bootstrap

import views.html.bootstrap._
import views.html.helper.FieldConstructor

object BasicField {
  implicit val bootstrapConstructor = FieldConstructor(basicField.f)
}

