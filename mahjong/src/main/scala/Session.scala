class SessionAlreadyStartedException extends Exception("Session is already started")

class Session {

  private var isStarted: Boolean = false

  def start() {
    if (isStarted) {
      throw new SessionAlreadyStartedException()
    }

    isStarted = true

    //val game = new Game()

    //todo: game XD
  }
}
