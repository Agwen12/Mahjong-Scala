object Application {

  def main(args: Array[String]) {
    val session = new Session()

    startSession(session)
  }

  private def startSession(session: Session) {
    try {
      session.start
    } catch {
      case e: Exception => e.printStackTrace
    }
  }
}
