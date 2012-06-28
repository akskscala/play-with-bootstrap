package views.helper.bootstrap

import views.html.helper.bootstrap._
import views.html.helper.FieldConstructor

// https://github.com/playframework/Play20/tree/master/framework/src/play/src/main/scala/views/helper

object BasicField {
  implicit val bootstrapConstructor = FieldConstructor(basicField.f)
}

